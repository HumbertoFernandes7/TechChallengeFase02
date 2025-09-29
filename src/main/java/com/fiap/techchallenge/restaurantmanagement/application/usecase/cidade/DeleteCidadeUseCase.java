package com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCidadeUseCase {
    private final CidadeGateway cidadeGateway;

    public void delete(Long id) {
        cidadeGateway.deleteById(id);
    }

}
