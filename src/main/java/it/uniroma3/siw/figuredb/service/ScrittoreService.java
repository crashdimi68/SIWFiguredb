package it.uniroma3.siw.figuredb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.figuredb.model.Scrittore;
import it.uniroma3.siw.figuredb.repository.ScrittoreRepository;

@Service
public class ScrittoreService {

    @Autowired
    private ScrittoreRepository scrittoreRepository;

    @Transactional(readOnly = true)
    public Optional<Scrittore> findById(Long id) {
        return scrittoreRepository.findById(id);
    }

    /** Dettaglio scrittore: fumetti caricati con la stessa query (EntityGraph). */
    @Transactional(readOnly = true)
    public Optional<Scrittore> findByIdWithFumetti(Long id) {
        return scrittoreRepository.findWithFumettiById(id);
    }

    @Transactional(readOnly = true)
    public List<Scrittore> findAll() {
        return scrittoreRepository.findAllByOrderByCognomeAscNomeAsc();
    }

    @Transactional
    public void save(Scrittore scrittore) {
        scrittoreRepository.save(scrittore);
    }

    /** Non si puo' eliminare uno scrittore che ha ancora fumetti collegati. */
    @Transactional
    public void delete(Long id) {
        Scrittore scrittore = scrittoreRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("lo scrittore", id));
        if (!scrittore.getFumetti().isEmpty()) {
            throw new OperazioneNonValidaException("Impossibile eliminare " + scrittore.getNomeCompleto()
                    + ": ha ancora fumetti collegati.");
        }
        scrittoreRepository.delete(scrittore);
    }
}
