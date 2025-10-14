package com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListEnderecoUseCase {
    private final EnderecoGateway enderecoGateway;

    public List<Endereco> findEndereco() {
        return enderecoGateway.findAll();
    }
}
