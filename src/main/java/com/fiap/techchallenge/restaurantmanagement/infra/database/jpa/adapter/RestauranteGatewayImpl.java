package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.RestauranteEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.UsuarioEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.RestauranteMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.UsuarioMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.RestauranteRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestauranteGatewayImpl implements RestauranteGateway {

    private final RestauranteMapper restauranteMapper;
    private final RestauranteRepository restauranteRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Restaurante save(Restaurante restaurante) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(restaurante.getDonoRestaurante().getId()).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado para associar ao restaurante")
        );
        if(usuarioEntity.getTipo() != TipoUsuario.CLIENTE){
            RestauranteEntity restauranteEntity = restauranteMapper.toEntity(restaurante, usuarioEntity);
            RestauranteEntity restauranteSalvo = restauranteRepository.save(restauranteEntity);
            Usuario usuarioSalvo = usuarioMapper.toDomain(restauranteSalvo.getDonoRestaurante());
            return restauranteMapper.toDomain(restauranteSalvo, usuarioSalvo);
        }
        throw new RuntimeException("Usuario associado não tem permissão para ser dono do restaurante");
    }

    @Override
    public Restaurante update(Long id, Restaurante restauranteAtualizado) {
        return null;
    }

    @Override
    public Restaurante findById(Long id) {
        return null;
    }

    @Override
    public List<Restaurante> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}
