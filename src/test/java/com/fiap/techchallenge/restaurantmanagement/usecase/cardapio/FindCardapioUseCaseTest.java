package com.fiap.techchallenge.restaurantmanagement.usecase.cardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio.FindCardapioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.CardapioNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCardapioUseCaseTest {

    @Mock
    private CardapioGateway cardapioGateway;

    @InjectMocks
    private FindCardapioUseCase findCardapioUseCase;

    @Test
    void quando_buscarPorIdExistente_deveRetornarCardapio() {

        Long cardapioId = 1L;
        Cardapio cardapioEsperado = new Cardapio(cardapioId, null, null);
        when(cardapioGateway.findById(cardapioId)).thenReturn(cardapioEsperado);

        Cardapio resultado = findCardapioUseCase.execute(cardapioId);

        assertNotNull(resultado);
        assertEquals(cardapioEsperado, resultado);
    }

    @Test
    void quando_buscarPorIdInexistente_deveLancarExcecao() {

        Long cardapioId = 2L;
        when(cardapioGateway.findById(cardapioId)).thenThrow(new CardapioNotFoundException("Cardápio não encontrado"));

        assertThrows(CardapioNotFoundException.class, () -> findCardapioUseCase.execute(cardapioId));
    }
}