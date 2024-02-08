package estructuraGraf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import excepcions.ExcepcioAdjacents;
import excepcions.ExcepcioValorAresta;
import excepcions.ExcepcioZonesNoGarantides;
import excepcions.ExcepcioAfegirAresta;
import excepcions.ExcepcioCamiOptim;

public class Graf<V, E> implements TADGraf<V, E> {
	private HashMap<String, Vertex<V, E>> vertex;
	private HashMap<Integer, Aresta<V, E>> arestas;
	private String[] dijkstra = new String[356];

	/**
	 * Contructor de la classe Graf
	 * 
	 * @param vertex. Vector de tots els vertex disponibles
	 **/
	public Graf(ArrayList<Vertex<V, E>> vertex) {
		this.vertex = new HashMap<String, Vertex<V, E>>();
		this.arestas = new HashMap<Integer, Aresta<V, E>>();
		int i = 0;
		for (Vertex<V, E> v : vertex) {
			dijkstra[i] = v.getEtiqueta();
			this.vertex.put(v.getEtiqueta(), v);
			i++;
		}
	}

	/**
	 * Inserta una aresta amb un pes per defecte de 1
	 *
	 * @param v1. Vertex 1
	 * @param v2. Vertex 2
	 * @return true-> si la aresta no existeix, false->si la aresta ja existeix
	 **/
	public boolean afegirAresta(Vertex<V, E> v1, Vertex<V, E> v2) {
		Pes<Double> p = new Pes<Double>(1.0);
		return afegirAresta(v1, v2, p);
	}

	/**
	 * Inserta una aresta entre els vertex v1 y v2 amb un per indicat per paràmetre
	 * La aresta s'inserirà sempre i quan sigui única (no existeixi)
	 * 
	 *
	 * @param v1.  Vertex 1
	 * @param v2.  Vertex 2
	 * @param pes. Pes que tindrà l'aresta que uneix als vertex v1 i v2
	 * @return true-> si la aresta no existeix, false->si la aresta ja existeix
	 **/
	public boolean afegirAresta(Vertex<V, E> v1, Vertex<V, E> v2, Pes<Double> p) {
		boolean afegida = true;
		if (v1.equals(v2)) // mirar si els vertex son iguals (no s'inserira)
			return false;

		Aresta<V, E> aresta = new Aresta<V, E>(v1, v2, p);

		if (arestas.containsKey(aresta.hashCode())) // mirar si l'aresta ja existeix al graf (no s'inserirà)
			afegida = false;
		else if (v1.conteVei(aresta) || v2.conteVei(aresta)) // mirar si existeix aquesta unió amb els mateixos vèrtexs
			afegida = false;
		if (afegida == true) {
			arestas.put(aresta.hashCode(), aresta);
			v1.insertarVei(aresta);
			v2.insertarVei(aresta);
		}

		try {
			if (afegida == false) {
				throw new ExcepcioAfegirAresta();
			}
		} catch (ExcepcioAfegirAresta e) {
			System.out.println(e.getMessage());
		}
		return afegida;
	}

	/**
	 * @param aresta. Aresta la qual es vol comprobar si ja existeix
	 * @return true-> el graf conté la aresta que es passa per paràmetre
	 **/
	public boolean existeixAresta(Vertex<V, E> v1, Vertex<V, E> v2) {
		Pes<Double> p = new Pes<Double>(1.0);
		Aresta<V, E> aresta = new Aresta<V, E>(v1, v2, p);
		if (aresta.getVertex1() == null || aresta.getVertex2() == null)
			return false;
		return this.arestas.containsKey(aresta.hashCode());
	}

	/**
	 * @param v1. Vertex 1
	 * @param v2. Vertex 2
	 * @return aresta-> Retorna el valor de l'aresta
	 **/
	public Pes<Double> valorAresta(Vertex<V, E> v1, Vertex<V, E> v2) {
		Pes<Double> p = new Pes<Double>(1.0);
		Aresta<V, E> aresta = new Aresta<V, E>(v1, v2, p);
		boolean existeix = false;
		if (aresta.getVertex1() == null || aresta.getVertex2() == null)
			aresta = null;
		else {
			if (this.arestas.containsKey(aresta.hashCode())) {
				aresta = arestas.get(aresta.hashCode());
				p = aresta.getPes();
				existeix = true;
			}
		}
		try {
			if (existeix != true) {
				throw new ExcepcioValorAresta();
			}
		} catch (ExcepcioValorAresta e) {
			System.out.println(e.getMessage());
		}
		return p;
	}

	/**
	 * @param etiqueta. Identificador del vertex
	 * @return Vertex. Retorna el vertex associat a l'etiqueta
	 **/
	public Vertex<V, E> getVertex(String etiqueta) {
		return this.vertex.get(etiqueta);
	}

	/**
	 * @param v. Vertex el qual es vol saber els seus adjacents
	 * @return adjacents. Retorna un array de tots els adjacents del vertex passat
	 *         per paràmetre
	 **/
	public ArrayList<Vertex<V, E>> adjacents(Vertex<V, E> v) {
		ArrayList<Vertex<V, E>> adjacents = new ArrayList<Vertex<V, E>>();
		Aresta<V, E> a;
		for (int k = 0; k < v.getContarVeins(); k++) {
			a = v.getVei(k);
			adjacents.add(a.getVeiDe(v));
		}
		try {
			if (adjacents.isEmpty()) {
				throw new ExcepcioAdjacents(v.getEtiqueta());
			}
		} catch (ExcepcioAdjacents e) {
			System.out.println(e.getMessage());
		}
		return adjacents;
	}

	/**
	 * @return Set<String>. Retorna els identificadors dels vertex que hi ha al graf
	 **/
	public Set<String> vertexKeys() {
		return this.vertex.keySet();
	}

	/**
	 * @return Set<Arista>. Retorna totes les arestes que hi ha al graf
	 **/
	public Set<Aresta<V, E>> getArestes() {
		return new HashSet<Aresta<V, E>>(this.arestas.values());
	}

	/**
	 * Funció que retorna una llista que conté tots els noms dels punts de càrrega
	 * pels quals ha de passar per arribar al seu destí.
	 * 
	 * @return camiOptim-> cami amb menys distancia entre dos vèrtex (tenint en
	 *         compte l'autonomia)
	 **/
	public ArrayList<String> camiOptim(String vo, String vd, int autonomia) {
		ArrayList<String> camiOptim = new ArrayList<String>(); // cami optim
		String[] estacionsNodes = new String[dijkstra.length]; // vertex totals al graf
		boolean[] visitats = new boolean[dijkstra.length];
		double[] costos = new double[dijkstra.length];
		String[] pred = new String[dijkstra.length];
		String aux = "0", origen, aux2 = "0";
		int index = 0, indexV = 0, indexAdj = 0, acaba = 1, indexPred1 = 0, indexPred2 = 0;
		ArrayList<Vertex<V, E>> adjacents; // adjacents del vertex a consultar
		Vertex<V, E> auxVertex; // vertex auxiliar
		LinkedList<String> cami = new LinkedList<String>(); // cami optim ordenat final a retornar
		String vCami = "0.0";
		Pes<Double> pes1;
		Pes<Double> pes2;
		double pesMinim; // distancia minima per seleccionar el vertex pròxim a consultar
		double dis = 0; // distancia per comparar amb l'autonomia
		boolean excepcio = false;

		// primer omplim la taula amb tots els nodes disponibles
		for (int i = 0; i < estacionsNodes.length; i++) {
			estacionsNodes[i] = dijkstra[i];
			costos[i] = Double.POSITIVE_INFINITY;
		}

		// Comprovem si existeix el vèrtex destí
		aux = estacionsNodes[0];
		origen = vd;
		for (int j = 1; j < estacionsNodes.length && !(aux.equalsIgnoreCase(origen)); j++) {
			aux = estacionsNodes[j];
			index = j;
		}

		try {
			if (index == estacionsNodes.length - 1 && !(aux.equalsIgnoreCase(origen))) {
				throw new ExcepcioCamiOptim();
			}
		} catch (ExcepcioCamiOptim e) {
			System.out.println(e.getMessage());
			return camiOptim = null;
		}

		// Comprovem si existeix el vèrtex origen per començar l'algorisme
		aux = estacionsNodes[0];
		origen = vo;
		for (int j = 1; j < estacionsNodes.length && !(aux.equalsIgnoreCase(origen)); j++) {
			aux = estacionsNodes[j];
			index = j;
		}

		try {
			if (index == estacionsNodes.length - 1 && !(aux.equalsIgnoreCase(origen))) {
				throw new ExcepcioCamiOptim();
			}
		} catch (ExcepcioCamiOptim e) {
			System.out.println(e.getMessage());
			return camiOptim = null;
		}

		Vertex<V, E> v1 = vertex.get(estacionsNodes[index]); // obtinc la informacio del vertex a la taulahash
		adjacents = adjacents(v1); // obtinc tots els adjacents del node
		visitats[index] = true; // marco node actual com a visitat (node origen)
		costos[index] = 0.0; // marco distancia 0 al node origen

		auxVertex = adjacents.get(indexAdj);
		while (indexAdj < adjacents.size()) {// mentre no superi el numero d'adjacents modifiquem les distancies
			for (int k = 0; k < estacionsNodes.length && (!(auxVertex.getEtiqueta().equalsIgnoreCase(aux2))); k++) {

				aux2 = estacionsNodes[k];
				indexV = k;
			}
			pes1 = (valorAresta(v1, auxVertex));
			double dist = pes1.convertir(pes1);
			if (dist < autonomia) { // si la distancia es inferior a l'autonomia i inferior al les demés distancies
									// dels altres vèrtex
				costos[indexV] = dist; // actualitzo nou cost més petit
				pred[indexV] = v1.getEtiqueta(); // actualitzo nou predecessor
				excepcio = true; // anulo excepcio ja que he trobat un vèrtex amb distancia inferior a
									// l'autonomia
			}

			indexAdj++;
			if (indexAdj < adjacents.size())
				auxVertex = adjacents.get(indexAdj);
		}
		indexAdj = 0;
		try {
			if (excepcio == false) {
				throw new ExcepcioCamiOptim();
			}
		} catch (ExcepcioCamiOptim e) {
			System.out.println(e.getMessage());
			return null;
		}

		while (acaba != estacionsNodes.length) {
			pesMinim = Double.POSITIVE_INFINITY; // parteixo d'una distancia gran
			for (int l = 0; l < estacionsNodes.length; l++) {
				if (pesMinim > costos[l] && visitats[l] != true) { // mirar aqui
					pesMinim = costos[l];
					index = l;
				}
			}

			Vertex<V, E> v2 = vertex.get(estacionsNodes[index]); // obtinc la informacio del vertex a la taulahash
			adjacents = adjacents(v2);
			visitats[index] = true;
			if (!adjacents.isEmpty()) {
				auxVertex = adjacents.get(indexAdj);

				while (indexAdj < adjacents.size()) {
					for (int k = 0; k < estacionsNodes.length
							&& (!(auxVertex.getEtiqueta().equalsIgnoreCase(aux2))); k++) {
						aux2 = estacionsNodes[k];
						indexV = k;
					}

					pes2 = (valorAresta(v2, auxVertex));
					double dist = pes2.convertir(pes2);
					dis = costos[index] + dist;
					if (dist < autonomia) { // si la distancia es inferior a l'autonomia i inferior al les demés
											// distancies de Dijkstra
						if (dis < costos[indexV]) {
							costos[indexV] = dis; // actualitzo nou cost més petit
							pred[indexV] = estacionsNodes[index]; // actualitzo nou predecessor
						}
					}

					indexAdj++; // segueixo mirant els adjacents que queden
					if (indexAdj < adjacents.size()) // controlar número d'adjacents que té el vèrtex actual
						auxVertex = adjacents.get(indexAdj);
				}
			}

			indexAdj = 0;
			acaba++;
		}
		cami.add(vd);

		while (!vCami.equalsIgnoreCase(vo)) { // formem el camí òptim ordenat començant pel vèrtex destí
			for (int d = 0; d < estacionsNodes.length && (!(vCami.equalsIgnoreCase(vd))); d++) {
				vCami = estacionsNodes[d];
				indexPred1 = d;
			}

			cami.add(pred[indexPred1]); // inserto primer vèrtex destí al camí òptim i segueixo amb els demés
										// predecessors fins el
										// vèrtex origen

			while (!vCami.equalsIgnoreCase(vo)) {
				for (int p = 0; p < estacionsNodes.length && !(vCami.equalsIgnoreCase(pred[indexPred1])); p++) {
					vCami = estacionsNodes[p];
					indexPred2 = p;
				}
				cami.add(pred[indexPred2]);
				indexPred1 = indexPred2;
			}
		}
		// inverteixo els vèrtex per retornar el camí òptim del vèrtex origen al vèrtex
		// destí
		for (int i = cami.size() - 2; 0 <= i; i--) {
			camiOptim.add(cami.get(i));
		}
		try {
			if((camiOptim.get(0).equalsIgnoreCase(vo))&&((camiOptim.get(1).equalsIgnoreCase(vd)))){
				throw new ExcepcioCamiOptim();
			}
		} catch (ExcepcioCamiOptim e) {
			System.out.println(e.getMessage());
			return null;
		}
		return camiOptim;

	}

	/**
	 * Funció que retorna una llista que conté aquelles zones de recàrrega que no
	 * compleixen la condició d'estar enllaçades amb, almenys, amb una altra zona de
	 * recàrrega a una distància màxima determinada per l’autonomia
	 * 
	 * @return noArriba-> vèrtex els quals no son accesibles a menys de l'autonomia
	 *         passada per paràmetre
	 **/
	public LinkedList<String> zonesDistMaxNoGarantida(String indentificador_origen, int autonomia) {

		String[] estacionsNodes = new String[dijkstra.length]; // vèrtex d'estacions totals que té el graf
		boolean[] visitats = new boolean[dijkstra.length]; // true->vèrtex visitat
		boolean[] arribat = new boolean[dijkstra.length]; // vèrtex true-> vèrtex accesible

		String aux = "0", id0, aux2 = "0";
		int index = 0, indexV = 0, indAd = 0, acaba = 1;
		ArrayList<Vertex<V, E>> adj;
		Vertex<V, E> auxA;
		LinkedList<String> cami = new LinkedList<String>(); // cami que es crearà a partir de Prim
		LinkedList<String> noArriba = new LinkedList<String>(); // vèrtex els quals no es poden arribar per qualsevol
																// lloc segons l'autonomia

		Pes<Double> pes1;

		double minim = Double.POSITIVE_INFINITY; // partim d'una distancia superior
		String vertexMin = "0";
		int indexMin = 0;
		int noConectats = 0;

		// carreguem totes les estacions que conté el graf a un array
		for (int i = 0; i < estacionsNodes.length; i++) {
			estacionsNodes[i] = dijkstra[i];
			visitats[i] = false;
			arribat[i] = false;
		}

		// agafem vèrtex origen i mirem si existeix a l'estructura del graf
		aux = estacionsNodes[0];
		id0 = indentificador_origen;
		for (int j = 1; j < estacionsNodes.length && !(aux.equalsIgnoreCase(id0)); j++) {
			aux = estacionsNodes[j];
			index = j;
		}
		try {
			if (index == estacionsNodes.length - 1 && !(aux.equalsIgnoreCase(id0))) {
				throw new ExcepcioZonesNoGarantides();
			}
		} catch (ExcepcioZonesNoGarantides e) {
			System.out.println(e.getMessage());
			return noArriba = null;
		}
		Vertex<V, E> v1 = vertex.get(estacionsNodes[index]); // obtinc la informacio del vertex a la taulahash
		adj = adjacents(v1); // obtinc tots els adjacents del vertex
		visitats[index] = true; // marco vertex actual com a visitat (vertex origen)
		cami.add(indentificador_origen); // inserto vèrtex origen per anant visitant els demès (Prim)

		auxA = adj.get(indAd); // obtinc primers adjacents del vèrtex origen
		while (indAd < adj.size()) {// mentre no superi el numero d'adjacents comprovem que els vèrtex adjacents son
									// accessibles
			// segons l'autonomia
			for (int k = 0; k < estacionsNodes.length && (!(auxA.getEtiqueta().equalsIgnoreCase(aux2))); k++) {

				aux2 = estacionsNodes[k];
				indexV = k;
			}
			pes1 = (valorAresta(v1, auxA));
			double dist = pes1.convertir(pes1);
			// si l'autonomia es superior a la distancia que hi ha entre els vèrtex i no
			// s'ha visitat, marquem com accessible
			if ((dist < autonomia) && (arribat[indexV] != true) && (visitats[indexV] != true)) {
				arribat[indexV] = true;
			}

			if (minim > dist) { // mirem si té distancia minima per seguir recorregut de Prim
				minim = dist;
				vertexMin = aux2;
				indexMin = indexV;
			}

			indAd++; // passem al següent vèrtex adjacent
			if (indAd < adj.size()) // controlem límil d'adjacents
				auxA = adj.get(indAd);
		}
		// ha trobat el primer vertex minim adjacent al vertex origen
		visitats[indexMin] = true; // actualitzem nou vertex adjacent connectat
		arribat[index] = true;
		cami.add(vertexMin); // insertem nou node adjacent

		indAd = 0;

		vertexMin = "0";
		indexMin = 0;
		aux2 = "0";
		while (acaba != estacionsNodes.length - 1) {
			minim = Double.POSITIVE_INFINITY; // partim distancia mínima gran
			for (String s : cami) { // per cada vèrtex que hi hagi al cami de Prim mirar els seus adjacents

				Vertex<V, E> vMirar = vertex.get(s);
				ArrayList<Vertex<V, E>> veins = adjacents(vMirar); // obtinc els vèrtex adjacents del vèrtex actual a
																	// consultar
				for (Vertex<V, E> vDesti : veins) { // per cada vèrtex adjacent
					for (int k = 0; k < estacionsNodes.length
							&& (!(vDesti.getEtiqueta().equalsIgnoreCase(aux2))); k++) {
						aux2 = estacionsNodes[k];
						indexV = k;
					}
					pes1 = (valorAresta(vMirar, vDesti));
					double dist = pes1.convertir(pes1);
					// si l'autonomia es superior a la distancia que hi ha entre els vèrtex i no
					// s'ha visitat, marquem com accessible
					if ((dist < autonomia) && (arribat[indexV] != true) && (visitats[indexV] != true)) {
						arribat[indexV] = true;
					}
					// mirem si es distancia minima per seguir amb el recorregut de Prim
					if (minim > dist && visitats[indexV] != true) {
						minim = dist;
						vertexMin = aux2;
						indexMin = indexV;
					}

				}

			}
			visitats[indexMin] = true; // actualitzem nou vertex adjacent connectat
			cami.add(vertexMin); // insertem nou node adjacent al cami de Prim
			aux2 = estacionsNodes[0]; // reiniciem recorregut pels adjacents
			indexV = 0;
			acaba++;

		}
		// guardem vèrtex que no s'han pogut arribar segons l'autonomia
		for (int i = 0; i < arribat.length; i++) {
			if (arribat[i] == false) {
				noArriba.add(estacionsNodes[i]);
				noConectats++;
			}
		}
		// si no hi ha cap false, s'han arribat a tots els vèrtex, per tant retornem una
		// llista nula
		try {
			if (noConectats == 0) {
				throw new ExcepcioZonesNoGarantides();
			}

		} catch (ExcepcioZonesNoGarantides e) {
			System.out.println(e.getMessage());
			return noArriba = null;
		}
		// System.out.println(acaba); //n connexions
		// System.out.println("No connectats: " + noConectats);
		return noArriba;
	}

}