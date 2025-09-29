package com.fiap.techchallenge.restaurantmanagement.application.gateway;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.ItemCardapio;

import java.util.List;

public interface ItemCardapioGateway {

    ItemCardapio save(ItemCardapio itemCardapio, Cardapio cardapio);

    ItemCardapio  update(Long id, ItemCardapio  itemCardapioAtualizado);

    ItemCardapio  findById(Long id);

    List<ItemCardapio > findAll();

    void deleteById(Long id);
}
