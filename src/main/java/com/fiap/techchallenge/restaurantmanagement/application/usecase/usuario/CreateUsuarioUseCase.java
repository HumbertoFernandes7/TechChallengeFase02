package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario execute(Usuario usuario, Long enderecoId){
        return usuarioGateway.save(usuario, enderecoId);
    }
}
