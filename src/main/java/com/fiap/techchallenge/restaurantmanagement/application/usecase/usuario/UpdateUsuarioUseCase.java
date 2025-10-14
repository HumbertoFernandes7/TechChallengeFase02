package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.UsuarioRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario execute(UsuarioRequest usuarioRequest, Usuario usuarioEncontrado){

        usuarioEncontrado.updateUsuario(
                usuarioRequest.getNome(),
                usuarioRequest.getEmail(),
                usuarioRequest.getTipo()
        );
        return usuarioGateway.update(usuarioEncontrado);
    }
}