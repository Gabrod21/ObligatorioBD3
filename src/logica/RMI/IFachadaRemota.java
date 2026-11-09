package logica.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import logica.LogicaException;
import logica.valueObjects.*;
import persistencia.conexiones.PersistenciaException;

public interface IFachadaRemota extends Remote {

    void nuevoAlumno(VOAlumno voA)
            throws RemoteException, PersistenciaException, LogicaException;

    void registrarNota(int cedA, VORegistro voR)
            throws RemoteException, PersistenciaException, LogicaException;

    List<VOAlumno> listarAlumnos()
            throws RemoteException, PersistenciaException;

    VORegistro obtenerRegistro(int cedA, int numR)
            throws RemoteException, PersistenciaException, LogicaException;

    List<VORegistroListado> listarRegistros(int cedA)
            throws RemoteException, PersistenciaException, LogicaException;

    double promedioNotas(int cedA)
            throws RemoteException, PersistenciaException, LogicaException;

    VOAlumnoProm alumnoMayorPromedio()
            throws RemoteException, PersistenciaException, LogicaException;

    int contarAlumnos(String act, int nota)
            throws RemoteException, PersistenciaException;

    VOActividad actividadmasCursada(int cedA)
            throws RemoteException, PersistenciaException, LogicaException;
}