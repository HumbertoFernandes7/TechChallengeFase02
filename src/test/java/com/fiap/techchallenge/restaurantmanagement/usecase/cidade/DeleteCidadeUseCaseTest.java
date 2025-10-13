package com.fiap.techchallenge.restaurantmanagement.usecase.cidade;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CidadeGateway;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade.DeleteCidadeUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCidadeUseCaseTest {

    @Mock
    private CidadeGateway cidadeGateway;

    @InjectMocks
    private DeleteCidadeUseCase deleteCidadeUseCase;
    @Test
    public void deleteCidadeById_sucess(){
        Long cidadeID = 1L;
        doNothing().when(cidadeGateway).deleteById(anyLong());
        deleteCidadeUseCase.delete(cidadeID);
        verify(cidadeGateway, times(1)).deleteById(cidadeID);
    }
}