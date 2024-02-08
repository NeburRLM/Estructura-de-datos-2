package excepcions;

public class ExcepcioCamiOptim extends Exception{
	private static final long serialVersionUID = 1L;

	public ExcepcioCamiOptim() {
		super("No s'ha pogut fer la consulta d'obtenir el camí òptim");
	}
}
