package com.fiap.techchallenge.restaurantmanagement.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.FindUsuarioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindUsuarioUseCasesTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private FindUsuarioUseCase findUsuarioUseCase;

    @Test
     void quando_buscarUsuarioPorId_deveRetornarSucesso(){
        // Preparação
        Long usuarioId = 1L;
        Usuario usuarioEsperado = new Usuario(usuarioId, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");
        when(usuarioGateway.findById(usuarioId)).thenReturn(usuarioEsperado);

        // Ação
        Usuario resultado = findUsuarioUseCase.execute(usuarioId);

        // Verificação
        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getId());
        assertEquals("Humberto", resultado.getNome());
        assertEquals("humberto@email.com", resultado.getEmail());
        assertEquals(TipoUsuario.ADMIN, resultado.getTipo());
        assertEquals("12345678", resultado.getSenha());
    }

    @Test
    void quando_buscarUsuarioPorIdInexistente_deveRetornarErro(){
        // Preparação
        Long idInexistente = 100L;
        String messagemErro = "Usuário não encontrado";
        when(usuarioGateway.findById(idInexistente)).thenThrow(new EntityNotFoundException(messagemErro));

        // Ação e Verificação
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            findUsuarioUseCase.execute(idInexistente);
        });

        // verifica se a mensagem de erro está correta
        assertEquals(messagemErro, exception.getMessage());
    }
}
