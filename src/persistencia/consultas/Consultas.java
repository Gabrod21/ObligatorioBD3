package persistencia.consultas;

public class Consultas {

    public String existeAlumno() {
        String query = "select * from ObligatorioBD3.Alumnos where cedula = ?";
        return query;
    }

    public String insertarAlumno() {
        String insert = "insert into ObligatorioBD3.Alumnos (cedula, nombre, apellido) values (?, ?, ?)";
        return insert;
    }

    public String numeroUltimoRegistroDeAlumno() {
        String query = "select coalesce(max(numero), 0) from ObligatorioBD3.Registros where cedAlu = ?";
        return query;
    }

    public String insertarNota() {
        String insert = "insert into ObligatorioBD3.Registros (numero, cedAlu, actividad, nota) values (?, ?, ?, ?)";
        return insert;
    }

    public String listarAlumnos() {
        String query = "select * from ObligatorioBD3.Alumnos order by cedula";
        return query;
    }

    public String obtenerRegistro() {
        String query = "select * from ObligatorioBD3.Registros where cedAlu = ? and numero = ?";
        return query;
    }

    public String listarRegistros() {
        String query = "select * from ObligatorioBD3.Registros where cedAlu = ? order by numero";
        return query;
    }

    public String promedioNotas() {
        String query = "select avg(nota) from ObligatorioBD3.Registros where cedAlu = ?";
        return query;
    }

    public String alumnoMayorPromedio() {
        String query = "select *, (select avg(nota) from ObligatorioBD3.Registros where cedAlu = Alumnos.cedula) as promedio from ObligatorioBD3.Alumnos where cedula in (select cedAlu from ObligatorioBD3.Registros group by cedAlu having avg(nota) = (select max(promedio) from (select avg(nota) as promedio from ObligatorioBD3.Registros group by cedAlu) as promedios)) limit 1;";
        return query;
    }

    public String contarAlumnos() {
        String query = "select count(distinct cedAlu) from ObligatorioBD3.Registros where actividad = ? and nota = ?";
        return query;
    }

    public String actividadMasCursada() {
        String query = "select actividad, count(*) as cantidad from ObligatorioBD3.Registros where cedAlu = ? group by actividad order by count(*) desc limit 1";
        return query;
    }
    public String esVacio() {
    	String query = "select * from Alumnos";
    	return query;
    }
}