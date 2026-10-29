package CrearBase;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearBase
{
	public static void main (String[] args)
	{
		InputStreamReader is = new InputStreamReader (System.in);
		BufferedReader br = new BufferedReader (is);
		
		try {
			
			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			String url = "jdbc:mysql://localhost:3306/Prueba";
			Connection con = DriverManager.getConnection(url, "root", "Pass1234");
			
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
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
