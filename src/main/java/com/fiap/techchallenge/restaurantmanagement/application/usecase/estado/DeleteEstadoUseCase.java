package com.fiap.techchallenge.restaurantmanagement.application.usecase.estado;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteEstadoUseCase {

    private final EstadoGateway estadoGateway;

    public void delete(Long id) {
        estadoGateway.deleteById(id);
    }
}
