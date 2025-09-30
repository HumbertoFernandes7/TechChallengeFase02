package com.fiap.techchallenge.restaurantmanagement.application.gateway;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;

import java.util.List;

public interface UsuarioGateway {
    Usuario save(Usuario usuario, Long enderecoId);

    Usuario update(Usuario usuarioAtualizado);

    Usuario findById(Long id);

    List<Usuario> findAll();

    void deleteById(Long id);
}