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
import persistencia.daos.interfaces.IDAORegistros;

public class Fachada extends UnicastRemoteObject implements IFachadaRemota {

    private IPoolConexiones pool;
    private FabricaAbstracta fabrica;

    public Fachada(IPoolConexiones pool) throws RemoteException {
        super();

        this.pool = pool;

        Properties propiedades = new Properties();

        try (FileInputStream fis = new FileInputStream("config.properties")) {

            propiedades.load(fis);

            String nombreClase = propiedades.getProperty("fabrica");

            Class<?> clase = Class.forName(nombreClase);

            fabrica = (FabricaAbstracta) clase
                    .getDeclaredConstructor()
                    .newInstance();

        } catch (Exception e) {
            throw new RemoteException(
                "Error al inicializar la fábrica: " + e.getMessage(), e
            );
        }
    }

    public void nuevoAlumno(VOAlumno voA) throws PersistenciaException {

        IConexion icon = pool.obtenerConexion(true);
        boolean transaccionOK = false;

        try {
            IDAOAlumnos dao = fabrica.crearIDAOAlumnos();

            if (dao.member(icon, voA.getCedula())) {
                throw new PersistenciaException("El alumno ya existe");
            }

            Alumno alu = new Alumno(
                voA.getCedula(),
                voA.getNombre(),
                voA.getApellido()
            );

            dao.insert(icon, alu);

            transaccionOK = true;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public void registrarNota(int cedA, VORegistro voR)
            throws PersistenciaException {

        IConexion icon = pool.obtenerConexion(true);
        boolean transaccionOK = false;

        try {
            IDAOAlumnos daoAlumnos = fabrica.crearIDAOAlumnos();

            if (!daoAlumnos.member(icon, cedA)) {
                throw new PersistenciaException("El alumno no existe");
            }

            IDAORegistros daoRegistros = fabrica.crearIDAORegistros(cedA);

            int numero = daoRegistros.largo(icon) + 1;

            Registro reg = new Registro(
                numero,
                voR.getNota(),
                voR.getActividad()
            );

            daoRegistros.insback(icon, reg);

            transaccionOK = true;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public List<VOAlumno> listarAlumnos() throws PersistenciaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            IDAOAlumnos dao = fabrica.crearIDAOAlumnos();

            List<VOAlumno> lista = dao.listarAlumnos(icon);

            transaccionOK = true;
            return lista;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public VORegistro obtenerRegistro(int cedA, int numR)
            throws PersistenciaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            IDAOAlumnos daoAlumnos = fabrica.crearIDAOAlumnos();

            if (!daoAlumnos.member(icon, cedA)) {
                throw new PersistenciaException("El alumno no existe");
            }

            IDAORegistros daoRegistros = fabrica.crearIDAORegistros(cedA);

            Registro reg = daoRegistros.kesimo(icon, numR);

            if (reg == null) {
                throw new PersistenciaException("El registro no existe");
            }

            VORegistro voR = new VORegistro(
                reg.getActividad(),
                reg.getNota()
            );

            transaccionOK = true;
            return voR;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public List<VORegistroListado> listarRegistros(int cedA)
            throws PersistenciaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            IDAOAlumnos daoAlumnos = fabrica.crearIDAOAlumnos();

            if (!daoAlumnos.member(icon, cedA)) {
                throw new PersistenciaException("El alumno no existe");
            }

            IDAORegistros daoRegistros = fabrica.crearIDAORegistros(cedA);

            List<VORegistroListado> lista =
                daoRegistros.listarRegistros(icon);

            transaccionOK = true;
            return lista;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public double promedioNotas(int cedA) throws PersistenciaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            IDAOAlumnos daoAlumnos = fabrica.crearIDAOAlumnos();

            if (!daoAlumnos.member(icon, cedA)) {
                throw new PersistenciaException("El alumno no existe");
            }

            IDAORegistros daoRegistros = fabrica.crearIDAORegistros(cedA);

            double promedio = daoRegistros.promedioNotas(icon);

            transaccionOK = true;
            return promedio;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public VOAlumnoProm alumnoMayorPromedio()
            throws PersistenciaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            IDAOAlumnos dao = fabrica.crearIDAOAlumnos();

            if (dao.esVacio(icon)) {
                throw new PersistenciaException("No hay alumnos");
            }

            VOAlumnoProm alu = dao.alumnoMayorPromedio(icon);

            if (alu == null) {
                throw new PersistenciaException(
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
            throws PersistenciaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            IDAOAlumnos dao = fabrica.crearIDAOAlumnos();

            int cantidad = dao.contarAlumnos(icon, act, nota);

            transaccionOK = true;
            return cantidad;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public VOActividad actividadmasCursada(int cedA)
            throws PersistenciaException {

        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            IDAOAlumnos daoAlumnos = fabrica.crearIDAOAlumnos();

            if (!daoAlumnos.member(icon, cedA)) {
                throw new PersistenciaException("El alumno no existe");
            }

            IDAORegistros daoRegistros = fabrica.crearIDAORegistros(cedA);

            if (daoRegistros.largo(icon) == 0) {
                throw new PersistenciaException(
                    "El alumno no tiene actividades registradas"
                );
            }

            VOActividad actividad =
                daoRegistros.actividadMasCursada(icon);

            transaccionOK = true;
            return actividad;

        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }
}