package com.fiap.techchallenge.restaurantmanagement.usecase.estado;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.estado.FindEstadoUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FindEstadoUseCaseTest {

    @Mock
    private EstadoGateway estadoGateway;

    @InjectMocks
    private FindEstadoUseCase findEstadoUseCase;

    @Test
    void findEstadoById_Success() {
        Long estadoID = 1L;
        Estado estadoEsperado = new Estado(estadoID,"Estado Teste","ET");
        Mockito.when(estadoGateway.findById(estadoID)).thenReturn(estadoEsperado);
        Estado estado = findEstadoUseCase.execute(estadoID);
        Assertions.assertNotNull(estado);
        assertEquals("Estado Teste",estado.getNome());
        assertEquals("ET",estado.getSigla());
    }

    @Test
    void findEstadoById_Failure() {
        Long estadoID = 9322L;
        String erroMessage = "Estado com o id 9322 não encontrado.";
        Mockito.when(estadoGateway.findById(estadoID)).thenThrow(new EntityNotFoundException(erroMessage));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> findEstadoUseCase.execute(estadoID));
        assertEquals(erroMessage, exception.getMessage());
    }
}