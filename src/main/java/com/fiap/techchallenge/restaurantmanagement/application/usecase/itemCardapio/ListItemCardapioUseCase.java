package com.fiap.techchallenge.restaurantmanagement.application.usecase.itemCardapio;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.ItemCardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.ItemCardapio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListItemCardapioUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public List<ItemCardapio> execute(){
        return itemCardapioGateway.findAll();
    }
}