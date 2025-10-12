package com.fiap.techchallenge.restaurantmanagement.usecase.estado;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.estado.ListEstadoUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListEstadoUseCaseTest {

    @Mock
    private EstadoGateway estadoGateway;

    @InjectMocks
    private ListEstadoUseCase listEstadoUseCase;

    @Test
    void testListEstadoSuccess() {
        List<Estado> estados = Arrays.asList(
                new Estado(1L, "Teste Um", "TU"),
                new Estado(2L, "Teste Dois", "TD"));
        Mockito.when(estadoGateway.findAll()).thenReturn(estados);
        List<Estado> execute = listEstadoUseCase.execute();
        Assertions.assertNotNull(execute);
        Assertions.assertEquals(estados.size(),execute.size());
    }
}