package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.application.usecase.mapper.UsuarioMapper;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FindUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDomain).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado")
        );
    }
}