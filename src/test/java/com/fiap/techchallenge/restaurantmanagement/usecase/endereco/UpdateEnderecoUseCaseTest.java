package com.fiap.techchallenge.restaurantmanagement.usecase.endereco;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco.UpdateEnderecoUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateEnderecoUseCaseTest {

    @Mock
    private EnderecoGateway enderecoGateway;

    @Mock
    private CidadeGateway cidadeGateway;

    @InjectMocks
    private UpdateEnderecoUseCase updateEnderecoUseCase;

    @Test
    public void testUpdateCidadeUseCaseSuccess() {


        Endereco endereco = new Endereco(1L,"Logradouro Teste", "123", "Casa 3", "Bairro Teste", "01020020", cidadeGateway.findById(1L));
        Endereco enderecoAtualizar = new Endereco(2L, "Logradouro Teste", "123", "Casa 3", "Bairro Teste", "01020020", cidadeGateway.findById(2L));
        Mockito.when(enderecoGateway.update(Mockito.any(Endereco.class))).thenAnswer(invocationOnMock ->
        {
            Endereco enderecoAtualizado = invocationOnMock.getArgument(0);
            return new Endereco(
                    endereco.getId(),
                    enderecoAtualizar.getLogradouro(), enderecoAtualizar.getNumero(),enderecoAtualizar.getComplemento(),enderecoAtualizar.getBairro(),enderecoAtualizar.getCep(),enderecoAtualizar.getCidade());
        });
        Endereco execute = updateEnderecoUseCase.execute(enderecoAtualizar);
        Assertions.assertEquals(1L, execute.getId());
        Assertions.assertEquals("Logradouro Teste", execute.getLogradouro());
        Assertions.assertEquals("123",execute.getNumero());
        Assertions.assertEquals("Casa 3",execute.getComplemento());
        Assertions.assertEquals("Bairro Teste",execute.getBairro());
        Assertions.assertEquals("01020020",execute.getCep());
    }
}