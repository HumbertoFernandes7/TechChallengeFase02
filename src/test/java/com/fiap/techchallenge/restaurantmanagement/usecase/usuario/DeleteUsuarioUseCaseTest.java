package com.fiap.techchallenge.restaurantmanagement.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.DeleteUsuarioUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private DeleteUsuarioUseCase deleteUsuarioUseCase;

    @Test
    void quando_deletarComIdInvalido_deveRetornarSucesso(){
        // Preparação
        Long usuarioId = 1L;

        // Diz ao Mockito para não fazer nada quando deleteById for chamado (opcional)
        doNothing().when(usuarioGateway).deleteById(anyLong());

        // Ação
        deleteUsuarioUseCase.execute(usuarioId);

        // Verificação
        verify(usuarioGateway, times(1)).deleteById(usuarioId);
    }
}
