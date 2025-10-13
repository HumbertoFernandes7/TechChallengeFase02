package com.fiap.techchallenge.restaurantmanagement.usecase.estado;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.estado.UpdateEstadoUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UpdateEstadoUseCaseTest {

    @Mock
    private EstadoGateway estadoGateway;

    @InjectMocks
    private UpdateEstadoUseCase updateEstadoUseCase;

    @Test
    public void testUpdateEstadoUseCaseSuccess() {
        Estado estado = new Estado(1L, "Estado Teste", "ET");
        Estado estadoAtualizar = new Estado(1L, "Estado Teste Atualizado", "ETA");
        Mockito.when(estadoGateway.update( Mockito.any(Estado.class))).thenAnswer(invocationOnMock ->
        {
            Estado estadoAtualizado = invocationOnMock.getArgument(0);
            return new Estado(
                    estado.getId(),
                    estadoAtualizado.getNome(),
                    estadoAtualizado.getSigla()
            );
        });
        Estado execute = updateEstadoUseCase.execute(estadoAtualizar);
        assertEquals(1L, execute.getId());
        assertEquals("Estado Teste Atualizado", execute.getNome());
        assertEquals("ETA", execute.getSigla());
    }
}