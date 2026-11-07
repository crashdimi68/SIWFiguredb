package it.uniroma3.siw.figuredb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.figuredb.model.Azienda;

public interface AziendaRepository extends JpaRepository<Azienda, Long> {

    List<Azienda> findAllByOrderByNomeAsc();

    boolean existsByNomeIgnoreCase(String nome);

    /** Dettaglio azienda: le figure prodotte arrivano con la stessa query. */
    @EntityGraph(attributePaths = { "figure" })
    Optional<Azienda> findWithFigureById(Long id);
}
