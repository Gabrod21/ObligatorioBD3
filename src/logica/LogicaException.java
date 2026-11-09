package logica;

@SuppressWarnings("serial")
public class LogicaException extends Exception {

    public LogicaException(String string) {
        super(string);
    }

    public LogicaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
