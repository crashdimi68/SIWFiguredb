package it.uniroma3.siw.figuredb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.figuredb.model.Recensione;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

    @EntityGraph(attributePaths = { "autore" })
    List<Recensione> findByFigureIdOrderByDataDesc(Long figureId);

    /** Pannello admin: tutte le recensioni del sito, con autore e figure in un'unica query. */
    @EntityGraph(attributePaths = { "autore", "figure" })
    List<Recensione> findAllByOrderByDataDesc();

    /** Regola di business: al massimo una recensione per (utente, figure). */
    boolean existsByFigureIdAndAutoreId(Long figureId, Long autoreId);

    @Query("SELECT AVG(r.voto) FROM Recensione r WHERE r.figure.id = :figureId")
    Double votoMedioPerFigure(@Param("figureId") Long figureId);
}
