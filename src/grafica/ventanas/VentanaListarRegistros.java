package grafica.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.Controlador;
import logica.valueObjects.VORegistroListado;

public class VentanaListarRegistros extends JDialog {

    private JTextField txtCedula;
    private Controlador controlador;
    private DefaultTableModel modelo;

    public VentanaListarRegistros(JFrame padre, Controlador controlador) {
        super(padre, "Listar registros", true);

        this.controlador = controlador;

        setSize(540, 400);
        setLocationRelativeTo(padre);

        // Panel principal con márgenes y color de fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 247, 249));
        setContentPane(panelPrincipal);

        // Panel superior para la búsqueda
        JPanel panelSuperior = new JPanel(new GridLayout(1, 3, 10, 0));
        panelSuperior.setOpaque(false);

        panelSuperior.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        txtCedula.setPreferredSize(new Dimension(120, 30)); // Altura uniforme para el textbox
        panelSuperior.add(txtCedula);

        JButton btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setBackground(new Color(33, 150, 243));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelSuperior.add(btnBuscar);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
            new String[]{"Número", "Actividad", "Nota"}, 0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para el botón de cerrar
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelBotones.setOpaque(false);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelBotones.add(btnCerrar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Listeners 
        btnBuscar.addActionListener(e -> buscar());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void buscar() {
        try {
            int cedula = Integer.parseInt(txtCedula.getText());

            modelo.setRowCount(0);

            List<VORegistroListado> registros =
                controlador.listarRegistros(cedula);

            if (registros == null || registros.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay registros para este alumno");
                return;
            }

            for (VORegistroListado registro : registros) {
                modelo.addRow(new Object[] {
                    registro.getNumero(),
                    registro.getActividad(),
                    registro.getNota()
                });
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula válida");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}