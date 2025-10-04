package com.fiap.techchallenge.restaurantmanagement.core.domain;

import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.InvalidPasswordException;
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
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> {
            new Usuario(1L, "Humberto Fernandes", "humberto@email.com", TipoUsuario.ADMIN, "senha");
        });

        assertEquals("A senha deve ter 8 ou mais caracteres", exception.getMessage());
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
    void quando_chamarChangePasswordComSenhaCurta_deveLancarExcecao() {
        // Preparação
        Usuario usuario = new Usuario(1L, "Humberto Fernandes", "humberto@email.com", TipoUsuario.ADMIN, "12345678");

        // Ação
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> {
            usuario.changePassword("123");
        });

        // Verificação
        assertEquals("A senha deve ter 8 ou mais caracteres", exception.getMessage());
    }
}