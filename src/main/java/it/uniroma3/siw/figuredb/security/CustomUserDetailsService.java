package it.uniroma3.siw.figuredb.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.figuredb.model.Credentials;
import it.uniroma3.siw.figuredb.repository.CredentialsRepository;

/**
 * Dice a Spring Security come recuperare le credenziali dal database.
 * Il ruolo salvato (DEFAULT o ADMIN) diventa l'authority ROLE_DEFAULT o ROLE_ADMIN.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credentials credentials = credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

        return new User(
                credentials.getUsername(),
                credentials.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + credentials.getRole()))
        );
    }
}
