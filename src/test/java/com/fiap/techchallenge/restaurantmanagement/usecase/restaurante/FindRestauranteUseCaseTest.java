package com.fiap.techchallenge.restaurantmanagement.usecase.restaurante;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante.FindRestauranteUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.RestaurantNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private FindRestauranteUseCase findRestauranteUseCase;

    @Test
    void quando_buscarPorIdExistente_deveRetornarRestaurante(){
        // Preparação
        Long restauranteId = 1L;
        Restaurante restauranteEsperado = new Restaurante();

        when(restauranteGateway.findById(restauranteId)).thenReturn(restauranteEsperado);

        // Ação
        Restaurante restaurante = findRestauranteUseCase.execute(restauranteId);

        // Verificação
        assertNotNull(restaurante);
        assertEquals(restauranteEsperado, restaurante);
    }

    @Test
    void quando_buscarPorIdInexistente_deveLancarRestaurantNotFoundException(){
        // Preparação
        Long restauranteId = 99L;
        String mensagemErro = "Restaurante não encontrado";

        when(restauranteGateway.findById(restauranteId)).thenThrow(new RestaurantNotFoundException(mensagemErro));

        //Ação
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                ()-> findRestauranteUseCase.execute(restauranteId));

        //Verificação
        assertEquals(mensagemErro, exception.getMessage());
    }
}