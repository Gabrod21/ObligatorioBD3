
package grafica.ventanas;

import java.awt.GridLayout;
import javax.swing.*;

import grafica.controladores.Controlador;

public class VentanaContarAlumnos extends JDialog {

    private JTextField txtActividad, txtNota;
    private Controlador controlador;

    public VentanaContarAlumnos(JFrame padre, Controlador controlador) {
        super(padre, "Contar alumnos", true);

        this.controlador = controlador;

        setSize(350, 200);
        setLocationRelativeTo(padre);
        setLayout(new GridLayout(3, 2, 5, 5));

        add(new JLabel("Actividad:"));
        txtActividad = new JTextField();
        add(txtActividad);

        add(new JLabel("Nota:"));
        txtNota = new JTextField();
        add(txtNota);

        JButton btnConsultar = new JButton("Consultar");
        JButton btnCerrar = new JButton("Cerrar");

        add(btnConsultar);
        add(btnCerrar);

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
