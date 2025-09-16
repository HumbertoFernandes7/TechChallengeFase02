package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario execute(Long id, Usuario usuarioAtualizado){
        return usuarioGateway.update(id, usuarioAtualizado);
    }
}