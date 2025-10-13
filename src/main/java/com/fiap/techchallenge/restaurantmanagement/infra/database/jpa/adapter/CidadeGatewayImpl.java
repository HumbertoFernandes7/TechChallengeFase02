package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.CidadeNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.CidadeEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.CidadeMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.CidadeRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EstadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class CidadeGatewayImpl implements CidadeGateway {

    private final CidadeRepository cidadeRepository;
    private final CidadeMapper cidadeMapper;
    private final EstadoRepository estadoRepository;

    @Override
    public Cidade save(Cidade cidade) {
        CidadeEntity cidadeEntity = cidadeMapper.toEntity(cidade);
        CidadeEntity cidadeSalvo = cidadeRepository.save(cidadeEntity);
        return cidadeMapper.toDomain(cidadeSalvo);
    }

    @Override
    public Cidade update(Cidade cidadeAtualizada) {
        CidadeEntity cidadeEntity = cidadeRepository.findById(cidadeAtualizada.getId()).orElseThrow(() -> new CidadeNotFoundException("Cidade com o id " + cidadeAtualizada.getId() + " não encontrado."));
        cidadeEntity.setNome(cidadeAtualizada.getNome());
        //esse ponto aqui pode causar falha
        cidadeEntity.setEstado(estadoRepository.findById(cidadeAtualizada.getEstado().getId()).get());
        CidadeEntity save = cidadeRepository.save(cidadeEntity);
        return cidadeMapper.toDomain(save);
    }

    @Override
    public Cidade findById(Long id) {
        return cidadeRepository.findById(id).map(cidadeMapper::toDomain).orElseThrow(
                () -> new CidadeNotFoundException("Cidade não encontrado"));
    }

    @Override
    public List<Cidade> findAll() {
        return cidadeRepository.findAll()
                .stream()
                .map(cidadeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!cidadeRepository.existsById(id)) {
            throw new CidadeNotFoundException("Cidade com o id " + id + " não encontrado.");
        }
        cidadeRepository.deleteById(id);
    }
}