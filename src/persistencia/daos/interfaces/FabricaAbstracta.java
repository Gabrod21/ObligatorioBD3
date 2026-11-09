package persistencia.daos.interfaces;

import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IPoolConexiones;

public interface FabricaAbstracta 
{
    IDAOAlumnos crearIDAOAlumnos() throws PersistenciaException;

    IDAORegistros crearIDAORegistros(int cedA) throws PersistenciaException;
    
    IPoolConexiones crearPoolConexiones() throws PersistenciaException;
}