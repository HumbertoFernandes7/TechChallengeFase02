package com.fiap.techchallenge.restaurantmanagement.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.UpdateUsuarioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.UsuarioRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private UpdateUsuarioUseCase updateUsuarioUseCase;

    @Test
    void quando_executarComDadosValidos_deveAtualizarComSucesso() {
        // Preparação
        Usuario usuarioEncontrado = new Usuario(1L, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");

        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setNome("Humberto atualizado");
        usuarioRequest.setEmail("humbertoatualizado@email.com");
        usuarioRequest.setTipo(TipoUsuario.ADMIN);
        usuarioRequest.setSenha("12345678");

        // Configuramos o mock do gateway
        when(usuarioGateway.update(any(Usuario.class))).thenAnswer(invocation -> {
           Usuario usuarioAtualizado = invocation.getArgument(0);
           return new Usuario(
                   usuarioEncontrado.getId(),
                   usuarioAtualizado.getNome(),
                   usuarioAtualizado.getEmail(),
                   usuarioAtualizado.getTipo(),
                   usuarioAtualizado.getSenha()
           );
        });

        // Ação
        Usuario resultado = updateUsuarioUseCase.execute(usuarioRequest, usuarioEncontrado);

        // Verificação
        assertEquals(1L, resultado.getId());
        assertEquals("Humberto atualizado", resultado.getNome());
        assertEquals("humbertoatualizado@email.com", resultado.getEmail());
    }
}
