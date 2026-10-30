
package grafica.ventanas;

import java.awt.BorderLayout;
import javax.swing.*;

import grafica.controladores.Controlador;
import logica.valueObjects.VOAlumnoProm;

public class VentanaAlumnoMayorPromedio extends JDialog {

    private Controlador controlador;

    public VentanaAlumnoMayorPromedio(JFrame padre, Controlador controlador) {
        super(padre, "Alumno con mayor promedio", true);

        this.controlador = controlador;

        setSize(350, 250);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());

        JButton btnConsultar = new JButton("Consultar alumno");
        JButton btnCerrar = new JButton("Cerrar");

        JPanel panel = new JPanel();
        panel.add(btnConsultar);
        panel.add(btnCerrar);

        add(panel, BorderLayout.SOUTH);

        btnConsultar.addActionListener(e -> consultar());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void consultar() {
        try {
            VOAlumnoProm alumno = controlador.alumnoMayorPromedio();

            if (alumno == null) {
                JOptionPane.showMessageDialog(this, "No hay alumnos registrados");
                return;
            }

            JOptionPane.showMessageDialog(
                this,
                "Cédula: " + alumno.getCedula() +
                "\nNombre: " + alumno.getNombre() +
                "\nApellido: " + alumno.getApellido() +
                "\nPromedio: " + alumno.getPromedio(),
                "Alumno con mayor promedio",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
