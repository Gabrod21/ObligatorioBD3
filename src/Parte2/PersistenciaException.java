package Parte2;

import java.sql.SQLException;

public class PersistenciaException extends Exception {

    public PersistenciaException(SQLException e) {
        super(e);
    }

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
