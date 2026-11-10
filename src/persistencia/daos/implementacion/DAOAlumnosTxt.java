
package persistencia.daos.implementacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import logica.Alumno;
import logica.LogicaException;
import logica.valueObjects.VOAlumno;
import logica.valueObjects.VOAlumnoProm;

import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.implementacion.ConexionTxt;
import persistencia.conexiones.interfaces.IConexion;
import persistencia.daos.interfaces.IDAOAlumnos;

public class DAOAlumnosTxt implements IDAOAlumnos {

    private ManejoTxt manejo;

    public DAOAlumnosTxt() throws PersistenciaException {
        manejo = new ManejoTxt();
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

    // Verifica que se permita modificar
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

    // Lee todos los alumnos
    private List<Alumno> leerAlumnos()
            throws PersistenciaException, LogicaException {

        List<Alumno> lista = new ArrayList<>();

        if (!manejo.existeTxt("alumnos.txt")) {
            return lista;
        }

        List<String> lineas = manejo.leerTxt("alumnos.txt");

        try {

            for (String linea : lineas) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split("\\|");

                if (datos.length != 3) {
                    throw new PersistenciaException(
                        "Formato incorrecto en alumnos.txt"
                    );
                }

                int cedula = Integer.parseInt(datos[0].trim());
                String nombre = datos[1].trim();
                String apellido = datos[2].trim();

                // Cada alumno tiene su propio DAO de registros TXT
                Alumno alu = new Alumno(
                    cedula,
                    nombre,
                    apellido
                );

                lista.add(alu);
            }

        } catch (NumberFormatException e) {

            throw new PersistenciaException(
                "Error en los datos de alumnos.txt"
            );
        }

        return lista;
    }

    // Guarda todos los alumnos ordenados por cedula
    private void guardarAlumnos(List<Alumno> lista)
            throws PersistenciaException {

        Collections.sort(
            lista,
            Comparator.comparingInt(Alumno::getCedula)
        );

        StringBuilder texto = new StringBuilder();

        for (Alumno alu : lista) {

            texto.append(alu.getCedula())
                 .append(" | ")
                 .append(alu.getNombre())
                 .append(" | ")
                 .append(alu.getApellido())
                 .append(System.lineSeparator());
        }

        manejo.escribirTxt("alumnos.txt", texto.toString());
    }

    // Lee los registros correspondientes a un alumno
    private List<String[]> leerRegistros(int cedula)
            throws PersistenciaException {

        List<String[]> lista = new ArrayList<>();

        String archivo = "registros" + cedula + ".txt";

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

                Integer.parseInt(datos[0].trim());
                Integer.parseInt(datos[2].trim());

                lista.add(datos);
            }

        } catch (NumberFormatException e) {

            throw new PersistenciaException(
                "Error en los datos de " + archivo
            );
        }

        return lista;
    }

    // Calcula el promedio de notas de un alumno
    private double calcularPromedio(int cedula)
            throws PersistenciaException {

        List<String[]> registros = leerRegistros(cedula);

        if (registros.isEmpty()) {
            return 0;
        }

        int suma = 0;

        for (String[] datos : registros) {
            int nota = Integer.parseInt(datos[2].trim());
            suma += nota;
        }

        return (double) suma / registros.size();
    }

    @Override
    public boolean member(IConexion icon, int cedA)
            throws PersistenciaException, LogicaException {

        validarConexion(icon);

        List<Alumno> lista = leerAlumnos();

        for (Alumno alu : lista) {

            if (alu.getCedula() == cedA) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void insert(IConexion icon, Alumno alu)
            throws PersistenciaException, LogicaException {

        validarModificacion(icon);

        List<Alumno> lista = leerAlumnos();

        if (member(icon, alu.getCedula())) {
            throw new PersistenciaException(
                "El alumno ya existe"
            );
        }

        lista.add(alu);

        guardarAlumnos(lista);

        // Creamos el archivo individual de registros
        String archivo = "registros" + alu.getCedula() + ".txt";

        if (!manejo.existeTxt(archivo)) {
            manejo.escribirTxt(archivo, "");
        }
    }

    @Override
    public Alumno find(IConexion icon, int cedA)
            throws PersistenciaException, LogicaException {

        validarConexion(icon);

        List<Alumno> lista = leerAlumnos();

        for (Alumno alu : lista) {

            if (alu.getCedula() == cedA) {
                return alu;
            }
        }

        return null;
    }

    @Override
    public Boolean esVacio(IConexion icon)
            throws PersistenciaException, LogicaException {

        validarConexion(icon);

        return leerAlumnos().isEmpty();
    }

    @Override
    public List<VOAlumno> listarAlumnos(IConexion icon)
            throws PersistenciaException, LogicaException {

        validarConexion(icon);

        List<VOAlumno> resultado = new ArrayList<>();

        List<Alumno> lista = leerAlumnos();

        for (Alumno alu : lista) {

            VOAlumno vo = new VOAlumno(
                alu.getCedula(),
                alu.getNombre(),
                alu.getApellido()
            );

            resultado.add(vo);
        }

        return resultado;
    }

    @Override
    public VOAlumnoProm alumnoMayorPromedio(IConexion icon)
            throws PersistenciaException, LogicaException {

        validarConexion(icon);

        List<Alumno> lista = leerAlumnos();

        VOAlumnoProm mayor = null;

        double mayorPromedio = -1;

        for (Alumno alu : lista) {

            List<String[]> registros =
                leerRegistros(alu.getCedula());

            // No consideramos alumnos sin registros
            if (!registros.isEmpty()) {

                double promedio =
                    calcularPromedio(alu.getCedula());

                if (promedio > mayorPromedio) {

                    mayorPromedio = promedio;

                    mayor = new VOAlumnoProm(
                        alu.getCedula(),
                        alu.getNombre(),
                        alu.getApellido(),
                        promedio
                    );
                }
            }
        }

        return mayor;
    }

    @Override
    public int contarAlumnos(IConexion icon, String act, int nota)
            throws PersistenciaException, LogicaException {

        validarConexion(icon);

        List<Alumno> lista = leerAlumnos();

        int contador = 0;

        for (Alumno alu : lista) {

            List<String[]> registros =
                leerRegistros(alu.getCedula());

            for (String[] datos : registros) {

                String actividad = datos[1].trim();

                int notaRegistro =
                    Integer.parseInt(datos[2].trim());

                if (actividad.equals(act) && notaRegistro == nota) {

                    contador++;

                    // Contamos al alumno una sola vez
                    break;
                }
            }
        }

        return contador;
    }
}
