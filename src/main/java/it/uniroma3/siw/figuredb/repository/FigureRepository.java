package it.uniroma3.siw.figuredb.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.figuredb.model.Figure;

public interface FigureRepository extends JpaRepository<Figure, Long> {

    /** Dettaglio figure: azienda e serie caricate insieme alla figure. */
    @EntityGraph(attributePaths = { "azienda", "serie" })
    Optional<Figure> findWithAziendaESerieById(Long id);

    /** Ricerca per nome con azienda e serie gia' caricate: evita una query per ogni figure trovata. */
    @Query("SELECT f FROM Figure f LEFT JOIN FETCH f.azienda LEFT JOIN FETCH f.serie "
            + "WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY f.nome")
    List<Figure> cercaPerNome(@Param("nome") String nome);

    List<Figure> findBySerieIdOrderByNomeAsc(Long serieId);

    List<Figure> findByAziendaIdOrderByNomeAsc(Long aziendaId);

    /** Vincolo di consistenza: stessa azienda, stesso nome, stessa data di uscita. */
    boolean existsByNomeIgnoreCaseAndAziendaIdAndDataUscita(String nome, Long aziendaId, LocalDate dataUscita);

    /**
     * Scenario 2 del confronto sulle strategie di fetch:
     * una sola query, JPQL scritta a mano con JOIN FETCH.
     */
    @Query("SELECT f FROM Figure f LEFT JOIN FETCH f.azienda ORDER BY f.nome")
    List<Figure> findAllWithAzienda();

    /**
     * Scenario 3 del confronto: stesso effetto del JOIN FETCH ma dichiarativo.
     */
    @EntityGraph(attributePaths = { "azienda" })
    List<Figure> findAllBy();

    /**
     * Elenco paginato, con azienda e serie gia' caricate.
     * Spring Data traduce il Pageable in LIMIT e OFFSET: dal database arrivano
     * soltanto le figure della pagina richiesta. Una seconda query conta le righe
     * totali e serve a sapere quante pagine esistono.
     */
    @EntityGraph(attributePaths = { "azienda", "serie" })
    Page<Figure> findAllBy(Pageable pageable);

    /** Elenco completo con azienda e serie: usato dalle API REST del widget React. */
    @EntityGraph(attributePaths = { "azienda", "serie" })
    List<Figure> findAllByOrderByNomeAsc();

    
}
