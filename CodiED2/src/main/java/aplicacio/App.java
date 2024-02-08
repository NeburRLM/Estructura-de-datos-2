package aplicacio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dades.Estacio;
import dades.NodeEndolls;
import estructuraGraf.Graf;
import estructuraGraf.Pes;
import estructuraGraf.Vertex;

public class App {
	static int ultimaPos = 1;
	static NodeEndolls n[] = new NodeEndolls[450];

	public static <T, K, E, V> void main(String[] args) throws IOException, ParseException {
		JSONParser jsonP = new JSONParser();

		try (FileReader reader = new FileReader(
				"C:\\Users\\RUBÉN\\Desktop\\UNIVERSIDAD\\CURSO 3\\ASIGNATURES\\PROGRAMACIÓ\\Java\\Practicar\\PR2-Ruben_LopezMartinez\\icaen.json")) {
			Object obj = jsonP.parse(reader);
			JSONArray nodeList = (JSONArray) obj;

			nodeList.forEach(node -> parseNodeObj((JSONObject) node)); // warning que no se solucionar
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<Estacio> llistaEstacions = new ArrayList<Estacio>();
		ArrayList<Vertex<V, E>> v = new ArrayList<Vertex<V, E>>();

		Estacio est;

		for (int i = 0; i < n.length && n[i] != null; i++) {
			est = new Estacio(n[i].llistaEndolls(n[i]), n[i].getId_estacio(), n[i].getLatitud(), n[i].getLongitud(),
					n[i].getPotencia());
			llistaEstacions.add(est);
			// v.add(new
			// Vertex<V,E>(est.getId_est(),String.valueOf(est.getLatitud())+String.valueOf(est.getLongitud())));
			v.add(new Vertex<V, E>(est.getId_est()));
		}

		Pes<Double> d, dMin;
		Graf<V, E> graf = new Graf<V, E>(v);
		boolean insertat;
		Estacio estMin = llistaEstacions.get(1);
		int vMin = 1;
		double distMin = 0;
		for (int i = 0; i < llistaEstacions.size(); i++) {
			insertat = false;
			for (int j = 0; j < llistaEstacions.size(); j++) {
				double dist = distancia(llistaEstacions.get(i).getLatitud(), llistaEstacions.get(i).getLongitud(),
						llistaEstacions.get(j).getLatitud(), llistaEstacions.get(j).getLongitud());
				if (dist < 40.0) {
					d = new Pes<Double>(dist);
					insertat = graf.afegirAresta(v.get(i), v.get(j), d); // retorna fals si ja exixteix la mateixa unió
				} else if (insertat == false) {
					if (dist < distancia(estMin.getLatitud(), estMin.getLongitud(), llistaEstacions.get(i).getLatitud(),
							llistaEstacions.get(i).getLongitud())) {
						estMin = llistaEstacions.get(j);
						vMin = j;
						distMin = dist;
					}

				}

			}
			if (insertat == false) {
				dMin = new Pes<Double>(distMin);
				graf.afegirAresta(v.get(i), v.get(vMin), dMin);
			}
		}

		//////////////////////////////////////////////////////////////////

		// Mostrar totes les connexions d'arestes amb cada vèrtex
		/*System.out.println("\n");
		for (int i = 0; i < v.size(); i++) {

			System.out.println(v.get(i)); // Vertex que es consulta

			for (int k = 0; k < v.get(i).getContarVeins(); k++) {
				System.out.println(v.get(i).getVei(k)); // Aresta que es consulta
			}
		}*/
		
		
		/////////////////////// JOC DE PROVES /////////////////////////////
		///////////////////////////////////////////////////////////////////
		
		// Vertex per fer el joc de proves
		Vertex<V, E> inventat = new Vertex<V, E>("1234567890");
		Vertex<V, E> v1 = new Vertex<V, E>("9142");
		Vertex<V, E> v2 = new Vertex<V, E>("9143");
		
		
		
		// Afegir una aresta ja existent al graf (comprovar excepcio)
		System.out.println("\n");
		Pes<Double> p = new Pes<Double>(50.0);
		graf.afegirAresta(v1, v2, p);

		///////////////////////////////////////////////////////////////////

		// Mirar si existeix la connexió entre dos vèrtex en forma d'aresta
		System.out.println("\n");
		System.out.println("Existeix la connexio entre " + v.get(1) + " i " + v.get(2) + ": "
				+ graf.existeixAresta(v.get(1), v.get(2)));

		System.out.println("Existeix la connexio entre " + v.get(1) + " i " + inventat + ": "
				+ graf.existeixAresta(v.get(1), inventat));

		///////////////////////////////////////////////////////////////////

		// Mirar el valor de l'aresta (valors existents)
		System.out.println("\n");
		System.out.println("Valor de l'aresta entre els vertexs ->>>> " + v.get(1) + " i " + v.get(2) + "-> "
				+ graf.valorAresta(v.get(1), v.get(2)));
		
		///////////////////////////////////////////////////////////////////	
		
		// Mirar el valor de l'aresta (valors aresta inexistents)
		System.out.println("Valor de l'aresta entre els vertexs ->>>> " + v.get(1) + " i " + inventat + "-> "
				+ graf.valorAresta(v.get(1), (inventat)));

		///////////////////////////////////////////////////////////////////	
		
		// Mirar totes les connexions que té un vèrtex (sense utilitzar mètode adjacents)
		System.out.println("\n");		
		int cont = 0;
		System.out.println("Connexions del " + v.get(0) + " :");
		for (int k = 0; k < v.get(0).getContarVeins(); k++) {
			System.out.println(v.get(0).getVei(k)); // Respresentacion String de Arista para este objeto
			cont++;
		}
		System.out.println("Número de connexions del " + v.get(0) + "= " + cont + " connexions\n");

		///////////////////////////////////////////////////////////////////	
		
		// Mirar totes les connexions que té un vèrtex (partir del mètode adjacents)
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("Connexions del " + v.get(0) + " (amb la funció adjacents)->");
		System.out.println(graf.adjacents(v.get(0)));
		
		
		// Mirar totes les connexions que té un vèrtex inexistent (partir del mètode adjacents comprovar 
		//funcioment excepcioAdjacents)
		System.out.println("\n");
		System.out.println(graf.adjacents(inventat));		
		
		
		///////////////////////////////////////////////////////////////////	
		
		// Buscar el camí òptim entre dos vèrtex tenint en compte una autonomia determinada	
		System.out.println("\n");
		System.out.println("\n----DIJKSTRA----");
		// System.out.println(graf.camiOptim("9142","61739", 100));
		System.out.println("--> "+graf.camiOptim("3386242", "7088639", 35));
		System.out.println("--> "+graf.camiOptim("3386242", inventat.getEtiqueta(), 35));
		
		///////////////////////////////////////////////////////////////////	
		
		// Mostrar els vèrtex inaccessibles segons una autonomia determinada a partir d'un vèrtex origen
		System.out.println("\n");
		System.out.println("\n---- ZonesDistMaxNoGarantida----");
		System.out.println("--> "+graf.zonesDistMaxNoGarantida("9142", 25));

		
		
		///////////////////////////////////////////////////////////////////	
		
		// Analitzar costos rutes
		System.out.println("\n\n");
		System.out.println("---- Costos----");
		ArrayList<Double> testLatitud1 =new ArrayList<Double>();
		ArrayList<Double> testLongitud1 =new ArrayList<Double>();
		ArrayList<Double> testLatitud2 =new ArrayList<Double>();
		ArrayList<Double> testLongitud2 =new ArrayList<Double>();
		
		
		testLatitud1.add(41.412473739646);
		testLongitud1.add(2.014127862566);
		testLatitud2.add(40.794775);
		testLongitud2.add(0.525542);
		
		testLatitud1.add(41.412473739646);
		testLongitud1.add(2.014127862566);
		testLatitud2.add(41.5555823);
		testLongitud2.add(2.4005556);
		
		testLatitud1.add(41.5555823);
		testLongitud1.add(2.4005556);
		testLatitud2.add(41.780674);
		testLongitud2.add(3.022077);
		
		testLatitud1.add(41.5555823);
		testLongitud1.add(2.4005556);
		testLatitud2.add(41.375768);
		testLongitud2.add(1.163327);
		
		testLatitud1.add(40.814151);
		testLongitud1.add(0.515161);
		testLatitud2.add(42.268984);
		testLongitud2.add(2.966869);
		
		int test=0,ruta=1;
		String vOrigen;
		String vDesti;
		boolean trobat=false;
		while(test<testLongitud1.size()) {
			trobat=false;
			for(int i=0;i<v.size() && trobat == false;i++) {
				if(llistaEstacions.get(i).getLatitud()==(testLatitud1.get(test))&&
					(llistaEstacions.get(i).getLongitud()==(testLongitud1.get(test)))){
					 vOrigen= llistaEstacions.get(i).getId_est();
					 
					for(int j=0;j<v.size() && trobat == false;j++) {
						if(llistaEstacions.get(j).getLatitud()==(testLatitud2.get(test))&&
								(llistaEstacions.get(j).getLongitud()==(testLongitud2.get(test)))){
							vDesti= llistaEstacions.get(j).getId_est();
							long startTime = System.nanoTime();
							System.out.println("Ruta "+ruta+"--> "+graf.camiOptim(vOrigen, vDesti,500));
							long endTime = System.nanoTime();
							System.out.println((endTime-startTime)/1e6 + " ms");
							test++;
							ruta++;
							trobat=true;
						}
					}
				
				}
			}
		}
	}

	public static double distancia(double latitudX, double longitudX, double latitudY, double longitudY) {
		double dist = 0;
		double lat1, lat2, lon1, lon2, incLat, incLon, a, r;

		lat1 = latitudX * Math.PI / 180;
		lat2 = latitudY * Math.PI / 180;
		lon1 = longitudX * Math.PI / 180;
		lon2 = longitudY * Math.PI / 180;

		incLat = lat2 - lat1;
		incLon = lon2 - lon1;

		a = Math.sin(incLat / 2) * Math.sin(incLat / 2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.sin(incLon / 2) * Math.sin(incLon / 2);

		r = 6378.137;
		dist = r * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (dist);
	}

	
	private static void parseNodeObj(JSONObject node) {
		String id = (String) node.get("id");
		System.out.println("Id: " + id);
		String id_estacio = (String) node.get("id_estacio");
		System.out.println("Id_estacio: " + id_estacio);
		String nom = (String) node.get("nom");
		System.out.println("Nom: " + nom);
		String data = (String) node.get("data");
		System.out.println("Data: " + data);
		String consum = (String) node.get("consum");
		System.out.println("Consum: " + consum);
		String carrer = (String) node.get("carrer");
		System.out.println("Carrer: " + carrer);
		String ciutat = (String) node.get("ciutat");
		System.out.println("Ciutat: " + ciutat);
		String estat = (String) node.get("estat");
		System.out.println("Estat: " + estat);
		String temps = (String) node.get("temps");
		System.out.println("Temps: " + temps);
		String potencia = (String) node.get("potencia");
		double pot;
		if (potencia.equalsIgnoreCase("")) { // mirar si la potencia es buida al fitxer json
			pot = 0.0;
		} else {
			pot = Double.parseDouble(potencia);
		}
		System.out.println("Potencia: " + potencia);
		String tipus = (String) node.get("tipus");
		System.out.println("Tipus: " + tipus);
		String latitud = (String) node.get("latitud");
		double lat = Double.parseDouble(latitud);
		System.out.println("Latitud: " + latitud);
		String longitud = (String) node.get("longitud");
		double lon = Double.parseDouble(longitud);
		System.out.println("Longitud: " + longitud);

		NodeEndolls nod = new NodeEndolls(id, id_estacio, nom, data, consum, carrer, ciutat, estat, temps, tipus, lat,
				lon, pot);
		int i = 0;
		boolean insertat = false;
		while (insertat == false && i < ultimaPos) {
			NodeEndolls temporal = n[i];
			if (temporal != null) {
				if ((temporal.getLatitud() == lat) && (temporal.getLongitud() == lon)) {

					while (temporal.getSeguent() != null) {
						temporal = temporal.getSeguent();
					}
					temporal.setSeguent(nod);
					insertat = true;
				} else {
					i++;
				}
			} else {
				n[i] = nod;
				ultimaPos++;
				insertat = true;
			}
		}
	}
}
