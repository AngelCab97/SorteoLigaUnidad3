package controlador;

import vista.VistaConsola;

// Punto de entrada de la aplicacion
public class Main {

    public static void main(String[] args) {
        VistaConsola vista = new VistaConsola();
        ControladorTorneo controlador = new ControladorTorneo(vista);
        controlador.iniciar();
    }
}