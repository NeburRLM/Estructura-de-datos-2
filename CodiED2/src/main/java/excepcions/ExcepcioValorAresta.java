package excepcions;

public class ExcepcioValorAresta extends Exception{
	private static final long serialVersionUID = 1L;

	public ExcepcioValorAresta() {
		super("No s'ha pogut fer la consulta d'obtenir el valor de l'aresta");
	}
}
