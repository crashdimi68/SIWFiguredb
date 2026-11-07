package it.uniroma3.siw.figuredb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.figuredb.model.Scrittore;

public interface ScrittoreRepository extends JpaRepository<Scrittore, Long> {

    List<Scrittore> findAllByOrderByCognomeAscNomeAsc();

    @EntityGraph(attributePaths = { "fumetti" })
    Optional<Scrittore> findWithFumettiById(Long id);
}
