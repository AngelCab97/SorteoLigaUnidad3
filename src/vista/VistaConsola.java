package vista;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import modelo.Partido;

// Vista por consola: solo maneja entrada y salida, sin logica de negocio.
public class VistaConsola {

    private Scanner scanner;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    // Muestra el menu principal y devuelve la opcion elegida
    public int mostrarMenu() {
        System.out.println("\n===== SORTEO LIGA PROFESIONAL =====");
        System.out.println("1. Realizar nuevo sorteo");
        System.out.println("2. Guardar torneo (torneo.ser)");
        System.out.println("3. Cargar torneo (torneo.ser)");
        System.out.println("4. Mostrar torneo actual");
        System.out.println("0. Salir");
        System.out.print("Elija una opcion: ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Muestra los partidos de todas las etapas
    public void mostrarEtapas(Map<String, List<Partido>> etapas) {
        System.out.println();
        for (Map.Entry<String, List<Partido>> etapa : etapas.entrySet()) {
            System.out.println("--- " + etapa.getKey() + " ---");
            int numero = 1;
            for (Partido p : etapa.getValue()) {
                System.out.println("Partido " + numero + ": " + p);
                numero++;
            }
            System.out.println();
        }
    }

    // Muestra un mensaje informativo
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Muestra un mensaje de error
    public void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }
}