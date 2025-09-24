package com.fiap.techchallenge.restaurantmanagement.application.gateway;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;

import java.util.List;

public interface EnderecoGateway {

    Endereco save(Endereco endereco);

    Endereco update(Long id, Endereco enderecoAtualizado);

    Endereco findById(Long id);

    List<Endereco> findAll();

    void deleteById(Long id);
}
