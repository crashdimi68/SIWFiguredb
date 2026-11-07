package it.uniroma3.siw.figuredb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.figuredb.model.Fumetto;

public interface FumettoRepository extends JpaRepository<Fumetto, Long> {

    /** Elenco fumetti con serie, scrittore ed editore in un'unica query. */
    @EntityGraph(attributePaths = { "serie", "scrittore", "editore" })
    List<Fumetto> findAllByOrderByTitoloAsc();

    /** Dettaglio fumetto: tutte le associazioni, disegnatori compresi. */
    @EntityGraph(attributePaths = { "serie", "scrittore", "editore", "disegnatori" })
    Optional<Fumetto> findCompletoById(Long id);

    List<Fumetto> findBySerieIdOrderByAnnoAsc(Long serieId);

    boolean existsByIsbn(String isbn);
}
