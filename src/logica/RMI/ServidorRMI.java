package logica.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import logica.Fachada;

public class ServidorRMI {

    private static ServidorRMI instancia = null;

    private Registry registro;
    private Fachada fachada;

    private ServidorRMI(int puerto) throws Exception {

        fachada = new Fachada();

        registro = LocateRegistry.createRegistry(puerto);

        registro.rebind("Fachada", fachada);

        System.out.println("Servidor RMI iniciado en puerto " + puerto);
    }

    public static synchronized ServidorRMI iniciarServidor(int puerto)
            throws Exception {

    	
    	//singleton
        if (instancia == null) {
            instancia = new ServidorRMI(puerto);
        }

        return instancia;
    }
}