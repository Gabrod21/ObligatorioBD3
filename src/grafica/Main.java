
package grafica;

import grafica.controladores.Controlador;
import grafica.ventanas.VentanaPrincipal;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        try {
            // Se aplica el estilo Nimbus
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            Controlador controlador = new Controlador();

            VentanaPrincipal ventana = new VentanaPrincipal(controlador);
            ventana.setVisible(true);

        } catch (RemoteException e) {

            JOptionPane.showMessageDialog(
                null,
                "No se pudo establecer la comunicación con el servidor RMI.\n"
                + "Verifique que el servidor RMI esté iniciado.",
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                null,
                "Ocurrió un error inesperado al iniciar la aplicación:\n"
                + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}
