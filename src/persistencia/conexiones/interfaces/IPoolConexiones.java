package persistencia.conexiones.interfaces;

import persistencia.conexiones.PersistenciaException;

public interface IPoolConexiones {

    IConexion obtenerConexion(boolean updates) throws PersistenciaException;

    void liberarConexion(IConexion conexion, boolean transaccionOK) throws PersistenciaException;

}
