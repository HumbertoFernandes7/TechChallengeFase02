package com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    public void execute(Long id){
        restauranteGateway.deleteById(id);
    }
}