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
import logica.valueObjects.VORegistro;

public class VentanaObtenerRegistro extends JDialog {

    private JTextField txtCedula, txtNumero;
    private Controlador controlador;

    public VentanaObtenerRegistro(JFrame padre, Controlador controlador) {
        super(padre, "Obtener registro", true);

        this.controlador = controlador;

        setSize(400, 220);
        setLocationRelativeTo(padre);
        setResizable(false); // Ventana no redimensionable

        // Panel principal con márgenes y color de fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 247, 249));
        setContentPane(panelPrincipal);

        // Panel central para las etiquetas y campos de texto
        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 12));
        panelFormulario.setOpaque(false);

        panelFormulario.add(new JLabel("Cédula del alumno:"));
        txtCedula = new JTextField();
        txtCedula.setPreferredSize(new Dimension(150, 30)); // Altura uniforme
        panelFormulario.add(txtCedula);

        panelFormulario.add(new JLabel("Número de registro:"));
        txtNumero = new JTextField();
        txtNumero.setPreferredSize(new Dimension(150, 30)); // Altura uniforme
        panelFormulario.add(txtNumero);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Panel inferior para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);

        JButton btnCerrar = new JButton("Cerrar");
        JButton btnBuscar = new JButton("🔍 Buscar");

        // Estilos del botón principal
        btnBuscar.setBackground(new Color(33, 150, 243));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);

        // Cursores en forma de mano
        btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnCerrar);
        panelBotones.add(btnBuscar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Listeners
        btnBuscar.addActionListener(e -> buscar());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void buscar() {
        try {
            int cedula = Integer.parseInt(txtCedula.getText());
            int numero = Integer.parseInt(txtNumero.getText());

            VORegistro registro = controlador.obtenerRegistro(cedula, numero);

            if (registro == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el registro");
                return;
            }

            JOptionPane.showMessageDialog(
                this,
                "Actividad: " + registro.getActividad() +
                "\nNota: " + registro.getNota(),
                "Resultado",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}