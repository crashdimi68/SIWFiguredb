package it.uniroma3.siw.figuredb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.figuredb.model.Disegnatore;

public interface DisegnatoreRepository extends JpaRepository<Disegnatore, Long> {

    List<Disegnatore> findAllByOrderByCognomeAscNomeAsc();

    @EntityGraph(attributePaths = { "fumetti" })
    Optional<Disegnatore> findWithFumettiById(Long id);
}
