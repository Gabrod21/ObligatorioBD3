package Parte3.logica.valueObjects;

public class VORegistroListado extends VORegistro {
    private int numero;

    public VORegistroListado(String actividad, int nota, int numero) {
        super(actividad, nota);
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }
}