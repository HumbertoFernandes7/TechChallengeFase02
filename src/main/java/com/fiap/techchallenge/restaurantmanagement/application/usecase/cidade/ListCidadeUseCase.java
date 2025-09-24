package com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListCidadeUseCase {

    private final CidadeGateway cidadeGateway;

    public List<Cidade> execute() {
        return cidadeGateway.findAll();
    }
}
