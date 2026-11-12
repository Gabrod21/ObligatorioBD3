package grafica.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import grafica.controladores.Controlador;

public class VentanaRegistrarNota extends JDialog {

    private JTextField txtCedula, txtActividad, txtNota;
    private Controlador controlador;

    public VentanaRegistrarNota(JFrame padre, Controlador controlador) {
        super(padre, "Registrar nota", true);

        this.controlador = controlador;

        setSize(400, 240);
        setLocationRelativeTo(padre);

        // Panel principal con márgenes y color de fondo moderno
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 247, 249));
        setContentPane(panelPrincipal);

        // Panel central para las etiquetas y campos de texto
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 12));
        panelFormulario.setOpaque(false);

        panelFormulario.add(new JLabel("Cédula del alumno:"));
        txtCedula = new JTextField();
        panelFormulario.add(txtCedula);

        panelFormulario.add(new JLabel("Actividad:"));
        txtActividad = new JTextField();
        panelFormulario.add(txtActividad);

        panelFormulario.add(new JLabel("Nota:"));
        txtNota = new JTextField();
        panelFormulario.add(txtNota);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Panel inferior para los botones alineados
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);

        JButton btnCancelar = new JButton("Cancelar");
        JButton btnRegistrar = new JButton("💾 Registrar");

        // Destacar el botón principal con el color de acento azul y texto blanco
        btnRegistrar.setBackground(new Color(33, 150, 243));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);

        // Cursores en forma de mano
        btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnCancelar);
        panelBotones.add(btnRegistrar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Listeners
        btnRegistrar.addActionListener(e -> registrar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void registrar() {
        try {
            int cedula = Integer.parseInt(txtCedula.getText());
            int nota = Integer.parseInt(txtNota.getText());

            if (txtActividad.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una actividad");
                return;
            }

            controlador.registrarNota(
                cedula,
                txtActividad.getText(),
                nota
            );

            JOptionPane.showMessageDialog(this, "Nota registrada correctamente");
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cédula y nota deben ser numéricas");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}