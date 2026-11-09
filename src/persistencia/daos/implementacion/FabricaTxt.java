package persistencia.daos.implementacion;

import persistencia.daos.interfaces.IDAOAlumnos;
import persistencia.daos.interfaces.IDAORegistros;
import persistencia.daos.interfaces.FabricaAbstracta;
import persistencia.conexiones.interfaces.IPoolConexiones;
import persistencia.conexiones.implementacion.PoolConexionesTxt;
import persistencia.conexiones.PersistenciaException;

public class FabricaTxt implements FabricaAbstracta
{
    @Override
    public IDAOAlumnos crearIDAOAlumnos() throws PersistenciaException 
    {
        return new DAOAlumnosTxt();
    }

    @Override
    public IDAORegistros crearIDAORegistros(int cedA) throws PersistenciaException 
    {
        return new DAORegistrosTxt(cedA);
    }

    @Override
    public IPoolConexiones crearPoolConexiones() throws PersistenciaException
    {
        return new PoolConexionesTxt();
    }
}