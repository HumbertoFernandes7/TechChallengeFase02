package com.fiap.techchallenge.restaurantmanagement.usecase.itemCardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.ItemCardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.itemCardapio.FindItemCardapioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.ItemCardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.ItemCardapioNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private FindItemCardapioUseCase findItemCardapioUseCase;

    @Test
    void quando_buscarPorIdExistente_deveRetornarRestaurante(){
        // Preparação
        Long itemCardapioId = 1L;
        ItemCardapio itemCardapio = new ItemCardapio(itemCardapioId,"Picanha", "Grelhada", BigDecimal.valueOf(80.0), true, "/picanha.jpg" );

        when(itemCardapioGateway.findById(itemCardapioId)).thenReturn(itemCardapio);

        // Ação
        ItemCardapio resultado = findItemCardapioUseCase.execute(itemCardapioId);

        // Verificação
        assertNotNull(resultado);
        assertEquals(resultado, itemCardapio);
        verify(itemCardapioGateway,times(1)).findById(itemCardapioId);
    }

    @Test
    void quando_buscarPorIdInexistente_deveRetornarExcecao(){
        // Preparação
        Long itemCardapioId = 1L;
        String mensagemErro = "Item de cardápio com o id " + itemCardapioId + " não encontrado.";

        when(itemCardapioGateway.findById(itemCardapioId)).thenThrow(new ItemCardapioNotFoundException(mensagemErro));

        // Ação
        ItemCardapioNotFoundException exception = assertThrows(ItemCardapioNotFoundException.class, () -> {
            findItemCardapioUseCase.execute(itemCardapioId);
        });

        // Verificação
        assertEquals(mensagemErro, exception.getMessage());
    }
}