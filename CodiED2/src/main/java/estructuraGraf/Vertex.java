package estructuraGraf;

import java.util.ArrayList;


public class Vertex<V,E> {

	private ArrayList<Aresta<V,E>> adjacents;
	private String etiqueta,clau;
	
	
	
	public Vertex (String etiqueta, String clau)
		    {
				this.clau=clau;
				this.etiqueta = etiqueta;
				this.adjacents = new ArrayList<Aresta<V,E>>();
		    }
	public Vertex (String etiqueta)
    {
		this.etiqueta = etiqueta;
		this.adjacents = new ArrayList<Aresta<V,E>>();
    }
	
	public String getClau() {
		return this.clau;
	} 

	/**
	 * Afegeix una aresta al array de llista de adjacents si no existeix ja al array
	 * 
	 * @param aresta. Relació que es guardarà entre dos vertex al array de adjacents del vertex
	 */
	public void insertarVei(Aresta<V,E> aresta) {
		if (!this.adjacents.contains(aresta))
			this.adjacents.add(aresta);
	}

	/**
	 * Comproba si la aresta incedeix en aquest vertex
	 * 
	 * @param aresta. Aresta la qual es vol consultar
	 * @return true-> Si la relació existeix al array de adjacents del vertex
	 */
	public boolean conteVei(Aresta<V,E> arista) {
		return this.adjacents.contains(arista);
	}

	/**
	 * @param index. Indica la posició en la qual s'ha d'agafar la relació de la aresta
	 * @return Retorna l'aresta que es troba en la posició indicada per l'index passat per paràmetre de l'array d'adjacents del vertex 
	 */
	public Aresta<V,E> getVei(int index) {
		return this.adjacents.get(index);
	}

	
	/**
	 * @return int. Retorna el número d'arestas que té el vertex en l'array d'adjacents
	 */
	public int getContarVeins() {
		return this.adjacents.size();
	}

	/**
	 * @return String. Retorna el valor de l'identificador del vertex
	 */
	public String getEtiqueta() {
		return this.etiqueta;
	}

	/**
	 * Funció per comprobar si dos vertex són iguals (contenen el mateix identificador)
	 * 
	 * @param vertex2. Vertex que es compararà amb altre vertex per veure si son iguals
	 * @return true-> els dos vertex son iguals, false-> els dos vertex son diferents
	 */
	public boolean equals(Object vertex2) {
		if (vertex2 instanceof Vertex<?, ?>) {
			return this.etiqueta.equals((((Vertex<?, ?>) vertex2).etiqueta));
		}else {
			return false;
		}		
	}

	/**
	 * @return String. Retorna l'identificador del vertexs
	 */
	public String toString() {
		return "Vertex: " + this.etiqueta;
	}

	/**
	 * @return int. Hash_code per saber la posició del vertex a la taula de hash
	 **/
	public int hashCode() {
		return this.getEtiqueta().hashCode();
	}

	
}