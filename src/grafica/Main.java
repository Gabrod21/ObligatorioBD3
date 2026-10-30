package grafica;

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
}