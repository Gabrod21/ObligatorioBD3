
package logica;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import logica.valueObjects.VOActividad;
import logica.valueObjects.VORegistro;
import logica.valueObjects.VORegistroListado;
import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;
import persistencia.daos.interfaces.FabricaAbstracta;
import persistencia.daos.interfaces.IDAORegistros;

public class Alumno {

    private int cedula;
    private String nombre;
    private String apellido;
    private IDAORegistros secuencia;

    public Alumno(int cedula, String nombre, String apellido, IDAORegistros secuencia)
            throws PersistenciaException, LogicaException {

        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.secuencia = secuencia;

        // Cargar la fábrica dinámicamente desde config.properties.
        Properties propiedades = new Properties();

        try (FileInputStream fis = new FileInputStream("config.properties")) {

            propiedades.load(fis);

            String nombreClase = propiedades.getProperty("fabrica");

            Class<?> clase = Class.forName(nombreClase);

            FabricaAbstracta fabrica = (FabricaAbstracta) clase
                    .getDeclaredConstructor()
                    .newInstance();

            // Crear la secuencia correspondiente a este alumno.
            this.secuencia = fabrica.crearIDAORegistros(cedula);

        } catch (PersistenciaException e) {
            throw e;

        } catch (Exception e) {
            throw new LogicaException(
                    "Error al inicializar la fábrica: " + e.getMessage()
            );
        }
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

    public void agregarRegistro(IConexion icon, Registro reg)
            throws PersistenciaException {
        secuencia.insback(icon, reg);
    }

    public VORegistro obtenerRegistro(IConexion icon, int numR)
            throws PersistenciaException {

        Registro reg = secuencia.kesimo(icon, numR);

        if (reg == null) {
            return null;
        }

        return new VORegistro(reg.getActividad(), reg.getNota());
    }

    public List<VORegistroListado> listarRegistros(IConexion icon)
            throws PersistenciaException {
        return secuencia.listarRegistros(icon);
    }

    public double promedioNotas(IConexion icon) throws PersistenciaException {
        return secuencia.promedioNotas(icon);
    }

    public VOActividad actividadMasCursada(IConexion icon)
            throws PersistenciaException {
        return secuencia.actividadMasCursada(icon);
    }

    // Versiones originales de la especificación.

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
