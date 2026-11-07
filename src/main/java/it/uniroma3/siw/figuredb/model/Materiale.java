package it.uniroma3.siw.figuredb.model;

public enum Materiale {

    PVC("PVC"),
    ABS("ABS"),
    PVC_ABS("PVC e ABS"),
    RESINA("Resina"),
    POLYSTONE("Polystone"),
    METALLO("Metallo"),
    VINILE("Vinile"),
    PORCELLANA("Porcellana");

    private final String label;

    Materiale(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
