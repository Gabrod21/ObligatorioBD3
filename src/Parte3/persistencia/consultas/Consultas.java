package Parte3.persistencia.consultas;

public class Consultas {

    public String existeAlumno() {
        String query = "select * from escuela.Alumnos where cedula = ?";
        return query;
    }

    public String insertarAlumno() {
        String insert = "insert into escuela.Alumnos (cedula, nombre, apellido) values (?, ?, ?)";
        return insert;
    }

    public String numeroUltimoRegistroDeAlumno() {
        String query = "select coalesce(max(numero), 0) from escuela.Registros where cedAlu = ?";
        return query;
    }

    public String insertarNota() {
        String insert = "insert into escuela.Registros (numero, cedAlu, actividad, nota) values (?, ?, ?, ?)";
        return insert;
    }

    public String listarAlumnos() {
        String query = "select * from escuela.Alumnos order by cedula";
        return query;
    }

    public String obtenerRegistro() {
        String query = "select * from escuela.Registros where cedAlu = ? and numero = ?";
        return query;
    }

    public String listarRegistros() {
        String query = "select * from escuela.Registros where cedAlu = ? order by numero";
        return query;
    }

    public String promedioNotas() {
        String query = "select avg(nota) from escuela.Registros where cedAlu = ?";
        return query;
    }

    public String alumnoMayorPromedio() {
        String query = "select * from escuela.Alumnos where cedula in (select cedAlu from escuela.Registros group by cedAlu order by avg(nota) desc limit 1)";
        return query;
    }

    public String contarAlumnos() {
        String query = "select count(distinct cedAlu) from escuela.Registros where actividad = ? and nota = ?";
        return query;
    }

    public String actividadMasCursada() {
        String query = "select actividad, count(*) as cantidad from escuela.Registros where cedAlu = ? group by actividad order by count(*) desc limit 1";
        return query;
    }
    public String esVacio() {
    	String query = "select * from Alumnos";
    	return query;
    }
}