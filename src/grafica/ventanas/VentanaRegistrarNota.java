
package grafica.ventanas;

import java.awt.GridLayout;
import javax.swing.*;

import grafica.controladores.Controlador;

public class VentanaRegistrarNota extends JDialog {

    private JTextField txtCedula, txtActividad, txtNota;
    private Controlador controlador;

    public VentanaRegistrarNota(JFrame padre, Controlador controlador) {
        super(padre, "Registrar nota", true);

        this.controlador = controlador;

        setSize(350, 220);
        setLocationRelativeTo(padre);
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel("Cédula del alumno:"));
        txtCedula = new JTextField();
        add(txtCedula);

        add(new JLabel("Actividad:"));
        txtActividad = new JTextField();
        add(txtActividad);

        add(new JLabel("Nota:"));
        txtNota = new JTextField();
        add(txtNota);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnRegistrar);
        add(btnCancelar);

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
