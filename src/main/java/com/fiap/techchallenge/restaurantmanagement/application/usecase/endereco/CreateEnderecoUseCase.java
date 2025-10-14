package com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateEnderecoUseCase {
    private final EnderecoGateway enderecoGateway;

    public Endereco saveEndereco(Endereco endereco) {
        return enderecoGateway.save(endereco);
    }
}
