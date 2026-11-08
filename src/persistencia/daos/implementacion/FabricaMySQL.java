package persistencia.daos.implementacion;

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
}