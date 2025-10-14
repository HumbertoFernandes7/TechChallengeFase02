package com.fiap.techchallenge.restaurantmanagement.application.usecase.estado;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteEstadoUseCase {

    private final EstadoGateway estadoGateway;

    public void delete(Long id) {
        estadoGateway.deleteById(id);
    }
}
