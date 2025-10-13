package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.estado.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.EstadoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EstadoApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateEstadoUseCase createEstadoUseCase;

    @MockitoBean
    private DeleteEstadoUseCase deleteEstadoUseCase;

    @MockitoBean
    private FindEstadoUseCase findEstadoUseCase;

    @MockitoBean
    private ListEstadoUseCase listEstadoUseCase;

    @MockitoBean
    private UpdateEstadoUseCase updateEstadoUseCase;

    @Test
    void quando_listarTodosEstados_deveRetornarStatusOk() throws Exception {
        // Preparação
        List<Estado> estados = Arrays.asList(
                new Estado(1L, "São Paulo", "SP"),
                new Estado(2L, "Rio de Janeiro", "RJ")
        );
        when(listEstadoUseCase.execute()).thenReturn(estados);

        // Ação e Verificação
        mockMvc.perform(get("/estado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("São Paulo")))
                .andExpect(jsonPath("$[1].sigla", is("RJ")));
    }

    @Test
    void quando_buscarEstadoPorIdExistente_deveRetornarStatusOk() throws Exception {
        // Preparação
        when(findEstadoUseCase.execute(1L)).thenReturn(new Estado(1L, "São Paulo", "SP"));

        // Ação e Verificação
        mockMvc.perform(get("/estado/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("São Paulo")));
    }

    @Test
    void quando_buscarEstadoPorIdInexistente_deveRetornarNotFound() throws Exception {
        // Preparação
        when(findEstadoUseCase.execute(99L)).thenThrow(new EstadoNotFoundException("Estado não encontrado"));

        // Ação e Verificação
        mockMvc.perform(get("/estado/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void quando_inserirEstadoComDadosValidos_deveRetornarStatusCreated() throws Exception {
        // Preparação
        Estado estadoParaSalvar = new Estado(null, "Minas Gerais", "MG");
        Estado estadoSalvo = new Estado(1L, "Minas Gerais", "MG");
        when(createEstadoUseCase.saveEstado(any(Estado.class))).thenReturn(estadoSalvo);

        // Ação e Verificação
        mockMvc.perform(post("/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estadoParaSalvar)))
                .andExpect(status().isCreated());
    }

    @Test
    void quando_atualizarEstado_deveRetornarStatusCreated() throws Exception {
        // Preparação
        Estado estadoParaAtualizar = new Estado(1L, "Paraná", "PR");
        when(updateEstadoUseCase.execute(any(Estado.class))).thenReturn(estadoParaAtualizar);

        // Ação e Verificação
        mockMvc.perform(put("/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estadoParaAtualizar)))
                .andExpect(status().isCreated());
    }

    @Test
    void quando_deletarEstadoExistente_deveRetornarNoContent() throws Exception {
        // Preparação
        doNothing().when(deleteEstadoUseCase).delete(1L);

        // Ação e Verificação
        mockMvc.perform(delete("/estado/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void quando_deletarEstadoInexistente_deveRetornarNotFound() throws Exception {
        // Preparação
        doThrow(new EstadoNotFoundException("Estado não encontrado")).when(deleteEstadoUseCase).delete(99L);

        // Ação e Verificação
        mockMvc.perform(delete("/estado/99"))
                .andExpect(status().isNotFound());
    }
}