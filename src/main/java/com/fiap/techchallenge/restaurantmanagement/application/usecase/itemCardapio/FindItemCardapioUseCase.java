package com.fiap.techchallenge.restaurantmanagement.application.usecase.itemCardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.ItemCardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.ItemCardapio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindItemCardapioUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public ItemCardapio execute(Long id){
        return itemCardapioGateway.findById(id);
    }
}