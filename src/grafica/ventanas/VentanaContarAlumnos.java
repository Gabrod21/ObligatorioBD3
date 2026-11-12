package grafica.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import grafica.controladores.Controlador;

public class VentanaContarAlumnos extends JDialog {

    private JTextField txtActividad, txtNota;
    private Controlador controlador;

    public VentanaContarAlumnos(JFrame padre, Controlador controlador) {
        super(padre, "Contar alumnos", true);

        this.controlador = controlador;

        setSize(400, 200);
        setLocationRelativeTo(padre);
        setResizable(false); // Ventana no redimensionable

        // Panel principal con márgenes y color de fondo moderno
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 247, 249));
        setContentPane(panelPrincipal);

        // Panel central para las etiquetas y campos de texto
        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 12));
        panelFormulario.setOpaque(false);

        panelFormulario.add(new JLabel("Actividad:"));
        txtActividad = new JTextField();
        txtActividad.setPreferredSize(new Dimension(150, 30)); // Altura uniforme
        panelFormulario.add(txtActividad);

        panelFormulario.add(new JLabel("Nota:"));
        txtNota = new JTextField();
        txtNota.setPreferredSize(new Dimension(150, 30)); // Altura uniforme
        panelFormulario.add(txtNota);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Panel inferior para los botones alineados
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);

        JButton btnCerrar = new JButton("Cerrar");
        JButton btnConsultar = new JButton("🔍 Consultar");

        // Estilos del botón principal
        btnConsultar.setBackground(new Color(33, 150, 243));
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.setFocusPainted(false);

        // Cursores en forma de mano
        btnConsultar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnCerrar);
        panelBotones.add(btnConsultar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Listeners
        btnConsultar.addActionListener(e -> consultar());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void consultar() {
        try {
            String actividad = txtActividad.getText().trim();
            int nota = Integer.parseInt(txtNota.getText());

            if (actividad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una actividad");
                return;
            }

            int cantidad = controlador.contarAlumnos(actividad, nota);

            JOptionPane.showMessageDialog(
                this,
                "Cantidad de alumnos: " + cantidad,
                "Resultado",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La nota debe ser numérica");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}