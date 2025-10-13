package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.EnderecoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EnderecoApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateEnderecoUseCase createEnderecoUseCase;
    @MockitoBean
    private DeleteEnderecoUseCase deleteEnderecoUseCase;
    @MockitoBean
    private FindEnderecoUseCase findEnderecoUseCase;
    @MockitoBean
    private ListEnderecoUseCase listEnderecoUseCase;
    @MockitoBean
    private UpdateEnderecoUseCase updateEnderecoUseCase;

    @Test
    void quando_listarTodosEnderecos_deveRetornarStatusOk() throws Exception {
        // Preparação
        List<Endereco> enderecos = Arrays.asList(
                new Endereco(1L, "Rua A", "1", null, "Bairro A", "11111-111", null),
                new Endereco(2L, "Rua B", "2", null, "Bairro B", "22222-222", null)
        );
        when(listEnderecoUseCase.findEndereco()).thenReturn(enderecos);

        // Ação e Verificação
        mockMvc.perform(get("/endereco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].logradouro", is("Rua A")));
    }

    @Test
    void quando_buscarEnderecoPorIdExistente_deveRetornarStatusOk() throws Exception {
        // Preparação
        when(findEnderecoUseCase.findEndereco(1L)).thenReturn(new Endereco(1L, "Rua A", "1", null, "Bairro A", "11111-111", null));

        // Ação e Verificação
        mockMvc.perform(get("/endereco/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.logradouro", is("Rua A")));
    }

    @Test
    void quando_buscarEnderecoInexistente_deveRetornarNotFound() throws Exception {
        // Preparação
        when(findEnderecoUseCase.findEndereco(99L)).thenThrow(new EnderecoNotFoundException("Endereço não encontrado"));

        // Ação e Verificação
        mockMvc.perform(get("/endereco/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void quando_inserirEndereco_deveRetornarStatusCreated() throws Exception {
        // Preparação
        Estado estado = new Estado(1L, "SP", "São Paulo");
        Cidade cidade = new Cidade(1L, "São Paulo", estado);
        Endereco enderecoParaSalvar = new Endereco(null, "Rua Nova", "123", null, "Bairro Novo", "33333-333", cidade);
        Endereco enderecoSalvo = new Endereco(1L, "Rua Nova", "123", null, "Bairro Novo", "33333-333", cidade);

        when(createEnderecoUseCase.saveEndereco(any(Endereco.class))).thenReturn(enderecoSalvo);

        // Ação e Verificação
        mockMvc.perform(post("/endereco")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enderecoParaSalvar)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void quando_atualizarEndereco_deveRetornarStatusCreated() throws Exception {
        // Preparação
        Endereco enderecoParaAtualizar = new Endereco(1L, "Rua Atualizada", "456", null, "Bairro", "12345-678", null);
        when(updateEnderecoUseCase.execute(any(Endereco.class))).thenReturn(enderecoParaAtualizar);

        // Ação e Verificação
        mockMvc.perform(put("/endereco")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enderecoParaAtualizar)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void quando_deletarEnderecoExistente_deveRetornarNoContent() throws Exception {
        // Preparação
        doNothing().when(deleteEnderecoUseCase).deleteEndereco(1L);

        // Ação e Verificação
        mockMvc.perform(delete("/endereco/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void quando_deletarEnderecoInexistente_deveRetornarNotFound() throws Exception {
        // Preparação
        doThrow(new EnderecoNotFoundException("Endereço não encontrado")).when(deleteEnderecoUseCase).deleteEndereco(99L);

        // Ação e Verificação
        mockMvc.perform(delete("/endereco/99"))
                .andExpect(status().isNotFound());
    }
}