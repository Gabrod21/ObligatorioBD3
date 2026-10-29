package persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CrearBase
{
	public static int ejecutaStatement(Connection con, String sql) throws SQLException 
	{
		Statement stmt = (Statement) con.createStatement();
		int r = ((Statement) stmt).executeUpdate(sql);
		stmt.close();
		return r;
	}
	
	public static void main (String[] args) throws FileNotFoundException, IOException
	{
		try {
			
			Properties p = new Properties();
			String nomArch = "config.properties";
			p.load (new FileInputStream (nomArch));
			
			String driver = p.getProperty("driver");
			String url  = p.getProperty("url");
			String user = p.getProperty("user");
			String password = p.getProperty("password");
			
			Class.forName(driver);
			Connection con = (Connection) DriverManager.getConnection(url, user, password);
			
			String sql;
						
			sql = "DROP DATABASE ObligatorioBD3";
			ejecutaStatement(con, sql);
						
			sql = "CREATE DATABASE ObligatorioBD3";
			ejecutaStatement(con, sql);
			
			sql = "USE ObligatorioBD3";
			ejecutaStatement(con, sql);
			
			sql = "CREATE TABLE Alumnos (cedula INT,nombre VARCHAR(45),apellido VARCHAR(45),PRIMARY KEY (cedula))";
			ejecutaStatement(con, sql);
			
			sql = "CREATE TABLE Registros (numero INT,cedAlu INT,actividad VARCHAR(45),nota INT,PRIMARY KEY (numero, cedAlu),FOREIGN KEY (cedAlu) REFERENCES Alumnos(cedula))";
			ejecutaStatement(con, sql);
			
			sql = "insert into Alumnos values (12345678, 'Matilda', 'Wormwood')";
			ejecutaStatement(con, sql);
			sql = "insert into Alumnos values (25554449, 'Charlie', 'Brown')";
			ejecutaStatement(con, sql);
			sql = "insert into Alumnos values (39876542, 'Dora', 'Márquez')";
			ejecutaStatement(con, sql);
			sql = "insert into Alumnos values (51112223, 'Daniel', 'Mitchell')";
			ejecutaStatement(con, sql);
			sql = "insert into Alumnos values (66666666, 'Merlina', 'Addams')";
			ejecutaStatement(con, sql);
						
			con.close();
			
		} 
		catch (FileNotFoundException e) {				// si no encuentra el archivo de configuracion
			e.printStackTrace();
		}
		catch (IOException e){ 							// si ocurre cualquier otro error de E/S 
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {			    // al cargar el driver puede no existir la clase
			e.printStackTrace();
		} 
		catch (SQLException e) {						// error en la conexión
			e.printStackTrace();
		}
	}
}
