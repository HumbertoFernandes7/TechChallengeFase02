package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.NovaSenhaRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChangePasswordUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario execute(Long id, NovaSenhaRequest novaSenha){
        return usuarioGateway.changePassword(id, novaSenha);
    }
}
