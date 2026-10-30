package logica;

import java.util.List;

import logica.valueObjects.VOActividad;
import logica.valueObjects.VOAlumno;
import logica.valueObjects.VOAlumnoProm;
import logica.valueObjects.VORegistro;
import logica.valueObjects.VORegistroListado;
import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;
import persistencia.conexiones.interfaces.IPoolConexiones;
import persistencia.daos.DAOAlumnos;

public class Fachada {

    private IPoolConexiones pool;

    public Fachada(IPoolConexiones pool) {
        this.pool = pool;
    }

    public void nuevoAlumno(VOAlumno voA) throws PersistenciaException {
        IConexion icon = pool.obtenerConexion(true);
        boolean transaccionOK = false;

        try {
            DAOAlumnos dao = new DAOAlumnos();

            if (dao.member(icon, voA.getCedula())) {
                throw new PersistenciaException("El alumno ya existe");
            }

            Alumno alu = new Alumno(voA.getCedula(), voA.getNombre(), voA.getApellido());
            dao.insert(icon, alu);
            transaccionOK = true;
        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public void registraNota(int cedA, VORegistro voR) throws PersistenciaException {
        IConexion icon = pool.obtenerConexion(true);
        boolean transaccionOK = false;

        try {
            DAOAlumnos dao = new DAOAlumnos();
            Alumno alu = dao.find(icon, cedA);

            if (alu == null) {
                throw new PersistenciaException("El alumno no existe");
            }

            int numero = alu.cantidadRegistros(icon) + 1;
            Registro reg = new Registro(numero, voR.getNota(), voR.getActividad());
            alu.agregarRegistro(icon, reg);

            transaccionOK = true;
        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public List<VOAlumno> listarAlumnos() throws PersistenciaException {
        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            List<VOAlumno> lista = new DAOAlumnos().listarAlumnos(icon);
            transaccionOK = true;
            return lista;
        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public VORegistro obtenerRegistro(int cedA, int numR) throws PersistenciaException {
        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            DAOAlumnos dao = new DAOAlumnos();
            Alumno alu = dao.find(icon, cedA);

            if (alu == null) {
                throw new PersistenciaException("El alumno no existe");
            }

            VORegistro reg = alu.obtenerRegistro(icon, numR);

            if (reg == null) {
                throw new PersistenciaException("El registro no existe");
            }

            transaccionOK = true;
            return reg;
        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public List<VORegistroListado> listarRegistros(int cedA) throws PersistenciaException {
        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            DAOAlumnos dao = new DAOAlumnos();
            Alumno alu = dao.find(icon, cedA);

            if (alu == null) {
                throw new PersistenciaException("El alumno no existe");
            }

            List<VORegistroListado> lista = alu.listarRegistros(icon);
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
            DAOAlumnos dao = new DAOAlumnos();
            Alumno alu = dao.find(icon, cedA);

            if (alu == null) {
                throw new PersistenciaException("El alumno no existe");
            }

            double promedio = alu.promedioNotas(icon);
            transaccionOK = true;
            return promedio;
        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public VOAlumnoProm alumnoMayorPromedio() throws PersistenciaException {
        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            DAOAlumnos dao = new DAOAlumnos();

            if (dao.esVacio(icon)) {
                throw new PersistenciaException("No hay alumnos");
            }

            VOAlumnoProm alu = dao.alumnoMayorPromedio(icon);

            if (alu == null) {
                throw new PersistenciaException("No hay alumnos con notas registradas");
            }

            transaccionOK = true;
            return alu;
        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public int contarAlumnos(String act, int nota) throws PersistenciaException {
        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            int cantidad = new DAOAlumnos().contarAlumnos(icon, act, nota);
            transaccionOK = true;
            return cantidad;
        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }

    public VOActividad actividadmasCursada(int cedA) throws PersistenciaException {
        IConexion icon = pool.obtenerConexion(false);
        boolean transaccionOK = false;

        try {
            DAOAlumnos dao = new DAOAlumnos();
            Alumno alu = dao.find(icon, cedA);

            if (alu == null) {
                throw new PersistenciaException("El alumno no existe");
            }

            if (alu.cantidadRegistros(icon) == 0) {
                throw new PersistenciaException("El alumno no tiene actividades registradas");
            }

            VOActividad actividad = alu.actividadMasCursada(icon);
            transaccionOK = true;
            return actividad;
        } finally {
            pool.liberarConexion(icon, transaccionOK);
        }
    }
}