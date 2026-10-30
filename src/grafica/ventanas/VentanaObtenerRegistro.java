
package grafica.ventanas;

import java.awt.GridLayout;
import javax.swing.*;

import grafica.controladores.Controlador;
import logica.valueObjects.VORegistro;

public class VentanaObtenerRegistro extends JDialog {

    private JTextField txtCedula, txtNumero;
    private Controlador controlador;

    public VentanaObtenerRegistro(JFrame padre, Controlador controlador) {
        super(padre, "Obtener registro", true);

        this.controlador = controlador;

        setSize(350, 200);
        setLocationRelativeTo(padre);
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel("Cédula del alumno:"));
        txtCedula = new JTextField();
        add(txtCedula);

        add(new JLabel("Número de registro:"));
        txtNumero = new JTextField();
        add(txtNumero);

        JButton btnBuscar = new JButton("Buscar");
        JButton btnCerrar = new JButton("Cerrar");

        add(btnBuscar);
        add(btnCerrar);

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
