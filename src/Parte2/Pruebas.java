package Parte2;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Pruebas
{
	public static void main (String[] args) throws PersistenciaException
	{
		InputStreamReader is = new InputStreamReader (System.in);
		BufferedReader br = new BufferedReader (is);
		
		try {
			
			IPoolConexiones pcons = new PoolConexiones();
			IConexion conx = pcons.obtenerConexion(true);
			
			Connection con = conx.getConnection();
			
			String createBD = "CREATE DATABASE Escuela";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(createBD);
			stmt.close();
			
			String use = "USE escuela";
			stmt = con.createStatement();
			stmt.executeUpdate(use);
			stmt.close();
			
			String createTableAlumnos = "CREATE TABLE Alumnos ("	+
										"    cedula INT,"			+
										"    nombre VARCHAR(45),"	+
										"    apellido VARCHAR(45),"	+
										"    PRIMARY KEY (cedula)"	+
										");";
			stmt = con.createStatement();
			stmt.executeUpdate(createTableAlumnos);
			stmt.close();
			
			String createTableRegistros = "CREATE TABLE Registros ("								+
										 "    numero INT,"											+
										 "    cedAlu INT,"											+
										 "    actividad VARCHAR(45),"								+
										 "    nota INT,"											+
										 "    PRIMARY KEY (numero, cedAlu),"						+
										 "    FOREIGN KEY (cedAlu) REFERENCES Alumnos(cedula)"		+
										 ");";
			stmt = con.createStatement();
			stmt.executeUpdate(createTableRegistros);
			stmt.close();
			
			String insert1 = "insert into Alumnos values (12345678, 'Matilda', 'Wormwood')";
			String insert2 = "insert into Alumnos values (25554449, 'Charlie', 'Brown')";
			String insert3 = "insert into Alumnos values (39876542, 'Dora', 'Márquez')";
			String insert4 = "insert into Alumnos values (51112223, 'Daniel', 'Mitchell')";
			String insert5 = "insert into Alumnos values (66666666, 'Merlina', 'Addams')";	
			stmt = con.createStatement();
			stmt.executeUpdate(insert1);
			stmt.executeUpdate(insert2);
			stmt.executeUpdate(insert3);
			stmt.executeUpdate(insert4);
			stmt.executeUpdate(insert5);
			stmt.close();
			
			pcons.liberarConexion(conx, true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
