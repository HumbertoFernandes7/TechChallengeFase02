package com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    public List<Restaurante> execute(){
        return restauranteGateway.findAll();
    }
}