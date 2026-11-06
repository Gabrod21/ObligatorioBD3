package persistencia.daos.implementacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import logica.Registro;
import logica.valueObjects.VOActividad;
import logica.valueObjects.VORegistroListado;
import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;
import persistencia.daos.interfaces.IDAORegistros;


public class DAORegistrosTxt implements IDAORegistros
{
    private int cedAlumno;
    private ManejoTxt manejo;

    public DAORegistrosTxt(int cedA) throws PersistenciaException {
        this.cedAlumno = cedA;
        this.manejo = new ManejoTxt();
    }

    // Devuelve el nombre del archivo del alumno
    private String nombreTxt() {
        return "registros" + cedAlumno + ".txt";
    }

    // Lee todos los registros del Txt
    private List<Registro> leerRegistros() throws PersistenciaException {

        List<Registro> lista = new ArrayList<>();

        if (!manejo.existeTxt(nombreTxt())) {
            return lista;
        }

        List<String> lineas = manejo.leerTxt(nombreTxt());

        try {
            for (String linea : lineas) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split("\\|");

                if (datos.length != 3) {
                    throw new PersistenciaException(
                        "Formato incorrecto en " + nombreTxt()
                    );
                }

                int numero = Integer.parseInt(datos[0].trim());
                String actividad = datos[1].trim();
                int nota = Integer.parseInt(datos[2].trim());

                Registro reg = new Registro(numero, nota, actividad);

                lista.add(reg);
            }

        } catch (NumberFormatException e) {
            throw new PersistenciaException(
                "Error en los datos de " + nombreTxt()
            );
        }

        Collections.sort(lista,
            Comparator.comparingInt(Registro::getNumero));

        return lista;
    }

    // Guarda los registros ordenados por número
    private void guardarRegistros(List<Registro> lista) throws PersistenciaException {

        Collections.sort(lista,
            Comparator.comparingInt(Registro::getNumero));

        StringBuilder texto = new StringBuilder();

        for (Registro reg : lista) {

            texto.append(reg.getNumero())
                 .append(" | ")
                 .append(reg.getActividad())
                 .append(" | ")
                 .append(reg.getNota())
                 .append(System.lineSeparator());
        }

        manejo.escribirTxt(nombreTxt(), texto.toString());
    }

    // Cantidad / último número de registro
    public int largo(IConexion icon) throws PersistenciaException {

        List<Registro> lista = leerRegistros();

        if (lista.isEmpty()) {
            return 0;
        }

        return lista.get(lista.size() - 1).getNumero();
    }

    // Agrega un registro al final lógico de la secuencia
    public void insback(IConexion icon, Registro reg)
            throws PersistenciaException {

        List<Registro> lista = leerRegistros();

        lista.add(reg);

        guardarRegistros(lista);
    }

    // Obtiene un registro por su número
    public Registro kesimo(IConexion icon, int numR) throws PersistenciaException {

        List<Registro> lista = leerRegistros();

        for (Registro reg : lista) {

            if (reg.getNumero() == numR) {
                return reg;
            }
        }

        return null;
    }

    // Devuelve el listado de registros
    public List<VORegistroListado> listarRegistros(IConexion icon) throws PersistenciaException {

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

    // Calcula el promedio de notas
    public double promedioNotas(IConexion icon) throws PersistenciaException {

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

    // Devuelve la actividad que más veces aparece
    public VOActividad actividadMasCursada(IConexion icon) throws PersistenciaException {

        List<Registro> lista = leerRegistros();

        if (lista.isEmpty()) {
            return null;
        }

        String actividadMayor = null;
        int cantidadMayor = 0;

        List<String> actividades = new ArrayList<>();

        for (Registro reg : lista) {

            String actividad = reg.getActividad();

            if (!actividades.contains(actividad)) {
                actividades.add(actividad);
            }
        }

        for (String actividad : actividades) {

            int cantidad = 0;

            for (Registro reg : lista) {

                if (reg.getActividad().equals(actividad)) {
                    cantidad++;
                }
            }

            if (cantidad > cantidadMayor) {
                cantidadMayor = cantidad;
                actividadMayor = actividad;
            }
        }

        return new VOActividad(actividadMayor, cantidadMayor);
    }
}
