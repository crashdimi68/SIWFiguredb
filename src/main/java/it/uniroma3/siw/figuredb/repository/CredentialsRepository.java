package it.uniroma3.siw.figuredb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.figuredb.model.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

    public Optional<Credentials> findByUsername(String username);

}
