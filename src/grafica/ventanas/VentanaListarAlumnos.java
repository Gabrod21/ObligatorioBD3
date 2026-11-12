package grafica.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.Controlador;
import logica.valueObjects.VOAlumno;

public class VentanaListarAlumnos extends JDialog {

    private Controlador controlador;

    public VentanaListarAlumnos(JFrame padre, Controlador controlador) {
        super(padre, "Listado de alumnos", true);

        this.controlador = controlador;

        setSize(520, 380);
        setLocationRelativeTo(padre);

        // Panel principal con márgenes y color de fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 247, 249));
        setContentPane(panelPrincipal);

        String[] columnas = {"Cédula", "Nombre", "Apellido"};

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para el botón alineado limpiamente
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelBotones.setOpaque(false);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnCerrar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

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