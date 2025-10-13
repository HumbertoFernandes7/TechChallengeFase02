package com.fiap.techchallenge.restaurantmanagement.usecase.endereco;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EnderecoGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco.DeleteEnderecoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteEnderecoUseCaseTest {

    @Mock
    private EnderecoGateway enderecoGateway;

    @InjectMocks
    private DeleteEnderecoUseCase deleteEnderecoUseCase;

    @Test
    public void deleteEnderecoById_sucess(){
        Long enderecoID = 1L;
        doNothing().when(enderecoGateway).deleteById(anyLong());
        deleteEnderecoUseCase.deleteEndereco(enderecoID);
        verify(enderecoGateway, times(1)).deleteById(enderecoID);
    }
}