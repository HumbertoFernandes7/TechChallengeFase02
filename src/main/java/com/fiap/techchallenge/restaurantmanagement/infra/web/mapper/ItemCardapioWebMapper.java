package com.fiap.techchallenge.restaurantmanagement.infra.web.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.ItemCardapio;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.ItemCardapioRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.ItemCardapioResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemCardapioWebMapper {

    private final ModelMapper modelMapper;

    public ItemCardapio toDomain(ItemCardapioRequest itemCardapioRequest) {
        return new ItemCardapio(
                null,
                itemCardapioRequest.getNome(),
                itemCardapioRequest.getDescricao(),
                itemCardapioRequest.getPreco(),
                itemCardapioRequest.isDisponibilidade(),
                itemCardapioRequest.getFotoPrato()
                );
    }

    public ItemCardapioResponse toResponse(ItemCardapio itemCardapioSalvo) {
        return modelMapper.map(itemCardapioSalvo, ItemCardapioResponse.class);
    }

    public List<ItemCardapioResponse> toResponseList(List<ItemCardapio> itemCardapioList) {
        return itemCardapioList.stream().map(this::toResponse).toList();
    }
}
