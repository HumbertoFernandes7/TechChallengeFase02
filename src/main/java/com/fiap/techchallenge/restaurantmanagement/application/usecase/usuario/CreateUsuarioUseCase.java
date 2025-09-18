package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario execute(Usuario usuario){
       return usuarioGateway.save(usuario);
    }
}
