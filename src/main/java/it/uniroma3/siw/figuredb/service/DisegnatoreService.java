package it.uniroma3.siw.figuredb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.figuredb.model.Disegnatore;
import it.uniroma3.siw.figuredb.repository.DisegnatoreRepository;

@Service
public class DisegnatoreService {

    @Autowired
    private DisegnatoreRepository disegnatoreRepository;

    @Transactional(readOnly = true)
    public Optional<Disegnatore> findById(Long id) {
        return disegnatoreRepository.findById(id);
    }

    /** Dettaglio disegnatore: fumetti caricati con la stessa query (EntityGraph). */
    @Transactional(readOnly = true)
    public Optional<Disegnatore> findByIdWithFumetti(Long id) {
        return disegnatoreRepository.findWithFumettiById(id);
    }

    @Transactional(readOnly = true)
    public List<Disegnatore> findAll() {
        return disegnatoreRepository.findAllByOrderByCognomeAscNomeAsc();
    }

    @Transactional
    public void save(Disegnatore disegnatore) {
        disegnatoreRepository.save(disegnatore);
    }

    /** Non si puo' eliminare un disegnatore che ha ancora fumetti collegati. */
    @Transactional
    public void delete(Long id) {
        Disegnatore disegnatore = disegnatoreRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("il disegnatore", id));
        if (!disegnatore.getFumetti().isEmpty()) {
            throw new OperazioneNonValidaException("Impossibile eliminare " + disegnatore.getNomeCompleto()
                    + ": ha ancora fumetti collegati.");
        }
        disegnatoreRepository.delete(disegnatore);
    }
}
