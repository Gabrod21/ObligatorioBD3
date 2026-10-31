package persistencia.daos.interfaces;

import java.util.List;

import logica.Registro;
import logica.valueObjects.VOActividad;
import logica.valueObjects.VORegistroListado;
import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;

public interface IDAORegistros {

    int largo(IConexion icon) throws PersistenciaException;

    void insback(IConexion icon, Registro reg) throws PersistenciaException;

    Registro kesimo(IConexion icon, int numR) throws PersistenciaException;

    List<VORegistroListado> listarRegistros(IConexion icon)
            throws PersistenciaException;

    double promedioNotas(IConexion icon) throws PersistenciaException;

    VOActividad actividadMasCursada(IConexion icon)
            throws PersistenciaException;
}