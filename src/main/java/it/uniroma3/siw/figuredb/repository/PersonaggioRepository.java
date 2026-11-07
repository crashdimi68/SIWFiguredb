package it.uniroma3.siw.figuredb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.figuredb.model.Personaggio;

public interface PersonaggioRepository extends JpaRepository<Personaggio, Long> {

    boolean existsByNomeIgnoreCaseAndSerieId(String nome, Long serieId);
}
