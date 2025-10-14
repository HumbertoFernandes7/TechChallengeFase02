package com.fiap.techchallenge.restaurantmanagement.usecase.restaurante;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante.UpdateRestauranteUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private UpdateRestauranteUseCase updateRestauranteUseCase;

    @Test
    void quando_executarUpdate_deveChamarGatewayUpdate(){
        // Preparação
        Long restauranteId = 1L;
        Restaurante restaurantePraAtualizar = new  Restaurante();

        when(restauranteGateway.update(anyLong(), any(Restaurante.class))).thenReturn(restaurantePraAtualizar);

        // Ação
        updateRestauranteUseCase.execute(restauranteId, restaurantePraAtualizar);

        // Verificação
        verify(restauranteGateway, times(1)).update(restauranteId, restaurantePraAtualizar);
    }
}
