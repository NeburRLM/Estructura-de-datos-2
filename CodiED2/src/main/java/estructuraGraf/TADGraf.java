package estructuraGraf;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 * @author RUBÉN
 * 
 *         TAD de la estructura de dades (graf)
 */
public interface TADGraf <V,E>{
	
	boolean afegirAresta(Vertex<V,E> v1, Vertex<V,E> v2, Pes<Double> e);
	
	boolean existeixAresta(Vertex<V,E> v1, Vertex<V,E> v2);
	
	Pes<Double> valorAresta(Vertex<V,E> v1, Vertex<V,E> v2);
	
	ArrayList<Vertex<V,E>> adjacents(Vertex<V,E> v);
	
	ArrayList<String> camiOptim(String identificador_origen, String indentificador_desti, int autonomia);
		
	LinkedList<String> zonesDistMaxNoGarantida(String indentificador_origen,int autonomia);
			
}
