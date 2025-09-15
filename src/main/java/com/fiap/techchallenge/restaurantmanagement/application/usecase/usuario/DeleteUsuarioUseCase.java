package com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario;

import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public void execute(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com o id " + id + " não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}
