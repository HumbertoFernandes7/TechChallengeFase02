package com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListCardapioUseCase {

    private final CardapioGateway cardapioGateway;

    public List<Cardapio> execute(){
        return cardapioGateway.findAll();
    }
}