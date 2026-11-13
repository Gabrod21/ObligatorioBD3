
package persistencia;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

            // Crear y cargar alumnos
            File alumnos = new File(directorio, "alumnos.txt");

            String contenidoAlumnos =
                    "12345678 | Matilda | Wormwood\n" +
                    "25554449 | Charlie | Brown\n" +
                    "39876542 | Dora | Márquez\n" +
                    "51112223 | Daniel | Mitchell\n" +
                    "66666666 | Merlina | Addams\n";

            escribirSiVacio(alumnos, contenidoAlumnos);

            // Crear archivos de registros
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

                // Matilda tiene los tres registros del ejemplo
                if (cedula == 12345678) {

                    String contenidoRegistros =
                            "1 | Matematica | 12\n" +
                            "2 | Lengua | 9\n" +
                            "3 | Dibujo | 10\n";

                    escribirSiVacio(registros, contenidoRegistros);

                } else {

                    escribirSiVacio(registros, "");
                }
            }

            System.out.println("Base TXT creada correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void escribirSiVacio(
            File archivo, String contenido) throws IOException {

        // Escribir solamente si el archivo no existe o está vacío
        if (!archivo.exists() || archivo.length() == 0) {

            Files.writeString(
                archivo.toPath(),
                contenido,
                StandardCharsets.UTF_8
            );
        }
    }
}
