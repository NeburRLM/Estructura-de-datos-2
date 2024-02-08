package dades;

public class Estacio {
	
	NodeEndolls llistaEndolls;
	String id_est;
	double latitud,longitud;
	double potencia;
	
	
	
	public Estacio (NodeEndolls llistaEndolls,String id_est,double latitud, double longitud, double potencia ) {
		this.llistaEndolls=llistaEndolls;
		this.id_est=id_est;	
		this.latitud=latitud;
		this.longitud=longitud;
		this.potencia=potencia;
	}
	
	
	
	
	public double getPotencia() {
		return potencia;
	}




	public void setPotencia(double potencia) {
		this.potencia = potencia;
	}




	public String getId_est() {
		return id_est;
	}
	
	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
	public NodeEndolls llistaEndolls(NodeEndolls llista) {
		return llista;
	}
	




	@Override
	public String toString() {
		return "Estacio [llistaEndolls=" + llistaEndolls + ", id_est=" + id_est + ", potencia=" + potencia + ", latitud=" + latitud + ", longitud="
				+ longitud + "]";
	}
	
	
	
}
