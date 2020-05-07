package model.data_structures;

import java.util.Iterator;

public interface IGrafo<K,V,A>
{
	public int V();
	public int E();
	public void addVertex(K id, V infoVertex);
	public void addEdge(K idVertIni , K idVertFin,A infoArc) throws Exception;
	public V getInfoVertex(K idVertex); 
	public void setInfoVertex(K idVertex, V infoVertex);
	public double getCostArc(K idVertIni, K idVertFin);
	public void setCostArc(K idVertIni, K idVertFin, A infoArc);
	public Iterable<K> adj(K idVertex);
	public Iterator<K> iterarVertices();
	public void uncheck();
	public void dfs(K s);
	public int cc();
	public Iterable<K> getCC(K idVertex);
}
