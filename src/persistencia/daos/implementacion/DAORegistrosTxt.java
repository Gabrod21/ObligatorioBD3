
package persistencia.daos.implementacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import logica.Registro;
import logica.valueObjects.VOActividad;
import logica.valueObjects.VORegistroListado;
import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.implementacion.ConexionTxt;
import persistencia.conexiones.interfaces.IConexion;
import persistencia.daos.interfaces.IDAORegistros;

public class DAORegistrosTxt implements IDAORegistros {

    private int cedula;
    private ManejoTxt manejo;

    public DAORegistrosTxt(int cedula) throws PersistenciaException {
        this.cedula = cedula;
        manejo = new ManejoTxt();
    }

    // Devuelve el nombre del archivo correspondiente al alumno
    private String nombreArchivo() {
        return "registros" + cedula + ".txt";
    }

    // Verifica que sea una conexión TXT
    private void validarConexion(IConexion icon)
            throws PersistenciaException {

        if (!(icon instanceof ConexionTxt)) {
            throw new PersistenciaException(
                "Se requiere una conexion TXT"
            );
        }
    }

    // Verifica que la conexión permita modificar
    private void validarModificacion(IConexion icon)
            throws PersistenciaException {

        validarConexion(icon);

        ConexionTxt conexion = (ConexionTxt) icon;

        if (!conexion.getModifica()) {
            throw new PersistenciaException(
                "La conexion no permite modificar archivos"
            );
        }
    }

    // Lee todos los registros del archivo
    private List<Registro> leerRegistros()
            throws PersistenciaException {

        List<Registro> lista = new ArrayList<>();

        String archivo = nombreArchivo();

        if (!manejo.existeTxt(archivo)) {
            return lista;
        }

        List<String> lineas = manejo.leerTxt(archivo);

        try {
            for (String linea : lineas) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split("\\|");

                if (datos.length != 3) {
                    throw new PersistenciaException(
                        "Formato incorrecto en " + archivo
                    );
                }

                int numero = Integer.parseInt(datos[0].trim());
                String actividad = datos[1].trim();
                int nota = Integer.parseInt(datos[2].trim());

                Registro reg = new Registro(
                    numero,
                    nota,
                    actividad
                );

                lista.add(reg);
            }

        } catch (NumberFormatException e) {
            throw new PersistenciaException(
                "Error en los datos de " + archivo
            );
        }

        return lista;
    }

    @Override
    public int largo(IConexion icon)
            throws PersistenciaException {

        validarConexion(icon);

        return leerRegistros().size();
    }

    @Override
    public void insback(IConexion icon, Registro reg)
            throws PersistenciaException {

        validarModificacion(icon);

        int siguiente = largo(icon) + 1;

        if (reg.getNumero() != siguiente) {
            throw new PersistenciaException(
                "El numero de registro debe ser " + siguiente
            );
        }

        String linea = reg.getNumero() + " | "
                     + reg.getActividad() + " | "
                     + reg.getNota();

        manejo.agregarTxt(nombreArchivo(), linea);
    }

    @Override
    public Registro kesimo(IConexion icon, int k)
            throws PersistenciaException {

        validarConexion(icon);

        List<Registro> lista = leerRegistros();

        for (Registro reg : lista) {

            if (reg.getNumero() == k) {
                return reg;
            }
        }

        return null;
    }

    @Override
    public List<VORegistroListado> listarRegistros(IConexion icon)
            throws PersistenciaException {

        validarConexion(icon);

        List<VORegistroListado> resultado = new ArrayList<>();

        List<Registro> lista = leerRegistros();

        for (Registro reg : lista) {

            VORegistroListado vo = new VORegistroListado(
                reg.getActividad(),
                reg.getNota(),
                reg.getNumero()
            );

            resultado.add(vo);
        }

        return resultado;
    }

    @Override
    public double promedioNotas(IConexion icon)
            throws PersistenciaException {

        validarConexion(icon);

        List<Registro> lista = leerRegistros();

        if (lista.isEmpty()) {
            return 0;
        }

        int suma = 0;

        for (Registro reg : lista) {
            suma += reg.getNota();
        }

        return (double) suma / lista.size();
    }

    @Override
    public VOActividad actividadMasCursada(IConexion icon)
            throws PersistenciaException {

        validarConexion(icon);

        List<Registro> lista = leerRegistros();

        if (lista.isEmpty()) {
            return null;
        }

        Map<String, Integer> cantidades = new LinkedHashMap<>();

        for (Registro reg : lista) {

            String actividad = reg.getActividad();

            cantidades.put(
                actividad,
                cantidades.getOrDefault(actividad, 0) + 1
            );
        }

        String mayorActividad = null;
        int mayorCantidad = 0;

        for (Map.Entry<String, Integer> entrada :
                cantidades.entrySet()) {

            if (entrada.getValue() > mayorCantidad) {

                mayorCantidad = entrada.getValue();
                mayorActividad = entrada.getKey();
            }
        }

        return new VOActividad(
            mayorActividad,
            mayorCantidad
        );
    }
}
