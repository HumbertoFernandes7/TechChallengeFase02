package com.fiap.techchallenge.restaurantmanagement.usecase.itemCardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.ItemCardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.itemCardapio.ListItemCardapioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.ItemCardapio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private ListItemCardapioUseCase listItemCardapioUseCase;

    @Test
    void quando_existiremRestaurantes_deveRetornarListaDeRestaurantes(){
        // Preparação
        List<ItemCardapio> itensCardapio = Arrays.asList(
                new ItemCardapio(1L, "Item Teste", "Descrição do item", BigDecimal.valueOf(10.0), true, "foto.jpg"),
                new ItemCardapio(2L, "Item Teste2", "Descrição do item2", BigDecimal.valueOf(20.0), true, "foto.jpg"));

        when(itemCardapioGateway.findAll()).thenReturn(itensCardapio);

        // Ação
        List<ItemCardapio> resultado = listItemCardapioUseCase.execute();

        // Verificação
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    void quando_naoExistiremRestaurantes_deveRetornarListaVazia(){
        // Preparação
        when(itemCardapioGateway.findAll()).thenReturn(Collections.emptyList());

        //Ação
        List<ItemCardapio> resultado = listItemCardapioUseCase.execute();

        //Verificação
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }
}
