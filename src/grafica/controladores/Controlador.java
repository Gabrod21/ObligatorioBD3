
package grafica.controladores;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

import logica.LogicaException;
import logica.RMI.IFachadaRemota;
import logica.valueObjects.VOActividad;
import logica.valueObjects.VOAlumno;
import logica.valueObjects.VOAlumnoProm;
import logica.valueObjects.VORegistro;
import logica.valueObjects.VORegistroListado;
import persistencia.conexiones.PersistenciaException;

public class Controlador {

    private IFachadaRemota fachada;

    public Controlador() throws RemoteException {

        try {
            this.fachada = (IFachadaRemota)
                Naming.lookup("rmi://localhost:711/Fachada");

        } catch (Exception e) {
            throw new RemoteException(
                "No se pudo conectar con el servidor RMI", e
            );
        }
    }

    // 1. Nuevo alumno
    public void nuevoAlumno(int cedula, String nombre, String apellido)
            throws RemoteException, PersistenciaException, LogicaException {

        VOAlumno voA = new VOAlumno(cedula, nombre, apellido);

        fachada.nuevoAlumno(voA);
    }

    // 2. Registrar nota
    public void registrarNota(int cedA, String actividad, int nota)
            throws RemoteException, PersistenciaException, LogicaException {

        VORegistro voR = new VORegistro(actividad, nota);

        fachada.registrarNota(cedA, voR);
    }

    // 3. Listar alumnos
    public List<VOAlumno> listarAlumnos()
            throws RemoteException, PersistenciaException, LogicaException {

        return fachada.listarAlumnos();
    }

    // 4. Obtener registro
    public VORegistro obtenerRegistro(int cedA, int numR)
            throws RemoteException, PersistenciaException, LogicaException {

        return fachada.obtenerRegistro(cedA, numR);
    }

    // 5. Listar registros
    public List<VORegistroListado> listarRegistros(int cedA)
            throws RemoteException, PersistenciaException, LogicaException {

        return fachada.listarRegistros(cedA);
    }

    // 6. Promedio de notas
    public double promedioNotas(int cedA)
            throws RemoteException, PersistenciaException, LogicaException {

        return fachada.promedioNotas(cedA);
    }

    // 7. Alumno con mayor promedio
    public VOAlumnoProm alumnoMayorPromedio()
            throws RemoteException, PersistenciaException, LogicaException {

        return fachada.alumnoMayorPromedio();
    }

    // 8. Contar alumnos
    public int contarAlumnos(String act, int nota)
            throws RemoteException, PersistenciaException, LogicaException {

        return fachada.contarAlumnos(act, nota);
    }

    // 9. Actividad más cursada
    public VOActividad actividadmasCursada(int cedA)
            throws RemoteException, PersistenciaException, LogicaException {

        return fachada.actividadmasCursada(cedA);
    }
}
