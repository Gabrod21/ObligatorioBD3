package persistencia.conexiones.implementacion;

import java.sql.Connection;

import persistencia.conexiones.PersistenciaException;

public  class ConexionTxt implements persistencia.conexiones.interfaces.IConexion {

	private boolean modifica = false;
	
	public ConexionTxt(boolean m) throws PersistenciaException {
		modifica = m;
	}
	
	public boolean getModifica() {
		return modifica;
	}

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}
}


