package com.fiap.techchallenge.restaurantmanagement.application.gateway;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;

import java.util.List;

public interface CardapioGateway {

    Cardapio save(Cardapio cardapio);

    Cardapio update(Long id, Cardapio cardapioAtualizado);

    Cardapio findById(Long id);

    List<Cardapio> findAll();

    void deleteById(Long id);
}
