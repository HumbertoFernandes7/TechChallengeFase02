package com.fiap.techchallenge.restaurantmanagement.infra.web.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.EnderecoNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter.EnderecoGatewayImpl;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.CidadeEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EnderecoEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EstadoEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.CidadeMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.CidadeRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EnderecoRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EstadoRepository;
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
public class EnderecoGatewayImplTest {

    @Autowired
    private EnderecoGatewayImpl enderecoGateway;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeMapper cidadeMapper;

    private Cidade cidadeSalva;

    @BeforeEach
    void setUp() {
        EstadoEntity estadoEntity = new EstadoEntity();
        estadoEntity.setNome("São Paulo");
        estadoEntity.setSigla("SP");
        EstadoEntity estadoSalvo = estadoRepository.save(estadoEntity);

        CidadeEntity cidadeEntity = new CidadeEntity();
        cidadeEntity.setNome("São Paulo");
        cidadeEntity.setEstado(estadoSalvo);
        cidadeSalva = cidadeMapper.toDomain(cidadeRepository.save(cidadeEntity));
    }

    @Test
    void quando_salvarEndereco_devePersistirComSucesso() {
        // Preparação
        Endereco enderecoParaSalvar = new Endereco(null, "Avenida Paulista", "1000", null, "Bela Vista", "01310-100", cidadeSalva);

        // Ação
        Endereco resultado = enderecoGateway.save(enderecoParaSalvar);

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getLogradouro()).isEqualTo("Avenida Paulista");
        assertThat(enderecoRepository.findById(resultado.getId())).isPresent();
    }

    @Test
    void quando_findByIdExistente_deveRetornarEndereco() {
        // Preparação
        Endereco enderecoSalvo = enderecoGateway.save(new Endereco(null, "Rua Augusta", "500", null, "Consolação", "01304-000", cidadeSalva));

        // Ação
        Endereco resultado = enderecoGateway.findById(enderecoSalvo.getId());

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(enderecoSalvo.getId());
        assertThat(resultado.getLogradouro()).isEqualTo("Rua Augusta");
    }

    @Test
    void quando_findByIdInexistente_deveLancarExcecao() {
        EnderecoNotFoundException exception = assertThrows(
                EnderecoNotFoundException.class,
                () -> enderecoGateway.findById(99L)
        );
        assertEquals("Endereço com o id 99 não encontrado.", exception.getMessage());
    }

    @Test
    void quando_findAll_deveRetornarTodosOsEnderecos() {
        // Preparação
        enderecoGateway.save(new Endereco(null, "Rua 1", "1", null, "Bairro 1", "11111-111", cidadeSalva));
        enderecoGateway.save(new Endereco(null, "Rua 2", "2", null, "Bairro 2", "22222-222", cidadeSalva));

        // Ação
        List<Endereco> resultado = enderecoGateway.findAll();

        // Verificação
        assertThat(resultado).hasSize(2);
    }

    @Test
    void quando_update_deveAtualizarDadosDoEndereco() {
        // Preparação
        Endereco endereco = new Endereco(null, "Lagradouro", "738", "casa", "Vila da Belezas", "05840-021", cidadeSalva);
        Endereco enderecoSalvo = enderecoGateway.save(endereco);

        Endereco dadosAtualizados = new Endereco(enderecoSalvo.getId(), "Lagradouro alterado", "739", "apartamento", "Vila das Belezas", "05840-020", cidadeSalva);

        // Ação
        Endereco resultado = enderecoGateway.update(dadosAtualizados);

        // Verificação
        assertThat(resultado.getLogradouro()).isEqualTo("Lagradouro alterado");
        assertThat(resultado.getNumero()).isEqualTo("739");
        assertThat(resultado.getBairro()).isEqualTo("Vila das Belezas");
    }

    @Test
    void quando_deleteById_deveRemoverDoBanco() {
        // Preparação
        Endereco enderecoSalvo = enderecoGateway.save(new Endereco(null, "Rua para deletar", "123", null, "Bairro", "12345-678", cidadeSalva));
        Long id = enderecoSalvo.getId();

        // Ação
        enderecoGateway.deleteById(id);

        // Verificação
        Optional<EnderecoEntity> resultado = enderecoRepository.findById(id);
        assertThat(resultado).isNotPresent();
    }
}