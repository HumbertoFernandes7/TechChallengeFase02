package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.exception.BusinessRuleException;
import com.fiap.techchallenge.restaurantmanagement.infra.exception.InvalidPasswordException;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.NovaSenhaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final UsuarioGateway usuarioGateway;

    public void execute(Long id, NovaSenhaRequest novaSenha){
        if(!novaSenha.getNovaSenha().equals(novaSenha.getRepetirNovaSenha())){
            throw new InvalidPasswordException("Nova senha e Repetir nova Senha não são iguais.");
        }
        Usuario usuario = usuarioGateway.findById(id);
        usuario.changePassword(novaSenha.getNovaSenha());
        usuarioGateway.update(usuario);
    }
}