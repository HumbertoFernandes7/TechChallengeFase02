package com.fiap.techchallenge.restaurantmanagement.usecase.estado;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.estado.DeleteEstadoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class DeleteEstadoUseCaseTest {
    @Mock
    private EstadoGateway estadoGateway;

    @InjectMocks
    private DeleteEstadoUseCase deleteEstadoUseCase;

    @Test
    public void deleteEstadoById_sucess(){
        Long estadoID = 1L;
        doNothing().when(estadoGateway).deleteById(anyLong());
        deleteEstadoUseCase.delete(estadoID);
        verify(estadoGateway, times(1)).deleteById(estadoID);
    }
}