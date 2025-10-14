package com.fiap.techchallenge.restaurantmanagement.usecase.restaurante;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.RestauranteGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante.CreateRestauranteUseCase;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante.FindRestauranteUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.BusinessRuleException;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.RestaurantNotFoundException;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private CreateRestauranteUseCase createRestauranteUseCase;

    @Test
    void quando_executarComDadosValidos_deveSalvarComSucesso(){
        // Preparação
        Restaurante restaurante = new Restaurante("Restaurante Teste", "Brasileira", LocalTime.now(), LocalTime.now(), new Usuario());

        when(restauranteGateway.save(any(Restaurante.class))).thenReturn(restaurante);

        // Ação
        Restaurante resultado = createRestauranteUseCase.execute(restaurante, new Endereco());

        // Verificação
        assertNotNull(resultado);
        assertEquals(resultado, restaurante);
        verify(restauranteGateway, times(1)).save(restaurante);
    }

    @Test
    void quando_executarComTipoUsuarioCliente_deveLancarExcecao(){
        // Preparação
        Usuario usuario =  new Usuario(1L, "Humberto", "humberto@email.com", TipoUsuario.CLIENTE, "12345678");
        Restaurante restaurante = new Restaurante("Restaurante Teste", "Brasileira", LocalTime.now(), LocalTime.now(), usuario);

        // Ação
        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> createRestauranteUseCase.execute(restaurante, new Endereco()));

        assertEquals("Usuário do tipo CLIENTE não pode ser dono de um restaurante", exception.getMessage());
        verifyNoInteractions(restauranteGateway);
    }
}