package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.RestauranteEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.UsuarioEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.RestauranteMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.RestauranteRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.UsuarioRepository;
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

    @Override
    public Restaurante save(Restaurante restaurante) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(restaurante.getDonoRestaurante().getId()).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado para associar ao restaurante")
        );
        if(usuarioEntity.getTipo() != TipoUsuario.CLIENTE){
            RestauranteEntity restauranteEntity = restauranteMapper.toEntity(restaurante);
            restauranteEntity.setDonoRestaurante(usuarioEntity);
            RestauranteEntity restauranteSalvo = restauranteRepository.save(restauranteEntity);
            return restauranteMapper.toDomain(restauranteSalvo);
        }
        throw new RuntimeException("Usuário associado não tem permissão para ser dono do restaurante");
    }

    @Override
    public Restaurante update(Long id, Restaurante restauranteAtualizado) {
        RestauranteEntity restauranteEntity = restauranteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante com o id " + id + " não encontrado."));

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
                () -> new EntityNotFoundException("Restaurante não encontrado"));
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
            throw new EntityNotFoundException("Restaurante com o id " + id + " não encontrado.");
        }
        restauranteRepository.deleteById(id);
    }
}