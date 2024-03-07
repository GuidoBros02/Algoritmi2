package upo.graph.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.HashSet;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import upo.graph.impl.*;
import upo.graph.base.*;

class AdjListDirWeightTest 
{
	
	AdjListDirWeight graph;
	
	@BeforeEach
	void setUp() 
	{
		graph= new AdjListDirWeight();
	}
	
	@Test
	void testGetEdgeWeight() 
	{
	    // Creazione del grafo
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addVertex("D");
	    graph.addEdge("A", "B");
	    graph.addEdge("A", "D");
	    graph.addEdge("B", "C");
	    graph.addEdge("C", "A");
	    graph.addEdge("C", "D");
	    graph.setEdgeWeight("A", "B", 4.7);
	    graph.setEdgeWeight("A", "D", 1.5);
	    graph.setEdgeWeight("B", "C", 1.0);
	    graph.setEdgeWeight("C", "A", 2.0);
	    graph.setEdgeWeight("C", "D", 1.5);

	    // Verifica dei pesi degli archi
	    assertEquals(4.7, graph.getEdgeWeight("A", "B"));
	    assertEquals(1.5, graph.getEdgeWeight("A", "D"));
	    assertEquals(1.0, graph.getEdgeWeight("B", "C"));
	    assertEquals(2.0, graph.getEdgeWeight("C", "A"));
	    assertEquals(1.5, graph.getEdgeWeight("C", "D"));

	    // Verifica della gestione di eccezioni
	    IllegalArgumentException exc1 = assertThrows(IllegalArgumentException.class, () -> { graph.getEdgeWeight("E", "D");});
	    assertEquals("Il vertice E o D non appartiene al grafo", exc1.getMessage());

	    IllegalArgumentException exc2 = assertThrows(IllegalArgumentException.class, () -> { graph.getEdgeWeight("A", "E"); });
	    assertEquals("Il vertice A o E non appartiene al grafo", exc2.getMessage());

	    NoSuchElementException exc3 = assertThrows(NoSuchElementException.class, () -> { graph.getEdgeWeight("B", "A");});
	    assertEquals("Non c'è un arco tra B e A", exc3.getMessage());
	}
	
	@Test
	void testAddEdge() 
	{
	    // Creazione del grafo
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addVertex("D");

	    // Aggiunta di archi
	    graph.addEdge("A", "B");
	    graph.addEdge("A", "D");
	    graph.addEdge("B", "C");

	    // Verifica degli archi aggiunti
	    assertTrue(graph.containsEdge("A", "B"));
	    assertTrue(graph.containsEdge("A", "D"));
	    assertTrue(graph.containsEdge("B", "C"));

	    // Verifica della gestione di eccezioni
	    IllegalArgumentException exc1 = assertThrows(IllegalArgumentException.class, () -> { graph.addEdge("A", "E"); });
	    assertEquals("Il vertice A o E non appartiene al grafo", exc1.getMessage());

	    IllegalArgumentException exc2 = assertThrows(IllegalArgumentException.class, () -> { graph.addEdge("E", "D");});
	    assertEquals("Il vertice E o D non appartiene al grafo", exc2.getMessage());
	}

	@Test
	void testContainsEdge() 
	{
	    // Creazione del grafo
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addEdge("A", "B");
	    graph.addEdge("B", "C");

	    // Verifica della presenza di archi
	    assertTrue(graph.containsEdge("A", "B"));
	    assertTrue(graph.containsEdge("B", "C"));

	    // Verifica della non presenza di archi
	    assertFalse(graph.containsEdge("A", "C"));
	    assertFalse(graph.containsEdge("C", "A"));

	    // Verifica della gestione di eccezioni
	    IllegalArgumentException exc1 = assertThrows(IllegalArgumentException.class, () -> { graph.containsEdge("A", "E"); });
	    assertEquals("Il vertice A o E non appartiene al grafo", exc1.getMessage());

	    IllegalArgumentException exc2 = assertThrows(IllegalArgumentException.class, () -> { graph.containsEdge("E", "D"); });
	    assertEquals("Il vertice E o D non appartiene al grafo", exc2.getMessage());
	}

	@Test
	void testGetVertex() 
	{
	    // Test funzionamento classico senza errori
	    AdjListDirWeight graph = new AdjListDirWeight();
	    assertEquals(graph.addVertex("A"), 0);
	    assertEquals(graph.addVertex("B"), 1);
	    assertEquals(graph.addVertex("C"), 2);

	    assertEquals(graph.size(), 3);

	    assertTrue(graph.containsVertex("A"));
	    assertTrue(graph.containsVertex("B"));
	    assertTrue(graph.containsVertex("C"));

	    assertEquals(graph.getVertexIndex("A"), 0);
	    assertEquals(graph.getVertexIndex("B"), 1);
	    assertEquals(graph.getVertexIndex("C"), 2);

	    assertEquals(graph.getVertexLabel(0), "A");
	    assertEquals(graph.getVertexLabel(1), "B");
	    assertEquals(graph.getVertexLabel(2), "C");

	    // Test per label == null
	    int index = graph.getVertexIndex(null);
	    assertEquals(-1, index);

	    // Verifica che il metodo restituisca -1 quando label non esiste
	    int nonExistentIndex = graph.getVertexIndex("NonEsiste");
	    assertEquals(-1, nonExistentIndex);

	    // Verifica il funzionamento di getVertexLabel con un indice valido
	    assertEquals(graph.getVertexLabel(0), "A");
	    assertEquals(graph.getVertexLabel(1), "B");
	    assertEquals(graph.getVertexLabel(2), "C");

	    // Test per index < 0
	    assertNull(graph.getVertexLabel(-1));

	    // Test per index >= vertexMap.size()
	    assertNull(graph.getVertexLabel(3));

	    // Verifica il funzionamento di addVertex con label esistente
	    assertEquals(graph.addVertex("A"), -1);
	}
	
	@Test
	void testRemoveVertex() 
	{
	    // Creazione di un grafo diretto pesato e aggiunta di vertici e archi
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addEdge("A", "B");
	    graph.addEdge("B", "C");
	    graph.addEdge("C", "A");
	    
	    // Impostazione di pesi sugli archi
	    graph.setEdgeWeight("A", "B", 4.7);
	    graph.setEdgeWeight("B", "C", 1.0);
	    graph.setEdgeWeight("C", "A", 2.0);

	    // Rimozione del vertice "B"
	    graph.removeVertex("B");

	    // Verifica che il vertice "B" sia stato rimosso correttamente
	    assertFalse(graph.containsVertex("B"));

	    // Verifica che i vertici rimanenti siano "A" e "C" e mantengano l'ordine
	    assertEquals(graph.getVertexLabel(0), "A");
	    assertEquals(graph.getVertexLabel(1), "C");

	    // Verifica che il peso dell'arco rimanente ("C", "A") sia corretto
	    assertEquals(graph.getEdgeWeight("C", "A"), 2.0);

	    // Verifica che la rimozione di un vertice non presente generi un'eccezione NoSuchElementException
	    NoSuchElementException exc = assertThrows(NoSuchElementException.class, () -> { graph.removeVertex("D"); });
	    assertEquals(exc.getMessage(), "Vertice non trovato: D");
	}
	
	@Test
	void testRemoveEdge() 
	{
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addEdge("A", "B");
	    graph.addEdge("B", "C");
	    graph.addEdge("C", "A");

	    // Test rimozione di un arco esistente
	    graph.removeEdge("A", "B");
	    assertFalse(graph.containsEdge("A", "B"));

	    // Verifica della gestione di eccezioni per vertici non validi
	    IllegalArgumentException exc1 = assertThrows(IllegalArgumentException.class, () -> { graph.removeEdge("A", "D"); });
	    assertEquals("Il vertice A o D non appartiene al grafo", exc1.getMessage());

	    // Verifica della gestione di eccezioni per arco inesistente
	    NoSuchElementException exc2 = assertThrows(NoSuchElementException.class, () -> { graph.removeEdge("A", "B");});
	    assertEquals("Non c'è un arco tra A e B", exc2.getMessage());
	}

	@Test
	void testAdjacent() 
	{
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addEdge("A", "B");
	    graph.addEdge("B", "C");
	    graph.addEdge("A", "C");

	    // Verifica della gestione di eccezioni per vertice non presente
	    NoSuchElementException exc2 = assertThrows(NoSuchElementException.class, () -> { graph.getAdjacent("D"); });
	    assertEquals("Il vertice D non appartiene al grafo", exc2.getMessage());

	    Set<String> adj = new HashSet<String>();
	    adj.add("B");
	    adj.add("C");
	    assertEquals(adj, graph.getAdjacent("A"));
	    assertTrue(graph.isAdjacent("B", "A"));
	    assertTrue(graph.isAdjacent("C", "A"));

	    // Verifica della gestione di eccezioni per vertici non validi
	    IllegalArgumentException exc1 = assertThrows(IllegalArgumentException.class, () -> { graph.isAdjacent("A", "D"); });
	    assertEquals("I vertici forniti non appartengono al grafo.", exc1.getMessage());
	}
	
	@Test
	void testGetBFSTree() 
	{
	    // Creazione di un grafo diretto pesato e aggiunta di vertici e archi
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addEdge("A", "B");
	    graph.addEdge("B", "C");
	    graph.addEdge("A", "C");

	    // Verifica che la chiamata al metodo con un vertice non presente generi un'eccezione IllegalArgumentException
	    IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> { graph.getBFSTree("D"); });
	    assertEquals("Il vertice specificato non appartiene al grafo", exc.getMessage());

	    // Ottiene l'albero BFS dal vertice "A"
	    VisitForest visit = graph.getBFSTree("A");
	    
	    // Verifica che i genitori dei vertici siano corretti
	    String[] parents = new String[graph.size()];
	    String[] labels = {"A", "B", "C"};
	    int i = 0;
	    for (String u : labels) 
	    {
	        parents[i] = visit.getParent(u);
	        i += 1;
	    }
	    String[] result = {null, "A", "A"};
	    
	    assertArrayEquals(result, parents);
	}

	@Test
	void testGetDFSTree() 
	{
	    // Creazione di un grafo diretto pesato e aggiunta di vertici e archi
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addEdge("A", "B");
	    graph.addEdge("B", "C");
	    graph.addEdge("A", "C");

	    // Verifica che la chiamata al metodo con un vertice non presente generi un'eccezione IllegalArgumentException
	    IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {graph.getDFSTree("D");});
	    assertEquals("Il vertice specificato non appartiene al grafo", exc.getMessage());

	    // Ottiene l'albero DFS dal vertice "A"
	    VisitForest visit = graph.getDFSTree("A");

	    // Verifica che i genitori dei vertici siano corretti
	    String[] parents = new String[graph.size()];
	    String[] labels = {"A", "B", "C"};
	    int i = 0;
	    for (String u : labels) 
	    {
	        parents[i] = visit.getParent(u);
	        i += 1;
	    }

	    // Il vertice "C" potrebbe avere come genitore "A" o "B" a seconda dell'ordine di visita
	    assertTrue(parents[2].equals("A") || parents[2].equals("B"));

	    // Verifica che gli altri genitori siano corretti
	    assertNull(parents[0]);
	    assertEquals("A", parents[1]);
	}

	@Test
	void testGetDFSTOTForest() 
	{
	    // Creazione di un grafo diretto pesato e aggiunta di vertici e archi
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addEdge("A", "B");
	    graph.addEdge("B", "C");
	    graph.addEdge("A", "C");

	    // Verifica che la chiamata al metodo con un vertice non presente generi un'eccezione IllegalArgumentException
	    IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {graph.getDFSTOTForest("D");});
	    assertEquals("Il vertice specificato non appartiene al grafo", exc.getMessage());

	    // Ottiene l'albero DFS OT dal vertice "A"
	    VisitForest visit = graph.getDFSTOTForest("A");

	    // Verifica che i genitori dei vertici siano corretti
	    String[] parents = new String[graph.size()];
	    String[] labels = {"A", "B", "C"};
	    int i = 0;
	    for (String u : labels) {
	        parents[i] = visit.getParent(u);
	        i += 1;
	    }

	    // Il vertice "C" potrebbe avere come genitore "A" o "B" a seconda dell'ordine di visita
	    assertTrue(parents[2].equals("A") || parents[2].equals("B"));

	    // Verifica che gli altri genitori siano corretti
	    assertNull(parents[0]); 
	    assertEquals("A", parents[1]);
	}
	
	@Test
	void testGetDFSTOTForestVertex() 
	{
	    // Creazione di un grafo diretto pesato e aggiunta di vertici e archi
	    AdjListDirWeight graph2 = new AdjListDirWeight();
	    graph2.addVertex("A");
	    graph2.addVertex("B");
	    graph2.addVertex("C");
	    graph2.addEdge("A", "B");
	    graph2.addEdge("B", "C");

	    // Ottiene l'albero DFS OT dai vertici di partenza "A", "B", "C"
	    VisitForest visit2 = graph2.getDFSTOTForest(new String[]{"A", "B", "C"});
	    
	    // Verifica che i genitori dei vertici siano corretti
	    String[] parents2 = new String[graph2.size()];
	    String[] labels2 = {"A", "B", "C"};
	    int i2 = 0;
	    for (String u : labels2) {
	        parents2[i2] = visit2.getParent(u);
	        i2 += 1;
	    }

	    // Verifica che i genitori siano corretti per i vertici specificati
	    assertNull(parents2[0]); // Il vertice "A" è la radice, quindi non ha genitori
	    assertEquals("A", parents2[1]); // Il vertice "B" ha come genitore "A"
	    assertEquals("B", parents2[2]); // Il vertice "C" ha come genitore "B"
	}

	@Test
    void testEquals() {
        // Creiamo due istanze della classe AdjListDirWeight con gli stessi valori
        AdjListDirWeight graph1 = new AdjListDirWeight();
        graph1.addVertex("A");
        graph1.addVertex("B");
        graph1.addEdge("A", "B");
        graph1.setEdgeWeight("A", "B", 3.0);
        
        AdjListDirWeight graph2 = new AdjListDirWeight();
        graph2.addVertex("A");
        graph2.addVertex("B");
        graph2.addEdge("A", "B");
        graph2.setEdgeWeight("A", "B", 3.0);
        
        // Verifichiamo che le due istanze siano uguali
        assertTrue(graph1.equals(graph2));
        
        AdjListDirWeight graph3 = new AdjListDirWeight();
        assertTrue(graph3.equals(graph3));
	}
	
	@Test
	void testIsCyclic() 
	{
	    // Creazione di un grafo diretto pesato aciclico
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addEdge("A", "B");
	    graph.addEdge("B", "C");
	    
	    // Verifica che il grafo non sia ciclico
	    assertFalse(graph.isCyclic());
	    
	    // Creazione di un grafo diretto pesato ciclico
	    AdjListDirWeight graph2 = new AdjListDirWeight();
	    graph2.addVertex("A");
	    graph2.addVertex("B");
	    graph2.addVertex("C");
	    graph2.addVertex("D");
	    graph2.addVertex("E");
	    graph2.addVertex("F");
	    graph2.addEdge("A", "F");
	    graph2.addEdge("B", "A");
	    graph2.addEdge("D", "B");
	    graph2.addEdge("E", "C");
	    graph2.addEdge("D", "C");
	    graph2.addEdge("E", "F");
	    
	    // Verifica che il grafo sia ciclico
	    assertTrue(graph2.isCyclic());
	}

	@Test
	void testIsDAG() 
	{
	    // Creazione di un grafo diretto pesato aciclico
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addEdge("A", "B");
	    graph.addEdge("B", "C");
	    
	    // Verifica che il grafo sia un DAG
	    assertTrue(graph.isDAG());
	    
	    // Creazione di un grafo diretto pesato ciclico
	    AdjListDirWeight graph2 = new AdjListDirWeight();
	    graph2.addVertex("A");
	    graph2.addVertex("B");
	    graph2.addVertex("C");
	    graph2.addVertex("D");
	    graph2.addVertex("E");
	    graph2.addVertex("F");
	    graph2.addEdge("A", "F");
	    graph2.addEdge("B", "A");
	    graph2.addEdge("D", "B");
	    graph2.addEdge("E", "C");
	    graph2.addEdge("D", "C");
	    graph2.addEdge("E", "F");
	    
	    // Verifica che il grafo non sia un DAG
	    assertFalse(graph2.isDAG());
	}

	@Test
	void testIsDirected() 
	{
	    // Creazione di un grafo diretto
	    AdjListDirWeight directedGraph = new AdjListDirWeight();
	    
	    // Verifica che il grafo sia diretto
	    assertTrue(directedGraph.isDirected());
	}

	@Test
	void testStronglyConnectedComponents() 
	{
	    // Creazione di un grafo orientato aciclico diretto (DAG) per il test
	    AdjListDirWeight graph = new AdjListDirWeight();
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addVertex("D");
	    graph.addEdge("A", "B");
	    graph.addEdge("B", "C");
	    graph.addEdge("C", "D");

	    // Verifica se il grafo è un DAG
	    assertTrue(graph.isDAG());

	    // Verifica che il metodo non sollevi un'eccezione su un DAG
	    assertDoesNotThrow(() -> graph.stronglyConnectedComponents());
	}
	
	@Test
    void testGetBellmanFordShortestPaths() 
	{
        // Grafo originale
	    AdjListDirWeight graph = new AdjListDirWeight();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addEdge("A", "C");
        graph.addEdge("A", "D");
        graph.addEdge("A", "E");
        graph.addEdge("D", "E");
        graph.addEdge("E", "C");
        graph.addEdge("D", "B");
        graph.addEdge("C", "B");
        graph.setEdgeWeight("A", "C", 3.0);
        graph.setEdgeWeight("A", "D", 5.0);
        graph.setEdgeWeight("A", "E", 2.0);
        graph.setEdgeWeight("D", "E", -6.0);
        graph.setEdgeWeight("E", "C", 0.0);
        graph.setEdgeWeight("D", "B", -3.0);
        graph.setEdgeWeight("C", "B", 1.0);
        
        // Grafo atteso
        AdjListDirWeight expectedGraph = new AdjListDirWeight(); // Modifica necessaria per utilizzare l'interfaccia Graph
        expectedGraph.addVertex("A");
        expectedGraph.addVertex("B");
        expectedGraph.addVertex("C");
        expectedGraph.addVertex("D");
        expectedGraph.addVertex("E");
        expectedGraph.addEdge("A", "C");
        expectedGraph.addEdge("A", "D");
        expectedGraph.addEdge("A", "E");
        expectedGraph.addEdge("D", "E");
        expectedGraph.addEdge("E", "C");
        expectedGraph.addEdge("D", "B");
        expectedGraph.addEdge("C", "B");
        expectedGraph.setEdgeWeight("A", "C", -1.0);
        expectedGraph.setEdgeWeight("A", "D", 5.0);
        expectedGraph.setEdgeWeight("A", "E", -1.0);
        expectedGraph.setEdgeWeight("D", "E", -6.0);
        expectedGraph.setEdgeWeight("E", "C", 0.0);
        expectedGraph.setEdgeWeight("D", "B", -5.0);
        expectedGraph.setEdgeWeight("C", "B", 1.0);

        // Verifica che il grafo restituito dal metodo sia uguale al grafo atteso
        assertTrue(graph.getBellmanFordShortestPaths("A").equals(expectedGraph));
	}
}