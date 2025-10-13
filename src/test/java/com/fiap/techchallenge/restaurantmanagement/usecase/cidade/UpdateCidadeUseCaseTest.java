package com.fiap.techchallenge.restaurantmanagement.usecase.cidade;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade.UpdateCidadeUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UpdateCidadeUseCaseTest {
    @Mock
    private EstadoGateway estadoGateway;

    @Mock
    private CidadeGateway cidadeGateway;

    @InjectMocks
    private UpdateCidadeUseCase updateCidadeUseCase;

    @Test
    public void testUpdateCidadeUseCaseSuccess() {
        Cidade cidade = new Cidade(1L, "Cidade Teste", estadoGateway.findById(1L));
        Cidade cidadeAtualizar = new Cidade(2L, "Cidade Teste Atualizada", estadoGateway.findById(2L));
        Mockito.when(cidadeGateway.update(Mockito.any(Cidade.class))).thenAnswer(invocationOnMock ->
        {
            Cidade cidadeAtualizado = invocationOnMock.getArgument(0);
            return new Cidade(
                    cidade.getId(),
                    cidadeAtualizado.getNome(), estadoGateway.findById(cidadeAtualizado.getId()));
        });
        Cidade execute = updateCidadeUseCase.execute(cidadeAtualizar);
        assertEquals(1L, execute.getId());
        assertEquals("Cidade Teste Atualizada", execute.getNome());
    }
}