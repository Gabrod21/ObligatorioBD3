package persistencia.conexiones.implementacion;
import java.sql.Connection;

import persistencia.conexiones.interfaces.IConexion;

public class Conexion implements IConexion {

    private Connection con;

    public Conexion(Connection con) {
        this.con = con;
    }

    @Override
    public Connection getConnection() {
        return con;
    }
}
