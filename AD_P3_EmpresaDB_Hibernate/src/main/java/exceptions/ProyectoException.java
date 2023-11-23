package exceptions;

/**
 * Clase para lanzar excepciones relacionadas con Proyecto.
 */
@SuppressWarnings("serial")
public class ProyectoException extends RuntimeException {
    public ProyectoException(String message) {
        super(message);
    }
}