package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EnderecoEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.UsuarioEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.UsuarioMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EnderecoRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.UsuarioRepository;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    private final EnderecoRepository enderecoRepository;

    @Override
    public Usuario save(Usuario usuario, Long enderecoId) {
        EnderecoEntity enderecoEntity = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new UserNotFoundException("Usuario com id" + enderecoId +" não encontrado "));

        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuario);
        usuarioEntity.setEndereco(enderecoEntity);
        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioEntity);
        return usuarioMapper.toDomain(usuarioSalvo);
    }

    @Override
    public Usuario update(Usuario usuarioEncontrado) {
        Optional<UsuarioEntity> usuarioEntityEncontrado = usuarioRepository.findById(usuarioEncontrado.getId());
        EnderecoEntity enderecoEntity = usuarioEntityEncontrado.get().getEndereco();

        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuarioEncontrado);
        usuarioEntity.setEndereco(enderecoEntity);
        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioEntity);
        return usuarioMapper.toDomain(usuarioSalvo);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDomain).orElseThrow(
                () -> new UserNotFoundException("Usuario com id" + id +" não encontrado "));
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UserNotFoundException("Usuario com id" + id +" não encontrado ");
        }
        usuarioRepository.deleteById(id);
    }
}