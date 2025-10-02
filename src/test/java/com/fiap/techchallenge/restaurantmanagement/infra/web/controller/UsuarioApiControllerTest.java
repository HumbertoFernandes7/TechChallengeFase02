package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco.FindEnderecoUseCase;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.UsuarioRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.mapper.UsuarioWebMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(UsuarioApiController.class)
@ActiveProfiles("test")
public class UsuarioApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CreateUsuarioUseCase createUsuarioUseCase;

    @Autowired
    private ListUsuarioUseCase listUsuarioUseCase;

    @TestConfiguration
    static class TestConfig {
        // Para cada dependência do nosso controller, criamos um @Bean que retorna um mock.
        @Bean
        public CreateUsuarioUseCase createUsuarioUseCase() {
            return mock(CreateUsuarioUseCase.class);
        }

        @Bean
        public FindUsuarioUseCase findUsuarioUseCase() {
            return mock(FindUsuarioUseCase.class);
        }

        @Bean
        public UpdateUsuarioUseCase updateUsuarioUseCase() {
            return mock(UpdateUsuarioUseCase.class);
        }

        @Bean
        public DeleteUsuarioUseCase deleteUsuarioUseCase() {
            return mock(DeleteUsuarioUseCase.class);
        }

        @Bean
        public ListUsuarioUseCase listUsuarioUseCase() {
            return mock(ListUsuarioUseCase.class);
        }

        @Bean
        public ChangePasswordUseCase changePasswordUseCase() {
            return mock(ChangePasswordUseCase.class);
        }

        @Bean
        public UsuarioWebMapper usuarioWebMapper() {
            return new UsuarioWebMapper(modelMapper());
        }

        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        public FindEnderecoUseCase findEnderecoUseCase() {
            return mock(FindEnderecoUseCase.class);
        }
    }

    @Test
    void quando_cadastrarUsuario_deveRetornarSucesso() throws Exception {
        // Preparação
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setNome("Humberto");
        usuarioRequest.setEmail("humberto@email.com");
        usuarioRequest.setTipo(TipoUsuario.ADMIN);
        usuarioRequest.setSenha("12345678");
        usuarioRequest.setEnderecoId(1L);

        Usuario usuarioSalvo = new Usuario(1L, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");

        when(createUsuarioUseCase.execute(any(Usuario.class), anyLong())).thenReturn(usuarioSalvo);

        // Ação e Verificação
        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Humberto"))
                .andExpect(jsonPath("$.email").value("humberto@email.com"));

    }

    @Test
    void quando_criarUsuarioDadosInvalidos_deveRetornarStatusBadRequest() throws Exception {
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        // usuarioRequest.setNome("Humberto"); Deixa o nome nulo
        usuarioRequest.setEmail("humberto@email.com");
        usuarioRequest.setTipo(TipoUsuario.ADMIN);
        usuarioRequest.setSenha("12345678");
        usuarioRequest.setEnderecoId(1L);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void quando_listarTodosUsuarios_deveRetornarStatusOkEListaDeUsuarios() throws Exception {
        // Preparação
        List<Usuario> listaDeUsuarios = Arrays.asList(
                new Usuario(1L, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678"),
                new Usuario(2L, "Maria", "maria@email.com", TipoUsuario.CLIENTE, "12345678")
        );

        // Configuração do Mock: Quando o listUsuarioUseCase for chamado, retorne a lista preparada.
        when(listUsuarioUseCase.execute()).thenReturn(listaDeUsuarios);

        // Ação e Verificação
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("Humberto")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nome", is("Maria")));
    }

    @Test
    void quando_listarTodosUsuariosSemResultados_deveRetornarStatusOkEListaVazia() throws Exception {
        // Preparação
        when(listUsuarioUseCase.execute()).thenReturn(Collections.emptyList());

        // Ação e Verificação
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}