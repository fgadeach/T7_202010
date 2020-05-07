package model.data_structures;

import java.util.Iterator;


public class Grafo<K extends Comparable<K>, V, A> implements IGrafo<K,V,A>
{
	private int numeroVertices;
	private int numeroArcos;
	private SeparateChainingHashST<K, Vertice<K,V>> vertices;
	private SeparateChainingHashST<String, Arco<K,A>> arcos;

	private int cc;

	public Grafo(int n)
	{
		this.numeroVertices = n;
		this.numeroArcos = 0;
		this.vertices = new SeparateChainingHashST<>(10000);
		this.arcos = new SeparateChainingHashST<>(10000);
	}

	@Override
	/*
	 * Obtener el número de vértices
	 */
	public int V() 
	{
		return numeroVertices;
	}

	@Override
	/*
	 * Obtener el número de arcos.
	 */
	public int E() 
	{
		return numeroArcos;
	}


	public int cc()
	{  
		return cc;  
	}

	@Override
	/*
	 * Adiciona un vértice con un Id único. El vértice tiene la información InfoVertex
	 */
	public void addVertex(K id, V infoVertex) 
	{
		Vertice<K,V> vertice = new Vertice<>(id, infoVertex);

		vertices.put(id, vertice);
		numeroVertices++;
	}


	public void addEdge(K idVertIni, K idVertFin, A infoArc) throws Exception 
	{
		if(!vertices.contiene(idVertIni)||!vertices.contiene(idVertFin))
		{
			throw new Exception("No existe alguno de los vertices en el grafo");
		}

		Arco<K, A> nuevoArco = new Arco<K,A>(idVertIni, idVertFin, infoArc);
		String key = idVertIni+"-"+idVertFin;
		String keyInv = idVertFin+"-"+idVertIni;

		if(arcos.contiene(key)||arcos.contiene(keyInv))
		{

		}

		else
		{
			arcos.put(key, nuevoArco);
			numeroArcos++;
			Vertice<K,V> verticeIni = vertices.get(idVertIni);
			Vertice<K,V> verticeFin = vertices.get(idVertFin);

			verticeIni.agregarVerticeAdj(idVertFin);
			verticeFin.agregarVerticeAdj(idVertIni);
		}
	}
	@Override
	/*
	 * Obtener la información de un vértice. Si el vértice no existe retorna null.
	 */
	public V getInfoVertex(K idVertex) 
	{	
		Vertice<K,V> vertice = vertices.get(idVertex);

		return vertice.darinfo();
	}

	@Override
	/*
	 * Modificar la información del vértice idVertex
	 */
	public void setInfoVertex(K idVertex, V infoVertex) 
	{
		Vertice<K,V> vertice = vertices.get(idVertex);
		vertice.setinfo(infoVertex);
	}

	@Override

	/*
	 * Obtener el costo de un arco, si el arco no existe, retorna –1.0
	 */
	public double getCostArc(K idVertIni, K idVertFin) 
	{
		double info = -1.0;
		String key = idVertIni+"-"+idVertFin;
		String keyInv = idVertFin+"-"+idVertIni;

		if(arcos.contiene(key))
		{
			Arco<K,A> arc = arcos.get(key);
			info = (double) arc.darInfo();
		}

		else if(arcos.contiene(keyInv))
		{
			Arco<K,A> arc = arcos.get(keyInv);
			info = (double) arc.darInfo();
		}

		return info;
	}

	@Override
	/*
	 * Modificar el costo del arco No dirigido entre los vértices idVertexIni e idVertexFin
	 */
	public void setCostArc(K idVertIni, K idVertFin, A infoArc) 
	{
		String key = idVertIni+"-"+idVertFin;
		Arco<K,A> arc = arcos.get(key);
		arc.setInfo(infoArc);
	}

	/*
	 * Retorna los identificadores de los vértices adyacentes a idVertex
	 */
	@Override
	public Iterable<K> adj(K idVertex) 
	{
		Vertice<K,V> vertice = vertices.get(idVertex);
		return vertice.darAdj();
	}

	@Override
	public Iterator<K> iterarVertices() 
	{
		// TODO Auto-generated method stub
		return vertices.keys();
	}

	public Iterator<String> iterarArcos() 
	{
		return arcos.keys();
	}

	public Iterable<Arco<K,A>> arcos() 
	{
		Queue<Arco<K,A>> lista = new Queue<>();

		Iterator<String> iter = arcos.keys();
		while(iter.hasNext()) 
		{
			Arco<K,A> arco = arcos.get(iter.next());

			lista.enqueue(arco);
		}
		return lista;
	}

	public void dfs(K s)
	{
		Vertice vert = (Vertice) s;
		vert.marcar();
		cc++;

		for (K w : this.adj(s)) 
		{
			Vertice vertW = (Vertice) w;

			if (!vertW.marcado()) 
			{
				dfs(w);
			}
		}
	}
	
	
	public void uncheck() 
	{
		Iterator<K> iter = this.iterarVertices();

		while(iter.hasNext()) 
		{
			Vertice vert = (Vertice) iter.next();
			vert.desMarcar();			
		}
	}

	/*
	 * Obtiene los vértices alcanzados a partir del vértice idVertex después de la ejecución de los metodos dfs(K) y cc().
	 */
	public Iterable<K> getCC(K idVertex) 
	{
		dfs(idVertex);
		Vertice<K,V> vertice = vertices.get(idVertex);
		if(vertice.marcado()) 
		{
			return vertice.darAdj();
		}
		else 
		{
			return null;
		}
	}


}
