package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;

import java.util.List;

public class EnderecoGatewayImpl implements EnderecoGateway {
    @Override
    public EnderecoGateway save(Endereco endereco) {
        return null;
    }

    @Override
    public Endereco update(Long id, Endereco enderecoAtualizado) {
        return null;
    }

    @Override
    public Endereco findById(Long id) {
        return null;
    }

    @Override
    public List<Endereco> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}
