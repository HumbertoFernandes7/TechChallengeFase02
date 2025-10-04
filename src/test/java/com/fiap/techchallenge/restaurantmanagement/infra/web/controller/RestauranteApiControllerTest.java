package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco.FindEnderecoUseCase;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante.*;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.FindUsuarioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.BusinessRuleException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}