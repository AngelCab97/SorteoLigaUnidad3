package modelo;

import java.io.Serializable;

// Representa un partido entre dos equipos. Es Serializable para guardarse con el torneo.
public class Partido implements Serializable {

    private static final long serialVersionUID = 1L;

    private Equipo equipoLocal;
    private Equipo equipoVisitante;

    public Partido(Equipo equipoLocal, Equipo equipoVisitante) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    @Override
    public String toString() {
        return equipoLocal + " vs " + equipoVisitante;
    }
}