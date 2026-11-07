package it.uniroma3.siw.figuredb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

/** Volume a fumetti appartenente a una serie. L'ISBN e' univoco. */
@Entity
public class Fumetto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String titolo;

    /** true se e' un'edizione variant (copertina alternativa). */
    private boolean variant;

    @NotNull
    @Min(1900)
    @Max(2100)
    private Integer anno;

    @NotBlank
    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$")
    @Column(unique = true)
    private String isbn;

    @NotNull
    @PositiveOrZero
    private Float prezzo;

    @NotBlank
    private String genere;

    /** Percorso della copertina, es. /images/fumetti/cartella/copertina.jpg */
    private String immagine;

    /** Un fumetto appartiene a una serie. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_id")
    private Serie serie;

    /** Un fumetto e' scritto da un solo scrittore. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrittore_id")
    private Scrittore scrittore;

    /** Un fumetto e' pubblicato da un solo editore. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editore_id")
    private Editore editore;

    /**
     * Un fumetto e' disegnato da uno o piu' disegnatori (lato proprietario della molti-a-molti:
     * qui si definisce la tabella di join).
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "fumetto_disegnatore",
            joinColumns = @JoinColumn(name = "fumetto_id"),
            inverseJoinColumns = @JoinColumn(name = "disegnatore_id"))
    private List<Disegnatore> disegnatori = new ArrayList<>();

    public Fumetto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public boolean isVariant() {
        return variant;
    }

    public void setVariant(boolean variant) {
        this.variant = variant;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Scrittore getScrittore() {
        return scrittore;
    }

    public void setScrittore(Scrittore scrittore) {
        this.scrittore = scrittore;
    }

    public Editore getEditore() {
        return editore;
    }

    public void setEditore(Editore editore) {
        this.editore = editore;
    }

    public List<Disegnatore> getDisegnatori() {
        return disegnatori;
    }

    public void setDisegnatori(List<Disegnatore> disegnatori) {
        this.disegnatori = disegnatori;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fumetto other = (Fumetto) obj;
        return Objects.equals(isbn, other.isbn);
    }
}
