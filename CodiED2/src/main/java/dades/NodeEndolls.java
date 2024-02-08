package dades;



public class NodeEndolls {
	String id;
	String id_estacio;	
	double potencia;
	String tipus;
	double latitud,longitud;
	private NodeEndolls seguent;
	private String nom;
	private String data;
	private String consum;
	private String carrer;
	private String estat;
	@Override
	public String toString() {
		return "NodeEndolls [id=" + id + ", id_estacio=" + id_estacio + ", potencia=" + potencia + ", tipus=" + tipus
				+ ", latitud=" + latitud + ", longitud=" + longitud + ", seguent=" + seguent + ", nom=" + nom
				+ ", data=" + data + ", consum=" + consum + ", carrer=" + carrer + ", estat=" + estat + ", ciutat="
				+ ciutat + ", temps=" + temps + "]";
	}

	private String ciutat;
	private String temps;
	
	public NodeEndolls(String id, String id_estacio, String nom, String data, String consum, String carrer, String ciutat, String estat,
			String temps, String tipus, 
			double latitud, double longitud, double potencia){
			 
		
		this.id = id;
		this.id_estacio = id_estacio;
		this.nom = nom;
		this.data = data;
		this.consum = consum;
		this.carrer = carrer;
		this.estat = estat;
		this.ciutat = ciutat;
		this.temps = temps;
		this.potencia = potencia;
		this.tipus = tipus;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId_estacio() {
		return id_estacio;
	}

	public void setId_estacio(String id_estacio) {
		this.id_estacio = id_estacio;
	}


	public Double getPotencia() {
		return potencia;
	}

	public void setPotencia(Double potencia) {
		this.potencia = potencia;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
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
	
	
	public NodeEndolls getSeguent() {
		return seguent;
	}

	/**
	 * Métode set per modificar el node següent
	 * 
	 * @param seguent -> node al qual es vol modificar el node següent actual
	 */
	public void setSeguent(NodeEndolls seguent) {
		this.seguent = seguent;
	}
	
	public NodeEndolls llistaEndolls(NodeEndolls llista) {
		return llista;
	}
	
}
