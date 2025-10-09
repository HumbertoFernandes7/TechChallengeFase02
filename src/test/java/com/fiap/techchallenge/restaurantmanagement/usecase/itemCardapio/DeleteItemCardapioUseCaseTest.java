package com.fiap.techchallenge.restaurantmanagement.usecase.itemCardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.ItemCardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.itemCardapio.DeleteItemCardapioUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private DeleteItemCardapioUseCase deleteItemCardapioUseCase;

    @Test
    void quando_executarDelete_deveChamarGatewayDelete(){
        // Preparação
        Long itemCardapioId = 1L;

        // Ação
        doNothing().when(itemCardapioGateway).deleteById(anyLong());
        deleteItemCardapioUseCase.execute(itemCardapioId);

        // Verificação
        verify(itemCardapioGateway, times(1)).deleteById(itemCardapioId);
    }
}