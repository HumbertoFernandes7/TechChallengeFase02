package com.fiap.techchallenge.restaurantmanagement.usecase.cidade;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade.ListCidadeUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListCidadeUseCaseTest {
    @Mock
    private EstadoGateway estadoGateway;

    @Mock
    private CidadeGateway cidadeGateway;

    @InjectMocks
    private ListCidadeUseCase listCidadeUseCase;

    @Test
    void testListCidadeSuccess() {

        List<Cidade> cidades = Arrays.asList(
                new Cidade(1L, "Teste Um", estadoGateway.findById(1L)),
                new Cidade(2L, "Teste Dois", estadoGateway.findById(2L)));
        Mockito.when(cidadeGateway.findAll()).thenReturn(cidades);
        List<Cidade> cidadesRecebidas = listCidadeUseCase.execute();
        Assertions.assertNotNull(cidadesRecebidas);
        Assertions.assertEquals(cidades.size(), cidadesRecebidas.size());
    }
}