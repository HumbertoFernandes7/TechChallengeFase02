package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fiap.techchallenge.restaurantmanagement.application.usecase.cardapio.*;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante.FindRestauranteUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.CardapioRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.CardapioResponse;
import com.fiap.techchallenge.restaurantmanagement.infra.web.mapper.CardapioWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cardapio")
public class CardapioController implements ICardapioController {

    private final CreateCardapioUseCase createCardapioUseCase;
    private final UpdateCardapioUseCase updateCardapioUseCase;
    private final ListCardapioUseCase listCardapioUseCase;
    private final FindCardapioUseCase findCardapioUseCase;
    private final DeleteCardapioUseCase deleteCardapioUseCase;

    private final FindRestauranteUseCase findRestauranteUseCase;

    private final CardapioWebMapper cardapioWebMapper;

    @Override
    public ResponseEntity<List<CardapioResponse>> get() {
        List<Cardapio> cardapios = listCardapioUseCase.execute();
        return ResponseEntity.ok(cardapioWebMapper.toResponseList(cardapios));
    }

    @Override
    public ResponseEntity<CardapioResponse> get(Long id) {
        Cardapio cardapio = findCardapioUseCase.execute(id);
        return ResponseEntity.ok(cardapioWebMapper.toResponse(cardapio));
    }

    @Override
    public ResponseEntity<CardapioResponse> insert(CardapioRequest cardapioRequest) {
        Restaurante restaurante = findRestauranteUseCase.execute(cardapioRequest.getRestaurante_id());
        Cardapio cardapio = cardapioWebMapper.toDomain(restaurante);
        Cardapio cardapioSalvo = createCardapioUseCase.execute(cardapio);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardapioWebMapper.toResponse(cardapioSalvo));
    }

    @Override
    public ResponseEntity<CardapioResponse> update(CardapioRequest cardapio) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        deleteCardapioUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}