package com.fiap.techchallenge.restaurantmanagement.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    void quando_criarUsuarioRetornaSucesso(){
        assertDoesNotThrow(() -> {
            new Usuario(1L, "Humberto Fernandes", "humberto@email.com", TipoUsuario.ADMIN, "12345678");
        });
    }

    @Test
    void quando_criarUsuarioComSenhaInvalida_deveLancarExcecao() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(1L, "Humberto Fernandes", "humberto@email.com", TipoUsuario.ADMIN, "senha");
        });

        assertEquals("A senha deve ter no mínimo 8 caracteres", exception.getMessage());
    }

    @Test
    void quando_chamarChangePasswordComSenhaValida_deveAtualizarASenha() {
        // Preparação
        Usuario usuario = new Usuario(1L, "Humberto Fernandes", "humberto@email.com", TipoUsuario.ADMIN, "12345678");

        // Ação
        assertDoesNotThrow(() -> {
            usuario.changePassword("novaSenhaValida456");
        });

        // Verificação
        assertEquals("novaSenhaValida456", usuario.getSenha());
    }

    @Test
    void quando_chamarChangePasswordComSenhaCurta_deveLancarIllegalArgumentException() {
        // Preparação
        Usuario usuario = new Usuario(1L, "Humberto Fernandes", "humberto@email.com", TipoUsuario.ADMIN, "12345678");

        // Ação
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuario.changePassword("123");
        });

        // Verificação
        assertEquals("A senha deve ter no mínimo 8 caracteres", exception.getMessage());
    }
}