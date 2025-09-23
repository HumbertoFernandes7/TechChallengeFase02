package com.fiap.techchallenge.restaurantmanagement.application.usecase.estado;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateEstadoUseCase {

    private final EstadoGateway estadoGateway;

    public Estado saveEstado(Estado estado) {
        return estadoGateway.save(estado);
    }
}
