package it.uniroma3.siw.figuredb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.figuredb.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    List<Serie> findAllByOrderByNomeAsc();

    boolean existsByNomeIgnoreCase(String nome);

    /** Dettaglio serie: i personaggi arrivano con la stessa query. */
    @EntityGraph(attributePaths = { "personaggi" })
    Optional<Serie> findWithPersonaggiById(Long id);
}
