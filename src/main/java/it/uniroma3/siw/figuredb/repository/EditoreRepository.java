package it.uniroma3.siw.figuredb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.figuredb.model.Editore;

public interface EditoreRepository extends JpaRepository<Editore, Long> {

    List<Editore> findAllByOrderByNomeAsc();

    @EntityGraph(attributePaths = { "fumetti" })
    Optional<Editore> findWithFumettiById(Long id);
}
