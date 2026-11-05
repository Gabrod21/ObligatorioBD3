
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
    public void nuevoAlumno(int cedula, String nombre, String apellido)
            throws RemoteException, PersistenciaException {

        VOAlumno voA = new VOAlumno(cedula, nombre, apellido);

        fachada.nuevoAlumno(voA);
    }

    // 2. Registrar nota
    public void registrarNota(int cedA, String actividad, int nota)
            throws RemoteException, PersistenciaException {

        VORegistro voR = new VORegistro(actividad, nota);

        fachada.registrarNota(cedA, voR);
    }

    // 3. Listar alumnos
    public List<VOAlumno> listarAlumnos()
            throws RemoteException, PersistenciaException {

        return fachada.listarAlumnos();
    }

    // 4. Obtener registro
    public VORegistro obtenerRegistro(int cedA, int numR)
            throws RemoteException, PersistenciaException {

        return fachada.obtenerRegistro(cedA, numR);
    }

    // 5. Listar registros
    public List<VORegistroListado> listarRegistros(int cedA)
            throws RemoteException, PersistenciaException {

        return fachada.listarRegistros(cedA);
    }

    // 6. Promedio de notas
    public double promedioNotas(int cedA)
            throws RemoteException, PersistenciaException {

        return fachada.promedioNotas(cedA);
    }
    
    // 7. Alumno con mayor promedio
    public VOAlumnoProm alumnoMayorPromedio()
            throws RemoteException, PersistenciaException {

        return fachada.alumnoMayorPromedio();
    }

    // 8. Contar alumnos
    public int contarAlumnos(String act, int nota)
            throws RemoteException, PersistenciaException {

        return fachada.contarAlumnos(act, nota);
    }

    // 9. Actividad más cursada
    public VOActividad actividadmasCursada(int cedA)
            throws RemoteException, PersistenciaException {

        return fachada.actividadmasCursada(cedA);
    }
}
