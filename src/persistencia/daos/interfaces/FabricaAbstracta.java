package persistencia.daos.interfaces;

import persistencia.conexiones.PersistenciaException;

public interface FabricaAbstracta 
{
    IDAOAlumnos crearIDAOAlumnos() throws PersistenciaException  ;

    IDAORegistros crearIDAORegistros(int cedA) throws PersistenciaException ;
}