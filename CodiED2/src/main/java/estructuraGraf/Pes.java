package estructuraGraf;

public class Pes<E> {
	private double pes;

	public Pes(Double pes) {
		this.pes = pes;
	}

	public double getPes() {
		return pes;
	}

	public void setPes(double pes) {
		this.pes = pes;
	}
	
	
	public double convertir(Pes<E> costo) {
		String costoString=(costo.toString());		
		String[] s = costoString.split(" ");	
		Double result = Double.parseDouble(s[1]);
		return result;
	}
	
	@Override
	public String toString() {
		return "Distancia: " + pes;
	}

}
