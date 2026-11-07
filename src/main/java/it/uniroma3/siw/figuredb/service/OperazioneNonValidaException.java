package it.uniroma3.siw.figuredb.service;

/**
 * Sollevata quando un'operazione violerebbe un vincolo di consistenza del catalogo:
 * figure duplicata (stessa azienda, nome e data di uscita), ISBN gia' usato,
 * cancellazione di una serie o di un'azienda a cui sono ancora collegate altre entita'.
 */
public class OperazioneNonValidaException extends RuntimeException {

    public OperazioneNonValidaException(String messaggio) {
        super(messaggio);
    }
}
