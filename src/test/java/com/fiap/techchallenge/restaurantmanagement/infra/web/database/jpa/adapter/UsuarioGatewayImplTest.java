package com.fiap.techchallenge.restaurantmanagement.infra.web.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.UsuarioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.EnderecoNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.UserNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.*;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.EnderecoMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.CidadeRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EnderecoRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EstadoRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UsuarioGatewayImplTest {

    @Autowired
    private UsuarioGateway usuarioGateway;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    private Endereco enderecoSalvo;
    private Endereco enderecoSalvo2;

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
        endereco2.setLogradouro("Logradouro");
        endereco2.setNumero("123");
        endereco2.setBairro("Bairro");
        endereco2.setCep("12345-678");
        endereco2.setCidade(cidadeSalva);
        enderecoSalvo2 = enderecoMapper.toDomain(enderecoRepository.save(endereco2));
    }

    @Test
    void quando_salvarUsuario_devePersistirComSucesso(){
        // Preparação
        Usuario usuario = new Usuario(null, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");

        // Ação
        Usuario resultado = usuarioGateway.save(usuario, enderecoSalvo.getId());

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Humberto");
        assertThat(resultado.getEmail()).isEqualTo("humberto@email.com");
        assertThat(resultado.getTipo()).isEqualTo(TipoUsuario.ADMIN);
        assertThat(resultado.getSenha()).isEqualTo("12345678");


        assertThat(usuarioRepository.findById(resultado.getId())).isPresent();
    }

    @Test
    void quando_salvarUsuarioComEnderecoInexistente_deveLancarExcecao(){
        // Preparação
        Usuario usuario = new Usuario(null, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");

        // Ação e Verificação
        EnderecoNotFoundException exception = assertThrows(EnderecoNotFoundException.class, () -> {
            usuarioGateway.save(usuario, 99L);
        });
        assertEquals("Endereço não encontrado para associar ao usuário", exception.getMessage());
    }

    @Test
    void quando_update_deveAtualizarDadosDoUsuarioComSucesso() {
        // Preparação
        Usuario usuario = new Usuario(null, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");
        Usuario usuarioSalvo = usuarioGateway.save(usuario, enderecoSalvo.getId());
        Usuario dadosAtualizados = new Usuario(usuarioSalvo.getId(), "Humberto Atualizado", "emailAlterado@email.com", TipoUsuario.CLIENTE, "12345678");

        // Ação
        Usuario resultado = usuarioGateway.update(dadosAtualizados);

        // Verificação
        assertThat(resultado.getNome()).isEqualTo("Humberto Atualizado");
        assertThat(resultado.getEmail()).isEqualTo("emailAlterado@email.com");
        assertThat(resultado.getTipo()).isEqualTo(TipoUsuario.CLIENTE);
        assertThat(resultado.getSenha()).isEqualTo("12345678");

    }

    @Test
    void quando_findByIdExistente_deveRetornarRestaurante(){
        // Preparação
        Usuario usuario = new Usuario(null, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");
        Usuario usuarioSalvo = usuarioGateway.save(usuario, enderecoSalvo.getId());

        // Ação
        Usuario usuarioEncontrado = usuarioGateway.findById(usuarioSalvo.getId());

        // Verificação
        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado.getId()).isNotNull();
        assertThat(usuarioEncontrado.getId()).isEqualTo(usuarioSalvo.getId());
        assertThat(usuarioEncontrado.getNome()).isEqualTo("Humberto");
        assertThat(usuarioEncontrado.getEmail()).isEqualTo("humberto@email.com");
        assertThat(usuarioEncontrado.getTipo()).isEqualTo(TipoUsuario.ADMIN);
        assertThat(usuarioEncontrado.getSenha()).isEqualTo("12345678");
    }

    @Test
    void quando_findByIdInexistente_deveLancarExcecao() {
        // Ação e Verificação
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> usuarioGateway.findById(99L)
        );
        assertEquals("Usuário com id: 99 não encontrado", exception.getMessage());
    }

    @Test
    void quando_listarTodosUsuarios_deveListarComSucesso(){

        Usuario usuario = new Usuario(null, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");
        usuarioGateway.save(usuario, enderecoSalvo.getId());

        Usuario usuario2 = new Usuario(null, "Danilo", "danilo@email.com", TipoUsuario.ADMIN, "12345678");
        usuarioGateway.save(usuario2, enderecoSalvo2.getId());

        List<Usuario> usuarios = usuarioGateway.findAll();

        assertThat(usuarios).hasSize(2);

    }

    @Test
    void quando_findAllSemRestaurantes_deveRetornarListaVazia() {
        // Ação
        List<Usuario> resultado = usuarioGateway.findAll();

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado).isEmpty();
    }

    @Test
    void quando_deleteById_deveDeletarComSucesso(){
        // Preparação
        Usuario usuario = new Usuario(null, "Humberto", "humberto@email.com", TipoUsuario.ADMIN, "12345678");
        Usuario usuarioSalvo = usuarioGateway.save(usuario, enderecoSalvo.getId());

        Long usuarioId = usuarioSalvo.getId();

        // Ação
        usuarioGateway.deleteById(usuarioId);

        // Verificação
        Optional<UsuarioEntity> resultado = usuarioRepository.findById(usuarioId);
        assertThat(resultado).isNotPresent();
    }

    @Test
    void quando_deleteByIdInexistente_deveLancarExcecao() {
        // Ação e Verificação
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> usuarioGateway.deleteById(99L)
        );
        assertEquals("Usuário com id: 99 não encontrado", exception.getMessage());
    }
}