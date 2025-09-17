package com.fiap.techchallenge.restaurantmanagement.application.gateway;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.NovaSenhaRequest;

import java.util.List;

public interface UsuarioGateway {

    Usuario save(Usuario usuario);

    Usuario update(Long id, Usuario usuarioAtualizado);

    Usuario findById(Long id);

    List<Usuario> findAll();

    void deleteById(Long id);

    Usuario changePassword(Long id, NovaSenhaRequest novaSenha);
}
