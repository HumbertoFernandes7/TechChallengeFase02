package com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCidadeUseCase {

    private final CidadeGateway cidadeGateway;

    public Cidade execute(Cidade cidadeAtualizado) {
        return cidadeGateway.update(cidadeAtualizado);
    }
}
