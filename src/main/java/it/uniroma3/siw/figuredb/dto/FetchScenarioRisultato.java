package it.uniroma3.siw.figuredb.dto;

/** Esito di una strategia di fetch nella pagina di analisi delle prestazioni. */
public class FetchScenarioRisultato {

    private final String titolo;
    private final String descrizione;
    private final long numeroQuery;
    private final long tempoMs;
    private final int numeroFigure;
    private final int numeroAziende;

    public FetchScenarioRisultato(String titolo, String descrizione, long numeroQuery, long tempoMs,
            int numeroFigure, int numeroAziende) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.numeroQuery = numeroQuery;
        this.tempoMs = tempoMs;
        this.numeroFigure = numeroFigure;
        this.numeroAziende = numeroAziende;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public long getNumeroQuery() {
        return numeroQuery;
    }

    public long getTempoMs() {
        return tempoMs;
    }

    public int getNumeroFigure() {
        return numeroFigure;
    }

    public int getNumeroAziende() {
        return numeroAziende;
    }
}
