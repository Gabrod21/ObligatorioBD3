package Parte2;

public interface IPoolConexiones {

    IConexion obtenerConexion(boolean updates) throws PersistenciaException;

    void liberarConexion(IConexion conexion, boolean transaccionOK) throws PersistenciaException;

}
