package it.uniroma3.siw.figuredb.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.figuredb.model.Azienda;
import it.uniroma3.siw.figuredb.model.Figure;
import it.uniroma3.siw.figuredb.model.Serie;
import it.uniroma3.siw.figuredb.repository.AziendaRepository;
import it.uniroma3.siw.figuredb.repository.FigureRepository;
import it.uniroma3.siw.figuredb.repository.SerieRepository;

@Service
public class FigureService {

    private static final Logger logger = LoggerFactory.getLogger(FigureService.class);

    @Autowired
    private FigureRepository figureRepository;
    @Autowired
    private AziendaRepository aziendaRepository;
    @Autowired
    private SerieRepository serieRepository;

    @Transactional(readOnly = true)
    public Optional<Figure> findById(Long id) {
        return figureRepository.findById(id);
    }

    /** Dettaglio figure: azienda e serie arrivano con la stessa query. */
    @Transactional(readOnly = true)
    public Optional<Figure> findByIdWithAziendaESerie(Long id) {
        return figureRepository.findWithAziendaESerieById(id);
    }

    /** Tutte le figure con azienda e serie gia' caricate (usato dalle API REST). */
    @Transactional(readOnly = true)
    public List<Figure> findAll() {
        return figureRepository.findAllByOrderByNomeAsc();
    }

    /**
     * Una singola pagina dell'elenco delle figure, ordinata per nome.
     * La paginazione avviene nel database: la lista completa non viene mai
     * caricata in memoria. L'oggetto Page porta con se' anche il numero totale
     * di pagine, che serve alla vista per disegnare la barra di navigazione.
     */
    @Transactional(readOnly = true)
    public Page<Figure> findPagina(int numeroPagina, int figurePerPagina) {
        return figureRepository.findAllBy(
                PageRequest.of(numeroPagina, figurePerPagina, Sort.by("nome").ascending()));
    }

    @Transactional(readOnly = true)
    public List<Figure> cercaPerNome(String nome) {
        return figureRepository.cercaPerNome(nome);
    }

    @Transactional(readOnly = true)
    public List<Figure> findBySerieId(Long serieId) {
        return figureRepository.findBySerieIdOrderByNomeAsc(serieId);
    }

    @Transactional(readOnly = true)
    public List<Figure> findByAziendaId(Long aziendaId) {
        return figureRepository.findByAziendaIdOrderByNomeAsc(aziendaId);
    }

    /**
     * CASO D'USO TRANSAZIONALE PRINCIPALE: inserimento a catalogo di una nuova figure.
     *
     * L'operazione coinvolge tre entita' e tre repository e deve essere atomica:
     *   1. recupero dell'azienda produttrice
     *   2. recupero della serie di appartenenza
     *   3. verifica del vincolo di consistenza (stessa azienda, nome e data di uscita)
     *   4. creazione della figure e collegamento ad azienda e serie
     *
     * Se un controllo fallisce viene sollevata un'eccezione runtime: la transazione
     * va in rollback e nel database non resta nulla di parziale.
     *
     * Isolamento SERIALIZABLE: la decisione di scrivere dipende da una lettura (il
     * controllo del duplicato). Con READ_COMMITTED due richieste contemporanee
     * potrebbero superare entrambe il controllo e inserire due volte la stessa figure.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Figure inserisciACatalogo(Figure figure, Long aziendaId, Long serieId) {
        Azienda azienda = aziendaRepository.findById(aziendaId)
                .orElseThrow(() -> new RisorsaNonTrovataException("l'azienda", aziendaId));

        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new RisorsaNonTrovataException("la serie", serieId));

        if (figureRepository.existsByNomeIgnoreCaseAndAziendaIdAndDataUscita(
                figure.getNome(), aziendaId, figure.getDataUscita())) {
            logger.warn("Figure duplicata rifiutata: {} di {} del {}",
                    figure.getNome(), azienda.getNome(), figure.getDataUscita());
            throw new OperazioneNonValidaException("L'azienda " + azienda.getNome()
                    + " ha gia' a catalogo la figure \"" + figure.getNome()
                    + "\" con uscita il " + figure.getDataUscita() + ".");
        }

        figure.setAzienda(azienda);
        figure.setSerie(serie);
        Figure salvata = figureRepository.save(figure);
        logger.info("Figure inserita a catalogo: {} ({}, serie {})",
                salvata.getNome(), azienda.getNome(), serie.getNome());
        return salvata;
    }

    /**
     * Aggiornamento di una figure gia' esistente.
     * Non si fa merge dell'oggetto che arriva dal form: quello ha la lista delle
     * recensioni vuota e, dato che Figure.recensioni e' mappata con orphanRemoval,
     * un merge diretto cancellerebbe tutte le recensioni. Si carica invece l'entita'
     * gestita e si copiano solo i campi modificabili dal form.
     */
    @Transactional
    public Figure aggiorna(Long id, Figure dati, Long aziendaId, Long serieId) {
        Figure figure = figureRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("la figure", id));
        Azienda azienda = aziendaRepository.findById(aziendaId)
                .orElseThrow(() -> new RisorsaNonTrovataException("l'azienda", aziendaId));
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new RisorsaNonTrovataException("la serie", serieId));

        figure.setNome(dati.getNome());
        figure.setDataUscita(dati.getDataUscita());
        figure.setAltezza(dati.getAltezza());
        figure.setMateriale(dati.getMateriale());
        figure.setPrezzo(dati.getPrezzo());
        figure.setDescrizione(dati.getDescrizione());
        figure.setImmagine(dati.getImmagine());
        figure.setAzienda(azienda);
        figure.setSerie(serie);
        return figureRepository.save(figure);
    }

    /** Le recensioni seguono la figure per cascata. */
    @Transactional
    public void delete(Long id) {
        figureRepository.deleteById(id);
    }
}
