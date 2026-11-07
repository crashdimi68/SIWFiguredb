package it.uniroma3.siw.figuredb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * L'opera (manga, anime, videogioco...) da cui derivano personaggi, fumetti e figure.
 */
@Entity
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoSerie tipo;

    @Size(max = 2000)
    @Column(length = 2000)
    private String descrizione;

    private String logo;

    /**
     * Una serie contiene piu' personaggi. E' una composizione: il personaggio non esiste
     * senza la sua serie, quindi salvataggio e cancellazione si propagano in cascata.
     */
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Personaggio> personaggi = new ArrayList<>();

    /** Una serie raccoglie piu' fumetti. */
    @OneToMany(mappedBy = "serie", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Fumetto> fumetti = new ArrayList<>();

    /** Una serie raccoglie piu' figure. */
    @OneToMany(mappedBy = "serie", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Figure> figure = new ArrayList<>();

    public Serie() {
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

    public TipoSerie getTipo() {
        return tipo;
    }

    public void setTipo(TipoSerie tipo) {
        this.tipo = tipo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Personaggio> getPersonaggi() {
        return personaggi;
    }

    public void setPersonaggi(List<Personaggio> personaggi) {
        this.personaggi = personaggi;
    }

    public List<Fumetto> getFumetti() {
        return fumetti;
    }

    public void setFumetti(List<Fumetto> fumetti) {
        this.fumetti = fumetti;
    }

    public List<Figure> getFigure() {
        return figure;
    }

    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Serie other = (Serie) obj;
        return Objects.equals(nome, other.nome);
    }
}
