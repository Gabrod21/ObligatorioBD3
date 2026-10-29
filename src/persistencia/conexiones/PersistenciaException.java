package persistencia.conexiones;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class PersistenciaException extends Exception {

    public PersistenciaException(SQLException e) {
        super(e);
    }

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
