package persistencia.daos.interfaces;

public interface FabricaAbstracta {

    IDAOAlumnos crearIDAOAlumnos();

    IDAORegistros crearIDAORegistros(int cedA);
}