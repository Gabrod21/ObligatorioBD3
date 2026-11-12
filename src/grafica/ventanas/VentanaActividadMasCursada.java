package grafica.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import grafica.controladores.Controlador;
import logica.valueObjects.VOActividad;

public class VentanaActividadMasCursada extends JDialog {

    private JTextField txtCedula;
    private Controlador controlador;

    public VentanaActividadMasCursada(JFrame padre, Controlador controlador) {
        super(padre, "Actividad más cursada", true);

        this.controlador = controlador;

        setSize(380, 200);
        setLocationRelativeTo(padre);

        // Panel principal con márgenes y color de fondo moderno
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 247, 249));
        setContentPane(panelPrincipal);

        // Panel central para el formulario de entrada
        JPanel panelFormulario = new JPanel(new GridLayout(1, 2, 10, 0));
        panelFormulario.setOpaque(false);

        panelFormulario.add(new JLabel("Cédula del alumno:"));
        txtCedula = new JTextField();
        panelFormulario.add(txtCedula);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Panel inferior para los botones alineados
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);

        JButton btnCerrar = new JButton("Cerrar");
        JButton btnConsultar = new JButton("🔍 Consultar");

        // Destacar el botón principal con el color de acento azul y texto blanco
        btnConsultar.setBackground(new Color(33, 150, 243));
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.setFocusPainted(false);

        // Cursores en forma de mano
        btnConsultar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnCerrar);
        panelBotones.add(btnConsultar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Listeners intactos
        btnConsultar.addActionListener(e -> consultar());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void consultar() {
        try {
            int cedula = Integer.parseInt(txtCedula.getText());

            VOActividad actividad = controlador.actividadmasCursada(cedula);

            if (actividad == null) {
                JOptionPane.showMessageDialog(this, "No se encontraron actividades");
                return;
            }

            JOptionPane.showMessageDialog(
                this,
                "Actividad: " + actividad.getActividad() +
                "\nCantidad de veces: " + actividad.getVeces(),
                "Actividad más cursada",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula válida");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}