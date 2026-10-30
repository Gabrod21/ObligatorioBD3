
package grafica.ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.Controlador;
import logica.valueObjects.VORegistroListado;

public class VentanaListarRegistros extends JDialog {

    private JTextField txtCedula;
    private Controlador controlador;
    private DefaultTableModel modelo;

    public VentanaListarRegistros(JFrame padre, Controlador controlador) {
        super(padre, "Listar registros", true);

        this.controlador = controlador;

        setSize(500, 350);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridLayout(1, 3, 5, 5));

        panelSuperior.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panelSuperior.add(txtCedula);

        JButton btnBuscar = new JButton("Buscar");
        panelSuperior.add(btnBuscar);

        add(panelSuperior, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
            new String[]{"Número", "Actividad", "Nota"}, 0
        ) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        add(btnCerrar, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscar());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void buscar() {
        try {
            int cedula = Integer.parseInt(txtCedula.getText());

            modelo.setRowCount(0);

            List<VORegistroListado> registros =
                controlador.listarRegistros(cedula);

            if (registros == null || registros.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay registros para este alumno");
                return;
            }

            for (VORegistroListado registro : registros) {
                modelo.addRow(new Object[] {
                    registro.getNumero(),
                    registro.getActividad(),
                    registro.getNota()
                });
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula válida");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
