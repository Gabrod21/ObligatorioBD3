package logica.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import logica.valueObjects.*;
import persistencia.conexiones.PersistenciaException;

public interface IFachadaRemota extends Remote {

    void nuevoAlumno(VOAlumno voA)
            throws RemoteException, PersistenciaException;

    void registrarNota(int cedA, VORegistro voR)
            throws RemoteException, PersistenciaException;

    List<VOAlumno> listarAlumnos()
            throws RemoteException, PersistenciaException;

    VORegistro obtenerRegistro(int cedA, int numR)
            throws RemoteException, PersistenciaException;

    List<VORegistroListado> listarRegistros(int cedA)
            throws RemoteException, PersistenciaException;

    double promedioNotas(int cedA)
            throws RemoteException, PersistenciaException;

    VOAlumnoProm alumnoMayorPromedio()
            throws RemoteException, PersistenciaException;

    int contarAlumnos(String act, int nota)
            throws RemoteException, PersistenciaException;

    VOActividad actividadmasCursada(int cedA)
            throws RemoteException, PersistenciaException;
}