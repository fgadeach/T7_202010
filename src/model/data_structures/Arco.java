package model.data_structures;

public class Arco<K extends Comparable<K>, A>
{
	private K primerVertice;
	private K ultimoVertice;
	private Array<K> adj = new Array<K>();
	private A info;

	
	public Arco(K primerVert,K ultimoVert, A info)
	{
		primerVertice = primerVert;
		ultimoVertice = ultimoVert;
		this.info = info;
	}
	
	public K darPrimerVertice()
	{
		return primerVertice;
	}
	
	public K darUltimoVertice()
	{
		return ultimoVertice;
	}
	
	public Array<K> darAdj()
	{
		return adj;
	}

	public void agregarVerticeAdj(K idVertAdj)
	{
		adj.append(idVertAdj);
	}
	
	public A darInfo()
	{
		return info;
	}
	
	public void setInfo(A newInfo)
	{
		info = newInfo;
	}
	
}
