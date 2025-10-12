package com.fiap.techchallenge.restaurantmanagement.usecase.cidade;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade.FindCidadeUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FindCidadeUseCaseTest {
    @Mock
    private CidadeGateway cidadeGateway;

    @Mock
    private EstadoGateway estadoGateway;

    @InjectMocks
    private FindCidadeUseCase findCidadeUseCase;

    @Test
    public void TestFindCidadeUseCase() {

        Long cidadeID = 1L;
        Cidade cidadeEsperado = new Cidade(cidadeID,"Cidade Teste",estadoGateway.findById(1L));
        Mockito.when(cidadeGateway.findById(cidadeID)).thenReturn(cidadeEsperado);
        Cidade cidade = findCidadeUseCase.get(cidadeID);
        Assertions.assertNotNull(cidade);
        assertEquals("Estado Teste",cidade.getNome());
    }

}