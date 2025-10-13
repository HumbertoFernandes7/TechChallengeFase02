package com.fiap.techchallenge.restaurantmanagement.usecase.endereco;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco.FindEnderecoUseCase;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco.ListEnderecoUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
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
public class ListEnderecoUseCaseTest {

    @Mock
    private EnderecoGateway enderecoGateway;

    @Mock
    private CidadeGateway cidadeGateway;

    @InjectMocks
    private ListEnderecoUseCase listEnderecoUseCase;

    @Test
    void testListCidadeSuccess() {
        List<Endereco> enderecos = Arrays.asList(
                new Endereco(null, "Logradouro Teste", "123", "Casa 3", "Bairro Teste", "01020020", cidadeGateway.findById(1L)),
                new Endereco(1L, "Logradouro Teste", "123", "Casa 3", "Bairro Teste", "01020020", cidadeGateway.findById(1L)));
        Mockito.when(enderecoGateway.findAll()).thenReturn(enderecos);
        List<Endereco> enderecosRecebidos = listEnderecoUseCase.findEndereco();
        Assertions.assertNotNull(enderecosRecebidos);
        Assertions.assertEquals(enderecos.size(), enderecosRecebidos.size());
    }
}