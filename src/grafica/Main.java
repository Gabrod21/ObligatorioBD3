/*package grafica;

import grafica.controladores.Controlador;
import grafica.ventanas.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        try {
            Controlador controlador = new Controlador();

            VentanaPrincipal ventana = new VentanaPrincipal(controlador);
            ventana.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/

package grafica;

import grafica.controladores.Controlador;
import grafica.ventanas.VentanaPrincipal;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            // Se aplica el Estilo Nimbus para mejorar la UI
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            Controlador controlador = new Controlador();

            VentanaPrincipal ventana = new VentanaPrincipal(controlador);
            ventana.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}