package upo.graph.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import upo.graph.impl.*;

class AdjListDirTest {

	AdjListDir graph;
	
	@BeforeEach
	void setUp() {
		graph= new AdjListDir();
	}
	
	@Test
	void testSetEdgeWeight() 
	{
		UnsupportedOperationException exc = assertThrows(UnsupportedOperationException.class, () -> 
		{
			graph.addVertex("A");
			graph.addVertex("B");
			graph.addEdge("A", "B");
			graph.setEdgeWeight("A", "B", 0);
		});
		assertEquals("Metodo non disponibile per un grafo non pesato",exc.getMessage());
	
	}
	
	@Test
	void testGetEdgeWeight() 
	{
		UnsupportedOperationException exc = assertThrows(UnsupportedOperationException.class, () -> 
		{
			graph.addVertex("A");
			graph.addVertex("B");
			graph.addEdge("A", "B");
			graph.getEdgeWeight("A", "B");
		});
		assertEquals("Metodo non disponibile per un grafo non pesato",exc.getMessage());
	
	}
	
}
