
package grafica.ventanas;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import grafica.controladores.Controlador;

public class VentanaPrincipal extends JFrame {

    private Controlador controlador;

    public VentanaPrincipal(Controlador controlador) {

        this.controlador = controlador;

        setTitle("Sistema de gestión de alumnos");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1, 10, 10));

        JButton btnNuevoAlumno = new JButton("Nuevo alumno");
        JButton btnRegistrarNota = new JButton("Registrar nota");
        JButton btnListarAlumnos = new JButton("Listar alumnos");
        JButton btnObtenerRegistro = new JButton("Obtener registro");
        JButton btnListarRegistros = new JButton("Listar registros");
        JButton btnPromedio = new JButton("Promedio de notas");
        JButton btnMayorPromedio = new JButton("Alumno con mayor promedio");
        JButton btnContarAlumnos = new JButton("Contar alumnos");
        JButton btnActividadMasCursada = new JButton("Actividad más cursada");

        panel.add(btnNuevoAlumno);
        panel.add(btnRegistrarNota);
        panel.add(btnListarAlumnos);
        panel.add(btnObtenerRegistro);
        panel.add(btnListarRegistros);
        panel.add(btnPromedio);
        panel.add(btnMayorPromedio);
        panel.add(btnContarAlumnos);
        panel.add(btnActividadMasCursada);

        add(panel);

        // Acciones de los botones

        btnNuevoAlumno.addActionListener(e ->
            new VentanaNuevoAlumno(this, controlador).setVisible(true)
        );

        btnRegistrarNota.addActionListener(e ->
            new VentanaRegistrarNota(this, controlador).setVisible(true)
        );

        btnListarAlumnos.addActionListener(e ->
            new VentanaListarAlumnos(this, controlador).setVisible(true)
        );

        btnObtenerRegistro.addActionListener(e ->
            new VentanaObtenerRegistro(this, controlador).setVisible(true)
        );

        btnListarRegistros.addActionListener(e ->
            new VentanaListarRegistros(this, controlador).setVisible(true)
        );

        btnPromedio.addActionListener(e ->
            new VentanaPromedioNotas(this, controlador).setVisible(true)
        );

        btnMayorPromedio.addActionListener(e ->
            new VentanaAlumnoMayorPromedio(this, controlador).setVisible(true)
        );

        btnContarAlumnos.addActionListener(e ->
            new VentanaContarAlumnos(this, controlador).setVisible(true)
        );

        btnActividadMasCursada.addActionListener(e ->
            new VentanaActividadMasCursada(this, controlador).setVisible(true)
        );
    }
}
