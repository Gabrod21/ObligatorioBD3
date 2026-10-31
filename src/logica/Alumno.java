package logica;

import java.util.List;

import logica.valueObjects.VOActividad;
import logica.valueObjects.VORegistro;
import logica.valueObjects.VORegistroListado;
import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;
import persistencia.daos.implementacion.DAORegistros;

public class Alumno {

    private int cedula;
    private String nombre;
    private String apellido;
    private DAORegistros secuencia;

    public Alumno(int cedula, String nombre, String apellido) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.secuencia = new DAORegistros(cedula);
    }

    public int getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int cantidadRegistros(IConexion icon) throws PersistenciaException {
        return secuencia.largo(icon);
    }

    public void agregarRegistro(IConexion icon, Registro reg) throws PersistenciaException {
        secuencia.insback(icon, reg);
    }

    public VORegistro obtenerRegistro(IConexion icon, int numR) throws PersistenciaException {
        Registro reg = secuencia.kesimo(icon, numR);
        if (reg == null) {
            return null;
        }
        return new VORegistro(reg.getActividad(), reg.getNota());
    }

    public List<VORegistroListado> listarRegistros(IConexion icon) throws PersistenciaException {
        return secuencia.listarRegistros(icon);
    }

    public double promedioNotas(IConexion icon) throws PersistenciaException {
        return secuencia.promedioNotas(icon);
    }

    public VOActividad actividadMasCursada(IConexion icon) throws PersistenciaException {
        return secuencia.actividadMasCursada(icon);
    }

    // Versiones originales de la especificacion.
    // Para utilizarlas sin parametros, habria que definir como obtener
    // una conexion; no deben crear una conexion independiente.

    public int cantidadRegistros() {
        throw new UnsupportedOperationException("Se requiere una IConexion");
    }

    public void agregarRegistro(Registro reg) {
        throw new UnsupportedOperationException("Se requiere una IConexion");
    }

    public VORegistro obtenerRegistro(int numR) {
        throw new UnsupportedOperationException("Se requiere una IConexion");
    }

    public List<VORegistroListado> listarRegistros() {
        throw new UnsupportedOperationException("Se requiere una IConexion");
    }

    public double promedioNotas() {
        throw new UnsupportedOperationException("Se requiere una IConexion");
    }

    public VOActividad actividadMasCursada() {
        throw new UnsupportedOperationException("Se requiere una IConexion");
    }
}