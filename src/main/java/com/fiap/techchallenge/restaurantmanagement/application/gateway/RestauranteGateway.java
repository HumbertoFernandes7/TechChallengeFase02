package com.fiap.techchallenge.restaurantmanagement.application.gateway;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;

import java.util.List;

public interface RestauranteGateway {

    Restaurante save(Restaurante restaurante);

    Restaurante update(Long id, Restaurante restauranteAtualizado);

    Restaurante findById(Long id);

    List<Restaurante> findAll();

    void deleteById(Long id);
}