package com.fiap.techchallenge.restaurantmanagement.usecase.cidade;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade.CreateCidadeUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateCidadeUseCaseTest {

    @Mock
    private EstadoGateway estadoGateway;
    @Mock
    private CidadeGateway cidadeGateway;

    @InjectMocks
    private CreateCidadeUseCase createCidadeUseCase;

    @Test
    public void testCreateCidadeSuccess() {
        Long cidadeID = 1L;
        Cidade cidadeSalvar = new Cidade(null, "Cidade Teste", estadoGateway.findById(1L));
        Cidade cidadeSalvo = new Cidade(cidadeID, "Cidade Teste", estadoGateway.findById(1L));
        Mockito.when(cidadeGateway.save(cidadeSalvar)).thenReturn(cidadeSalvo);

        Cidade cidadeResultado = createCidadeUseCase.saveCidade(cidadeSalvar);
        Assertions.assertNotNull(cidadeResultado);
        Assertions.assertEquals("Cidade Teste", cidadeResultado.getNome());
        Mockito.verify(cidadeGateway).save(cidadeSalvar);
    }
}