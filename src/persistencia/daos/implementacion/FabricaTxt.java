package persistencia.daos.implementacion;

import persistencia.daos.interfaces.IDAOAlumnos;
import persistencia.daos.interfaces.IDAORegistros;
import persistencia.daos.interfaces.FabricaAbstracta;
import persistencia.conexiones.PersistenciaException;

public class FabricaTxt implements FabricaAbstracta
{
    public IDAOAlumnos crearIDAOAlumnos() throws PersistenciaException 
    {
		return new DAOAlumnosTxt();
	}

    public IDAORegistros crearIDAORegistros(int cedA) throws PersistenciaException 
    {
		return new DAORegistrosTxt(cedA);
	}
}