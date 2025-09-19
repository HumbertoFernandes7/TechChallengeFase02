package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestauranteGatewayImpl implements RestauranteGateway {

    @Override
    public Restaurante save(Restaurante restaurante) {
        return null;
    }

    @Override
    public Restaurante update(Long id, Restaurante restauranteAtualizado) {
        return null;
    }

    @Override
    public Restaurante findById(Long id) {
        return null;
    }

    @Override
    public List<Restaurante> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}
