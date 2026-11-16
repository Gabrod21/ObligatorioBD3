package persistencia.conexiones.implementacion;

import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;
import persistencia.daos.implementacion.ManejoTxt;

public  class ConexionTxt implements IConexion {

	private boolean modifica = false;
	private ManejoTxt manejo = null;
	
	public ConexionTxt(boolean m) throws PersistenciaException {
		modifica = m;
		manejo = new ManejoTxt();
	}
	
	public boolean getModifica() {
		return modifica;
	}
	
	public ManejoTxt getManejoTxt() {
		return this.manejo;
	}
}


