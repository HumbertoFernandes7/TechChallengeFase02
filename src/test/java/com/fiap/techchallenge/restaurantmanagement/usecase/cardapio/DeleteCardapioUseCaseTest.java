package com.fiap.techchallenge.restaurantmanagement.usecase.cardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio.DeleteCardapioUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteCardapioUseCaseTest {

    @Mock
    private CardapioGateway cardapioGateway;

    @InjectMocks
    private DeleteCardapioUseCase deleteCardapioUseCase;

    @Test
    void quando_deletar_deveChamarMetodoDeleteDoGateway() {

        Long cardapioId = 1L;
        doNothing().when(cardapioGateway).deleteById(cardapioId);

        deleteCardapioUseCase.execute(cardapioId);


        verify(cardapioGateway).deleteById(cardapioId);
    }
}