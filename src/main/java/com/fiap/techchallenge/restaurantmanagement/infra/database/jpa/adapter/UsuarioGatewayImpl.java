package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.UsuarioEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.UsuarioMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuario);
        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioEntity);
        return usuarioMapper.toDomain(usuarioSalvo);
    }

    @Override
    public Usuario update(Long id, Usuario usuarioAtualizado) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com o id " + id + " não encontrado."));

        usuarioEntity.setNome(usuarioAtualizado.getNome());
        usuarioEntity.setEmail(usuarioAtualizado.getEmail());
        usuarioEntity.setTipo(usuarioAtualizado.getTipo());
        usuarioEntity.setSenha(usuarioAtualizado.getSenha());

        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioEntity);
        return usuarioMapper.toDomain(usuarioSalvo);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDomain).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado"));
    }

    @Override
    public List<Usuario> findAll() {
        return  usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com o id " + id + " não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}
