package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio.*;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante.FindRestauranteUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.CardapioNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.CardapioRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.mapper.CardapioWebMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardapioController.class)
@ActiveProfiles("test")
class CardapioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateCardapioUseCase createCardapioUseCase;

    @MockBean
    private ListCardapioUseCase listCardapioUseCase;

    @MockBean
    private FindCardapioUseCase findCardapioUseCase;

    @MockBean
    private DeleteCardapioUseCase deleteCardapioUseCase;

    @MockBean
    private FindRestauranteUseCase findRestauranteUseCase;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CardapioWebMapper cardapioWebMapper() {
            // O CardapioWebMapper depende de ModelMapper e RestauranteWebMapper
            // Como RestauranteWebMapper não está no contexto, criamos uma instância simples
            return new CardapioWebMapper(new ModelMapper(), null);
        }
    }


    @Test
    void quando_inserirCardapio_deveRetornarStatusCreated() throws Exception {
        // Preparação
        CardapioRequest request = new CardapioRequest();
        request.setRestaurante_id(1L);

        Restaurante restaurante = new Restaurante();
        Cardapio cardapioSalvo = new Cardapio(1L, restaurante, Collections.emptyList());

        when(findRestauranteUseCase.execute(anyLong())).thenReturn(restaurante);
        when(createCardapioUseCase.execute(any(Cardapio.class))).thenReturn(cardapioSalvo);

        // Ação & Verificação
        mockMvc.perform(post("/cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void quando_buscarCardapioPorId_deveRetornarStatusOk() throws Exception {
        // Preparação
        Cardapio cardapio = new Cardapio(1L, new Restaurante(), Collections.emptyList());
        when(findCardapioUseCase.execute(1L)).thenReturn(cardapio);

        // Ação & Verificação
        mockMvc.perform(get("/cardapio/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void quando_buscarCardapioPorIdInexistente_deveRetornarNotFound() throws Exception {
        // Preparação
        when(findCardapioUseCase.execute(anyLong())).thenThrow(new CardapioNotFoundException("Cardápio não encontrado"));

        // Ação & Verificação
        mockMvc.perform(get("/cardapio/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void quando_listarCardapios_deveRetornarStatusOk() throws Exception {
        // Preparação
        Cardapio cardapio = new Cardapio(1L, new Restaurante(), Collections.emptyList());
        when(listCardapioUseCase.execute()).thenReturn(Collections.singletonList(cardapio));

        // Ação & Verificação
        mockMvc.perform(get("/cardapio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void quando_deletarCardapio_deveRetornarNoContent() throws Exception {
        // Preparação
        doNothing().when(deleteCardapioUseCase).execute(anyLong());

        // Ação & Verificação
        mockMvc.perform(delete("/cardapio/1"))
                .andExpect(status().isNoContent());
    }
}