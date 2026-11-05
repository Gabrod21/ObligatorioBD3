package persistencia.consultas;

public class Consultas {

    public String existeAlumno() {
        String query = "select * from Alumnos where cedula = ?";
        return query;
    }

    public String insertarAlumno() {
        String insert = "insert into Alumnos (cedula, nombre, apellido) values (?, ?, ?)";
        return insert;
    }

    public String numeroUltimoRegistroDeAlumno() {
        String query = "select coalesce(max(numero), 0) from Registros where cedAlu = ?";
        return query;
    }

    public String insertarNota() {
        String insert = "insert into Registros (numero, cedAlu, actividad, nota) values (?, ?, ?, ?)";
        return insert;
    }

    public String listarAlumnos() {
        String query = "select * from Alumnos order by cedula";
        return query;
    }

    public String obtenerRegistro() {
        String query = "select * from Registros where cedAlu = ? and numero = ?";
        return query;
    }

    public String listarRegistros() {
        String query = "select * from Registros where cedAlu = ? order by numero";
        return query;
    }

    public String promedioNotas() {
        String query = "select avg(nota) from Registros where cedAlu = ?";
        return query;
    }

    public String alumnoMayorPromedio() {
        String query = "select *, (select avg(nota) from Registros where cedAlu = Alumnos.cedula) as promedio from Alumnos where cedula in (select cedAlu from Registros group by cedAlu having avg(nota) = (select max(promedio) from (select avg(nota) as promedio from Registros group by cedAlu) as promedios)) limit 1;";
        return query;
    }

    public String contarAlumnos() {
        String query = "select count(distinct cedAlu) from Registros where actividad = ? and nota = ?";
        return query;
    }

    public String actividadMasCursada() {
        String query = "select actividad, count(*) as cantidad from Registros where cedAlu = ? group by actividad order by count(*) desc limit 1";
        return query;
    }
    public String esVacio() {
    	String query = "select * from Alumnos";
    	return query;
    }
}