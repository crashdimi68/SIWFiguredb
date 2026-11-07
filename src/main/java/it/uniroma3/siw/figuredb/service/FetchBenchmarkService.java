package it.uniroma3.siw.figuredb.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.uniroma3.siw.figuredb.dto.FetchScenarioRisultato;
import it.uniroma3.siw.figuredb.model.Figure;
import it.uniroma3.siw.figuredb.repository.FigureRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Analisi sperimentale dell'accesso ai dati.
 *
 * Lo stesso caso d'uso — "carica tutte le figure con la relativa azienda" — viene
 * eseguito con tre strategie diverse sullo stesso insieme di dati, misurando per
 * ciascuna il numero di query SQL effettivamente eseguite (statistiche di Hibernate),
 * il tempo totale e il numero di oggetti caricati.
 */
@Service
public class FetchBenchmarkService {

    @Autowired
    private FigureRepository figureRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<FetchScenarioRisultato> eseguiConfronto() {
        Statistics statistics = entityManager.getEntityManagerFactory()
                .unwrap(SessionFactory.class)
                .getStatistics();

        List<FetchScenarioRisultato> risultati = new ArrayList<>();

        risultati.add(eseguiScenario(statistics,
                "Default (LAZY)",
                "findAll() e poi, per ogni figure, getAzienda().getNome(): Figure.azienda e' LAZY, "
                        + "quindi ogni accesso a un'azienda non ancora caricata scatena una SELECT separata "
                        + "(1 query per l'elenco + N per le aziende, il problema N+1).",
                () -> {
                    List<Figure> figure = figureRepository.findAll();
                    return conta(figure);
                }));

        risultati.add(eseguiScenario(statistics,
                "JOIN FETCH esplicito",
                "findAllWithAzienda(): JPQL scritta a mano "
                        + "'SELECT f FROM Figure f LEFT JOIN FETCH f.azienda', un'unica query. "
                        + "L'azienda arriva insieme alla figure, gia' inizializzata.",
                () -> {
                    List<Figure> figure = figureRepository.findAllWithAzienda();
                    return conta(figure);
                }));

        risultati.add(eseguiScenario(statistics,
                "@EntityGraph",
                "findAllBy() con @EntityGraph(attributePaths = \"azienda\"): stesso risultato del "
                        + "JOIN FETCH, ma dichiarativo, senza scrivere JPQL a mano.",
                () -> {
                    List<Figure> figure = figureRepository.findAllBy();
                    return conta(figure);
                }));

        return risultati;
    }

    /**
     * Tocca l'azienda di ogni figure: e' questo accesso che, con l'associazione LAZY
     * non inizializzata, provoca le query aggiuntive.
     */
    private int[] conta(List<Figure> figure) {
        Set<Long> aziende = new HashSet<>();
        for (Figure f : figure) {
            if (f.getAzienda() != null) {
                f.getAzienda().getNome();
                aziende.add(f.getAzienda().getId());
            }
        }
        return new int[] { figure.size(), aziende.size() };
    }

    private FetchScenarioRisultato eseguiScenario(Statistics statistics, String titolo, String descrizione,
            Supplier<int[]> scenario) {
        entityManager.clear();
        statistics.clear();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int[] conteggi = scenario.get();
        stopWatch.stop();

        long numeroQuery = statistics.getPrepareStatementCount();
        return new FetchScenarioRisultato(titolo, descrizione, numeroQuery, stopWatch.getTotalTimeMillis(),
                conteggi[0], conteggi[1]);
    }
}
