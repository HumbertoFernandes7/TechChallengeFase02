package com.fiap.techchallenge.restaurantmanagement.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.CreateUsuarioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private CreateUsuarioUseCase createUsuarioUseCase;

    @Test
    void quando_executarComDadosValidos_deveSalvarComSucesso() {
        // Preparação (Arrange)
        Long enderecoId = 1L;
        Usuario usuarioParaSalvar = new Usuario(null, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");

        // Configurando o comportamento do Mock
        Usuario usuarioSalvo = new Usuario(1L, "Humberto", "humberto@email.com", TipoUsuario.CLIENTE, "12345678");
        when(usuarioGateway.save(any(Usuario.class), anyLong())).thenReturn(usuarioSalvo); // 3. Definindo a regra do mock

        // Ação (Act)
        Usuario resultado = createUsuarioUseCase.execute(usuarioParaSalvar, enderecoId);

        // Verificação (Assert)
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId()); // Verifica se o ID foi atribuído
        assertEquals("Humberto", resultado.getNome());

        // 4. Verificando se o método do mock foi chamado
        verify(usuarioGateway).save(usuarioParaSalvar, enderecoId);
    }
}