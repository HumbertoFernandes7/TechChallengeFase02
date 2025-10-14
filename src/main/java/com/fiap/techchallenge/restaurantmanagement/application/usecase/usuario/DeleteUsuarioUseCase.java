package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public void execute(Long id) {
         usuarioGateway.deleteById(id);
    }
}
