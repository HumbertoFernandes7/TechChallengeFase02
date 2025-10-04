package com.fiap.techchallenge.restaurantmanagement.usecase.restaurante;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante.ListRestauranteUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private ListRestauranteUseCase listRestauranteUseCase;

    @Test
    void quando_existiremRestaurantes_deveRetornarListaDeRestaurantes(){
        // Preparação
        List<Restaurante> restaurantes = Arrays.asList(new Restaurante(), new Restaurante());

        when(restauranteGateway.findAll()).thenReturn(restaurantes);

        // Ação
        List<Restaurante> resultado = listRestauranteUseCase.execute();

        // Verificação
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    void quando_naoExistiremRestaurantes_deveRetornarListaVazia(){
        // Preparação
        when(restauranteGateway.findAll()).thenReturn(Collections.emptyList());

        //Ação
        List<Restaurante> resultado = listRestauranteUseCase.execute();

        //Verificação
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }
}
