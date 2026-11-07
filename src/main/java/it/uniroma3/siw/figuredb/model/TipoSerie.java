package it.uniroma3.siw.figuredb.model;

public enum TipoSerie {

    MANGA("Manga"),
    ANIME("Anime"),
    FUMETTO("Fumetto"),
    GRAPHIC_NOVEL("Graphic novel"),
    VIDEOGIOCO("Videogioco"),
    FILM("Film"),
    SERIE_TV("Serie TV"),
    SPORT("Sport");

    private final String label;

    TipoSerie(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
