package com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateCardapioUseCase {

    private final CardapioGateway cardapioGateway;

    public Cardapio execute(Long id, Cardapio cardapio){
        return cardapioGateway.update(id, cardapio);
    }
}