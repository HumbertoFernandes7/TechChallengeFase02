package com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCardapioUseCase {

    private final CardapioGateway cardapioGateway;

    public Cardapio execute(Cardapio cardapio) {
       return cardapioGateway.save(cardapio);
    }
}
