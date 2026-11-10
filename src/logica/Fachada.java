
package logica;

import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Properties;

import logica.RMI.IFachadaRemota;
import logica.valueObjects.VOActividad;
import logica.valueObjects.VOAlumno;
import logica.valueObjects.VOAlumnoProm;
import logica.valueObjects.VORegistro;
import logica.valueObjects.VORegistroListado;

import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;
import persistencia.conexiones.interfaces.IPoolConexiones;

import persistencia.daos.interfaces.FabricaAbstracta;
import persistencia.daos.interfaces.IDAOAlumnos;

public class Fachada extends UnicastRemoteObject implements IFachadaRemota {

    private IPoolConexiones pool;
    private FabricaAbstracta fabrica;
    private IDAOAlumnos idaoalumnos;

    public Fachada() throws RemoteException {
        super();

        Properties propiedades = new Properties();

        try (FileInputStream fis = new FileInputStream("config.properties")) {

            propiedades.load(fis);

            String nombreClase = propiedades.getProperty("fabrica");

            Class<?> clase = Class.forName(nombreClase);

            fabrica = (FabricaAbstracta) clase
                    .getDeclaredConstructor()
                    .newInstance();
            
            this.pool = fabrica.crearPoolConexiones();
            this.idaoalumnos = fabrica.crearIDAOAlumnos();

        } catch (Exception e) {
            throw new RemoteException(
                "Error al inicializar la fábrica: " + e.getMessage(), e
            );
        }
    }

    public void nuevoAlumno(VOAlumno voA)
            throws PersistenciaException, LogicaException {

        IConexion icon = pool.obtenerConexion(true);
        boolean transaccionOK = false;

        try {

            if (idaoalumnos.member(icon, voA.getCedula())) {
                throw new LogicaException("El alumno ya existe");
            }

            Alumno alu = new Alumno(
                voA.getCedula(),
                voA.getNombre(),
                voA.getApellido()
            );

            idaoalumnos.insert(icon, alu);

            transaccionOK = true;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public void registrarNota(int cedA, VORegistro voR)
            throws PersistenciaException, LogicaException {

        IConexion icon = pool.obtenerConexion(true);
        boolean transaccionOK = false;

        try {
            if (!idaoalumnos.member(icon, cedA)) {
                throw new LogicaException("El alumno no existe");
            }
            else {
            	
            	Alumno alu = idaoalumnos.find(icon, cedA);
            	int cantidadRegistros = alu.cantidadRegistros(icon);
            	
            	Registro reg = new Registro(
                        cantidadRegistros + 1,
                        voR.getNota(),
                        voR.getActividad()
                    );
            	alu.agregarRegistro(icon, reg);

                transaccionOK = true;
            	
            }



        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public List<VOAlumno> listarAlumnos()
            throws PersistenciaException, LogicaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {

            List<VOAlumno> lista = idaoalumnos.listarAlumnos(icon);

            transaccionOK = true;
            return lista;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public VORegistro obtenerRegistro(int cedA, int numR)
            throws PersistenciaException, LogicaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;
        VORegistro vor = null;

        try {

            if (!idaoalumnos.member(icon, cedA)) {
                throw new LogicaException("El alumno no existe");
            }
            else {
            	
            	Alumno alu = idaoalumnos.find(icon, cedA);
            	vor = alu.obtenerRegistro(icon, numR);
            	
            	if (vor == null) {
                    throw new LogicaException("El registro no existe");
                }
            	
            }

            
            transaccionOK = true;
            return vor;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public List<VORegistroListado> listarRegistros(int cedA)
            throws PersistenciaException, LogicaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;
        List<VORegistroListado> lista = null;

        try {

            if (!idaoalumnos.member(icon, cedA)) {
                throw new LogicaException("El alumno no existe");
            }
            else {
            	
            	Alumno alu = idaoalumnos.find(icon, cedA);
            	lista = alu.listarRegistros(icon);
            }
          

            transaccionOK = true;
            return lista;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public double promedioNotas(int cedA)
            throws PersistenciaException, LogicaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;
        double promedioNotas = 0;

        try {
            if (!idaoalumnos.member(icon, cedA)) {
                throw new LogicaException("El alumno no existe");
            }
            else {
            	
            	Alumno alu = idaoalumnos.find(icon, cedA);
            	
            	if (alu.cantidadRegistros(icon) == 0) {
            		
            		throw new LogicaException("No hay registros para el alumno");
            		
            	}
            	promedioNotas = alu.promedioNotas(icon);
            	
            }

            transaccionOK = true;
            return promedioNotas;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public VOAlumnoProm alumnoMayorPromedio()
            throws PersistenciaException, LogicaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {

            if (idaoalumnos.esVacio(icon)) {
                throw new LogicaException("No hay alumnos");
            }

            VOAlumnoProm alu = idaoalumnos.alumnoMayorPromedio(icon);

            if (alu == null) {
                throw new LogicaException(
                    "No hay alumnos con notas registradas"
                );
            }

            transaccionOK = true;
            return alu;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public int contarAlumnos(String act, int nota)
            throws PersistenciaException, LogicaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {

            int cantidad = idaoalumnos.contarAlumnos(icon, act, nota);

            transaccionOK = true;
            return cantidad;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public VOActividad actividadmasCursada(int cedA)
            throws PersistenciaException, LogicaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;
        VOActividad actMasCursada = null;
        
        try {

            if (!idaoalumnos.member(icon, cedA)) {
                throw new LogicaException("El alumno no existe");
            }
            else {
            	            	
            	Alumno alu = idaoalumnos.find(icon, cedA);
            	
            	if (alu.cantidadRegistros(icon) == 0) {
            		throw new LogicaException("El alumno no tiene registros");
            	}
            	else {
            		actMasCursada = alu.actividadMasCursada(icon);
            	}
            	
            	
            }


            transaccionOK = true;
            return actMasCursada;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }
}
