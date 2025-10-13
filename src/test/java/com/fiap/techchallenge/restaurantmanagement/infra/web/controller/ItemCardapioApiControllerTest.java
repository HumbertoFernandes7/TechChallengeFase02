package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio.FindCardapioUseCase;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.itemCardapio.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.CardapioNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.ItemCardapioNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.ItemCardapioRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemCardapioApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateItemCardapioUseCase createItemCardapioUseCase;

    @MockitoBean
    private FindItemCardapioUseCase findItemCardapioUseCase;

    @MockitoBean
    private ListItemCardapioUseCase listItemCardapioUseCase;

    @MockitoBean
    private UpdateItemCardapioUseCase updateItemCardapioUseCase;

    @MockitoBean
    private DeleteItemCardapioUseCase deleteItemCardapioUseCase;

    @MockitoBean
    private FindCardapioUseCase findCardapioUseCase;

    // Post
    @Test
    void quando_criarItemCardapioComDadosValidos_deveRetornarStatusCreated() throws Exception {
        // Preparação
        ItemCardapioRequest request = new ItemCardapioRequest();
        request.setNome("Item Teste");
        request.setDescricao("Descrição do item");
        request.setPreco(BigDecimal.valueOf(10));
        request.setDisponibilidade(true);
        request.setFotoPrato("foto.jpg");
        request.setCardapioId(1L);

        when(findCardapioUseCase.execute(1L)).thenReturn(new Cardapio(null, new Restaurante(), null));

        ItemCardapio itemCardapioSalvo = new ItemCardapio(1L, "Feijoada", "A melhor da cidade", new BigDecimal("45.50"), true, "/feijoada.jpg");
        when(createItemCardapioUseCase.execute(any(ItemCardapio.class), any(Cardapio.class))).thenReturn(itemCardapioSalvo);

        // Ação e Verificação
        mockMvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Feijoada"))
                .andExpect(jsonPath("$.descricao").value("A melhor da cidade"))
                .andExpect(jsonPath("$.preco").value(45.50))
                .andExpect(jsonPath("$.disponibilidade").value(true))
                .andExpect(jsonPath("$.fotoPrato").value("/feijoada.jpg"));
    }

    @Test
    void quando_criarItemCardapioComDadosInvalidos_deveRetornarStatusNotFound() throws Exception {
        // Preparação
        ItemCardapioRequest request = new ItemCardapioRequest();

        when(findCardapioUseCase.execute(1L)).thenReturn(new Cardapio(null, new Restaurante(), null));

        // Ação
        mockMvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void quando_criarItemCardapioComCardapioInexistente_deveRetornarException() throws Exception {
        // Preparação
        ItemCardapioRequest request = new ItemCardapioRequest();
        request.setNome("Item Teste");
        request.setDescricao("Descrição do item");
        request.setPreco(BigDecimal.valueOf(10));
        request.setDisponibilidade(true);
        request.setFotoPrato("foto.jpg");
        request.setCardapioId(1L);

        when(findCardapioUseCase.execute(1L)).thenThrow(new CardapioNotFoundException("Cardápio não encontrado"));

        // Ação e Verificação
        mockMvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    // Get
    @Test
    void quando_buscarItemCardapioPorIdExistente_deveRetornarStatusOk() throws Exception {
        // Preparação
        Long itemCardapio = 1L;
        ItemCardapio itemCardapioSalvo = new ItemCardapio(1L, "Feijoada", "A melhor da cidade", new BigDecimal("45.50"), true, "/feijoada.jpg");

        when(findItemCardapioUseCase.execute(itemCardapio)).thenReturn(itemCardapioSalvo);

        //Ação e Verificação
        mockMvc.perform(get("/item/{id}", itemCardapio))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Feijoada")))
                .andExpect(jsonPath("$.descricao", is("A melhor da cidade")))
                .andExpect(jsonPath("$.preco", is(45.50)))
                .andExpect(jsonPath("$.disponibilidade", is(true)))
                .andExpect(jsonPath("$.fotoPrato", is("/feijoada.jpg")));
    }

    @Test
    void quando_buscarItemPorIdInexistente_deveRetornarStatusNotFound() throws Exception {
        // Preparação
        Long idInexistente = 99L;
        when(findItemCardapioUseCase.execute(idInexistente)).thenThrow(new ItemCardapioNotFoundException("Item não encontrado"));

        // Ação e Verificação
        mockMvc.perform(get("/item/{id}", idInexistente))
                .andExpect(status().isNotFound());
    }

    @Test
    void quando_listarTodosItens_deveRetornarStatusOkELista() throws Exception {
        // Preparação
        List<ItemCardapio> lista = Arrays.asList(
                new ItemCardapio(1L, "Item A", "Desc A", BigDecimal.ONE, true, "fotoA.jpg"),
                new ItemCardapio(2L, "Item B", "Desc B", BigDecimal.TEN, true, "fotoB.jpg")
        );
        when(listItemCardapioUseCase.execute()).thenReturn(lista);

        // Ação e Verificação
        mockMvc.perform(get("/item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Item A")))
                .andExpect(jsonPath("$[1].nome", is("Item B")));
    }

    @Test
    void quando_listarTodosItensSemResultados_deveRetornarStatusOkEListaVazia() throws Exception {
        when(listItemCardapioUseCase.execute()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // Post
    @Test
    void quando_atualizarItemExistente_deveRetornarStatusOk() throws Exception {
        // Preparação
        Long itemId = 1L;
        ItemCardapioRequest request = new ItemCardapioRequest();
        request.setNome("Nome antigo");
        request.setDescricao("Desc antigo");
        request.setPreco(BigDecimal.ZERO);
        request.setDisponibilidade(true);
        request.setFotoPrato("fotoAntiga.jpg");

        ItemCardapio itemAtualizado = new ItemCardapio(itemId, "Nome Atualizado", "Desc Atualizada", BigDecimal.valueOf(10.00), false, "fotoNova.jpg");
        when(updateItemCardapioUseCase.execute(eq(itemId), any(ItemCardapio.class))).thenReturn(itemAtualizado);

        // Ação e Verificação
        mockMvc.perform(put("/item/{id}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Nome Atualizado")))
                .andExpect(jsonPath("$.descricao", is("Desc Atualizada")))
                .andExpect(jsonPath("$.preco", is(10.00)))
                .andExpect(jsonPath("$.disponibilidade", is(false)))
                .andExpect(jsonPath("$.fotoPrato", is("fotoNova.jpg")));

    }

    @Test
    void quando_atualizarItemInexistente_deveRetornarStatusNotFound() throws Exception {
        // Preparação
        Long idInexistente = 99L;
        ItemCardapioRequest request = new ItemCardapioRequest();
        request.setNome("Nome Qualquer");
        request.setDescricao("Desc");
        request.setPreco(BigDecimal.ONE);
        request.setDisponibilidade(true);
        request.setFotoPrato("foto.jpg");

        when(updateItemCardapioUseCase.execute(eq(idInexistente), any(ItemCardapio.class)))
                .thenThrow(new ItemCardapioNotFoundException("Item não encontrado"));

        // Ação e Verificação
        mockMvc.perform(put("/item/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    // Delete
    @Test
    void quando_deletarItemExistente_deveRetornarStatusNoContent() throws Exception {
        Long itemId = 1L;
        doNothing().when(deleteItemCardapioUseCase).execute(itemId);
        mockMvc.perform(delete("/item/{id}", itemId))
                .andExpect(status().isNoContent());
    }

    @Test
    void quando_deletarItemInexistente_deveRetornarStatusNotFound() throws Exception {
        Long idInexistente = 99L;
        doThrow(new ItemCardapioNotFoundException("Item não encontrado")).when(deleteItemCardapioUseCase).execute(idInexistente);
        mockMvc.perform(delete("/item/{id}", idInexistente))
                .andExpect(status().isNotFound());
    }
}