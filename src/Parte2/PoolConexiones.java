package Parte2;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PoolConexiones implements IPoolConexiones {

    private String driver;
    private String url;
    private String user;
    private String password;
    private int nivelTransaccionalidad;
    private Conexion[] conexiones;
    private int tamanio;
    private int creadas;
    private int tope;

    public PoolConexiones() throws PersistenciaException {

        try {
        	//Leo config:
        	Properties props = new Properties();
            FileInputStream config = new FileInputStream("config.properties");
            props.load(config); //cargo todo a props
            config.close();

            driver = props.getProperty("driver");
            url = props.getProperty("url");
            user = props.getProperty("user");
            password = props.getProperty("password");
            tamanio =  Integer.parseInt(props.getProperty("maxConnections"));

            Class.forName(driver);
            conexiones = new Conexion[tamanio];
            creadas = 0;
            tope = -1;
            nivelTransaccionalidad = Connection.TRANSACTION_SERIALIZABLE;
            
            //nota: no creo transacciones aca porque la letra dice: "Las conexiones irán siendo instanciadas a demanda"

        } catch (Exception e) {
            throw new PersistenciaException(
                "Error al inicializar el pool", e
            );
        }
    }

    @Override
    public synchronized IConexion obtenerConexion(boolean updates) throws PersistenciaException {

        try {
        	Conexion conexion = null;
        	
            // si estoy saturado mando al hilo a esperar
            while (tope == -1 && creadas == tamanio) { //hay maximo de conexiones pero estan todas en uso
            	//uso while como guarda por si despierta y no hay libre
                wait();
            }

            // si hay alguna disponible, la asigno:
            if (tope > -1) {
                conexion = conexiones[tope]; //le asigno la ultima disponible
                Connection con = conexion.getConnection();
                con.setAutoCommit(!updates);
                conexiones[tope] = null; //por las dudas
                tope--; //deja de estar disponible

            } else {
                // Crear una conexión nueva a demanda:
                Connection con = DriverManager.getConnection(url, user, password);
                con.setTransactionIsolation(nivelTransaccionalidad);
                con.setAutoCommit(!updates); //si modifica le pongo en false el autocommit, si no en true
                conexion = new Conexion(con);
                creadas++;
            }
           

            return conexion;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new PersistenciaException(
                "Interrupción al esperar una conexión", e
            );

        } catch (SQLException e) {
            throw new PersistenciaException(
                "Error al obtener una conexión", e
            );
        }
    }

    @Override
    public synchronized void liberarConexion(IConexion conexion, boolean transaccionOK) throws PersistenciaException {

        try {
            Connection con = conexion.getConnection();

            if (!con.getAutoCommit()) {
                if (transaccionOK) {
                    con.commit();
                } else {
                    con.rollback();
                }

            }

            tope++;
            conexiones[tope] = (Conexion) conexion;

            // Notifico a hilo en espera que se libero una
            notify();

        } catch (SQLException e) {
            throw new PersistenciaException(
                "Error al liberar una conexión", e
            );
        }
    }
}
