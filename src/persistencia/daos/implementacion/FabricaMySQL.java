package persistencia.daos.implementacion;

import persistencia.daos.interfaces.FabricaAbstracta;
import persistencia.daos.interfaces.IDAOAlumnos;
import persistencia.daos.interfaces.IDAORegistros;

public class FabricaMySQL implements FabricaAbstracta {

    @Override
    public IDAOAlumnos crearIDAOAlumnos() {
        try {
            String nombreClase =
                "persistencia.daos.implementacion.DAOAlumnos";

            Class<?> clase = Class.forName(nombreClase);

            return (IDAOAlumnos) clase
                    .getDeclaredConstructor()
                    .newInstance();

        } catch (Exception e) {
            throw new RuntimeException(
                "Error al crear DAOAlumnos: " + e.getMessage()
            );
        }
    }

    @Override
    public IDAORegistros crearIDAORegistros(int cedA) {
        try {
            String nombreClase =
                "persistencia.daos.implementacion.DAORegistros";

            Class<?> clase = Class.forName(nombreClase);

            return (IDAORegistros) clase
                    .getDeclaredConstructor(int.class)
                    .newInstance(cedA);

        } catch (Exception e) {
            throw new RuntimeException(
                "Error al crear DAORegistros: " + e.getMessage()
            );
        }
    }
}