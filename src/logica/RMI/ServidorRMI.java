package logica.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import logica.Fachada;
import persistencia.conexiones.interfaces.IPoolConexiones;
import persistencia.conexiones.implementacion.PoolConexiones;

public class ServidorRMI {

    public static void main(String[] args) {

        try {
            // Crear el pool de conexiones
            IPoolConexiones pool = new PoolConexiones();

            // Crear la fachada original
            Fachada fachada = new Fachada(pool);

            // Iniciar el registro RMI
            Registry registro = LocateRegistry.createRegistry(816);

            // Publicar la fachada
            registro.rebind("Fachada", fachada);

            System.out.println("Servidor RMI iniciado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}