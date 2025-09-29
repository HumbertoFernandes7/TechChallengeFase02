package com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateCidadeUseCase {

    private final CidadeGateway cidadeGateway;

    public Cidade execute(Long id, Cidade cidadeAtualizado) {
        return cidadeGateway.update(id, cidadeAtualizado);
    }
}
