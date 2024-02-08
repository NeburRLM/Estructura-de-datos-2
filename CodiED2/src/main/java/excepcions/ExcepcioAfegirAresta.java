package excepcions;

public class ExcepcioAfegirAresta extends Exception {
	private static final long serialVersionUID = 1L;

	public ExcepcioAfegirAresta() {
		super("No s'ha pogut fer la inserció correctament");
	}
}
