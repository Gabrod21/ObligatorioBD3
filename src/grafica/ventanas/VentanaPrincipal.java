package grafica.ventanas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import grafica.controladores.Controlador;

public class VentanaPrincipal extends JFrame {

    private Controlador controlador;

    public VentanaPrincipal(Controlador controlador) {
        this.controlador = controlador;
        setTitle("Obligatorio grupo 1");
        setSize(420, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Ventana principal no redimensionable

        // Panel principal con márgenes y color de fondo
        JPanel panelPrincipal = new JPanel(new GridLayout(9, 1, 0, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 20, 15, 20));
        panelPrincipal.setBackground(new Color(245, 247, 249));
        setContentPane(panelPrincipal);

        // Creación de botones con íconos de emojis y cursor de mano
        JButton btnNuevoAlumno = crearBotonEstilizado("👤  Nuevo alumno");
        JButton btnRegistrarNota = crearBotonEstilizado("📝  Registrar nota");
        JButton btnListarAlumnos = crearBotonEstilizado("👥  Listar alumnos");
        JButton btnObtenerRegistro = crearBotonEstilizado("📥  Obtener registro");
        JButton btnListarRegistros = crearBotonEstilizado("📋  Listar registros");
        JButton btnPromedio = crearBotonEstilizado("📊  Promedio de notas");
        JButton btnMayorPromedio = crearBotonEstilizado("⭐  Alumno con mayor promedio");
        JButton btnContarAlumnos = crearBotonEstilizado("🔢  Contar alumnos");
        JButton btnActividadMasCursada = crearBotonEstilizado("📈  Actividad más cursada");

        panelPrincipal.add(btnNuevoAlumno);
        panelPrincipal.add(btnRegistrarNota);
        panelPrincipal.add(btnListarAlumnos);
        panelPrincipal.add(btnObtenerRegistro);
        panelPrincipal.add(btnListarRegistros);
        panelPrincipal.add(btnPromedio);
        panelPrincipal.add(btnMayorPromedio);
        panelPrincipal.add(btnContarAlumnos);
        panelPrincipal.add(btnActividadMasCursada);

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
            new VentanaAlumnoMayorPromedio(this, controlador)
        );
        btnContarAlumnos.addActionListener(e ->
            new VentanaContarAlumnos(this, controlador).setVisible(true)
        );
        btnActividadMasCursada.addActionListener(e ->
            new VentanaActividadMasCursada(this, controlador).setVisible(true)
        );
    }

    // Método para evitar repetir código de estilos en los botones
    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
    }
}