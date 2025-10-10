package com.fiap.techchallenge.restaurantmanagement.usecase.cardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio.ListCardapioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCardapioUseCaseTest {

    @Mock
    private CardapioGateway cardapioGateway;

    @InjectMocks
    private ListCardapioUseCase listCardapioUseCase;

    @Test
    void quando_houverCardapios_deveRetornarListaDeCardapios() {
        // Preparação
        List<Cardapio> cardapios = Collections.singletonList(new Cardapio(1L, null, null));
        when(cardapioGateway.findAll()).thenReturn(cardapios);

        // Ação
        List<Cardapio> resultado = listCardapioUseCase.execute();

        // Verificação
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void quando_naoHouverCardapios_deveRetornarListaVazia() {
        // Preparação
        when(cardapioGateway.findAll()).thenReturn(Collections.emptyList());

        // Ação
        List<Cardapio> resultado = listCardapioUseCase.execute();

        // Verificação
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }
}