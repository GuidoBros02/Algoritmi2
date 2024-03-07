package upo.graph.impl;

public class AdjListDir extends AdjListDirWeight {
	
	public AdjListDir () 
	{
		super();
	}

	@Override
    public void setEdgeWeight(String vertex1, String vertex2, double weight) 
	{
        throw new UnsupportedOperationException("Metodo non disponibile per un grafo non pesato");
    }
	
	@Override
    public double getEdgeWeight(String vertex1, String vertex2) 
	{
        throw new UnsupportedOperationException("Metodo non disponibile per un grafo non pesato");
    }

}
