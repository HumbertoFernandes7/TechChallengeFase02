package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.usecase.mapper.UsuarioMapper;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.UsuarioEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public Usuario execute(Usuario usuario){
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuario);
        UsuarioEntity usuarioSalvo =  usuarioRepository.save(usuarioEntity);
        return usuarioMapper.toDomain(usuarioSalvo);
    }
}
