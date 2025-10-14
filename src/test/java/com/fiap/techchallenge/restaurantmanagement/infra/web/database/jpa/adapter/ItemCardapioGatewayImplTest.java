package com.fiap.techchallenge.restaurantmanagement.infra.web.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.ItemCardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.ItemCardapioNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.*;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.CardapioMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ItemCardapioGatewayImplTest {

    @Autowired
    private ItemCardapioGateway itemCardapioGateway;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private CardapioMapper cardapioMapper;

    private Cardapio cardapioSalvo;

    @BeforeEach
    void setUp() {

        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setLogradouro("Logradouro");
        endereco.setNumero("123");
        endereco.setBairro("Bairro");
        endereco.setCep("12345-678");
        EnderecoEntity enderecoSalvo = enderecoRepository.save(endereco);

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Humberto");
        usuario.setEmail("humberto@email.com");
        usuario.setSenha("12345678");
        usuario.setTipo(TipoUsuario.ADMIN);
        usuario.setEndereco(enderecoSalvo);
        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuario);

        RestauranteEntity restaurante = new RestauranteEntity();
        restaurante.setNome("Restaurante Teste");
        restaurante.setTipoCozinha("Brasileira");
        restaurante.setHorarioAbertura(LocalTime.now());
        restaurante.setHorarioFechamento(LocalTime.now());
        restaurante.setDonoRestaurante(usuarioSalvo);
        restaurante.setEndereco(enderecoSalvo);
        restauranteRepository.save(restaurante);

        CardapioEntity cardapio = new CardapioEntity();
        cardapio.setRestaurante(restaurante);
        cardapioSalvo = cardapioMapper.toDomain(cardapioRepository.save(cardapio));
    }

    @Test
    void quando_salvarItemCardapio_devePersistirComSucesso(){
        // Preparação
        ItemCardapio itemCardapio = new ItemCardapio(null, "Item Teste", "Descrição do item", BigDecimal.valueOf(10.0), true, "foto.jpg");

        // Ação
        ItemCardapio resultado = itemCardapioGateway.save(itemCardapio, cardapioSalvo);

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Item Teste");
        assertThat(resultado.getDescricao()).isEqualTo("Descrição do item");
        assertThat(resultado.getPreco()).isEqualTo(BigDecimal.valueOf(10.0));
        assertThat(resultado.isDisponibilidade()).isTrue();
        assertThat(resultado.getFotoPrato()).isEqualTo("foto.jpg");
    }

    @Test
    void quando_updateItemCardapio_deveAtualizarDados(){
        // Preparação
        ItemCardapio item = new ItemCardapio(null, "Nome Antigo", "Desc Antiga", BigDecimal.valueOf(10.0), true, "foto.jpg");
        ItemCardapio itemSalvo = itemCardapioGateway.save(item, cardapioSalvo);
        ItemCardapio dadosAtualizados = new ItemCardapio(null, "Nome Novo", "Desc Nova", BigDecimal.valueOf(15.0), false, "fotoNova.jpg");

        // Ação
        ItemCardapio resultado = itemCardapioGateway.update(itemSalvo.getId(), dadosAtualizados);

        // Verificação
        assertThat(resultado.getNome()).isEqualTo("Nome Novo");
        assertThat(resultado.getDescricao()).isEqualTo("Desc Nova");
        assertThat(resultado.getPreco()).isEqualTo(BigDecimal.valueOf(15.0));
        assertThat(resultado.isDisponibilidade()).isFalse();
        assertThat(resultado.getFotoPrato()).isEqualTo("fotoNova.jpg");
    }

    @Test
    void quando_updateItemCardapioComIdInexistente_deveLancarException(){
        // Preparação
        Long idInexistente = 99L;
        ItemCardapio item = new ItemCardapio(null, "Nome Antigo", "Desc Antiga", BigDecimal.valueOf(10.0), true, "foto.jpg");
        ItemCardapio itemSalvo = itemCardapioGateway.save(item, cardapioSalvo);
        ItemCardapio dadosAtualizados = new ItemCardapio(idInexistente, "Nome Novo", "Desc Nova", BigDecimal.valueOf(15.0), false, "fotoNova.jpg");

        // Ação e Verificação
        ItemCardapioNotFoundException exception = assertThrows(
                ItemCardapioNotFoundException.class,
                () -> itemCardapioGateway.update(idInexistente, dadosAtualizados)
        );

        // Verificação
        assertThat(exception.getMessage()).isEqualTo("Item de cardápio com o id " + idInexistente + " não encontrado.");
    }

    @Test
    void quando_findByIdExistente_deveRetornarRestaurante(){
        // Preparação
        ItemCardapio item = new ItemCardapio(null, "Item Teste", "Descrição do item", BigDecimal.valueOf(10.0), true, "foto.jpg");
        ItemCardapio itemSalvo = itemCardapioGateway.save(item, cardapioSalvo);

        // Ação
        ItemCardapio resultado = itemCardapioGateway.findById(itemSalvo.getId());

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(itemSalvo.getId());
        assertThat(resultado.getNome()).isEqualTo("Item Teste");
        assertThat(resultado.getDescricao()).isEqualTo("Descrição do item");
        assertThat(resultado.getPreco()).isEqualTo(BigDecimal.valueOf(10.0));
        assertThat(resultado.isDisponibilidade()).isTrue();
        assertThat(resultado.getFotoPrato()).isEqualTo("foto.jpg");

    }

    @Test
    void quando_findByIdInexistente_deveLancarExcecao() {
        // Preparação
        Long idInexistente = 99L;

        // Ação e Verificação
        ItemCardapioNotFoundException exception = assertThrows(
                ItemCardapioNotFoundException.class,
                () -> itemCardapioGateway.findById(idInexistente)
        );

        assertEquals("Item de cardápio com o id " + idInexistente + " não encontrado.", exception.getMessage());
    }

    @Test
    void quando_findaAllItensCardapio_deveListarComSucesso(){

        ItemCardapio item = new ItemCardapio(null, "Item Teste", "Descrição do item", BigDecimal.valueOf(10.0), true, "foto.jpg");
        ItemCardapio item2 = new ItemCardapio(null, "Item Teste2", "Descrição do item2", BigDecimal.valueOf(15.0), false, "foto2.jpg");
        itemCardapioGateway.save(item, cardapioSalvo);
        itemCardapioGateway.save(item2, cardapioSalvo);

        List<ItemCardapio> itensEncontrados = itemCardapioGateway.findAll();

        assertThat(itensEncontrados).hasSize(2);

    }

    @Test
    void quando_findAllSemItensCardapio_deveRetornarListaVazia(){

        List<ItemCardapio> itensEncontrados = itemCardapioGateway.findAll();

        assertThat(itensEncontrados).isEmpty();
        assertThat(itensEncontrados).isNotNull();
        assertThat(itensEncontrados).hasSize(0);
    }

    @Test
    void quando_deleteById_deveDeletarComSucesso(){
        // Preparação
        ItemCardapio itemCardapio = new ItemCardapio(null, "Item Teste", "Descrição do item", BigDecimal.valueOf(10.0), true, "foto.jpg");
        ItemCardapio itemCardapioSalvo = itemCardapioGateway.save(itemCardapio, cardapioSalvo);
        Long itemCardapioId = itemCardapioSalvo.getId();

        // Ação
        itemCardapioGateway.deleteById(itemCardapioId);

        // Verificação
        Optional<ItemCardapioEntity> resultado = itemCardapioRepository.findById(itemCardapioId);
        assertThat(resultado).isNotPresent();
    }

    @Test
    void quando_deleteByIdInexistente_deveLancarExcecao() {
        // Ação e Verificação
        ItemCardapioNotFoundException exception = assertThrows(
                ItemCardapioNotFoundException.class,
                () -> itemCardapioGateway.deleteById(99L)
        );
        assertEquals("Item de cardápio com o id 99 não encontrado.", exception.getMessage());
    }
}
