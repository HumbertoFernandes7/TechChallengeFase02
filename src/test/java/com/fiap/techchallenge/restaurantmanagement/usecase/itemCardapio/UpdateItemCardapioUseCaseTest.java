package com.fiap.techchallenge.restaurantmanagement.usecase.itemCardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.ItemCardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.itemCardapio.UpdateItemCardapioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.ItemCardapio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private UpdateItemCardapioUseCase updateItemCardapioUseCase;

    @Test
    void quando_executarUpdate_deveChamarGatewayUpdate(){
        // Preparação
        Long itemCardapioId = 1L;
        ItemCardapio itemCardapioParaAtualizar = new ItemCardapio(1L, "Item Teste", "Descrição do item", BigDecimal.valueOf(10.0), true, "foto.jpg");

        when(itemCardapioGateway.update(anyLong(), any(ItemCardapio.class))).thenReturn(itemCardapioParaAtualizar);

        // Ação
        updateItemCardapioUseCase.execute(itemCardapioId, itemCardapioParaAtualizar);

        // Verificação
        verify(itemCardapioGateway, times(1)).update(itemCardapioId, itemCardapioParaAtualizar);
    }
}