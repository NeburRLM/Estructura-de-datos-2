package excepcions;

public class ExcepcioAdjacents extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ExcepcioAdjacents (String v) {
		super("No s'ha pogut obtenir els adjacents del vèrtex "+ v);
	}
	
}
