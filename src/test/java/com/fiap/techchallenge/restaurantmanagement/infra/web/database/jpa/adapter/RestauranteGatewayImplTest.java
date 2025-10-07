package com.fiap.techchallenge.restaurantmanagement.infra.web.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.core.domain.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.EnderecoNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.RestaurantNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.UserNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter.RestauranteGatewayImpl;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.*;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.*;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.*;
import com.fiap.techchallenge.restaurantmanagement.infra.web.controller.UsuarioApiController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class RestauranteGatewayImplTest {

    @Autowired
    private RestauranteGatewayImpl restauranteGateway;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private EnderecoMapper enderecoMapper;

    private Usuario usuarioSalvo;
    private Usuario usuario2Salvo;
    private Endereco enderecoSalvo;
    private Endereco endereco2Salvo;

    @TestConfiguration
    static class RestauranteGatewayImplTestConfig {

        @Bean
        public RestauranteGatewayImpl restauranteGateway(RestauranteMapper restauranteMapper,
                                                         RestauranteRepository restauranteRepository,
                                                         UsuarioRepository usuarioRepository,
                                                         EnderecoRepository enderecoRepository) {
            return new RestauranteGatewayImpl(restauranteMapper, restauranteRepository, usuarioRepository, enderecoRepository);
        }

        @Bean
        public RestauranteMapper restauranteMapper(UsuarioMapper usuarioMapper, @Lazy CardapioMapper cardapioMapper, EnderecoMapper enderecoMapper) {
            return new RestauranteMapper(usuarioMapper, cardapioMapper, enderecoMapper);
        }

        @Bean
        public UsuarioMapper usuarioMapper() {
            return new UsuarioMapper();
        }

        @Bean
        public CardapioMapper cardapioMapper(RestauranteMapper restauranteMapper, ItemCardapioMapper itemCardapioMapper) {
            return new CardapioMapper(restauranteMapper, itemCardapioMapper);
        }

        @Bean
        public ItemCardapioMapper itemCardapioMapper() {
            return new ItemCardapioMapper();
        }

        @Bean
        public EnderecoMapper enderecoMapper(CidadeMapper cidadeMapper) {
            return new EnderecoMapper(cidadeMapper);
        }

        @Bean
        public CidadeMapper cidadeMapper(EstadoMapper estadoMapper) {
            return new CidadeMapper(estadoMapper);
        }

        @Bean
        public EstadoMapper estadoMapper() {
            return new EstadoMapper();
        }
    }

    @BeforeEach
    void setUp() {

        EstadoEntity estadoEntity = new EstadoEntity();
        estadoEntity.setNome("São Paulo");
        estadoEntity.setSigla("SP");
        EstadoEntity estadoSalvo = estadoRepository.save(estadoEntity);

        CidadeEntity cidadeEntity = new CidadeEntity();
        cidadeEntity.setNome("São Paulo");
        cidadeEntity.setEstado(estadoSalvo);
        CidadeEntity cidadeSalva = cidadeRepository.save(cidadeEntity);

        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setLogradouro("Logradouro");
        endereco.setNumero("123");
        endereco.setBairro("Bairro");
        endereco.setCep("12345-678");
        endereco.setCidade(cidadeSalva);
        enderecoSalvo = enderecoMapper.toDomain(enderecoRepository.save(endereco));

        EnderecoEntity endereco2 = new EnderecoEntity();
        endereco2.setLogradouro("Logradouro2");
        endereco2.setNumero("123");
        endereco2.setBairro("Bairro");
        endereco2.setCep("12345-678");
        endereco2.setCidade(cidadeSalva);
        endereco2Salvo = enderecoMapper.toDomain(enderecoRepository.save(endereco2));

       UsuarioEntity usuario = new UsuarioEntity();
       usuario.setNome("Humberto");
       usuario.setEmail("humberto@email.com");
       usuario.setSenha("12345678");
       usuario.setEndereco(new EnderecoEntity());
       usuario.setTipo(TipoUsuario.ADMIN);
       usuarioSalvo = usuarioMapper.toDomain(usuarioRepository.save(usuario));

        UsuarioEntity usuario2 = new UsuarioEntity();
        usuario2.setNome("Humberto2");
        usuario2.setEmail("humberto@email.com");
        usuario2.setSenha("12345678");
        usuario2.setEndereco(new EnderecoEntity());
        usuario2.setTipo(TipoUsuario.ADMIN);
        usuario2Salvo = usuarioMapper.toDomain(usuarioRepository.save(usuario2));
    }

    @Test
    void quando_salvarRestaurante_devePersistirComSucesso(){
        // Preparação
        Restaurante restauranteParaSalvar = new Restaurante(
                "Restaurante Teste",
                "Brasileira",
                LocalTime.of(12, 0),
                LocalTime.of(22, 0),
                usuarioSalvo);

        restauranteParaSalvar.associarEndereco(enderecoSalvo);

        // Ação
        Restaurante resultado = restauranteGateway.save(restauranteParaSalvar);

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Restaurante Teste");
        assertThat(resultado.getTipoCozinha()).isEqualTo("Brasileira");

        assertThat(restauranteRepository.findById(resultado.getId())).isPresent();
    }

    @Test
    void quando_salvarRestauranteComUsuarioInexistente_deveLancarExcecao(){
        // Preparação
        Usuario dono = new Usuario(99L, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");

        Restaurante restauranteParaSalvar = new Restaurante("Restaurante", "Cozinha", LocalTime.now(), LocalTime.now(), dono);
        restauranteParaSalvar.associarEndereco(enderecoSalvo);

        // Ação e Verificação
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            restauranteGateway.save(restauranteParaSalvar);
        });
        assertEquals("Usuário não encontrado para associar ao restaurante", exception.getMessage());
    }

    @Test
    void quando_salvarRestauranteComEnderecoInexistente_deveLancarExcecao(){
        // Preparação
        Endereco enderecoInvalido = new Endereco(99L, "Logradouro", "123", "casa", "Bairro", "12345-678", new Cidade());

        Restaurante restauranteParaSalvar = new Restaurante("Restaurante", "Cozinha", LocalTime.now(), LocalTime.now(), usuarioSalvo);
        restauranteParaSalvar.associarEndereco(enderecoInvalido);

        // Ação e Verificação
        EnderecoNotFoundException exception = assertThrows(EnderecoNotFoundException.class, () -> {
            restauranteGateway.save(restauranteParaSalvar);
        });
        assertEquals("Endereço não encontrado para associar ao restaurante", exception.getMessage());
    }

    @Test
    void quando_update_deveAtualizarDadosDoRestaurante() {
        // Preparação
        Restaurante restaurante = new Restaurante("Nome Antigo", "Cozinha Antiga", LocalTime.now(), LocalTime.now(), usuarioSalvo);
        restaurante.associarEndereco(enderecoSalvo);
        Restaurante restauranteSalvo = restauranteGateway.save(restaurante);

        Restaurante dadosAtualizados = new Restaurante("Nome Novo", "Cozinha Nova", LocalTime.of(10, 0), LocalTime.of(20, 0), usuarioSalvo);

        // Ação
        Restaurante resultado = restauranteGateway.update(restauranteSalvo.getId(), dadosAtualizados);

        // Verificação
        assertThat(resultado.getNome()).isEqualTo("Nome Novo");
        assertThat(resultado.getTipoCozinha()).isEqualTo("Cozinha Nova");
        assertThat(resultado.getHorarioAbertura()).isEqualTo(LocalTime.of(10, 0));
        assertThat(resultado.getHorarioFechamento()).isEqualTo(LocalTime.of(20, 0));
    }

    @Test
    void quando_updateRestauranteInexistente_deveLancarException() {
        // Preparação
        Long idInexistente = 99L;
        Restaurante dadosAtualizados = new Restaurante("Nome Novo", "Cozinha Nova", LocalTime.now(), LocalTime.now(), usuarioSalvo);

        // Ação e Verificação
        RestaurantNotFoundException exception = assertThrows(
                RestaurantNotFoundException.class,
                () -> restauranteGateway.update(idInexistente, dadosAtualizados)
        );
        assertThat(exception.getMessage()).isEqualTo("Restaurante com o id " + idInexistente + " não encontrado.");
    }

    @Test
    void quando_findByIdExistente_deveRetornarRestaurante(){
        // Preparação
        Restaurante restaurante = new Restaurante("Restaurante Teste", "Italiana", LocalTime.now(), LocalTime.now(), usuarioSalvo);
        restaurante.associarEndereco(enderecoSalvo);
        Restaurante restauranteSalvo = restauranteGateway.save(restaurante);

        // Ação
        Restaurante restauranteEncontrado = restauranteGateway.findById(restauranteSalvo.getId());

        // Verificação
        assertThat(restauranteEncontrado).isNotNull();
        assertThat(restauranteEncontrado.getId()).isEqualTo(restauranteSalvo.getId());
        assertThat(restauranteEncontrado.getNome()).isEqualTo("Restaurante Teste");
        assertThat(restauranteEncontrado.getTipoCozinha()).isEqualTo("Italiana");


    }

    @Test
    void quando_findByIdInexistente_deveLancarExcecao() {
        // Ação e Verificação
        RestaurantNotFoundException exception = assertThrows(
                RestaurantNotFoundException.class,
                () -> restauranteGateway.findById(99L)
        );

        assertEquals("Restaurante não encontrado", exception.getMessage());
    }

    @Test
    void quando_listarTodosRestaurantes_deveListarComSucesso(){

        Restaurante restaurante1 = new Restaurante("Restaurante A", "Tipo A", LocalTime.now(), LocalTime.now(), usuarioSalvo);
        restaurante1.associarEndereco(enderecoSalvo);
        restauranteGateway.save(restaurante1);

        Restaurante restaurante2 = new Restaurante("Restaurante B", "Tipo B", LocalTime.now(), LocalTime.now(), usuario2Salvo);
        restaurante2.associarEndereco(endereco2Salvo);
        restauranteGateway.save(restaurante2);

        List<Restaurante> restaurantes = restauranteGateway.findAll();

        assertThat(restaurantes).hasSize(2);

    }

    @Test
    void quando_findAllSemRestaurantes_deveRetornarListaVazia() {
        // Ação
        List<Restaurante> resultado = restauranteGateway.findAll();

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado).isEmpty();
    }

    @Test
    void quando_deleteById_deveDeletarComSucesso(){
        // Preparação
        Restaurante restaurante = new Restaurante("Para Deletar", "Qualquer", LocalTime.now(), LocalTime.now(), usuarioSalvo);
        restaurante.associarEndereco(enderecoSalvo);
        Restaurante restauranteSalvo = restauranteGateway.save(restaurante);

        Long restauranteId = restauranteSalvo.getId();

        // Ação
        restauranteGateway.deleteById(restauranteId);

        // Verificação
        Optional<RestauranteEntity> resultado = restauranteRepository.findById(restauranteId);
        assertThat(resultado).isNotPresent();
    }

    @Test
    void quando_deleteByIdInexistente_deveLancarExcecao() {
        // Ação e Verificação
        RestaurantNotFoundException exception = assertThrows(
                RestaurantNotFoundException.class,
                () -> restauranteGateway.deleteById(99L)
        );
        assertEquals("Restaurante com o id 99 não encontrado.", exception.getMessage());
    }
}
