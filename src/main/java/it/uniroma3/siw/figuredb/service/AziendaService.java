package it.uniroma3.siw.figuredb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.figuredb.model.Azienda;
import it.uniroma3.siw.figuredb.repository.AziendaRepository;

@Service
public class AziendaService {

    @Autowired
    private AziendaRepository aziendaRepository;

    @Transactional(readOnly = true)
    public Optional<Azienda> findById(Long id) {
        return aziendaRepository.findById(id);
    }

    /** Dettaglio azienda: figure prodotte caricate con la stessa query (EntityGraph). */
    @Transactional(readOnly = true)
    public Optional<Azienda> findByIdWithFigure(Long id) {
        return aziendaRepository.findWithFigureById(id);
    }

    @Transactional(readOnly = true)
    public List<Azienda> findAll() {
        return aziendaRepository.findAllByOrderByNomeAsc();
    }

    @Transactional
    public Azienda save(Azienda azienda) {
        if (azienda.getId() == null && aziendaRepository.existsByNomeIgnoreCase(azienda.getNome())) {
            throw new OperazioneNonValidaException("Esiste gia' un'azienda chiamata " + azienda.getNome() + ".");
        }
        return aziendaRepository.save(azienda);
    }

    /**
     * Aggiornamento di un'azienda esistente: si caricano i dati dal database e si
     * copiano solo i campi del form, cosi' la lista delle figure resta intatta.
     */
    @Transactional
    public Azienda aggiorna(Long id, Azienda dati) {
        Azienda azienda = aziendaRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("l'azienda", id));
        azienda.setNome(dati.getNome());
        azienda.setRegione(dati.getRegione());
        azienda.setDescrizione(dati.getDescrizione());
        azienda.setLogo(dati.getLogo());
        return aziendaRepository.save(azienda);
    }

    /** Non si puo' eliminare un'azienda che ha ancora figure a catalogo. */
    @Transactional
    public void delete(Long id) {
        Azienda azienda = aziendaRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("l'azienda", id));
        if (!azienda.getFigure().isEmpty()) {
            throw new OperazioneNonValidaException("Impossibile eliminare " + azienda.getNome()
                    + ": ha ancora " + azienda.getFigure().size() + " figure a catalogo.");
        }
        aziendaRepository.delete(azienda);
    }
}
