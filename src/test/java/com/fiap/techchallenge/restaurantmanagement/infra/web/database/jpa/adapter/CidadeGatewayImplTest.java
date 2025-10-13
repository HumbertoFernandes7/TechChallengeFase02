package com.fiap.techchallenge.restaurantmanagement.infra.web.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.CidadeNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter.CidadeGatewayImpl;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.CidadeEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EstadoEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.CidadeRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EstadoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CidadeGatewayImplTest {

    @Autowired
    private CidadeGatewayImpl cidadeGateway;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    private EstadoEntity estadoSalvo;
    private EstadoEntity outroEstadoSalvo;

    @BeforeEach
    void setUp() {
        EstadoEntity estado = new EstadoEntity();
        estado.setNome("estado");
        estado.setSigla("SP");
        estadoSalvo = estadoRepository.save(estado);
    }

    @Test
    void quando_salvarCidade_devePersistirComSucesso() {
        // Preparação
        Estado estadoDominio = new Estado(estadoSalvo.getId(), estadoSalvo.getNome(), estadoSalvo.getSigla());
        Cidade cidadeParaSalvar = new Cidade(null, "Campinas", estadoDominio);

        // Ação
        Cidade resultado = cidadeGateway.save(cidadeParaSalvar);

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Campinas");
        assertThat(cidadeRepository.findById(resultado.getId())).isPresent();
    }

    @Test
    void quando_findByIdExistente_deveRetornarCidade() {
        // Preparação
        Estado estadoDominio = new Estado(estadoSalvo.getId(), estadoSalvo.getNome(), estadoSalvo.getSigla());
        Cidade cidadeSalva = cidadeGateway.save(new Cidade(null, "Santos", estadoDominio));

        // Ação
        Cidade resultado = cidadeGateway.findById(cidadeSalva.getId());

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(cidadeSalva.getId());
        assertThat(resultado.getNome()).isEqualTo("Santos");
    }

    @Test
    void quando_findByIdInexistente_deveLancarExcecao() {
        assertThrows(CidadeNotFoundException.class, () -> cidadeGateway.findById(99L));
    }

    @Test
    void quando_findAll_deveRetornarTodasAsCidades() {
        // Preparação
        Estado estadoDominio = new Estado(estadoSalvo.getId(), estadoSalvo.getNome(), estadoSalvo.getSigla());
        cidadeGateway.save(new Cidade(null, "Guarulhos", estadoDominio));
        cidadeGateway.save(new Cidade(null, "Osasco", estadoDominio));

        // Ação
        List<Cidade> resultado = cidadeGateway.findAll();

        // Verificação
        assertThat(resultado).hasSize(2);
    }

    @Test
    void quando_deleteById_deveRemoverDoBanco() {
        // Preparação
        Estado estadoDominio = new Estado(estadoSalvo.getId(), estadoSalvo.getNome(), estadoSalvo.getSigla());
        Cidade cidadeSalva = cidadeGateway.save(new Cidade(null, "Para Deletar", estadoDominio));
        Long id = cidadeSalva.getId();

        // Ação
        cidadeGateway.deleteById(id);

        // Verificação
        Optional<CidadeEntity> resultado = cidadeRepository.findById(id);
        assertThat(resultado).isNotPresent();
    }

    @Test
    void quando_update_deveAtualizarCidadeComSucesso() {
        // Preparação
        Estado estadoDominio = new Estado(estadoSalvo.getId(), estadoSalvo.getNome(), estadoSalvo.getSigla());
        Cidade cidadeOriginal = cidadeGateway.save(new Cidade(1L, "Nome Antigo", estadoDominio));

        Estado outroEstadoDominio = new Estado(outroEstadoSalvo.getId(), outroEstadoSalvo.getNome(), outroEstadoSalvo.getSigla());
        Cidade cidadeAtualizada = new Cidade(cidadeOriginal.getId(), "Nome Novo", outroEstadoDominio);

        // Ação
        Cidade resultado = cidadeGateway.update(cidadeAtualizada);

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(cidadeOriginal.getId());
        assertThat(resultado.getNome()).isEqualTo("Nome Novo");
        assertThat(resultado.getEstado().getId()).isEqualTo(outroEstadoSalvo.getId());
    }

    @Test
    void quando_updateCidadeInexistente_deveLancarExcecao() {
        // Preparação
        Estado estadoDominio = new Estado(estadoSalvo.getId(), estadoSalvo.getNome(), estadoSalvo.getSigla());
        Cidade cidadeInexistente = new Cidade(99L, "Cidade Inexistente", estadoDominio);

        // Ação e Verificação
        CidadeNotFoundException exception = assertThrows(
                CidadeNotFoundException.class,
                () -> cidadeGateway.update(cidadeInexistente)
        );
        assertThat(exception.getMessage()).isEqualTo("Cidade com o id 99 não encontrado.");
    }

    @Test
    void quando_updateComEstadoInexistente_deveLancarExcecao() {
        // Preparação
        Estado estadoDominio = new Estado(estadoSalvo.getId(), estadoSalvo.getNome(), estadoSalvo.getSigla());
        Cidade cidadeOriginal = cidadeGateway.save(new Cidade(null, "Cidade Teste", estadoDominio));

        Estado estadoInexistente = new Estado(999L, "Estado Inexistente", "XX");
        Cidade cidadeParaAtualizar = new Cidade(cidadeOriginal.getId(), "Nome Novo", estadoInexistente);

        // Ação e Verificação
        // Verifica a NoSuchElementException, que é a exceção lançada pelo .get() no código original
        assertThrows(NoSuchElementException.class, () -> {
            cidadeGateway.update(cidadeParaAtualizar);
        });
    }

}