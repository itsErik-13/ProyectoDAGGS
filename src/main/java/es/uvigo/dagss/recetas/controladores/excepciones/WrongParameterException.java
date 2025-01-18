package es.uvigo.dagss.recetas.controladores.excepciones;

public class WrongParameterException extends RuntimeException {
    public WrongParameterException(String message) {
        super(message);
    }
}