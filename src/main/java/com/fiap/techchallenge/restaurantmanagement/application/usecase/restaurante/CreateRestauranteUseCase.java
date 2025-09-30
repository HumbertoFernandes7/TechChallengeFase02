package com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    public Restaurante execute(Restaurante restaurante, Endereco endereco){
        if(restaurante.getDonoRestaurante().getTipo() != TipoUsuario.CLIENTE) {
            restaurante.associarEndereco(endereco);
            return restauranteGateway.save(restaurante);
        }else{
            throw new RuntimeException("Usuário associado não tem permissão para ser dono do restaurante");
        }
    }
}
