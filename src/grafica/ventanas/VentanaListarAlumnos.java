
package grafica.ventanas;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.Controlador;
import logica.valueObjects.VOAlumno;

public class VentanaListarAlumnos extends JDialog {

    private Controlador controlador;

    public VentanaListarAlumnos(JFrame padre, Controlador controlador) {
        super(padre, "Listado de alumnos", true);

        this.controlador = controlador;

        setSize(500, 350);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());

        String[] columnas = {"Cédula", "Nombre", "Apellido"};

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        add(btnCerrar, BorderLayout.SOUTH);

        btnCerrar.addActionListener(e -> dispose());

        cargarAlumnos(modelo);
    }

    private void cargarAlumnos(DefaultTableModel modelo) {
        try {
            List<VOAlumno> alumnos = controlador.listarAlumnos();

            if (alumnos == null || alumnos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay alumnos registrados");
                return;
            }

            for (VOAlumno alumno : alumnos) {
                modelo.addRow(new Object[] {
                    alumno.getCedula(),
                    alumno.getNombre(),
                    alumno.getApellido()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
