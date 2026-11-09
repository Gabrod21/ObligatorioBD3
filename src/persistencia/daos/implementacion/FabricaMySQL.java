package persistencia.daos.implementacion;

import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.implementacion.PoolConexiones;
import persistencia.conexiones.interfaces.IPoolConexiones;
import persistencia.daos.interfaces.FabricaAbstracta;
import persistencia.daos.interfaces.IDAOAlumnos;
import persistencia.daos.interfaces.IDAORegistros;

public class FabricaMySQL implements FabricaAbstracta {

    @Override
    public IDAOAlumnos crearIDAOAlumnos() {
        return new DAOAlumnos();
    }

    @Override
    public IDAORegistros crearIDAORegistros(int cedA) {
        return new DAORegistros(cedA);
    }

    @Override
    public IPoolConexiones crearPoolConexiones() {
        try {
			return new PoolConexiones();
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		return null;
    }
}