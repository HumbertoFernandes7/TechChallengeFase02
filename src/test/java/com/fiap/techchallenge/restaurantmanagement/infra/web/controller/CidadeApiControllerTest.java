package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.CidadeNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CidadeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateCidadeUseCase createCidadeUseCase;
    @MockitoBean
    private DeleteCidadeUseCase deleteCidadeUseCase;
    @MockitoBean
    private FindCidadeUseCase findCidadeUseCase;
    @MockitoBean
    private ListCidadeUseCase listCidadeUseCase;
    @MockitoBean
    private UpdateCidadeUseCase updateCidadeUseCase;

    @Test
    void quando_listarTodasCidades_deveRetornarStatusOk() throws Exception {
        // Preparação
        when(listCidadeUseCase.execute()).thenReturn(Arrays.asList(
                new Cidade(1L, "São Paulo", null),
                new Cidade(2L, "Rio de Janeiro", null)
        ));

        // Ação e Verificação
        mockMvc.perform(get("/cidade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("São Paulo")));
    }

    @Test
    void quando_buscarCidadePorIdExistente_deveRetornarStatusOk() throws Exception {
        // Preparação
        when(findCidadeUseCase.get(1L)).thenReturn(new Cidade(1L, "São Paulo", null));

        // Ação e Verificação
        mockMvc.perform(get("/cidade/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("São Paulo")));
    }

    @Test
    void quando_buscarCidadeInexistente_deveRetornarNotFound() throws Exception {
        // Preparação
        when(findCidadeUseCase.get(99L)).thenThrow(new CidadeNotFoundException("Cidade não encontrada"));

        // Ação e Verificação
        mockMvc.perform(get("/cidade/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void quando_inserirCidade_deveRetornarStatusCreated() throws Exception {
        // Preparação
        Estado estado = new Estado(1L, "São Paulo", "SP");
        Cidade cidadeParaSalvar = new Cidade(null, "Campinas", estado);
        Cidade cidadeSalva = new Cidade(1L, "Campinas", estado);
        when(createCidadeUseCase.saveCidade(any(Cidade.class))).thenReturn(cidadeSalva);

        // Ação e Verificação
        mockMvc.perform(post("/cidade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cidadeParaSalvar)))
                .andExpect(status().isCreated());
    }

    @Test
    void quando_atualizarCidade_deveRetornarStatusCreated() throws Exception {
        // Preparação
        Estado estado = new Estado(1L, "São Paulo", "SP");
        Cidade cidadeParaAtualizar = new Cidade(1L, "Campinas", estado);

        when(updateCidadeUseCase.execute(any(Cidade.class))).thenReturn(cidadeParaAtualizar);

        // Ação e Verificação
        mockMvc.perform(put("/cidade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cidadeParaAtualizar)))
                .andExpect(status().isCreated());
    }

    @Test
    void quando_deletarCidadeExistente_deveRetornarNoContent() throws Exception {
        // Preparação
        doNothing().when(deleteCidadeUseCase).delete(1L);

        // Ação e Verificação
        mockMvc.perform(delete("/cidade/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void quando_deletarCidadeInexistente_deveRetornarNotFound() throws Exception {
        // Preparação
        doThrow(new CidadeNotFoundException("Cidade não encontrada")).when(deleteCidadeUseCase).delete(99L);

        // Ação e Verificação
        mockMvc.perform(delete("/cidade/99"))
                .andExpect(status().isNotFound());
    }
}