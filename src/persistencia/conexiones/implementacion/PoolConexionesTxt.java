package persistencia.conexiones.implementacion;

import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;

public class PoolConexionesTxt implements persistencia.conexiones.interfaces.IPoolConexiones {
	
	Monitor monitor = null;
	
	public PoolConexionesTxt() throws PersistenciaException {

			monitor = Monitor.getInstanceMonitor();
		 
	}
	
	public synchronized IConexion obtenerConexion(boolean modifica) throws PersistenciaException  {
		
		if(modifica) {
			monitor.comienzoEscritura();
		}
		else {
			monitor.comienzoLectura();
		}
		
		return new ConexionTxt(modifica);
	}

	public synchronized void liberarConexion(IConexion icon, boolean ok) throws PersistenciaException {
	
		boolean modifica = ((ConexionTxt) icon).getModifica();

		if(modifica) {
			monitor.terminoEscritura();
		}
		else {
			monitor.terminoLectura();
		}
		
	}

}
	