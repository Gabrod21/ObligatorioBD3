package persistencia.conexiones.implementacion;

import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;

public  class ConexionTxt implements IConexion {

	private boolean modifica = false;
	
	public ConexionTxt(boolean m) throws PersistenciaException {
		modifica = m;
	}
	
	public boolean getModifica() {
		return modifica;
	}
}


