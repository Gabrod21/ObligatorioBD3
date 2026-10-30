
package grafica.ventanas;

import java.awt.GridLayout;
import javax.swing.*;

import grafica.controladores.Controlador;

public class VentanaPromedioNotas extends JDialog {

    private JTextField txtCedula;
    private Controlador controlador;

    public VentanaPromedioNotas(JFrame padre, Controlador controlador) {
        super(padre, "Promedio de notas", true);

        this.controlador = controlador;

        setSize(350, 170);
        setLocationRelativeTo(padre);
        setLayout(new GridLayout(3, 2, 5, 5));

        add(new JLabel("Cédula del alumno:"));
        txtCedula = new JTextField();
        add(txtCedula);

        JButton btnConsultar = new JButton("Consultar");
        JButton btnCerrar = new JButton("Cerrar");

        add(btnConsultar);
        add(btnCerrar);

        btnConsultar.addActionListener(e -> consultar());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void consultar() {
        try {
            int cedula = Integer.parseInt(txtCedula.getText());

            double promedio = controlador.promedioNotas(cedula);

            JOptionPane.showMessageDialog(
                this,
                "Promedio del alumno: " + promedio,
                "Resultado",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula válida");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
