package com.fiap.techchallenge.restaurantmanagement.infra.web;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("cidade")
@RequiredArgsConstructor
public class CidadeApiController implements  ICidadeApiController<Cidade> {
    @Override
    public ResponseEntity<List<Cidade>> get() {
        return null;
    }

    @Override
    public ResponseEntity<Cidade> get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> insert(Cidade cidade) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update(Cidade cidade) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return null;
    }
}
