package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.usecase.mapper.UsuarioMapper;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ListUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public List<Usuario> execute() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDomain)
                .collect(Collectors.toList());
    }
}
