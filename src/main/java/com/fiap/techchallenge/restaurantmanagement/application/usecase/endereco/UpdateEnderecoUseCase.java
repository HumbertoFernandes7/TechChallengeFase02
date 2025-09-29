package com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateEnderecoUseCase {

    private final EnderecoGateway enderecoGateway;

    public Endereco execute(Long id, Endereco enderecoAtualizado) {
        return enderecoGateway.update(id, enderecoAtualizado);
    }

}
