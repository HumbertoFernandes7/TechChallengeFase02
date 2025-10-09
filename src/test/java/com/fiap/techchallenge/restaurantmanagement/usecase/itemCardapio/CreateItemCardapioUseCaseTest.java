package com.fiap.techchallenge.restaurantmanagement.usecase.itemCardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.ItemCardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.itemCardapio.CreateItemCardapioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private CreateItemCardapioUseCase createItemCardapioUseCase;

    @Test
    void quando_executarComDadosValidos_deveSalvarComSucesso(){
        // Preparação
        ItemCardapio itemCardapio = new ItemCardapio(1L, "Item Teste", "Descrição do item", BigDecimal.valueOf(10.0), true, "foto.jpg");
        Cardapio cardapioAssociado = new Cardapio(null);

        when(itemCardapioGateway.save(any(ItemCardapio.class), any(Cardapio.class))).thenReturn(itemCardapio);

        // Ação
        ItemCardapio resultado = createItemCardapioUseCase.execute(itemCardapio, cardapioAssociado);

        // Verificação
        assertNotNull(resultado);
        assertEquals(resultado, itemCardapio);
        verify(itemCardapioGateway, times(1)).save(itemCardapio, cardapioAssociado);
    }
}