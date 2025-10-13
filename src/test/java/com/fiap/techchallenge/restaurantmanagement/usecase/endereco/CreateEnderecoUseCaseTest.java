package com.fiap.techchallenge.restaurantmanagement.usecase.endereco;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco.CreateEnderecoUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import org.aspectj.apache.bcel.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateEnderecoUseCaseTest {

    @Mock
    private EnderecoGateway enderecoGateway;

    @Mock
    private CidadeGateway cidadeGateway;

    @InjectMocks
    private CreateEnderecoUseCase createEnderecoUseCase;

    @Test
    public void testCreateEnderecoSuccess() {
        Endereco enderecoSalvar =
                new Endereco(null, "Logradouro Teste", "123", "Casa 3", "Bairro Teste", "01020020", cidadeGateway.findById(1L));
        Endereco enderecoSalvo =
                new Endereco(1L, "Logradouro Teste", "123", "Casa 3", "Bairro Teste", "01020020", cidadeGateway.findById(1L));
        Mockito.when(enderecoGateway.save(enderecoSalvar)).thenReturn(enderecoSalvo);

        Endereco enderecoResultado = createEnderecoUseCase.saveEndereco(enderecoSalvar);
        Assertions.assertNotNull(enderecoResultado);
        Assertions.assertEquals("Logradouro Teste", enderecoResultado.getLogradouro());
        Assertions.assertEquals("123",enderecoResultado.getNumero());
        Assertions.assertEquals("Casa 3",enderecoResultado.getComplemento());
        Assertions.assertEquals("Bairro Teste",enderecoResultado.getBairro());
        Assertions.assertEquals("01020020",enderecoResultado.getCep());
    }
}