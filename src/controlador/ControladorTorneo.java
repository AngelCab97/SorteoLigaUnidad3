package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.Equipo;
import modelo.Torneo;
import modelo.excepciones.NumeroEquiposInvalidoException;
import modelo.excepciones.SorteoInvalidoException;
import vista.VistaConsola;

// Controlador: media entre la Vista y el Modelo.
// Aqui se capturan las excepciones con try-catch.
public class ControladorTorneo {

    private static final String ARCHIVO = "torneo.ser";

    private Torneo torneo;
    private VistaConsola vista;

    public ControladorTorneo(VistaConsola vista) {
        this.vista = vista;
        this.torneo = new Torneo(crearEquipos());
    }

    // Crea los 16 equipos iniciales de la liga
    private List<Equipo> crearEquipos() {
        String[] nombres = {
            "Real Madrid", "FC Barcelona", "Manchester City", "Liverpool",
            "Bayern Munich", "Paris Saint-Germain", "Inter de Milan", "Arsenal",
            "Atletico de Madrid", "Borussia Dortmund", "Juventus", "AC Milan",
            "Bayer Leverkusen", "Benfica", "Chelsea", "Napoli"
        };
        List<Equipo> lista = new ArrayList<>();
        for (String nombre : nombres) {
            lista.add(new Equipo(nombre));
        }
        return lista;
    }

    // Ciclo principal: pide una opcion a la Vista y ejecuta la accion
    public void iniciar() {
        int opcion;
        do {
            opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1: realizarSorteo(); break;
                case 2: guardarTorneo(); break;
                case 3: cargarTorneo(); break;
                case 4: mostrarTorneo(); break;
                case 0: vista.mostrarMensaje("Hasta luego."); break;
                default: vista.mostrarError("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private void realizarSorteo() {
        try {
            torneo.realizarSorteo();
            vista.mostrarMensaje("Sorteo realizado correctamente.");
            vista.mostrarEtapas(torneo.getEtapas());
        } catch (NumeroEquiposInvalidoException e) {
            vista.mostrarError(e.getMessage());
        } catch (SorteoInvalidoException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    private void guardarTorneo() {
        try {
            torneo.guardar(ARCHIVO);
            vista.mostrarMensaje("Torneo guardado en " + ARCHIVO);
        } catch (IOException e) {
            vista.mostrarError("No se pudo guardar el torneo: " + e.getMessage());
        }
    }

    private void cargarTorneo() {
        try {
            torneo = Torneo.cargar(ARCHIVO);
            vista.mostrarMensaje("Torneo cargado desde " + ARCHIVO);
            vista.mostrarEtapas(torneo.getEtapas());
        } catch (IOException e) {
            vista.mostrarError("No se pudo leer el archivo: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            vista.mostrarError("Clase no encontrada al cargar: " + e.getMessage());
        }
    }

    private void mostrarTorneo() {
        if (torneo.getEtapas().isEmpty()) {
            vista.mostrarError("Aun no hay sorteo. Elija la opcion 1 primero.");
        } else {
            vista.mostrarEtapas(torneo.getEtapas());
        }
    }
}