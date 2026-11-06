package persistencia;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;

public class CrearBaseTxt {

    public static void main(String[] args) {

        try {
            Properties p = new Properties();

            p.load(new FileInputStream("config.properties"));

            String ruta = p.getProperty("rutaTxt");

            File directorio = new File(ruta);

            // Crear directorio si no existe
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            // Crear archivo de alumnos
            File alumnos = new File(directorio, "alumnos.txt");

            if (!alumnos.exists()) {
                alumnos.createNewFile();
            }

            // Crear archivos de registros para cada alumno
            int[] cedulas = {
                12345678,
                25554449,
                39876542,
                51112223,
                66666666
            };

            for (int cedula : cedulas) {
                File registros = new File(
                    directorio,
                    "registros" + cedula + ".txt"
                );

                if (!registros.exists()) {
                    registros.createNewFile();
                }
            }

            System.out.println("Archivos TXT creados correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}