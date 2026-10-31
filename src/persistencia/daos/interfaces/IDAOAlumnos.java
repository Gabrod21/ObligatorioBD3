package persistencia.daos.interfaces;

import java.util.List;

import logica.Alumno;
import logica.valueObjects.VOAlumno;
import logica.valueObjects.VOAlumnoProm;
import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;

public interface IDAOAlumnos {

    boolean member(IConexion icon, int cedA) throws PersistenciaException;

    void insert(IConexion icon, Alumno alu) throws PersistenciaException;

    Alumno find(IConexion icon, int cedA) throws PersistenciaException;

    Boolean esVacio(IConexion icon) throws PersistenciaException;

    List<VOAlumno> listarAlumnos(IConexion icon) throws PersistenciaException;

    VOAlumnoProm alumnoMayorPromedio(IConexion icon) throws PersistenciaException;

    int contarAlumnos(IConexion icon, String act, int nota) throws PersistenciaException;
}