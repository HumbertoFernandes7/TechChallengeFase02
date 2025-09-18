package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public List<Usuario> execute() {
        return usuarioGateway.findAll();
    }
}
