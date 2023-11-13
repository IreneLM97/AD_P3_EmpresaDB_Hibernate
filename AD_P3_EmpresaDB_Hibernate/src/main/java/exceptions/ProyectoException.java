package exceptions;

@SuppressWarnings("serial")
public class ProyectoException extends RuntimeException {
    public ProyectoException(String message) {
        super(message);
    }
}