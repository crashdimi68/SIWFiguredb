package it.uniroma3.siw.figuredb.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.figuredb.model.Figure;
import it.uniroma3.siw.figuredb.model.Recensione;
import it.uniroma3.siw.figuredb.model.User;
import it.uniroma3.siw.figuredb.repository.FigureRepository;
import it.uniroma3.siw.figuredb.repository.RecensioneRepository;

@Service
public class RecensioneService {

    @Autowired
    private RecensioneRepository recensioneRepository;
    @Autowired
    private FigureRepository figureRepository;

    @Transactional(readOnly = true)
    public Optional<Recensione> findById(Long id) {
        return recensioneRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Recensione> findByFigureId(Long figureId) {
        return recensioneRepository.findByFigureIdOrderByDataDesc(figureId);
    }

    /** Pannello admin: tutte le recensioni presenti nel sito, dalla piu' recente. */
    @Transactional(readOnly = true)
    public List<Recensione> findAll() {
        return recensioneRepository.findAllByOrderByDataDesc();
    }

    @Transactional(readOnly = true)
    public Double votoMedio(Long figureId) {
        return recensioneRepository.votoMedioPerFigure(figureId);
    }

    /**
     * Inserimento di una recensione.
     * Regola di business della specifica: un utente puo' inserire al massimo una
     * recensione per la stessa figure. Il controllo e' qui, nel Service Layer, ed e'
     * rinforzato dal vincolo di unicita' (figure_id, autore_id) sulla tabella.
     */
    @Transactional
    public Recensione inserisci(Long figureId, User autore, String titolo, String testo, Integer voto) {
        if (autore == null) {
            throw new AccessDeniedException("Occorre essere autenticati per recensire una figure.");
        }
        Figure figure = figureRepository.findById(figureId)
                .orElseThrow(() -> new RisorsaNonTrovataException("la figure", figureId));

        if (recensioneRepository.existsByFigureIdAndAutoreId(figureId, autore.getId())) {
            throw new RecensioneDuplicataException(
                    "Hai gia' recensito \"" + figure.getNome() + "\": puoi modificare la tua recensione.");
        }

        Recensione recensione = new Recensione();
        recensione.setFigure(figure);
        recensione.setAutore(autore);
        recensione.setTitolo(titolo);
        recensione.setTesto(testo);
        recensione.setVoto(voto);
        return recensioneRepository.save(recensione);
    }

    /** Un utente puo' modificare esclusivamente le recensioni di cui e' autore. */
    @Transactional
    public Recensione modifica(Long recensioneId, String nuovoTitolo, String nuovoTesto, Integer nuovoVoto,
            User utenteRichiedente) {
        Recensione recensione = recensioneRepository.findById(recensioneId)
                .orElseThrow(() -> new RisorsaNonTrovataException("la recensione", recensioneId));

        if (utenteRichiedente == null
                || recensione.getAutore() == null
                || !Objects.equals(recensione.getAutore().getId(), utenteRichiedente.getId())) {
            throw new AccessDeniedException("Non sei l'autore della recensione");
        }

        recensione.setTitolo(nuovoTitolo);
        recensione.setTesto(nuovoTesto);
        if (nuovoVoto != null) {
            recensione.setVoto(nuovoVoto);
        }
        return recensioneRepository.save(recensione);
    }

    /** L'autore puo' cancellare la propria recensione; l'amministratore qualunque recensione. */
    @Transactional
    public void elimina(Long recensioneId, User utenteRichiedente, boolean isAdmin) {
        Recensione recensione = recensioneRepository.findById(recensioneId)
                .orElseThrow(() -> new RisorsaNonTrovataException("la recensione", recensioneId));

        boolean autore = utenteRichiedente != null
                && recensione.getAutore() != null
                && Objects.equals(recensione.getAutore().getId(), utenteRichiedente.getId());

        if (!isAdmin && !autore) {
            throw new AccessDeniedException("Non sei l'autore della recensione");
        }
        recensioneRepository.delete(recensione);
    }
}
