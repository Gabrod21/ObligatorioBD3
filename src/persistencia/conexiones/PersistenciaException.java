package persistencia.conexiones;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class PersistenciaException extends Exception {

    public PersistenciaException(String string) {
        super(string);
    }

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
