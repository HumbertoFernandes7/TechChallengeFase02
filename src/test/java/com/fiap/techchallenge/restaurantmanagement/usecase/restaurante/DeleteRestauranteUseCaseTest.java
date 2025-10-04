package com.fiap.techchallenge.restaurantmanagement.usecase.restaurante;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante.DeleteRestauranteUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private DeleteRestauranteUseCase deleteRestauranteUseCase;

    @Test
    void quando_executarDelete_deveChamarGatewayDeleteById() {
        // Preparação
        Long restauranteId = 1L;

        doNothing().when(restauranteGateway).deleteById(anyLong());

        // Ação
        deleteRestauranteUseCase.execute(restauranteId);

        // Verificação
        verify(restauranteGateway, times(1)).deleteById(restauranteId);
    }
}
