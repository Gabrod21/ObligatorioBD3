package persistencia.daos.interfaces;

import java.util.List;

import logica.Alumno;
import logica.LogicaException;
import logica.valueObjects.VOAlumno;
import logica.valueObjects.VOAlumnoProm;
import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;

public interface IDAOAlumnos {

    boolean member(IConexion icon, int cedA) throws PersistenciaException, LogicaException;

    void insert(IConexion icon, Alumno alu) throws PersistenciaException, LogicaException;

    Alumno find(IConexion icon, int cedA) throws PersistenciaException, LogicaException;

    Boolean esVacio(IConexion icon) throws PersistenciaException, LogicaException;

    List<VOAlumno> listarAlumnos(IConexion icon) throws PersistenciaException, LogicaException;

    VOAlumnoProm alumnoMayorPromedio(IConexion icon) throws PersistenciaException, LogicaException;

    int contarAlumnos(IConexion icon, String act, int nota) throws PersistenciaException, LogicaException;
}