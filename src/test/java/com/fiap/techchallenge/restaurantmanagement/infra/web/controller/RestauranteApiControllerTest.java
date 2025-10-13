package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco.FindEnderecoUseCase;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante.*;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.FindUsuarioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.BusinessRuleException;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.EnderecoNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.RestaurantNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.RestauranteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RestauranteApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FindUsuarioUseCase findUsuarioUseCase;

    @MockitoBean
    private FindEnderecoUseCase findEnderecoUseCase;

    @MockitoBean
    private CreateRestauranteUseCase createRestauranteUseCase;

    @MockitoBean
    private FindRestauranteUseCase findRestauranteUseCase;

    @MockitoBean
    private ListRestauranteUseCase listRestauranteUseCase;

    @MockitoBean
    private UpdateRestauranteUseCase updateRestauranteUseCase;

    @MockitoBean
    private DeleteRestauranteUseCase deleteRestauranteUseCase;

    // Post
    @Test
    void quando_criarRestauranteComDadosValidos_deveRetornarStatusCreated() throws Exception {
        // Preparação
        RestauranteRequest restauranteRequest = new RestauranteRequest();
        restauranteRequest.setNome("Restaurante Teste");
        restauranteRequest.setTipoCozinha("Brasileira");
        restauranteRequest.setHorarioAbertura(LocalTime.of(12, 0));
        restauranteRequest.setHorarioFechamento(LocalTime.of(22, 0));
        restauranteRequest.setDonoRestauranteId(1L);
        restauranteRequest.setEnderecoId(1L);

        Usuario usuario = new Usuario(1L, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");

        when(findUsuarioUseCase.execute(1L)).thenReturn(usuario);
        when(findEnderecoUseCase.findEndereco(1L)).thenReturn(new Endereco());

        Restaurante restauranteSalvo = new Restaurante(1L, "Restaurante Teste", new Endereco(), "Brasileira", LocalTime.of(12, 0), LocalTime.of(22, 0), usuario, null);
        when(createRestauranteUseCase.execute(any(Restaurante.class), any(Endereco.class))).thenReturn(restauranteSalvo);

        // Ação e Verificação
        mockMvc.perform(post("/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Restaurante Teste"))
                .andExpect(jsonPath("$.tipoCozinha").value("Brasileira"))
                .andExpect(jsonPath("$.horarioAbertura").value("12:00:00"))
                .andExpect(jsonPath("$.horarioFechamento").value("22:00:00"))
                .andExpect(jsonPath("$.donoRestaurante.id").value(1L));
    }

    @Test
    void quando_criarRestauranteComDadosInvalidos_deveRetornarStatusBadRequest() throws Exception {
        // Preparação
        RestauranteRequest restauranteRequest = new RestauranteRequest();
        //restauranteRequest.setNome("Restaurante Teste");
        restauranteRequest.setTipoCozinha("Brasileira");
        restauranteRequest.setHorarioAbertura(LocalTime.of(12, 0));
        restauranteRequest.setHorarioFechamento(LocalTime.of(22, 0));
        restauranteRequest.setDonoRestauranteId(1L);
        restauranteRequest.setEnderecoId(1L);

        // Ação
        mockMvc.perform(post("/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void quando_criarRestauranteComDonoDoTipoCliente_deveRetornarBadRequest() throws Exception {
        // Preparação
        RestauranteRequest restauranteRequest = new RestauranteRequest();
        restauranteRequest.setNome("Restaurante Inválido");
        restauranteRequest.setTipoCozinha("Qualquer");
        restauranteRequest.setHorarioAbertura(LocalTime.of(10, 0));
        restauranteRequest.setHorarioFechamento(LocalTime.of(20, 0));
        restauranteRequest.setDonoRestauranteId(2L);
        restauranteRequest.setEnderecoId(1L);

        Usuario usuarioCliente = new Usuario(2L, "Apenas Cliente", "cliente@email.com", TipoUsuario.CLIENTE, "12345678");
        when(findUsuarioUseCase.execute(2L)).thenReturn(usuarioCliente);
        when(findEnderecoUseCase.findEndereco(1L)).thenReturn(new Endereco());

        when(createRestauranteUseCase.execute(any(Restaurante.class), any(Endereco.class))).thenThrow(new BusinessRuleException("Usuario do tipo CLIENTE não pode ser dono de um restaurante"));

        //Ação
        mockMvc.perform(post("/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequest)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void quando_criarRestauranteComEnderecoInexistente_deveRetornarStatusNotFound() throws Exception {
        // Preparação
        RestauranteRequest restauranteRequest = new RestauranteRequest();
        restauranteRequest.setNome("Restaurante Teste");
        restauranteRequest.setTipoCozinha("Brasileira");
        restauranteRequest.setHorarioAbertura(LocalTime.of(12, 0));
        restauranteRequest.setHorarioFechamento(LocalTime.of(22, 0));
        restauranteRequest.setDonoRestauranteId(1L);
        restauranteRequest.setEnderecoId(99L);

        when(findUsuarioUseCase.execute(1L)).thenReturn(new Usuario(1L, "Humberto", "humberto@email.com", TipoUsuario.DONO_RESTAURANTE, "12345678"));
        when(findEnderecoUseCase.findEndereco(99L)).thenThrow(new EnderecoNotFoundException("Endereço não encontrado"));

        // Ação
        mockMvc.perform(post("/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequest)))
                .andExpect(status().isNotFound());

    }

    // Get
    @Test
    void quando_buscarRestaurantePorIdExistente_deveRetornarStatusOk() throws Exception{
        // Preparação
        Long restauranteId = 1L;
        Restaurante restaurante = new Restaurante(restauranteId, "Restaurante Encontrado", new Endereco(), "Japonesa", LocalTime.now(), LocalTime.now(), new Usuario(), null);

        when(findRestauranteUseCase.execute(restauranteId)).thenReturn(restaurante);

        //Ação e Verificação
        mockMvc.perform(get("/restaurante/{id}", restauranteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Restaurante Encontrado")));
    }

    @Test
    void quando_buscarRestaurantePorIdInexistente_deveRetornarStatusNotFound() throws Exception{
        // Preparação
        Long restauranteId = 99L;
        when(findRestauranteUseCase.execute(restauranteId)).thenThrow(new RestaurantNotFoundException("Restaurante não encontrado"));

        //Ação e Verificação
        mockMvc.perform(get("/restaurante/{id}", restauranteId))
                .andExpect(status().isNotFound());
    }

    @Test
    void quando_listarTodosRestaurantes_deveRetornarStatusOkEListaDeRestaurantes() throws Exception{
        // Preparação
        List<Restaurante> restaurantesEncontrados = Arrays.asList(
                new Restaurante(1L, "Restaurante A", null, "Brasileira", null, null, null, null),
                new Restaurante(2L, "Restaurante B", null, "Italiana", null, null, null, null));

        when(listRestauranteUseCase.execute()).thenReturn(restaurantesEncontrados);

        // Ação e Verificação
        mockMvc.perform(get("/restaurante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("Restaurante A")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nome", is("Restaurante B")));
    }

    @Test
    void quando_listarTodosRestaurantesSemResultados_deveRetornarStatusOkEListaVazia() throws Exception{
        // Preparação
        when(listRestauranteUseCase.execute()).thenReturn(Collections.emptyList());

        // Ação e Verificação
        mockMvc.perform(get("/restaurante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // Put
    @Test
    void quando_atualizarRestauranteExistente_deveRetornarStatusOk() throws Exception{
        // Preparação
        Long restauranteId = 1L;
        RestauranteRequest restauranteRequest = new RestauranteRequest();
        restauranteRequest.setNome("Restaurante Atualizado");
        restauranteRequest.setTipoCozinha("Italiana");
        restauranteRequest.setHorarioAbertura(LocalTime.of(12, 0));
        restauranteRequest.setHorarioFechamento(LocalTime.of(22, 0));
        restauranteRequest.setDonoRestauranteId(1L);
        restauranteRequest.setEnderecoId(1L);

        when(findUsuarioUseCase.execute(1L)).thenReturn(new Usuario());
        Restaurante restauranteAtualizado = new Restaurante(restauranteId, "Nome Atualizado", null, "Italiana", null, null, null, null);
        when(updateRestauranteUseCase.execute(eq(restauranteId), any(Restaurante.class))).thenReturn(restauranteAtualizado);

        mockMvc.perform(put("/restaurante/{id}", restauranteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Nome Atualizado")))
                .andExpect(jsonPath("$.tipoCozinha", is("Italiana")));

    }

    @Test
    void quando_atualizarRestauranteInexistente_deveRetornarStatusNotFound() throws Exception {
        // Preparação
        Long idInexistente = 99L;
        RestauranteRequest request = new RestauranteRequest();
        request.setNome("Nome Qualquer");
        request.setTipoCozinha("Qualquer");
        request.setHorarioAbertura(LocalTime.now());
        request.setHorarioFechamento(LocalTime.now());
        request.setDonoRestauranteId(1L);
        request.setEnderecoId(1L);

        when(findUsuarioUseCase.execute(1L)).thenReturn(new Usuario());

        when(updateRestauranteUseCase.execute(eq(idInexistente), any(Restaurante.class)))
                .thenThrow(new RestaurantNotFoundException("Restaurante com o id " + idInexistente + " não encontrado."));

        // Ação e Verificação
        mockMvc.perform(put("/restaurante/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void quando_atualizarRestauranteComDadosInvalidos_deveRetornarStatusBadRequest() throws Exception{
        // Preparação
        Long restauranteId = 1L;
        RestauranteRequest restauranteRequest = new RestauranteRequest();
        //restauranteRequest.setNome("Restaurante Atualizado");
        restauranteRequest.setTipoCozinha("Italiana");
        restauranteRequest.setHorarioAbertura(LocalTime.of(12, 0));
        restauranteRequest.setHorarioFechamento(LocalTime.of(22, 0));
        restauranteRequest.setDonoRestauranteId(1L);
        restauranteRequest.setEnderecoId(1L);

        // Ação
        mockMvc.perform(put("/restaurante/{id}", restauranteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequest)))
                .andExpect(status().isBadRequest());
    }

    // Delete
    @Test
    void quando_deletarRestauranteExistente_deveRetornarStatusNoContent() throws Exception {
        // Preparação
        Long restauranteId = 1L;

        doNothing().when(deleteRestauranteUseCase).execute(restauranteId);

        // Ação e Verificação
        mockMvc.perform(delete("/restaurante/{id}", restauranteId))
                .andExpect(status().isNoContent());
    }

    @Test
    void quando_deletarRestauranteInexistente_deveRetornarStatusNotFound() throws Exception {
        // Preparação
        Long idInexistente = 99L;

        doThrow(new RestaurantNotFoundException("Restaurante não encontrado")).when(deleteRestauranteUseCase).execute(idInexistente);

        // Ação e Verificação
        mockMvc.perform(delete("/restaurante/{id}", idInexistente))
                .andExpect(status().isNotFound());
    }
}