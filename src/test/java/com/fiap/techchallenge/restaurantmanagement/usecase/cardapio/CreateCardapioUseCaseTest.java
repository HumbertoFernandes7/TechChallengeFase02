package com.fiap.techchallenge.restaurantmanagement.usecase.cardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio.CreateCardapioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCardapioUseCaseTest {

    @Mock
    private CardapioGateway cardapioGateway;

    @InjectMocks
    private CreateCardapioUseCase createCardapioUseCase;

    @Test
    void quando_executarComDadosValidos_deveSalvarComSucesso() {
        Restaurante restaurante = new Restaurante();
        Cardapio cardapioParaSalvar = new Cardapio(restaurante);

        Cardapio cardapioSalvo = new Cardapio(1L, restaurante, null);
        when(cardapioGateway.save(any(Cardapio.class))).thenReturn(cardapioSalvo);

        Cardapio resultado = createCardapioUseCase.execute(cardapioParaSalvar);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(cardapioGateway).save(cardapioParaSalvar);
    }
}