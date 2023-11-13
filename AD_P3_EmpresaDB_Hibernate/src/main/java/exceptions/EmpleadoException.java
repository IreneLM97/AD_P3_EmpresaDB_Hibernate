package exceptions;

@SuppressWarnings("serial")
public class EmpleadoException extends RuntimeException {
    public EmpleadoException(String message) {
        super(message);
    }
}