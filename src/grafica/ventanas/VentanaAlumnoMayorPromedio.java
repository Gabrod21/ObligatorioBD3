package grafica.ventanas;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import grafica.controladores.Controlador;
import logica.valueObjects.VOAlumnoProm;

public class VentanaAlumnoMayorPromedio {

    public VentanaAlumnoMayorPromedio(JFrame padre, Controlador controlador) {

        try {
            VOAlumnoProm alumno = controlador.alumnoMayorPromedio();

            if (alumno == null) {
                JOptionPane.showMessageDialog(
                    padre,
                    "⚠️ No hay alumnos registrados en el sistema.",
                    "Atención",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                padre,
                "🆔 Cédula: " + alumno.getCedula() +
                "\n👤 Nombre: " + alumno.getNombre() +
                "\n👤 Apellido: " + alumno.getApellido() +
                "\n⭐ Promedio: " + alumno.getPromedio(),
                "⭐ Alumno con mayor promedio",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                padre,
                "❌ Error: " + e.getMessage(),
                "Error del sistema",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}