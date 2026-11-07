package it.uniroma3.siw.figuredb.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Figure da collezione: l'entita' centrale del catalogo.
 * Vincolo di consistenza: la stessa azienda non puo' avere due figure con lo stesso
 * nome e la stessa data di uscita. Il vincolo e' dichiarato qui sulla tabella ed e'
 * controllato anche nel Service Layer (FigureService).
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_figure_nome_azienda_data",
        columnNames = { "nome", "azienda_id", "data_uscita" }))
public class Figure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataUscita;

    /** Altezza in centimetri. */
    @NotNull
    @Positive
    private Float altezza;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Materiale materiale;

    /** Percorso dell'immagine, es. /images/figure/cartella/foto.jpg */
    private String immagine;

    @NotNull
    @Positive
    private Float prezzo;

    @Size(max = 2000)
    @Column(length = 2000)
    private String descrizione;

    /**
     * Ogni figure e' prodotta da una sola azienda.
     * LAZY di proposito: e' l'associazione su cui si osserva il problema N+1
     * nella pagina di analisi delle prestazioni.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "azienda_id")
    private Azienda azienda;

    /** Ogni figure appartiene a una serie. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_id")
    private Serie serie;

    /** Composizione: cancellando la figure si cancellano anche le sue recensioni. */
    @OneToMany(mappedBy = "figure", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Recensione> recensioni = new ArrayList<>();

    public Figure() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataUscita() {
        return dataUscita;
    }

    public void setDataUscita(LocalDate dataUscita) {
        this.dataUscita = dataUscita;
    }

    public Float getAltezza() {
        return altezza;
    }

    public void setAltezza(Float altezza) {
        this.altezza = altezza;
    }

    public Materiale getMateriale() {
        return materiale;
    }

    public void setMateriale(Materiale materiale) {
        this.materiale = materiale;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public List<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dataUscita);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Figure other = (Figure) obj;
        return Objects.equals(nome, other.nome)
                && Objects.equals(dataUscita, other.dataUscita);
    }
}
