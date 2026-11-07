package it.uniroma3.siw.figuredb.dto;

import java.time.LocalDate;

import it.uniroma3.siw.figuredb.model.Figure;

/**
 * Dati di una figure esposti dalle API REST.
 * Si usa un DTO e non l'entita': niente associazioni LAZY da serializzare e niente cicli.
 */
public class FigureDTO {

    private Long id;
    private String nome;
    private LocalDate dataUscita;
    private Float altezza;
    private String materiale;
    private Float prezzo;
    private String immagine;
    private String azienda;
    private String serie;

    public FigureDTO() {
    }

    public static FigureDTO from(Figure f) {
        FigureDTO dto = new FigureDTO();
        dto.id = f.getId();
        dto.nome = f.getNome();
        dto.dataUscita = f.getDataUscita();
        dto.altezza = f.getAltezza();
        dto.materiale = f.getMateriale() != null ? f.getMateriale().getLabel() : null;
        dto.prezzo = f.getPrezzo();
        dto.immagine = f.getImmagine();
        dto.azienda = f.getAzienda() != null ? f.getAzienda().getNome() : null;
        dto.serie = f.getSerie() != null ? f.getSerie().getNome() : null;
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getDataUscita() { return dataUscita; }
    public void setDataUscita(LocalDate dataUscita) { this.dataUscita = dataUscita; }
    public Float getAltezza() { return altezza; }
    public void setAltezza(Float altezza) { this.altezza = altezza; }
    public String getMateriale() { return materiale; }
    public void setMateriale(String materiale) { this.materiale = materiale; }
    public Float getPrezzo() { return prezzo; }
    public void setPrezzo(Float prezzo) { this.prezzo = prezzo; }
    public String getImmagine() { return immagine; }
    public void setImmagine(String immagine) { this.immagine = immagine; }
    public String getAzienda() { return azienda; }
    public void setAzienda(String azienda) { this.azienda = azienda; }
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
}
