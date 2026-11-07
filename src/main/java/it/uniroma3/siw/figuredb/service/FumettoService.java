package it.uniroma3.siw.figuredb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.figuredb.model.Disegnatore;
import it.uniroma3.siw.figuredb.model.Fumetto;
import it.uniroma3.siw.figuredb.repository.DisegnatoreRepository;
import it.uniroma3.siw.figuredb.repository.EditoreRepository;
import it.uniroma3.siw.figuredb.repository.FumettoRepository;
import it.uniroma3.siw.figuredb.repository.ScrittoreRepository;
import it.uniroma3.siw.figuredb.repository.SerieRepository;

@Service
public class FumettoService {

    @Autowired
    private FumettoRepository fumettoRepository;
    @Autowired
    private SerieRepository serieRepository;
    @Autowired
    private ScrittoreRepository scrittoreRepository;
    @Autowired
    private EditoreRepository editoreRepository;
    @Autowired
    private DisegnatoreRepository disegnatoreRepository;

    @Transactional(readOnly = true)
    public Optional<Fumetto> findById(Long id) {
        return fumettoRepository.findById(id);
    }

    /** Dettaglio fumetto: serie, scrittore, editore e disegnatori in un'unica query. */
    @Transactional(readOnly = true)
    public Optional<Fumetto> findByIdCompleto(Long id) {
        return fumettoRepository.findCompletoById(id);
    }

    @Transactional(readOnly = true)
    public List<Fumetto> findAll() {
        return fumettoRepository.findAllByOrderByTitoloAsc();
    }

    @Transactional(readOnly = true)
    public List<Fumetto> findBySerieId(Long serieId) {
        return fumettoRepository.findBySerieIdOrderByAnnoAsc(serieId);
    }

    /**
     * Inserimento di un nuovo fumetto: recupera serie, scrittore, editore e disegnatori
     * (molti-a-molti) e controlla che l'ISBN non sia gia' usato.
     */
    @Transactional
    public Fumetto inserisci(Fumetto fumetto, Long serieId, Long scrittoreId, Long editoreId,
            List<Long> disegnatoriIds) {
        if (fumettoRepository.existsByIsbn(fumetto.getIsbn())) {
            throw new OperazioneNonValidaException("Esiste gia' un fumetto con ISBN " + fumetto.getIsbn() + ".");
        }
        collega(fumetto, serieId, scrittoreId, editoreId, disegnatoriIds);
        return fumettoRepository.save(fumetto);
    }

    /** Aggiornamento: si copiano i campi del form sull'entita' gia' presente nel database. */
    @Transactional
    public Fumetto aggiorna(Long id, Fumetto dati, Long serieId, Long scrittoreId, Long editoreId,
            List<Long> disegnatoriIds) {
        Fumetto fumetto = fumettoRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("il fumetto", id));
        if (!fumetto.getIsbn().equals(dati.getIsbn()) && fumettoRepository.existsByIsbn(dati.getIsbn())) {
            throw new OperazioneNonValidaException("Esiste gia' un fumetto con ISBN " + dati.getIsbn() + ".");
        }
        fumetto.setTitolo(dati.getTitolo());
        fumetto.setVariant(dati.isVariant());
        fumetto.setAnno(dati.getAnno());
        fumetto.setIsbn(dati.getIsbn());
        fumetto.setPrezzo(dati.getPrezzo());
        fumetto.setGenere(dati.getGenere());
        fumetto.setImmagine(dati.getImmagine());
        collega(fumetto, serieId, scrittoreId, editoreId, disegnatoriIds);
        return fumettoRepository.save(fumetto);
    }

    @Transactional
    public void delete(Long id) {
        fumettoRepository.deleteById(id);
    }

    /** Collega il fumetto alle entita' scelte nel form. Un fumetto ha almeno un disegnatore. */
    private void collega(Fumetto fumetto, Long serieId, Long scrittoreId, Long editoreId,
            List<Long> disegnatoriIds) {
        if (disegnatoriIds == null || disegnatoriIds.isEmpty()) {
            throw new OperazioneNonValidaException("Un fumetto deve avere almeno un disegnatore.");
        }
        fumetto.setSerie(serieRepository.findById(serieId)
                .orElseThrow(() -> new RisorsaNonTrovataException("la serie", serieId)));
        fumetto.setScrittore(scrittoreRepository.findById(scrittoreId)
                .orElseThrow(() -> new RisorsaNonTrovataException("lo scrittore", scrittoreId)));
        fumetto.setEditore(editoreRepository.findById(editoreId)
                .orElseThrow(() -> new RisorsaNonTrovataException("l'editore", editoreId)));
        List<Disegnatore> disegnatori = new ArrayList<>(disegnatoreRepository.findAllById(disegnatoriIds));
        fumetto.setDisegnatori(disegnatori);
    }
}
