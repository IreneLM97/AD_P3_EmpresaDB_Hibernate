package exceptions;

@SuppressWarnings("serial")
public class DepartamentoException extends RuntimeException {
    public DepartamentoException(String message) {
        super(message);
    }
}