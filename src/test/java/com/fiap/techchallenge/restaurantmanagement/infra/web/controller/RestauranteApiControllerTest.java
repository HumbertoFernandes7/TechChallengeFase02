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
import com.fiap.techchallenge.restaurantmanagement.infra.web.mapper.RestauranteWebMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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

@WebMvcTest(RestauranteApiController.class)
@ActiveProfiles("test")
public class RestauranteApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FindUsuarioUseCase findUsuarioUseCase;

    @Autowired
    private FindEnderecoUseCase findEnderecoUseCase;

    @Autowired
    private CreateRestauranteUseCase createRestauranteUseCase;

    @Autowired
    private FindRestauranteUseCase findRestauranteUseCase;

    @Autowired
    private ListRestauranteUseCase listRestauranteUseCase;

    @Autowired
    private UpdateRestauranteUseCase updateRestauranteUseCase;

    @Autowired
    private DeleteRestauranteUseCase deleteRestauranteUseCase;

    @TestConfiguration
    static class TestConfig {
        // Mock de todas as dependências do RestauranteApiController
        @Bean
        public CreateRestauranteUseCase createRestauranteUseCase() { return mock(CreateRestauranteUseCase.class); }
        @Bean
        public FindRestauranteUseCase findRestauranteUseCase() { return mock(FindRestauranteUseCase.class); }
        @Bean
        public UpdateRestauranteUseCase updateRestauranteUseCase() { return mock(UpdateRestauranteUseCase.class); }
        @Bean
        public DeleteRestauranteUseCase deleteRestauranteUseCase() { return mock(DeleteRestauranteUseCase.class); }
        @Bean
        public ListRestauranteUseCase listRestauranteUseCase() { return mock(ListRestauranteUseCase.class); }
        @Bean
        public FindUsuarioUseCase findUsuarioUseCase() { return mock(FindUsuarioUseCase.class); }
        @Bean
        public FindEnderecoUseCase findEnderecoUseCase() { return mock(FindEnderecoUseCase.class); }

        // Mappers necessários (instâncias reais)
        @Bean
        public RestauranteWebMapper restauranteWebMapper() { return new RestauranteWebMapper(modelMapper()); }
        @Bean
        public ModelMapper modelMapper() { return new ModelMapper(); }
    }

    //Post
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

    //Get
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