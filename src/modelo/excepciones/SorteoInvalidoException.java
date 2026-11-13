package modelo.excepciones;

public class SorteoInvalidoException extends Exception {
	
	 private static final long serialVersionUID = 1L;

	    // Constructor que recibe un mensaje descriptivo y lo pasa a la superclase
	    public SorteoInvalidoException(String mensaje) {
	        super(mensaje);
	    }

}
