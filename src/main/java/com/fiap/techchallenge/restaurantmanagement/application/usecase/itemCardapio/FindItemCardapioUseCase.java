package com.fiap.techchallenge.restaurantmanagement.application.usecase.itemCardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.ItemCardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.ItemCardapio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindItemCardapioUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public ItemCardapio execute(Long id){
        return itemCardapioGateway.findById(id);
    }
}