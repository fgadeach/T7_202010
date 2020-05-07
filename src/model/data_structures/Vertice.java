package model.data_structures;

public class Vertice <K extends Comparable<K>, V>
{
	private K key;
	private V value;
	private Array<K> adj = new Array<K>();
	private boolean marked;
	
	public Vertice(K pKey, V pValue)
	{
		key = pKey;
		value = pValue;
		marked = false;
	}
	
	public K darKey()
	{
		return key;
	}
	
	
	public V darValue()
	{
		return value; 
	}
	
	public boolean marcado()
	{
		return !marked; 
	}


	public void setValue(V newValue)
	{
		value=newValue;
	}
	
	public Array<K> darAdj()
	{
		return adj;
	}
	
	public void agregarVerticeAdj(K idVertAdj)
	{
		adj.append(idVertAdj);
	}
	
	public void marcar() {
		marked = true;
	}
	
	public void desMarcar() {
		marked = false;
	}
}
