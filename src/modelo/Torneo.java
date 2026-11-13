package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import modelo.excepciones.NumeroEquiposInvalidoException;
import modelo.excepciones.SorteoInvalidoException;

// Representa el torneo completo: equipos, etapas y partidos sorteados.
// Es Serializable para poder guardar su estado en un archivo (torneo.ser).
public class Torneo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Equipo> equipos;
    // Guarda los partidos de cada etapa (octavos, cuartos, semifinales, final)
    private Map<String, List<Partido>> etapas;
    private Random random;

    public Torneo(List<Equipo> equipos) {
        this.equipos = new ArrayList<>(equipos);
        this.etapas = new LinkedHashMap<>();
        this.random = new Random();
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public Map<String, List<Partido>> getEtapas() {
        return etapas;
    }

    // Verifica que la cantidad sea potencia de 2 (16, 8, 4, 2)
    private boolean esPotenciaDeDos(int n) {
        return n >= 2 && (n & (n - 1)) == 0;
    }

    // Devuelve el nombre de la etapa según la cantidad de equipos que quedan
    private String nombreEtapa(int cantidadEquipos) {
        switch (cantidadEquipos) {
            case 16: return "Octavos de final";
            case 8:  return "Cuartos de final";
            case 4:  return "Semifinales";
            case 2:  return "Final";
            default: return "Etapa de " + cantidadEquipos + " equipos";
        }
    }

    // Realiza el sorteo de todas las etapas, desde octavos hasta la final
    public void realizarSorteo() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        etapas.clear();
        List<Equipo> clasificados = new ArrayList<>(equipos);

        while (clasificados.size() >= 2) {
            // Valida que la cantidad de equipos sea potencia de 2
            if (!esPotenciaDeDos(clasificados.size())) {
                throw new NumeroEquiposInvalidoException(
                    "El numero de equipos (" + clasificados.size()
                    + ") no es valido para eliminacion directa. Debe ser potencia de 2.");
            }

            // Se mezcla la lista una sola vez por etapa
            List<Equipo> mezclados = new ArrayList<>(clasificados);
            Collections.shuffle(mezclados, random);

            // Sorteo recursivo de la etapa
            List<Partido> partidos = new ArrayList<>();
            emparejar(mezclados, 0, partidos);
            etapas.put(nombreEtapa(clasificados.size()), partidos);

            // Se simula un ganador al azar de cada partido para la siguiente etapa
            List<Equipo> siguientes = new ArrayList<>();
            for (Partido p : partidos) {
                siguientes.add(random.nextBoolean() ? p.getEquipoLocal() : p.getEquipoVisitante());
            }
            clasificados = siguientes;
        }
    }

    // METODO RECURSIVO: empareja los equipos de dos en dos.
    // Caso base: cuando el indice llega al final de la lista.
    // Paso recursivo: crea un partido y se llama con indice + 2.
    private void emparejar(List<Equipo> lista, int indice, List<Partido> partidos)
            throws SorteoInvalidoException {

        // Caso base: ya se emparejaron todos los equipos
        if (indice >= lista.size()) {
            return;
        }

        // Si queda un solo equipo sin pareja, el sorteo es imposible
        if (indice + 1 >= lista.size()) {
            throw new SorteoInvalidoException(
                "Equipo sin oponente: " + lista.get(indice));
        }

        Equipo local = lista.get(indice);
        Equipo visitante = lista.get(indice + 1);

        // Un equipo no puede ser competidor y oponente a la vez
        if (local == visitante) {
            throw new SorteoInvalidoException(
                "Un equipo no puede enfrentarse a si mismo: " + local);
        }

        // Ningun equipo puede repetirse en la misma etapa
        for (Partido p : partidos) {
            if (p.getEquipoLocal() == local || p.getEquipoVisitante() == local
                || p.getEquipoLocal() == visitante || p.getEquipoVisitante() == visitante) {
                throw new SorteoInvalidoException(
                    "Equipo repetido en la misma etapa: " + local + " o " + visitante);
            }
        }

        partidos.add(new Partido(local, visitante));

        // Llamada recursiva con los siguientes dos equipos
        emparejar(lista, indice + 2, partidos);
    }
}