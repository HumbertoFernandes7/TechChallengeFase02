package com.fiap.techchallenge.restaurantmanagement.application.usecase.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toDomain(UsuarioEntity usuarioEntity){
        if(usuarioEntity == null) {
            return null;
        }
        return new Usuario(
                usuarioEntity.getId(),
                usuarioEntity.getNome(),
                usuarioEntity.getEmail(),
                usuarioEntity.getTipo(),
                usuarioEntity.getSenha(),
                null
        );
    }

    public UsuarioEntity toEntity(Usuario usuario){
        if(usuario == null) {
            return null;
        }
        return new UsuarioEntity(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipo(),
                usuario.getSenha(),
                null
        );
    }

}
