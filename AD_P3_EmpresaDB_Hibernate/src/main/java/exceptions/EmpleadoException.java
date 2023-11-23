package exceptions;

/**
 * Clase para lanzar excepciones relacionadas con Empleado.
 */
@SuppressWarnings("serial")
public class EmpleadoException extends RuntimeException {
    public EmpleadoException(String message) {
        super(message);
    }
}