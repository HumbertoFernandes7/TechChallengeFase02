package com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteEnderecoUseCase {
    private final EnderecoGateway enderecoGateway;

    public void deleteEndereco(Long id) {
        enderecoGateway.deleteById(id);
    }
}
