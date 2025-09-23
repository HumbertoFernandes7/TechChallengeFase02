package com.fiap.techchallenge.restaurantmanagement.application.gateway;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;

import java.util.List;

public interface CidadeGateway {

    Cidade save(Cidade cidade);

    Cidade update(Long id, Cidade cidadeAtualizada);

    Cidade findById(Long id);

    List<Cidade> findAll();

    void deleteById(Long id);
}
