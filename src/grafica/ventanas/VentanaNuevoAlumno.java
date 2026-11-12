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

public class VentanaNuevoAlumno extends JDialog {

    private JTextField txtCedula, txtNombre, txtApellido;
    private Controlador controlador;

    public VentanaNuevoAlumno(JFrame padre, Controlador controlador) {
        super(padre, "Nuevo alumno", true);

        this.controlador = controlador;

        setSize(400, 240);
        setLocationRelativeTo(padre);
        setResizable(false); // Ventana no redimensionable
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 247, 249));
        setContentPane(panelPrincipal);

        // Panel central para las etiquetas y campos de texto
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 12));
        panelFormulario.setOpaque(false);

        panelFormulario.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        txtCedula.setPreferredSize(new Dimension(150, 30)); // Altura uniforme
        panelFormulario.add(txtCedula);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(150, 30)); // Altura uniforme
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        txtApellido.setPreferredSize(new Dimension(150, 30)); // Altura uniforme
        panelFormulario.add(txtApellido);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Panel inferior para los botones alineados
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);

        JButton btnCancelar = new JButton("Cancelar");
        JButton btnRegistrar = new JButton("Registrar");

        // Estilos del botón principal
        btnRegistrar.setBackground(new Color(33, 150, 243));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);

        // Cambiar el cursor a pointer (mano)
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

            if (txtNombre.getText().trim().isEmpty() ||
                txtApellido.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }

            controlador.nuevoAlumno(
                cedula,
                txtNombre.getText(),
                txtApellido.getText()
            );

            JOptionPane.showMessageDialog(this, "Alumno registrado correctamente");
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cédula debe ser numérica");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}