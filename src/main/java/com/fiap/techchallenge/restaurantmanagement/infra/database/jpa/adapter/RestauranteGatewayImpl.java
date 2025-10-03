package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EnderecoEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.RestauranteEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.UsuarioEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.RestauranteMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EnderecoRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.RestauranteRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.UsuarioRepository;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.RestaurantNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestauranteGatewayImpl implements RestauranteGateway {

    private final RestauranteMapper restauranteMapper;

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;

    @Override
    public Restaurante save(Restaurante restaurante) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(restaurante.getDonoRestaurante().getId()).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado para associar ao restaurante")
        );

        EnderecoEntity enderecoEntity = enderecoRepository.findById(restaurante.getEndereco().getId()).orElseThrow(
                () -> new EntityNotFoundException("Endereço não encontrado para associar ao restaurante")
        );
            RestauranteEntity restauranteEntity = restauranteMapper.toEntity(restaurante);
            restauranteEntity.setDonoRestaurante(usuarioEntity);
            restauranteEntity.setEndereco(enderecoEntity);
            RestauranteEntity restauranteSalvo = restauranteRepository.save(restauranteEntity);
            return restauranteMapper.toDomain(restauranteSalvo);
    }

    @Override
    public Restaurante update(Long id, Restaurante restauranteAtualizado) {
        RestauranteEntity restauranteEntity = restauranteRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante com o id " + id + " não encontrado."));

        restauranteEntity.setNome(restauranteAtualizado.getNome());
        restauranteEntity.setTipoCozinha(restauranteAtualizado.getTipoCozinha());
        restauranteEntity.setHorarioAbertura(restauranteAtualizado.getHorarioAbertura());
        restauranteEntity.setHorarioFechamento(restauranteAtualizado.getHorarioFechamento());

        RestauranteEntity restauranteSalvo = restauranteRepository.save(restauranteEntity);
        return restauranteMapper.toDomain(restauranteSalvo);
    }

    @Override
    public Restaurante findById(Long id) {
        return restauranteRepository.findById(id).map(restauranteMapper::toDomain).orElseThrow(
                () -> new RestaurantNotFoundException("Restaurante não encontrado"));
    }

    @Override
    public List<Restaurante> findAll() {
        return restauranteRepository.findAll()
                .stream()
                .map(restauranteMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!restauranteRepository.existsById(id)) {
            throw new RestaurantNotFoundException("Restaurante com o id " + id + " não encontrado.");
        }
        restauranteRepository.deleteById(id);
    }
}