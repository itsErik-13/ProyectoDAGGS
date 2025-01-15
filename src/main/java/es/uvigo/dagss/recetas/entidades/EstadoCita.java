package es.uvigo.dagss.recetas.entidades;

public enum EstadoCita {
    PLANIFICADA("PLANIFICADA"),
    ANULADA("ANULADA"),
    COMPLETADA("COMPLETADA"),
    AUSENTE("AUSENTE");

    private final String etiqueta;

    private EstadoCita(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
