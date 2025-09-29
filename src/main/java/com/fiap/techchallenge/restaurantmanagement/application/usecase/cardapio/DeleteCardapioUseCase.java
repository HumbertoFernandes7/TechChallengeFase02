package com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCardapioUseCase {

    private final CardapioGateway cardapioGateway;

    public void execute(Long id){
        cardapioGateway.deleteById(id);
    }
}