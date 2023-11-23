package exceptions;

/**
 * Clase para lanzar excepciones relacionadas con Departamento.
 */
@SuppressWarnings("serial")
public class DepartamentoException extends RuntimeException {
    public DepartamentoException(String message) {
        super(message);
    }
}