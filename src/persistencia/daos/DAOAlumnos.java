package persistencia.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logica.Alumno;
import logica.valueObjects.VOAlumno;
import logica.valueObjects.VOAlumnoProm;
import persistencia.conexiones.PersistenciaException;
import persistencia.conexiones.interfaces.IConexion;
import persistencia.consultas.Consultas;

public class DAOAlumnos {

    private Consultas consultas;

    public DAOAlumnos() {
        this.consultas = new Consultas();
    }

    public boolean member(IConexion icon, int cedA) throws PersistenciaException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = icon.getConnection().prepareStatement(consultas.existeAlumno());
            ps.setInt(1, cedA);

            rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new PersistenciaException(e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el ResultSet
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el PreparedStatement
            }
        }
    }

    public void insert(IConexion icon, Alumno alu) throws PersistenciaException {
        PreparedStatement ps = null;

        try {
            ps = icon.getConnection().prepareStatement(consultas.insertarAlumno());

            ps.setInt(1, alu.getCedula());
            ps.setString(2, alu.getNombre());
            ps.setString(3, alu.getApellido());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException(e);

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el PreparedStatement
            }
        }
    }

    public Alumno find(IConexion icon, int cedA) throws PersistenciaException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = icon.getConnection().prepareStatement(consultas.existeAlumno());
            ps.setInt(1, cedA);

            rs = ps.executeQuery();

            if (rs.next()) {
                return new Alumno(
                    rs.getInt("cedula"),
                    rs.getString("nombre"),
                    rs.getString("apellido")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new PersistenciaException(e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el ResultSet
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el PreparedStatement
            }
        }
    }

    public Boolean esVacio(IConexion icon) throws PersistenciaException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = icon.getConnection().prepareStatement(consultas.esVacio());
            rs = ps.executeQuery();

            rs.next();
            return rs.getInt(1) == 0;

        } catch (SQLException e) {
            throw new PersistenciaException(e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el ResultSet
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el PreparedStatement
            }
        }
    }

    public List<VOAlumno> listarAlumnos(IConexion icon) throws PersistenciaException {
        List<VOAlumno> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = icon.getConnection().prepareStatement(consultas.listarAlumnos());
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new VOAlumno(
                    rs.getInt("cedula"),
                    rs.getString("nombre"),
                    rs.getString("apellido")
                ));
            }

            return lista;

        } catch (SQLException e) {
            throw new PersistenciaException(e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el ResultSet
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el PreparedStatement
            }
        }
    }

    public VOAlumnoProm alumnoMayorPromedio(IConexion icon) throws PersistenciaException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = icon.getConnection().prepareStatement(consultas.alumnoMayorPromedio());
            rs = ps.executeQuery();

            if (rs.next()) {
                return new VOAlumnoProm(
                    rs.getInt("cedula"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDouble("promedio")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new PersistenciaException(e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el ResultSet
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el PreparedStatement
            }
        }
    }

    public int contarAlumnos(IConexion icon, String act, int nota) throws PersistenciaException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = icon.getConnection().prepareStatement(consultas.contarAlumnos());

            ps.setString(1, act);
            ps.setInt(2, nota);

            rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1);

        } catch (SQLException e) {
            throw new PersistenciaException(e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el ResultSet
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                // Se ignora el error al cerrar el PreparedStatement
            }
        }
    }
}
