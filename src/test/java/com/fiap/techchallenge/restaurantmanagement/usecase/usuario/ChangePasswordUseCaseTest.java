package com.fiap.techchallenge.restaurantmanagement.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.ChangePasswordUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.NovaSenhaRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChangePasswordUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private ChangePasswordUseCase changePasswordUseCase;

    @Test
    void quando_senhaForemIguais_deveAlterarSenhaComSucesso(){
        // Preparação
        Long usuarioId = 1L;
        Usuario usuarioEncontrado = new Usuario(usuarioId, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");

        NovaSenhaRequest novaSenhaRequest = new NovaSenhaRequest();
        novaSenhaRequest.setNovaSenha("novaSenha_12345678");
        novaSenhaRequest.setRepetirNovaSenha("novaSenha_12345678");

        when(usuarioGateway.findById(usuarioId)).thenReturn(usuarioEncontrado);
        when(usuarioGateway.update(any(Usuario.class))).thenReturn(null);

        //Ação
        changePasswordUseCase.execute(usuarioId, novaSenhaRequest);

        //Verificação
        ArgumentCaptor<Usuario> capturadorDeUsuario = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioGateway, times(1)).update(capturadorDeUsuario.capture());

        Usuario usuarioSalvo = capturadorDeUsuario.getValue();
        assertEquals("novaSenha_12345678", usuarioSalvo.getSenha());
    }

    @Test
    void quando_senhasForemDiferentes_deveRetornarMensagemDeErro(){
        // Preparação
        Long usuarioId = 1L;
        NovaSenhaRequest novaSenha = new NovaSenhaRequest();
        novaSenha.setNovaSenha("novaSenha_12345678");
        novaSenha.setRepetirNovaSenha("repetirNovaSenha_12345678");

        // Ação
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            changePasswordUseCase.execute(usuarioId, novaSenha);
        });

        // Verificação
        assertEquals("Nova senha e Repetir nova Senha não são iguais", exception.getMessage());

        // Garante que, se as senhas não batem, NENHUMA interação com o gateway acontece
        verifyNoInteractions(usuarioGateway);
    }
}