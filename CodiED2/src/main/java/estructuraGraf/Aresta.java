package estructuraGraf;


public class Aresta<V, E> {

	 private Vertex<V, E> vertex1, vertex2;
	 private Pes<Double> pes;

    
	

    public Aresta(Vertex<V, E> vertex1, Vertex<V, E> vertex2, Pes<Double> p)
    {
    	if(vertex1.getEtiqueta().compareTo(vertex2.getEtiqueta()) <= 0)
	    {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
	    }
	else
	    {
		this.vertex1 = vertex2;
		this.vertex2 = vertex1;
	    }

     	this.pes = p;

    }
    
    /** 
     * Funció que retorna l'adjacent d'un vertex indicat per paràmetre
     *
     * @param v
     * @return el veï adjacent a partir de l'aresta
     **/
    public Vertex<V,E> getVeiDe(Vertex<V,E> v)
    {
	if (v.equals(this.vertex1))
	    return this.vertex2;
	else if( v.equals(this.vertex2))
	    return this.vertex1;
	else
	    return null;
    }
    
    
    
    public String getVertexA()
    {
	return this.vertex1.getEtiqueta();
    }
    
    /**
     * @return el contingut del vertex 2
     **/
    public String getVertexB()
    {
	return this.vertex2.getEtiqueta();
    }
    
    
    
    
    /**
     * @return el contingut del vertex 1
     **/
    public Vertex<V,E> getVertex1()
    {
	return this.vertex1;
    }
    
    /**
     * @return el contingut del vertex 2
     **/
    public Vertex<V,E> getVertex2()
    {
	return this.vertex2;
    }

    /**
     * @return el valor del pes que conté la relació aresta
     **/
    public Pes<Double> getPes()
    {
	return this.pes;
    }

    /**
     * Funció set del pes
     * 
     * @param pes. Nou pes de l'aresta
     **/
    public void setPeso(Pes<Double> peso)
    {
	this.pes = peso;
    }

    /**
     * Funció per comprobar si dos arestes tenen el mateix pes 
     * 
     * @param aresta2. Aresta que es compararà amb altre aresta per veure si son iguals
     * @return int. Es retorna 0 en cas de que les dues arestes tinguin el mateix pes
     **/
    public int compareTo(Aresta<V,E> aresta2)
    {
    	int iguals=1;
    	String p1=this.pes.toString();
    	String p2=aresta2.pes.toString();
    	if(p1.equalsIgnoreCase(p2)) {
    		iguals=0;
    	}
    	
    return iguals;
    }

    /**
     * @return String. Retorna la relació que conté l'aresta amb els dos vertex
     **/
    public String toString()
    {
	return "({" + this.vertex1 + ", " + this.vertex2  + "}, "+ this.pes  +")";
    }


    /**
     * @return int. Hash_code per saber la posició de l'aresta a la taula de hash
     **/
    public int hashCode()
    {
	return (vertex1.getEtiqueta() + vertex2.getEtiqueta()).hashCode();
    }
    
    /**
     *	Funció per comprobar si dos arestas són iguals
     *
     * @param arest. Aresta en la que es vol fer la consulta
     * @return true -> si les dues arestes contenen la mateixa relació (mateixos vertex)
     **/
    public boolean equals(Aresta<V,E> arest)
    {
	if(!(arest instanceof Aresta))
	    return false;

	Aresta<V,E> arista2 = (Aresta<V,E>) arest;

	if(arista2.vertex1.equals(this.vertex1) && arista2.vertex2.equals(this.vertex2))
	    return true;

	return false;
    }

}
    
    
