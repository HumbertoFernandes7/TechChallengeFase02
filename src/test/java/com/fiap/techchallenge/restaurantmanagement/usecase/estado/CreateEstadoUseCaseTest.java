package com.fiap.techchallenge.restaurantmanagement.usecase.estado;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.estado.CreateEstadoUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateEstadoUseCaseTest {

    @Mock
    private EstadoGateway estadoGateway;

    @InjectMocks
    private CreateEstadoUseCase createEstadoUseCase;

    @Test
    void salvarEstadoComSucesso() {
        Long estadoId = 1L;
        Estado estadoSalvar = new Estado(null, "Nome Teste", "Sigla Teste");
        Estado estadoSalvo = new Estado(estadoId, "Nome Teste", "Sigla Teste");
        Mockito.when(estadoGateway.save(estadoSalvar)).thenReturn(estadoSalvo);

        Estado resultado = createEstadoUseCase.saveEstado(estadoSalvar);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Nome Teste", resultado.getNome());
        Assertions.assertEquals("Sigla Teste", resultado.getSigla());
        Mockito.verify(estadoGateway).save(estadoSalvar);
    }
}