package com.fiap.techchallenge.restaurantmanagement.infra.web.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.CardapioNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter.CardapioGatewayImpl;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EnderecoEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.RestauranteEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.UsuarioEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.RestauranteMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.CardapioRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EnderecoRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.RestauranteRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private RestauranteMapper restauranteMapper;

    private Restaurante restauranteSalvo;
    private Restaurante restauranteSalvo2; // Variável para o segundo restaurante

    @BeforeEach
    void setUp() {
        // Restaurante 1
        UsuarioEntity dono1 = new UsuarioEntity();
        dono1.setNome("Dono Teste 1");
        dono1.setEmail("dono1@teste.com");
        dono1.setSenha("12345678");
        dono1.setTipo(TipoUsuario.DONO_RESTAURANTE);
        UsuarioEntity donoSalvo1 = usuarioRepository.save(dono1);

        EnderecoEntity endereco1 = new EnderecoEntity();
        endereco1.setLogradouro("Rua Teste 1");
        EnderecoEntity enderecoSalvo1 = enderecoRepository.save(endereco1);

        RestauranteEntity restauranteEntity1 = new RestauranteEntity();
        restauranteEntity1.setNome("Restaurante para Cardapio 1");
        restauranteEntity1.setDonoRestaurante(donoSalvo1);
        restauranteEntity1.setEndereco(enderecoSalvo1);
        RestauranteEntity entitySalva1 = restauranteRepository.save(restauranteEntity1);
        restauranteSalvo = restauranteMapper.toDomain(entitySalva1);

        // CORREÇÃO: Crie um segundo restaurante para o teste de findAll
        UsuarioEntity dono2 = new UsuarioEntity();
        dono2.setNome("Dono Teste 2");
        dono2.setEmail("dono2@teste.com");
        dono2.setSenha("12345678");
        dono2.setTipo(TipoUsuario.DONO_RESTAURANTE);
        UsuarioEntity donoSalvo2 = usuarioRepository.save(dono2);

        EnderecoEntity endereco2 = new EnderecoEntity();
        endereco2.setLogradouro("Rua Teste 2");
        EnderecoEntity enderecoSalvo2 = enderecoRepository.save(endereco2);

        RestauranteEntity restauranteEntity2 = new RestauranteEntity();
        restauranteEntity2.setNome("Restaurante para Cardapio 2");
        restauranteEntity2.setDonoRestaurante(donoSalvo2);
        restauranteEntity2.setEndereco(enderecoSalvo2);
        RestauranteEntity entitySalva2 = restauranteRepository.save(restauranteEntity2);
        restauranteSalvo2 = restauranteMapper.toDomain(entitySalva2);
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
        cardapioGateway.save(new Cardapio(restauranteSalvo2));

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