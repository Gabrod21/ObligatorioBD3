
package persistencia.daos.implementacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import persistencia.conexiones.PersistenciaException;

public class ManejoTxt {

    private static String rutaTxt = null;

    public ManejoTxt() throws PersistenciaException {

        try {
            Properties p = new Properties();

            String nomTxtProp = "config.properties";

            try (FileInputStream fis = new FileInputStream(nomTxtProp)) {
                p.load(fis);
            }

            rutaTxt = p.getProperty("rutaTxt");

            if (rutaTxt == null || rutaTxt.trim().isEmpty()) {
                throw new IOException("No se configuró rutaTxt");
            }

            File directorio = new File(rutaTxt);

            if (!directorio.exists()) {
                directorio.mkdirs();
            }

        } catch (IOException e) {
            throw new PersistenciaException("Error en la persistencia de datos");
        }
    }

    public List<String> leerTxt(String nomTxt)
            throws PersistenciaException {

        List<String> resu = new ArrayList<>();

        String ruta = rutaTxt + File.separator + nomTxt;

        try (BufferedReader b = new BufferedReader(new FileReader(ruta))) {

            String aux;

            while ((aux = b.readLine()) != null) {
                resu.add(aux);
            }

        } catch (IOException e) {
            throw new PersistenciaException("Error lectura");
        }

        return resu;
    }

    public void escribirTxt(String nomTxt, String texto)
            throws PersistenciaException {

        String ruta = rutaTxt + File.separator + nomTxt;

        try (BufferedWriter b = new BufferedWriter(new FileWriter(ruta))) {

            b.write(texto);

        } catch (IOException e) {
            throw new PersistenciaException("Error escritura");
        }
    }

    public void agregarTxt(String nomTxt, String texto)
            throws PersistenciaException {

        String ruta = rutaTxt + File.separator + nomTxt;

        try (BufferedWriter b = new BufferedWriter(
                new FileWriter(ruta, true))) {

            b.write(texto);
            b.newLine();

        } catch (IOException e) {
            throw new PersistenciaException("Error escritura");
        }
    }

    public boolean existeTxt(String nomTxt) {

        String ruta = rutaTxt + File.separator + nomTxt;

        File txt = new File(ruta);

        return txt.exists();
    }

    public void borrarTxt(String nomTxt)
            throws PersistenciaException {

        String ruta = rutaTxt + File.separator + nomTxt;

        File txt = new File(ruta);

        if (!txt.exists() || !txt.delete()) {
            throw new PersistenciaException(
                    "Error al borrar el archivo");
        }
    }

    public List<String> listarTxt(String patron) {

        List<String> lista = new ArrayList<>();

        File directorio = new File(rutaTxt);

        File[] files = directorio.listFiles();

        if (files != null) {

            Pattern pattern = Pattern.compile(patron);

            for (File file : files) {

                if (file.isFile()) {

                    Matcher matcher =
                            pattern.matcher(file.getName());

                    if (matcher.matches()) {
                        lista.add(file.getName());
                    }
                }
            }
        }

        return lista;
    }
}
