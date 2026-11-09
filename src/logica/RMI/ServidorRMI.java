package logica.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import logica.Fachada;
import persistencia.conexiones.implementacion.PoolConexiones;
import persistencia.conexiones.interfaces.IPoolConexiones;

public class ServidorRMI {

    private static boolean iniciado = false;

    public static synchronized void iniciarServidor(int puerto)
            throws Exception {

        if (iniciado) {
            return;
        }

        IPoolConexiones pool = new PoolConexiones();

        Fachada fachada = new Fachada(pool);

        Registry registro = LocateRegistry.createRegistry(puerto);

        registro.rebind("Fachada", fachada);

        iniciado = true;

        System.out.println("Servidor RMI iniciado en puerto " + puerto);
    }
}