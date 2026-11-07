package it.uniroma3.siw.figuredb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.figuredb.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
