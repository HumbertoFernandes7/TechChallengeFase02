package com.fiap.techchallenge.restaurantmanagement.application.gateway;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;

import java.util.List;

public interface EstadoGateway {

    Estado save(Estado estado);

    Estado update(Estado estadoAtualizado);

    Estado findById(Long id);

    List<Estado> findAll();

    void deleteById(Long id);
}
