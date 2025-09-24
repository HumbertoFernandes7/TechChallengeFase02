package com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindEnderecoUseCase {
    private final EnderecoGateway enderecoGateway;

    public Endereco findEndereco(Long id) {
        return enderecoGateway.findById(id);
    }
}
