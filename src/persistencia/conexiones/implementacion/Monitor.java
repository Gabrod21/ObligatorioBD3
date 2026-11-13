package persistencia.conexiones.implementacion;

import persistencia.conexiones.PersistenciaException;

public class Monitor {
	
	private Integer cantLectores;
	private Boolean escribiendo;
	
	private static Monitor instance;
	
	public Monitor() {
		this.cantLectores = 0;
		this.escribiendo = false;
	}
	
    public static synchronized Monitor getInstanceMonitor(){
        if(instance == null)
            instance = new Monitor();
        return instance;
    }
    
    
    // Permite que un lector acceda al recurso, siempre que no haya un escritor activo. Si hay un escritor activo, el lector debe esperar.
    public synchronized void comienzoLectura() throws PersistenciaException {
        while (escribiendo) {
            try {
            	// Esperamos hasta que no haya un escritor activo
                wait(); 
            } catch (InterruptedException e) {
            	String msg = "Error en el acceso al medio de persistencia";
                throw new PersistenciaException(msg);
            }
        }
        // Incrementamos la cantidad de lectores
        cantLectores++;
    }
    
    
	// Indica que un lector ha terminado de usar el recurso. Si no hay más lectores, notifica a los escritores en espera.
    public synchronized void terminoLectura() {
    	// Decrementa el contador de lectores
    	cantLectores--; 
        if (cantLectores == 0) {
        	// Notifica a los escritores en espera
        	notifyAll();
        }
    }
	
    public synchronized void comienzoEscritura() throws PersistenciaException {
        while (escribiendo || cantLectores > 0) {
            try {
            	// Espera hasta que no haya lectores ni escritores activos
                wait();
            } catch (InterruptedException e) {
            	String msg = "Error en el acceso al medio de persistencia";
                throw new PersistenciaException(msg);
            }
        }
     // Marca que hay un escritor activo
        escribiendo = true; 
    }
	
	public synchronized void terminoEscritura () {
		// Marca que no hay un escritor activo
		escribiendo = false;
		
		// Notifica a los lectores y escritores en espera
		notifyAll();
	}
	
}
