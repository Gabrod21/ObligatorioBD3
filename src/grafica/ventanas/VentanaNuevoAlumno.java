
package grafica.ventanas;

import java.awt.GridLayout;
import javax.swing.*;

import grafica.controladores.Controlador;

public class VentanaNuevoAlumno extends JDialog {

    private JTextField txtCedula, txtNombre, txtApellido;
    private Controlador controlador;

    public VentanaNuevoAlumno(JFrame padre, Controlador controlador) {
        super(padre, "Nuevo alumno", true);

        this.controlador = controlador;

        setSize(350, 220);
        setLocationRelativeTo(padre);
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        add(txtCedula);

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        add(txtApellido);

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
