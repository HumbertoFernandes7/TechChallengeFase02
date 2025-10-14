package com.fiap.techchallenge.restaurantmanagement.infra.web.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.EstadoNotFoundException;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter.EstadoGatewayImpl;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EstadoEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EstadoRepository;
import jakarta.transaction.Transactional;
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
public class EstadoGatewayImplTest {

    @Autowired
    private EstadoGatewayImpl estadoGateway;

    @Autowired
    private EstadoRepository estadoRepository;

    @Test
    void quando_salvarEstado_devePersistirComSucesso() {
        // Preparação
        Estado estadoParaSalvar = new Estado(null, "Rio de Janeiro", "RJ");

        // Ação
        Estado resultado = estadoGateway.save(estadoParaSalvar);

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Rio de Janeiro");
        assertThat(estadoRepository.findById(resultado.getId())).isPresent();
    }

    @Test
    void quando_findByIdExistente_deveRetornarEstado() {
        // Preparação
        Estado estadoSalvo = estadoGateway.save(new Estado(null, "Minas Gerais", "MG"));

        // Ação
        Estado resultado = estadoGateway.findById(estadoSalvo.getId());

        // Verificação
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(estadoSalvo.getId());
        assertThat(resultado.getNome()).isEqualTo("Minas Gerais");
    }

    @Test
    void quando_findByIdInexistente_deveLancarExcecao() {
        EstadoNotFoundException exception = assertThrows(
                EstadoNotFoundException.class,
                () -> estadoGateway.findById(99L)
        );
        assertEquals("Estado com o id 99 não encontrado.", exception.getMessage());
    }

    @Test
    void quando_findAll_deveRetornarTodosOsEstados() {
        // Preparação
        estadoGateway.save(new Estado(null, "Bahia", "BA"));
        estadoGateway.save(new Estado(null, "Paraná", "PR"));

        // Ação
        List<Estado> resultado = estadoGateway.findAll();

        // Verificação
        assertThat(resultado).hasSize(2);
    }

    @Test
    void quando_findAllSemEstados_deveRetornarListaVazia() {
        // Ação
        List<Estado> resultado = estadoGateway.findAll();

        // Verificação
        assertThat(resultado).isNotNull().isEmpty();
    }

    @Test
    void quando_update_deveAtualizarDadosDoEstado() {
        // Preparação
        Estado estadoSalvo = estadoGateway.save(new Estado(null, "Nome Antigo", "NA"));
        Estado dadosAtualizados = new Estado(estadoSalvo.getId(), "Nome Novo", "NN");

        // Ação
        Estado resultado = estadoGateway.update(dadosAtualizados);

        // Verificação
        assertThat(resultado.getNome()).isEqualTo("Nome Novo");
        assertThat(resultado.getSigla()).isEqualTo("NN");
    }

    @Test
    void quando_updateEstadoInexistente_deveLancarExcecao() {
        // Preparação
        Estado dadosAtualizados = new Estado(99L, "Nome Novo", "NN");

        // Ação e Verificação
        EstadoNotFoundException exception = assertThrows(
                EstadoNotFoundException.class,
                () -> estadoGateway.update(dadosAtualizados)
        );
        assertThat(exception.getMessage()).isEqualTo("Estado com o id 99 não encontrado.");
    }

    @Test
    void quando_deleteById_deveRemoverDoBanco() {
        // Preparação
        Estado estadoSalvo = estadoGateway.save(new Estado(null, "Para Deletar", "PD"));
        Long id = estadoSalvo.getId();

        // Ação
        estadoGateway.deleteById(id);

        // Verificação
        Optional<EstadoEntity> resultado = estadoRepository.findById(id);
        assertThat(resultado).isNotPresent();
    }

    @Test
    void quando_deleteByIdInexistente_deveLancarExcecao() {
        // Ação e Verificação
        EstadoNotFoundException exception = assertThrows(
                EstadoNotFoundException.class,
                () -> estadoGateway.deleteById(99L)
        );
        assertEquals("Estado com o id 99 não encontrado.", exception.getMessage());
    }
}