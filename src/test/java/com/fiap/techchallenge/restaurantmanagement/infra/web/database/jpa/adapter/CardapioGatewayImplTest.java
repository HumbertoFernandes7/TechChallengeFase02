package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.CardapioNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.RestauranteEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.RestauranteMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.CardapioRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CardapioGatewayImplTest {

    @Autowired
    private CardapioGatewayImpl cardapioGateway;

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteMapper restauranteMapper;

    private Restaurante restauranteSalvo;

    @BeforeEach
    void setUp() {
        RestauranteEntity restauranteEntity = new RestauranteEntity();
        restauranteEntity.setNome("Restaurante para Cardapio");
        RestauranteEntity entitySalva = restauranteRepository.save(restauranteEntity);
        restauranteSalvo = restauranteMapper.toDomain(entitySalva);
    }

    @Test
    void quando_salvarCardapio_devePersistirComSucesso() {
        // Preparação
        Cardapio cardapio = new Cardapio(restauranteSalvo);

        // Ação
        Cardapio resultado = cardapioGateway.save(cardapio);

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getRestaurante().getId()).isEqualTo(restauranteSalvo.getId());
        assertThat(cardapioRepository.findById(resultado.getId())).isPresent();
    }

    @Test
    void quando_findByIdExistente_deveRetornarCardapio() {
        // Preparação
        Cardapio cardapio = new Cardapio(restauranteSalvo);
        Cardapio cardapioSalvoNoTeste = cardapioGateway.save(cardapio);

        // Ação
        Cardapio resultado = cardapioGateway.findById(cardapioSalvoNoTeste.getId());

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(cardapioSalvoNoTeste.getId());
    }

    @Test
    void quando_findByIdInexistente_deveLancarExcecao() {
        // Ação e Verificação
        assertThrows(CardapioNotFoundException.class, () -> cardapioGateway.findById(999L));
    }

    @Test
    void quando_findAll_deveRetornarTodosOsCardapios() {
        // Preparação
        cardapioGateway.save(new Cardapio(restauranteSalvo));
        cardapioGateway.save(new Cardapio(restauranteSalvo));

        // Ação
        List<Cardapio> resultado = cardapioGateway.findAll();

        // Verificação
        assertThat(resultado).hasSize(2);
    }

    @Test
    void quando_deleteById_deveRemoverORegistro() {
        // Preparação
        Cardapio cardapioSalvoNoTeste = cardapioGateway.save(new Cardapio(restauranteSalvo));
        Long id = cardapioSalvoNoTeste.getId();

        // Ação
        cardapioGateway.deleteById(id);

        // Verificação
        assertThat(cardapioRepository.findById(id)).isNotPresent();
    }

    @Test
    void quando_deleteByIdInexistente_deveLancarExcecao() {
        // Ação e Verificação
        assertThrows(CardapioNotFoundException.class, () -> cardapioGateway.deleteById(999L));
    }
}