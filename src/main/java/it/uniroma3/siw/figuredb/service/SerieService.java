package it.uniroma3.siw.figuredb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.figuredb.model.Personaggio;
import it.uniroma3.siw.figuredb.model.Serie;
import it.uniroma3.siw.figuredb.repository.PersonaggioRepository;
import it.uniroma3.siw.figuredb.repository.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;
    @Autowired
    private PersonaggioRepository personaggioRepository;

    @Transactional(readOnly = true)
    public Optional<Serie> findById(Long id) {
        return serieRepository.findById(id);
    }

    /** Dettaglio serie: personaggi caricati con la stessa query (EntityGraph). */
    @Transactional(readOnly = true)
    public Optional<Serie> findByIdWithPersonaggi(Long id) {
        return serieRepository.findWithPersonaggiById(id);
    }

    @Transactional(readOnly = true)
    public List<Serie> findAll() {
        return serieRepository.findAllByOrderByNomeAsc();
    }

    @Transactional
    public Serie save(Serie serie) {
        if (serie.getId() == null && serieRepository.existsByNomeIgnoreCase(serie.getNome())) {
            throw new OperazioneNonValidaException("Esiste gia' una serie chiamata " + serie.getNome() + ".");
        }
        return serieRepository.save(serie);
    }

    /**
     * Aggiornamento di una serie esistente.
     * Non si salva l'oggetto che arriva dal form: ha la lista dei personaggi vuota e,
     * dato che Serie.personaggi e' mappata con orphanRemoval, un salvataggio diretto
     * cancellerebbe tutti i personaggi. Si copiano solo i campi modificabili.
     */
    @Transactional
    public Serie aggiorna(Long id, Serie dati) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("la serie", id));
        serie.setNome(dati.getNome());
        serie.setTipo(dati.getTipo());
        serie.setDescrizione(dati.getDescrizione());
        serie.setLogo(dati.getLogo());
        return serieRepository.save(serie);
    }

    /** Aggiunta di un personaggio a una serie: coinvolge due entita' nella stessa transazione. */
    @Transactional
    public void aggiungiPersonaggio(Long serieId, String nome, String descrizione) {
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new RisorsaNonTrovataException("la serie", serieId));
        if (nome == null || nome.isBlank()) {
            throw new OperazioneNonValidaException("Il nome del personaggio e' obbligatorio.");
        }
        if (personaggioRepository.existsByNomeIgnoreCaseAndSerieId(nome, serieId)) {
            throw new OperazioneNonValidaException(nome + " e' gia' un personaggio di " + serie.getNome() + ".");
        }
        Personaggio personaggio = new Personaggio();
        personaggio.setNome(nome);
        personaggio.setDescrizione(descrizione);
        personaggio.setSerie(serie);
        personaggioRepository.save(personaggio);
    }

    /** Non si puo' eliminare una serie a cui sono ancora collegati fumetti o figure. */
    @Transactional
    public void delete(Long id) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("la serie", id));
        if (!serie.getFumetti().isEmpty() || !serie.getFigure().isEmpty()) {
            throw new OperazioneNonValidaException("Impossibile eliminare " + serie.getNome()
                    + ": ha ancora fumetti o figure collegati.");
        }
        serieRepository.delete(serie);
    }
}
