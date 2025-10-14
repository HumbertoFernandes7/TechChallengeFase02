package com.fiap.techchallenge.restaurantmanagement.application.usecase.itemCardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.ItemCardapioGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteItemCardapioUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public void execute(Long id){
        itemCardapioGateway.deleteById(id);
    }
}