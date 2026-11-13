package persistencia.conexiones.implementacion;

import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;
import persistencia.conexiones.interfaces.IPoolConexiones;

public class PoolConexionesTxt implements IPoolConexiones {
	
	private Monitor monitor;

	
	public PoolConexionesTxt() throws PersistenciaException {

			monitor = Monitor.getInstanceMonitor();
		 
	}
	
	public IConexion obtenerConexion(boolean modifica) throws PersistenciaException  {
		
		if(modifica) {
			monitor.comienzoEscritura();
		}
		else {
			monitor.comienzoLectura();
		}
		
		return new ConexionTxt(modifica);
	}

	public void liberarConexion(IConexion icon, boolean ok) throws PersistenciaException {
	
		boolean modifica = ((ConexionTxt) icon).getModifica();

		if(modifica) {
			monitor.terminoEscritura();
		}
		else {
			monitor.terminoLectura();
		}
		
	}

}
	