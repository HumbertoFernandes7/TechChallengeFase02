package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio.FindCardapioUseCase;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.itemCardapio.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.ItemCardapio;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.ItemCardapioRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.ItemCardapioResponse;
import com.fiap.techchallenge.restaurantmanagement.infra.web.mapper.ItemCardapioWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/item")
public class ItemCardapioApiControler implements iItemCardapioApiController {

    private final CreateItemCardapioUseCase createItemCardapioUseCase;
    private final UpdateItemCardapioUseCase updateItemCardapioUseCase;
    private final FindItemCardapioUseCase findItemCardapioUseCase;
    private final DeleteItemCardapioUseCase deleteItemCardapioUseCase;
    private final ListItemCardapioUseCase listItemCardapioUseCase;

    private final FindCardapioUseCase findCardapioUseCase;


    private final ItemCardapioWebMapper itemCardapioWebMapper;


    @Override
    public ResponseEntity<List<ItemCardapioResponse>> get() {
        List<ItemCardapio> itemCardapioList = listItemCardapioUseCase.execute();
        return ResponseEntity.ok(itemCardapioWebMapper.toResponseList(itemCardapioList));
    }

    @Override
    public ResponseEntity<ItemCardapioResponse> get(Long id) {
        ItemCardapio itemCardapio = findItemCardapioUseCase.execute(id);
        return ResponseEntity.ok(itemCardapioWebMapper.toResponse(itemCardapio));
    }

    @Override
    public ResponseEntity<ItemCardapioResponse> insert(ItemCardapioRequest itemCardapioRequest) {
        Cardapio cardapio = findCardapioUseCase.execute(itemCardapioRequest.getCardapioId());
        ItemCardapio itemCardapio = itemCardapioWebMapper.toDomain(itemCardapioRequest);
        ItemCardapio itemCardapioSalvo = createItemCardapioUseCase.execute(itemCardapio);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemCardapioWebMapper.toResponse(itemCardapioSalvo));
    }

    @Override
    public ResponseEntity<ItemCardapioResponse> update(Long id, ItemCardapioRequest itemCardapio) {
        ItemCardapio itemCardapioParaAtualizar = itemCardapioWebMapper.toDomain(itemCardapio);
        ItemCardapio itemCardapioAtualizado = updateItemCardapioUseCase.execute(id, itemCardapioParaAtualizar);
        return ResponseEntity.ok(itemCardapioWebMapper.toResponse(itemCardapioAtualizado));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        deleteItemCardapioUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
