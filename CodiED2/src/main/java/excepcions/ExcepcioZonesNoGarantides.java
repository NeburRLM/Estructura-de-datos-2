package excepcions;

public class ExcepcioZonesNoGarantides extends Exception{
	private static final long serialVersionUID = 1L;

	public ExcepcioZonesNoGarantides() {
		super("No s'ha pogut fer la consulta d'obtenir els vèrtex no accessibles");
	}
}
