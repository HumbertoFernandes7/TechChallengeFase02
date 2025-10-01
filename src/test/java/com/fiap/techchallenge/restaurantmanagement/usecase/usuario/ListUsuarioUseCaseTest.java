package com.fiap.techchallenge.restaurantmanagement.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.ListUsuarioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private ListUsuarioUseCase listUsuarioUseCase;

    @Test
    void quando_listarUsuariosRetornaListaComUsuariosCadastrados(){
        // Preparação
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1L, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678"),
                new Usuario(2L, "Danilo", "danilo@email.com", TipoUsuario.ADMIN, "12345678")
        );

        when(usuarioGateway.findAll()).thenReturn(usuarios);

        // Ação
        List<Usuario> resultado = listUsuarioUseCase.execute();

        // Verificação
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Humberto", resultado.get(0).getNome());
        assertEquals("humberto@email.com", resultado.get(0).getEmail());
        assertEquals(TipoUsuario.ADMIN, resultado.get(0).getTipo());
        assertEquals("12345678", resultado.get(0).getSenha());

        assertEquals("Danilo", resultado.get(1).getNome());
        assertEquals("danilo@email.com", resultado.get(1).getEmail());
        assertEquals(TipoUsuario.ADMIN, resultado.get(1).getTipo());
        assertEquals("12345678", resultado.get(1).getSenha());
    }

    @Test
    void quando_listarUsuariosRetornaListaVazia(){
        // Preparação
        when(usuarioGateway.findAll()).thenReturn(Collections.emptyList());

        // Ação
        List<Usuario> resultado = listUsuarioUseCase.execute();

        // Verificação
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }
}
