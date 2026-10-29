package Parte2;
import java.sql.Connection;

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
