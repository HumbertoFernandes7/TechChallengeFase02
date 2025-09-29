package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UsuarioMapper {

    private final EnderecoMapper enderecoMapper;

    public Usuario toDomain(UsuarioEntity usuarioEntity) {
        if (usuarioEntity == null) {
            return null;
        }
        return new Usuario(
                usuarioEntity.getId(),
                usuarioEntity.getNome(),
                usuarioEntity.getEmail(),
                usuarioEntity.getTipo(),
                usuarioEntity.getSenha(),
                enderecoMapper.toDomain(usuarioEntity.getEndereco()));
    }

    public UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioEntity(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipo(),
                usuario.getSenha(),
                enderecoMapper.toEntity(usuario.getEndereco()));
    }
}
