package Parte3.persistencia.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Parte2.IConexion;
import Parte2.PersistenciaException;
import Parte3.logica.Registro;
import Parte3.logica.valueObjects.VOActividad;
import Parte3.logica.valueObjects.VORegistroListado;
import Parte3.persistencia.consultas.Consultas;

public class DAORegistros {

    private int cedAlumno;
    private Consultas consultas;

    public DAORegistros(int cedA) {
        this.cedAlumno = cedA;
        this.consultas = new Consultas();
    }

    public int largo(IConexion icon) throws PersistenciaException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = icon.getConnection().prepareStatement(
                consultas.numeroUltimoRegistroDeAlumno()
            );

            ps.setInt(1, cedAlumno);
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

    public void insback(IConexion icon, Registro reg) throws PersistenciaException {
        PreparedStatement ps = null;

        try {
            ps = icon.getConnection().prepareStatement(
                consultas.insertarNota()
            );

            ps.setInt(1, reg.getNumero());
            ps.setInt(2, cedAlumno);
            ps.setString(3, reg.getActividad());
            ps.setInt(4, reg.getNota());

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

    public Registro kesimo(IConexion icon, int numR) throws PersistenciaException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = icon.getConnection().prepareStatement(
                consultas.obtenerRegistro()
            );

            ps.setInt(1, cedAlumno);
            ps.setInt(2, numR);

            rs = ps.executeQuery();

            if (rs.next()) {
                return new Registro(
                    rs.getInt("numero"),
                    rs.getInt("nota"),
                    rs.getString("actividad")
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

    public List<VORegistroListado> listarRegistros(IConexion icon)
            throws PersistenciaException {

        List<VORegistroListado> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = icon.getConnection().prepareStatement(
                consultas.listarRegistros()
            );

            ps.setInt(1, cedAlumno);
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new VORegistroListado(
                    rs.getString("actividad"),
                    rs.getInt("nota"),
                    rs.getInt("numero")
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

    public double promedioNotas(IConexion icon) throws PersistenciaException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = icon.getConnection().prepareStatement(
                consultas.promedioNotas()
            );

            ps.setInt(1, cedAlumno);
            rs = ps.executeQuery();

            rs.next();
            return rs.getDouble(1);

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

    public VOActividad actividadMasCursada(IConexion icon)
            throws PersistenciaException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = icon.getConnection().prepareStatement(
                consultas.actividadMasCursada()
            );

            ps.setInt(1, cedAlumno);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new VOActividad(
                    rs.getString("actividad"),
                    rs.getInt("cantidad")
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
}