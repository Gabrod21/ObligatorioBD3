package logica.RMI;

import java.io.FileInputStream;
import java.util.Properties;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

import logica.Fachada;
import logica.LogicaException;

public class ServidorRMI {

    private static ServidorRMI instancia = null;

    private Registry registro;
    private Fachada fachada;

    private ServidorRMI(int puerto) throws Exception {

        fachada = new Fachada();

        // Intentar crear el registro RMI
        try {
            registro = LocateRegistry.createRegistry(puerto);

        } catch (ExportException e) {

            // Si el puerto está ocupado, intentar obtener el registro existente
            registro = LocateRegistry.getRegistry(puerto);

            // Verificar que el registro realmente esté funcionando
            registro.list();
        }

        registro.rebind("Fachada", fachada);

        System.out.println("Servidor RMI iniciado en puerto " + puerto);
    }

    public static synchronized ServidorRMI iniciarServidor(int puerto)
            throws Exception {

        // Singleton
        if (instancia == null) {
            instancia = new ServidorRMI(puerto);
        }

        return instancia;
    }

    public static void main(String[] args) {

        Properties propiedades = new Properties();
        int puerto;

        // Cargar configuración
        try (FileInputStream archivo = new FileInputStream("config.properties")) {

            propiedades.load(archivo);

            puerto = Integer.parseInt(
                propiedades.getProperty("puertoRMI")
            );

        } catch (Exception e) {

            LogicaException ex = new LogicaException(
                "Error al cargar el archivo de configuración.", e
            );

            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        }

        // Iniciar servidor RMI
        try {

            ServidorRMI.iniciarServidor(puerto);

        } catch (Exception e) {

            LogicaException ex = new LogicaException(
                "Error al iniciar el servidor RMI.", e
            );

            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}