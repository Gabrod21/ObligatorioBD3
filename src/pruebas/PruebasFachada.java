package pruebas;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

import logica.Fachada;
import logica.valueObjects.VORegistroListado;
import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.implementacion.PoolConexiones;
import persistencia.conexiones.interfaces.IPoolConexiones;
import logica.valueObjects.VOAlumno;
import logica.valueObjects.VOActividad;
import logica.valueObjects.VOAlumnoProm;
import logica.valueObjects.VORegistro;

public class PruebasFachada
{
	public static void main (String[] args) throws PersistenciaException
	{
		InputStreamReader is = new InputStreamReader (System.in);
		BufferedReader br = new BufferedReader (is);
		
		IPoolConexiones pool = new PoolConexiones();
		Fachada fach = null;
		try {
			fach = new Fachada(pool);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//1: Prueba método nuevoAlumno(voA: VOAlumno): void			--OK, passed
		Random random = new Random();
		int cedula = 10000000 + random.nextInt(90000000);
		
		VOAlumno voA = new VOAlumno(cedula, "nombre", "apellido");
		fach.nuevoAlumno(voA);
		
		
		//2: Prueba método registrarNota(cedA: int, voR: VORegistro): void		--OK, passed
		VORegistro voR = new VORegistro("actividad", 12);
		fach.registrarNota(cedula, voR);
		
		
		//3: Prueba método listarAlumnos(): List<VOAlumno>			--OK, passed
		List<VOAlumno> alumnos = fach.listarAlumnos();
		
		System.out.println("\n\nLista Alumnos:\n");
		for (VOAlumno voa: alumnos) {
			int ced = voa.getCedula();
			String nombre = voa.getNombre();
			String apellido = voa.getApellido();
			System.out.println(ced + " - " + nombre + " " + apellido);
		}
		
		
		//4: Prueba método obtenerRegistro(cedA: int, numR: int): VORegistro			--OK, passed
		System.out.println("\n\nRegistro obtenido:\n");
		VORegistro vor = fach.obtenerRegistro(cedula, 1);
		String actividad = vor.getActividad();
		int nota = vor.getNota();
		System.out.println(actividad + " : " + nota);
		
		
		//5: Prueba método listarRegistros(cedA: int): List<VORegistroListado>			--OK, passed
		System.out.println("\n\nRegistros listados:\n");
		List<VORegistroListado> registros = fach.listarRegistros(cedula);
		
		for (VORegistroListado reglistado: registros) {
			int numero = reglistado.getNumero();
			int notareglistado = reglistado.getNota();
			String actividadreglistado = reglistado.getActividad();
			System.out.println(numero + " - " + notareglistado + " " + actividadreglistado);
		}
		
		
		//6: Prueba método promedioNotas(cedA: int): double			--OK, passed
		double promedioNotas = fach.promedioNotas(cedula);
		System.out.println("\n\nPromedio notas: " + promedioNotas + "\n");
		
		
		//7: Prueba método alumnoMayorPromedio(): VOAlumnoProm			--OK, passed
		VOAlumnoProm voap = fach.alumnoMayorPromedio();
		int cedMayProm = voap.getCedula();
		String nomMayProm = voap.getNombre();
		String apellMayProm = voap.getApellido();
		double promMayProm = voap.getPromedio();
		System.out.println("\n\nAlumno mayor promedio:\n");
		System.out.println(cedMayProm + " - " + nomMayProm + " " + apellMayProm + " " + promMayProm);
		
		
		
		//8: Prueba método contarAlumnos(act: String, nota: int): int			--OK, passed
		int cantAlumnos = fach.contarAlumnos("actividad", 12);
		System.out.println("\n\n8. Cantidad de alumnos:\n");
		System.out.println(cantAlumnos);
		
		
		
		//9: Prueba método actividadMasCursada(cedA: int): VOActividad			--OK, passed
		VOActividad voActMasCursada = fach.actividadmasCursada(cedula);
		String nombreActividad = voActMasCursada.getActividad();
		int vecesCursada = voActMasCursada.getVeces();
		System.out.println("\n\n9. Actividad mas cursada:\n");
		System.out.println(nombreActividad + ", cursada " + vecesCursada + " veces" );
		
	}
}
