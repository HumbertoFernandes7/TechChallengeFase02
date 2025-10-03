package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EnderecoEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.EnderecoMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EnderecoRepository;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.EnderecoNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EnderecoGatewayImpl implements EnderecoGateway {
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Override
    public Endereco save(Endereco endereco) {
        EnderecoEntity enderecoEntity = enderecoMapper.toEntity(endereco);
        EnderecoEntity enderecoSalvo = enderecoRepository.save(enderecoEntity);
        return enderecoMapper.toDomain(enderecoSalvo);
    }

    //todo
    @Override
    public Endereco update(Long id, Endereco enderecoAtualizado) {
        return null;
    }

    @Override
    public Endereco findById(Long id) {
        return enderecoRepository.findById(id).map(enderecoMapper::toDomain).orElseThrow(
                () -> new EnderecoNotFoundException("Endereço com o id " + id + " não encontrado."));
    }

    @Override
    public List<Endereco> findAll() {
        return enderecoRepository.findAll()
                .stream()
                .map(enderecoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new EnderecoNotFoundException("Endereço com o id " + id + " não encontrado.");
        }
        enderecoRepository.deleteById(id);
    }
}
