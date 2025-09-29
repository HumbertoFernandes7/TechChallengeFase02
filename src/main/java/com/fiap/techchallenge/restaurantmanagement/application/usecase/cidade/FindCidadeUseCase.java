package com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindCidadeUseCase {
    private final CidadeGateway cidadeGateway;

    public Cidade get(Long id) {
        return cidadeGateway.findById(id);
    }
}
