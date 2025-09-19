package com.fiap.techchallenge.restaurantmanagement.infra.web.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RestauranteWebMapper {

    private final ModelMapper modelMapper;

}
