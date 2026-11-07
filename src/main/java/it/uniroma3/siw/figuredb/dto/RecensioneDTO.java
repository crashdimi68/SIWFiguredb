package it.uniroma3.siw.figuredb.dto;

import java.time.LocalDate;

import it.uniroma3.siw.figuredb.model.Recensione;

public class RecensioneDTO {

    private Long id;
    private String titolo;
    private String testo;
    private Integer voto;
    private LocalDate data;
    private String autore;

    public RecensioneDTO() {
    }

    public static RecensioneDTO from(Recensione r) {
        RecensioneDTO dto = new RecensioneDTO();
        dto.id = r.getId();
        dto.titolo = r.getTitolo();
        dto.testo = r.getTesto();
        dto.voto = r.getVoto();
        dto.data = r.getData();
        dto.autore = r.getAutore() != null ? r.getAutore().getNomeCompleto() : "Anonimo";
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }
    public Integer getVoto() { return voto; }
    public void setVoto(Integer voto) { this.voto = voto; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getAutore() { return autore; }
    public void setAutore(String autore) { this.autore = autore; }
}
