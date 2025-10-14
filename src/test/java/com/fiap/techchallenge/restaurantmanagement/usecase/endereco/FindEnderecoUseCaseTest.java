package com.fiap.techchallenge.restaurantmanagement.usecase.endereco;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco.FindEnderecoUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FindEnderecoUseCaseTest {

    @Mock
    private EnderecoGateway enderecoGateway;

    @Mock
    private CidadeGateway cidadeGateway;

    @InjectMocks
    private FindEnderecoUseCase findEnderecoUseCase;

    @Test
    public void TestFindEnderecoUseCase() {
        Long enderecoID = 1L;
        Endereco enderecoEsperado = new Endereco(enderecoID,"Logradouro Teste", "123", "Casa 3", "Bairro Teste", "01020020",cidadeGateway.findById(1L));
        Mockito.when(enderecoGateway.findById(enderecoID)).thenReturn(enderecoEsperado);
        Endereco endereco = findEnderecoUseCase.findEndereco(enderecoID);
        Assertions.assertNotNull(endereco);
        Assertions.assertEquals("Logradouro Teste", endereco.getLogradouro());
        Assertions.assertEquals("123",endereco.getNumero());
        Assertions.assertEquals("Casa 3",endereco.getComplemento());
        Assertions.assertEquals("Bairro Teste",endereco.getBairro());
        Assertions.assertEquals("01020020",endereco.getCep());
    }
}