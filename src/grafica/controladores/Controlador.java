
package grafica.controladores;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

import logica.Fachada;
import logica.valueObjects.VOActividad;
import logica.valueObjects.VOAlumno;
import logica.valueObjects.VOAlumnoProm;
import logica.valueObjects.VORegistro;
import logica.valueObjects.VORegistroListado;
import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.implementacion.PoolConexiones;
import persistencia.conexiones.interfaces.IPoolConexiones;

public class Controlador {

    private Fachada fachada;

    public Controlador() {
    	IPoolConexiones pool = null;
		try {
			pool = new PoolConexiones();
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//if (pool != null) System.out.println("pool no es null");
		
		try {
			this.fachada = new Fachada(pool);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // 1. Nuevo alumno
    public void nuevoAlumno(int cedula, String nombre, String apellido) throws RemoteException {
        VOAlumno voA = new VOAlumno(cedula, nombre, apellido);

        try {
            fachada.nuevoAlumno(voA);
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }
    }

    // 2. Registrar nota
    public void registrarNota(int cedA, String actividad, int nota) throws RemoteException {
        VORegistro voR = new VORegistro(actividad, nota);

        try {
            fachada.registrarNota(cedA, voR);
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }
    }

    // 3. Listar alumnos
    public List<VOAlumno> listarAlumnos() throws RemoteException {
        try {
            return fachada.listarAlumnos();
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 4. Obtener registro
    public VORegistro obtenerRegistro(int cedA, int numR) throws RemoteException {
        try {
            return fachada.obtenerRegistro(cedA, numR);
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 5. Listar registros
    public List<VORegistroListado> listarRegistros(int cedA) throws RemoteException {
        try {
            return fachada.listarRegistros(cedA);
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 6. Promedio de notas
    public double promedioNotas(int cedA) throws RemoteException {
        try {
            return fachada.promedioNotas(cedA);
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // 7. Alumno con mayor promedio
    public VOAlumnoProm alumnoMayorPromedio() throws RemoteException {
        try {
            return fachada.alumnoMayorPromedio();
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 8. Contar alumnos
    public int contarAlumnos(String act, int nota) throws RemoteException {
        try {
            return fachada.contarAlumnos(act, nota);
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // 9. Actividad más cursada
    public VOActividad actividadmasCursada(int cedA) throws RemoteException {
        try {
            return fachada.actividadmasCursada(cedA);
        } catch (PersistenciaException e) {
            e.printStackTrace();
        }

        return null;
    }
}
