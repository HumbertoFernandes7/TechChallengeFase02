package com.fiap.techchallenge.restaurantmanagement.application.usecase.estado;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListEstadoUseCase {

    private final EstadoGateway estadoGateway;

    public List<Estado> execute() {
        return estadoGateway.findAll();
    }
}
