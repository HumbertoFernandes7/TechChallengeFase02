package com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    public Restaurante execute(Restaurante restaurante){
        return restauranteGateway.save(restaurante);
    }
}
