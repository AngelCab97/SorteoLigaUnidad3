package modelo.excepciones;

public class NumeroEquiposInvalidoException extends Exception {
	
	private static final long serialVersionUID = 1L;

    // Constructor que recibe un mensaje descriptivo y lo pasa a la superclase
    public NumeroEquiposInvalidoException(String mensaje) {
        super(mensaje);
    }

}
