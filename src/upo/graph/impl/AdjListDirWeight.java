package upo.graph.impl;

import upo.graph.base.*;
import upo.graph.base.VisitForest.Color;
import upo.graph.base.VisitForest.VisitType;

import java.util.*;

public class AdjListDirWeight implements WeightedGraph 
{
	private Map<String, Vertex> vertexMap;
	private boolean directed;
	
    public AdjListDirWeight() 
    {
        vertexMap = new HashMap<>();
        directed = true;
    }
    
    /**
     * Classe interna che rappresenta un vertice nel grafo orientato pesato.
     * Ogni vertice ha un'etichetta, una mappa dei vertici adiacenti con i pesi degli archi corrispondenti,
     * e metodi per manipolare e recuperare informazioni sui vertici adiacenti.
     * 
     * @author Guido
     */
    private class Vertex 
    {
        private String label;
        private Map<Vertex, Double> adjacentVertices;

        /**
         * Costruisce un nuovo vertice con l'etichetta fornita.
         * 
         * @param label L'etichetta del vertice.
         */
        public Vertex(String label) 
        {
            this.label = label;
            this.adjacentVertices = new HashMap<>();
        }

        /**
         * Recupera l'etichetta del vertice.
         * 
         * @return L'etichetta del vertice.
         */
        public String getLabel() 
        {
            return label;
        }

        /**
         * Aggiunge un vertice adiacente con il peso specificato al vertice corrente.
         * 
         * @param vertex Il vertice adiacente da aggiungere.
         * @param weight Il peso dell'arco che collega il vertice corrente al vertice adiacente.
         */
        public void addAdjacentVertex(Vertex vertex, double weight) 
        {
            adjacentVertices.put(vertex, weight);
        }

        /**
         * Verifica se il vertice fornito è adiacente al vertice corrente.
         * 
         * @param vertex Il vertice da verificare per l'adiacenza.
         * @return True se i vertici sono adiacenti, false altrimenti.
         */
        public boolean isAdjacent(Vertex vertex) 
        {
            return adjacentVertices.containsKey(vertex);
        }

        /**
         * Recupera il peso dell'arco che collega il vertice corrente al vertice adiacente specificato.
         * 
         * @param vertex Il vertice adiacente.
         * @return Il peso dell'arco.
         */
        public double getEdgeWeight(Vertex vertex) 
        {
            return adjacentVertices.get(vertex);
        }

        /**
         * Rimuove il vertice adiacente specificato dal vertice corrente.
         * 
         * @param vertex Il vertice adiacente da rimuovere.
         */
        public void removeAdjacentVertex(Vertex vertex) 
        {
            adjacentVertices.remove(vertex);
        }

        /**
         * Recupera un insieme non modificabile di tutti i vertici adiacenti al vertice corrente.
         * 
         * @return Un insieme non modificabile di vertici adiacenti.
         */
        public Set<Vertex> getAdjacentVertices() 
        {
            return Collections.unmodifiableSet(adjacentVertices.keySet());
        }
        
        /**
         * Confronta questo vertice con un altro oggetto per determinare se sono uguali.
         * Due vertici sono considerati uguali se hanno lo stesso label.
         *
         * @param obj l'oggetto da confrontare con questo vertice
         * @return true se l'oggetto passato è uguale a questo vertice, false altrimenti
         */
        @Override
        public boolean equals(Object obj) 
        {
            if (this == obj) 
            {
                return true;
            }
            if (!(obj instanceof Vertex)) 
            {
                return false;
            }
            Vertex other = (Vertex) obj;
            return Objects.equals(label, other.label);
        }

    }

    /**
     * Restituisce l'indice del vertice associato all'etichetta specificata nel grafo.
     * Se l'etichetta è null o il vertice non è presente nel grafo, il metodo restituisce -1.
     * L'indice rappresenta la posizione del vertice in una lista ordinata di vertici.
     * 
     * @param label L'etichetta del vertice di cui si desidera ottenere l'indice.
     * @return L'indice del vertice o -1 se l'etichetta è null o il vertice non è presente.
     */
    @Override
    public int getVertexIndex(String label) 
    {
        if (label == null) 
        {
            return -1;
        }

        Vertex vertex = vertexMap.get(label);
        if (vertex != null) 
        {
            List<Vertex> vertices = new ArrayList<>(vertexMap.values());
            return vertices.indexOf(vertex);
        } 
        else 
        {
            return -1;
        }
    }

    /**
     * Restituisce l'etichetta del vertice associato all'indice specificato nel grafo.
     * Se l'indice è null, negativo o oltre la dimensione del grafo, il metodo restituisce null.
     * L'etichetta rappresenta l'identificatore univoco del vertice.
     * 
     * @param index L'indice del vertice di cui si desidera ottenere l'etichetta.
     * @return L'etichetta del vertice o null se l'indice è null, negativo o oltre la dimensione del grafo.
     */
    @Override
    public String getVertexLabel(Integer index) 
    {
        if (index == null || index < 0 || index >= vertexMap.size()) 
        {
            return null;
        }

        List<Vertex> vertices = new ArrayList<>(vertexMap.values());
        return vertices.get(index).getLabel();
    }

    /**
     * Aggiunge un nuovo vertice al grafo con l'etichetta specificata.
     * Se il grafo già contiene un vertice con la stessa etichetta, il vertice non viene aggiunto e il metodo restituisce -1.
     * Dopo l'aggiunta del vertice, il metodo restituisce l'indice del vertice appena aggiunto nel grafo.
     * L'indice rappresenta la posizione del vertice nella lista ordinata dei vertici.
     * 
     * @param label L'etichetta del nuovo vertice da aggiungere al grafo.
     * @return L'indice del vertice appena aggiunto o -1 se un vertice con la stessa etichetta è già presente nel grafo.
     */
    @Override
    public int addVertex(String label) 
    {
        if (!vertexMap.containsKey(label)) 
        {
            Vertex newVertex = new Vertex(label);
            vertexMap.put(label, newVertex);
            return new ArrayList<>(vertexMap.values()).indexOf(newVertex);
        }
        return -1;
    }

    /**
     * Verifica se il grafo contiene un vertice con l'etichetta specificata.
     * 
     * @param label L'etichetta del vertice da cercare nel grafo.
     * @return true se il vertice è presente nel grafo, altrimenti false.
     */
    @Override
    public boolean containsVertex(String label) 
    {
        return vertexMap.containsKey(label);
    }

    /**
     * Rimuove un vertice dal grafo insieme a tutti gli archi associati a quel vertice.
     * Se il vertice non è presente nel grafo, solleva un'eccezione NoSuchElementException.
     * 
     * @param label L'etichetta del vertice da rimuovere dal grafo.
     * @throws NoSuchElementException Se il vertice specificato non è presente nel grafo.
     */
    @Override
    public void removeVertex(String label) throws NoSuchElementException 
    {
        Vertex vertexToRemove = vertexMap.get(label);
        if (vertexToRemove == null) 
        {
            throw new NoSuchElementException("Vertice non trovato: " + label);
        }
        vertexMap.remove(label);

        for (Vertex vertex : vertexMap.values()) 
        {
            if (vertex.isAdjacent(vertexToRemove)) 
            {
                vertex.removeAdjacentVertex(vertexToRemove);
            }
        }
    }

    /**
     * Aggiunge un arco non pesato al grafo tra il vertice di origine e il vertice di destinazione.
     * Se uno o entrambi i vertici non sono presenti nel grafo, solleva un'eccezione IllegalArgumentException.
     * 
     * @param sourceVertex L'etichetta del vertice di origine.
     * @param targetVertex L'etichetta del vertice di destinazione.
     * @throws IllegalArgumentException Se uno o entrambi i vertici non appartengono al grafo.
     */
    @Override
    public void addEdge(String sourceVertex, String targetVertex) throws IllegalArgumentException 
    {
        Vertex source = vertexMap.get(sourceVertex);
        Vertex target = vertexMap.get(targetVertex);

        if (source == null || target == null) 
        {
            throw new IllegalArgumentException("Il vertice " + sourceVertex + " o " + targetVertex + " non appartiene al grafo");
        }
        source.addAdjacentVertex(target, WeightedGraph.defaultEdgeWeight);
    }

    /**
     * Verifica se esiste un arco tra il vertice di origine e il vertice di destinazione nel grafo.
     * Se uno o entrambi i vertici non sono presenti nel grafo, solleva un'eccezione IllegalArgumentException.
     * 
     * @param sourceVertex L'etichetta del vertice di origine.
     * @param targetVertex L'etichetta del vertice di destinazione.
     * @return true se esiste un arco tra i vertici specificati, altrimenti false.
     * @throws IllegalArgumentException Se uno o entrambi i vertici non appartengono al grafo.
     */
    @Override
    public boolean containsEdge(String sourceVertex, String targetVertex) throws IllegalArgumentException 
    {
        Vertex source = vertexMap.get(sourceVertex);
        Vertex target = vertexMap.get(targetVertex);

        if (source == null || target == null) 
        {
            throw new IllegalArgumentException("Il vertice " + sourceVertex + " o " + targetVertex + " non appartiene al grafo");
        }
        return source.isAdjacent(target);
    }

    /**
     * Rimuove l'arco tra il vertice di origine e il vertice di destinazione nel grafo.
     * Se uno o entrambi i vertici non sono presenti nel grafo, solleva un'eccezione IllegalArgumentException.
     * Se non c'è un arco tra i vertici specificati, solleva un'eccezione NoSuchElementException.
     * 
     * @param sourceVertex L'etichetta del vertice di origine.
     * @param targetVertex L'etichetta del vertice di destinazione.
     * @throws IllegalArgumentException Se uno o entrambi i vertici non appartengono al grafo.
     * @throws NoSuchElementException Se non c'è un arco tra i vertici specificati.
     */
    @Override
    public void removeEdge(String sourceVertex, String targetVertex) 
    {
        Vertex source = vertexMap.get(sourceVertex);
        Vertex target = vertexMap.get(targetVertex);

        if (source == null || target == null) 
        {
            throw new IllegalArgumentException("Il vertice " + sourceVertex + " o " + targetVertex + " non appartiene al grafo");
        }
        if (!source.isAdjacent(target)) 
        {
            throw new NoSuchElementException("Non c'è un arco tra " + sourceVertex + " e " + targetVertex);
        }

        source.removeAdjacentVertex(target);
    }

    /**
     * Restituisce un insieme di etichette dei vertici adiacenti al vertice specificato nel grafo.
     * Se il vertice specificato non è presente nel grafo, solleva un'eccezione NoSuchElementException.
     * 
     * @param vertex L'etichetta del vertice di cui ottenere i vertici adiacenti.
     * @return Un insieme di etichette dei vertici adiacenti al vertice specificato.
     * @throws NoSuchElementException Se il vertice specificato non appartiene al grafo.
     */
    @Override
    public Set<String> getAdjacent(String vertex) throws NoSuchElementException 
    {
        Vertex source = vertexMap.get(vertex);

        if (source == null) 
        {
            throw new NoSuchElementException("Il vertice " + vertex + " non appartiene al grafo");
        }

        Set<String> adjacentVertices = new HashSet<>();
        for (Vertex adjacent : source.getAdjacentVertices()) 
        {
            adjacentVertices.add(adjacent.getLabel());
        }
        return adjacentVertices;
    }

    /**
     * Verifica se esiste un arco diretto dal vertice sorgente al vertice destinatario nel grafo.
     * Restituisce true se esiste un arco tra i vertici specificati, altrimenti restituisce false.
     * Se uno o entrambi i vertici non sono presenti nel grafo, solleva un'eccezione IllegalArgumentException.
     * 
     * @param targetVertex L'etichetta del vertice destinatario.
     * @param sourceVertex L'etichetta del vertice sorgente.
     * @return True se esiste un arco diretto tra il vertice sorgente e il vertice destinatario, altrimenti false.
     * @throws IllegalArgumentException Se uno o entrambi i vertici non appartengono al grafo.
     */
    @Override
    public boolean isAdjacent(String targetVertex, String sourceVertex) throws IllegalArgumentException 
    {
        Vertex source = vertexMap.get(sourceVertex);
        Vertex target = vertexMap.get(targetVertex);

        if (source != null && target != null) 
        {
            return source.isAdjacent(target);
        } 
        else 
        {
            throw new IllegalArgumentException("I vertici forniti non appartengono al grafo.");
        }
    }

    /**
     * Restituisce il numero totale di vertici presenti nel grafo.
     * 
     * @return Il numero totale di vertici nel grafo.
     */
    @Override
    public int size() 
    {
        return vertexMap.size();
    }

    /**
     * Verifica se l'oggetto corrente è uguale a un altro oggetto.
     *
     * @param obj l'oggetto da confrontare con l'istanza corrente
     * @return true se i due oggetti sono uguali, false altrimenti
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
        {
            return true;
        }
        if (!(obj instanceof AdjListDirWeight)) 
        {
            return false;
        }
        AdjListDirWeight other = (AdjListDirWeight) obj;
        return Objects.equals(vertexMap, other.vertexMap);
    }
    
    /**
     * Verifica se il grafo è orientato (diretto). Restituisce sempre true per un grafo diretto.
     * 
     * @return True se il grafo è orientato (diretto), altrimenti false.
     */
    @Override
    public boolean isDirected() 
    {
        return true;
    }

    /**
     * Verifica se il grafo contiene cicli utilizzando una ricerca in profondità (DFS).
     * Restituisce true se il grafo contiene almeno un ciclo, altrimenti restituisce false.
     * 
     * @return True se il grafo contiene cicli, altrimenti false.
     */
    @Override
    public boolean isCyclic() 
    {
        Set<Vertex> visited = new HashSet<>();
        for (Vertex vertex : vertexMap.values()) 
        {
            if (!visited.contains(vertex)) 
            {
                if (isCyclicUtil(vertex, visited, null)) 
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metodo di supporto per la verifica dei cicli nel grafo mediante una ricerca in profondità (DFS).
     * 
     * @param current Il vertice corrente in esame.
     * @param visited Insieme di vertici visitati durante la ricerca.
     * @param parent Il vertice genitore del vertice corrente.
     * @return True se il grafo contiene un ciclo, altrimenti false.
     */
    private boolean isCyclicUtil(Vertex current, Set<Vertex> visited, Vertex parent) 
    {
        visited.add(current);

        for (Vertex adjacent : current.adjacentVertices.keySet()) 
        {
            if (!visited.contains(adjacent)) 
            {
                if (isCyclicUtil(adjacent, visited, current)) 
                {
                    return true;
                }
            } 
            else if (!adjacent.equals(parent)) 
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se il grafo è un grafo aciclico diretto (DAG). Restituisce true se il grafo è un DAG,
     * altrimenti restituisce false. Un grafo aciclico diretto è un grafo diretto che non contiene cicli.
     * 
     * @return True se il grafo è un grafo aciclico diretto (DAG), altrimenti false.
     */
    @Override
    public boolean isDAG() 
    {
        return isDirected() && !isCyclic();
    }

    /**
     * Restituisce l'albero di visita in ampiezza (BFS) a partire dal vertice di partenza specificato.
     * L'albero di visita in ampiezza è costruito utilizzando una coda. Ogni vertice raggiunto durante la visita
     * è colorato di grigio quando scoperto, di nero quando completamente esplorato. 
     * 
     * @param startingVertex Il vertice di partenza per la visita in ampiezza.
     * @return Un oggetto {@code VisitForest} rappresentante l'albero di visita in ampiezza.
     * @throws UnsupportedOperationException Se l'operazione non è supportata dal grafo.
     * @throws IllegalArgumentException Se il vertice specificato non appartiene al grafo.
     */
    @Override
    public VisitForest getBFSTree(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException 
    {
        if (!vertexMap.containsKey(startingVertex)) 
        {
            throw new IllegalArgumentException("Il vertice specificato non appartiene al grafo");
        }

        // Inizializza l'oggetto VisitForest per la BFS
        VisitForest visitForest = new VisitForest(this, VisitType.BFS);
        Queue<String> queue = new LinkedList<>();

        // Inizia la BFS dal vertice di partenza
        visitForest.setColor(startingVertex, Color.GRAY);
        visitForest.setDistance(startingVertex, 0);
        visitForest.setParent(startingVertex, null); // Inizializza il genitore del vertice di partenza come null
        queue.add(startingVertex);

        while (!queue.isEmpty()) 
        {
            String u = queue.poll();
            Set<String> adj = getAdjacent(u);

            for (String v : adj) 
            {
                if (visitForest.getColor(v) == Color.WHITE) 
                {
                    visitForest.setColor(v, Color.GRAY);
                    visitForest.setParent(v, u);
                    visitForest.setDistance(v, visitForest.getDistance(u) + 1);
                    queue.add(v);
                }
            }

            visitForest.setColor(u, Color.BLACK);
        }

        return visitForest;
    }

    /**
     * Restituisce l'albero di visita in profondità (DFS) a partire dal vertice di partenza specificato.
     * L'albero di visita in profondità è costruito utilizzando uno stack. Ogni vertice raggiunto durante la visita
     * è colorato di grigio quando scoperto, di nero quando completamente esplorato. Inoltre, vengono registrati i tempi
     * di inizio e fine della visita di ciascun vertice.
     * 
     * @param startingVertex Il vertice di partenza per la visita in profondità.
     * @return Un oggetto {@code VisitForest} rappresentante l'albero di visita in profondità.
     * @throws UnsupportedOperationException Se l'operazione non è supportata dal grafo.
     * @throws IllegalArgumentException Se il vertice specificato non appartiene al grafo.
     */
    @Override
    public VisitForest getDFSTree(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException 
    {
        if (!vertexMap.containsKey(startingVertex)) 
        {
            throw new IllegalArgumentException("Il vertice specificato non appartiene al grafo");
        }

        // Inizializza l'oggetto VisitForest per la DFS
        VisitForest visitForest = new VisitForest(this, VisitType.DFS);
        Stack<String> stack = new Stack<>();

        int time = 0;
        visitForest.setColor(startingVertex, Color.GRAY);
        visitForest.setStartTime(startingVertex, time++);
        stack.push(startingVertex);

        while (!stack.isEmpty()) 
        {
            String u = stack.pop();
            Set<String> adj = getAdjacent(u);
            for (String v : adj) 
            {
                if (visitForest.getColor(v) == Color.WHITE) 
                {
                    visitForest.setColor(v, Color.GRAY);
                    visitForest.setParent(v, u);
                    visitForest.setStartTime(v, time++);
                    stack.push(v);
                }
            }
            visitForest.setColor(u, Color.BLACK);
            visitForest.setEndTime(u, time++);
        }

        return visitForest;
    }

    /**
     * Restituisce l'albero di visita in profondità totale (DFS-TOT) a partire dal vertice di partenza specificato.
     * L'albero di visita in profondità totale è costruito eseguendo la DFS da ogni vertice non ancora visitato.
     * Ogni vertice raggiunto durante la visita è colorato di grigio quando scoperto, di nero quando completamente esplorato.
     * 
     * @param startingVertex Il vertice di partenza per la visita in profondità totale.
     * @return Un oggetto {@code VisitForest} rappresentante l'albero di visita in profondità totale.
     * @throws UnsupportedOperationException Se l'operazione non è supportata dal grafo.
     * @throws IllegalArgumentException Se il vertice specificato non appartiene al grafo.
     */
    @Override
    public VisitForest getDFSTOTForest(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException 
    {
        if (!vertexMap.containsKey(startingVertex)) 
        {
            throw new IllegalArgumentException("Il vertice specificato non appartiene al grafo");
        }

        // Inizializza l'oggetto VisitForest per la DFS-TOT
        VisitForest visitForest = new VisitForest(this, VisitType.DFS_TOT);

        for (String u : vertexMap.keySet()) 
        {
            if (visitForest.getColor(u) == Color.WHITE) 
            {
            	// Inizializza il tempo di visita
            	int[] time = {0};
            	
            	// Esegue la DFS-TOT ricorsiva
            	recursiveDFS(visitForest, startingVertex, time);
            }
        }

        return visitForest;
    }

    /**
     * Metodo ricorsivo per eseguire la DFS-TOT e costruire l'albero di visita in profondità totale.
     * 
     * @param visitForest L'oggetto VisitForest associato alla DFS-TOT.
     * @param vertex Il vertice corrente della DFS-TOT.
     * @param time Un array contenente il tempo di visita.
     */
    private void recursiveDFS(VisitForest visitForest, String vertex, int[] time) 
    {
        visitForest.setColor(vertex, Color.GRAY);	    
        for (String v : getAdjacent(vertex)) 
        {
            if (visitForest.getColor(v) == Color.WHITE) 
            {
                visitForest.setParent(v, vertex);
                recursiveDFS(visitForest, v, time);
            }
        }

        visitForest.setColor(vertex, Color.BLACK);
    }

    /**
     * Restituisce l'albero di visita in profondità totale (DFS-TOT) a partire da un ordinamento specifico dei vertici.
     * L'ordinamento dei vertici determina l'ordine in cui vengono eseguite le visite DFS. 
     * L'albero di visita in profondità totale è costruito eseguendo la DFS da ogni vertice non ancora visitato secondo l'ordinamento fornito.
     * Ogni vertice raggiunto durante la visita è colorato di grigio quando scoperto, di nero quando completamente esplorato.
     * 
     * @param vertexOrdering Un array di stringhe che rappresenta l'ordinamento specifico dei vertici.
     * @return Un oggetto {@code VisitForest} rappresentante l'albero di visita in profondità totale.
     * @throws UnsupportedOperationException Se l'operazione non è supportata dal grafo.
     * @throws IllegalArgumentException Se l'ordinamento dei vertici non è valido o se uno dei vertici specificati non appartiene al grafo.
     */
    @Override
	public VisitForest getDFSTOTForest(String[] vertexOrdering) throws UnsupportedOperationException, IllegalArgumentException 
	{
	    if (vertexOrdering == null || vertexOrdering.length != vertexMap.size()) 
	    {
	        throw new IllegalArgumentException("L'ordinamento dei vertici non è valido");
	    }

	    VisitForest visitForest = new VisitForest(this, VisitType.DFS_TOT);
	    int[] time = {0};

	    for (String vertex : vertexOrdering) 
	    {
	        if (!vertexMap.containsKey(vertex)) 
	        {
	            throw new IllegalArgumentException("Il vertice " + vertex + " non appartiene al grafo");
	        }

	        if (visitForest.getColor(vertex) == Color.WHITE) 
	        {
	            recursiveDFS(visitForest, vertex, time);
	        }
	    }

	    return visitForest;
	}

    /**
     * Esegue un ordinamento topologico dei vertici del grafo diretto aciclico (DAG).
     * L'ordinamento topologico è un ordinamento lineare dei vertici tale che per ogni arco diretto (u, v), 
     * il vertice u appare prima di v nell'ordinamento.
     * 
     * @return Un array di stringhe rappresentante l'ordinamento topologico dei vertici del grafo.
     * @throws UnsupportedOperationException Se il grafo non è un DAG (grafo diretto aciclico).
     */
    @Override
    public String[] topologicalSort() throws UnsupportedOperationException 
    {
        if (!isDAG()) 
        {
            throw new UnsupportedOperationException("Il grafo non è un DAG");
        }

        // Inizializza una lista per contenere l'ordinamento topologico e uno stack per l'elaborazione
        List<String> result = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        // Esegue la DFS modificata per generare l'ordinamento topologico
        for (String vertex : vertexMap.keySet()) 
        {
            if (!visited.contains(vertex)) 
            {
                topologicalSortUtil(vertex, visited, stack);
            }
        }

        // Popola l'elenco result con gli elementi dello stack
        while (!stack.isEmpty()) 
        {
            result.add(stack.pop());
        }

        // Converte l'elenco in un array di stringhe e restituiscilo
        return result.toArray(new String[0]);
    }

    /**
     * Metodo ausiliario per l'ordinamento topologico.
     * 
     * @param vertex Il vertice corrente durante l'esplorazione.
     * @param visited Insieme di vertici già visitati durante la DFS.
     * @param stack Uno stack utilizzato per tenere traccia dell'ordinamento topologico.
     */
    private void topologicalSortUtil(String vertex, Set<String> visited, Stack<String> stack) 
    {
        visited.add(vertex);

        for (String adjacent : getAdjacent(vertex)) {
            if (!visited.contains(adjacent)) {
                topologicalSortUtil(adjacent, visited, stack);
            }
        }
        stack.push(vertex);
    }

    /**
     * Restituisce l'insieme delle componenti fortemente connesse (SCC) del grafo orientato.
     * Una componente fortemente connessa è un sottoinsieme massimale di vertici in cui esiste un percorso 
     * da ogni vertice a ogni altro vertice della componente.
     * 
     * @return Un insieme di insiemi di stringhe rappresentante le SCC del grafo orientato.
     * @throws UnsupportedOperationException Se il grafo non è orientato.
     */
    @Override
    public Set<Set<String>> stronglyConnectedComponents() throws UnsupportedOperationException 
    {
        // Verifica se il grafo è orientato
        if(!isDirected())
        {
            throw new UnsupportedOperationException("Il grafo non è orientato");
        }

        // Ottiene l'ordinamento topologico inverso dei vertici
        List<String> verticesInOrder = Arrays.asList(topologicalSort());
        Collections.reverse(verticesInOrder);
        
        // Inizializza l'insieme delle SCC e un insieme di vertici già visitati
        Set<Set<String>> sccSet = new HashSet<>();
        Set<String> visited = new HashSet<>();
        
        // Itera attraverso i vertici nell'ordinamento topologico inverso
        for (String vertex : verticesInOrder)
        {
            // Se il vertice non è già stato visitato
            if(!visited.contains(vertex))
            {
                // Inizializza un insieme per la nuova SCC e avvia la DFS
                Set<String> scc = new HashSet<>();
                dfsForSCC(vertex, visited, scc);
                
                // Aggiunge la SCC all'insieme delle SCC
                sccSet.add(scc);
            }
        }
        return sccSet;
    }

    /**
     * Metodo ausiliario per la ricerca delle SCC utilizzando la DFS.
     * 
     * @param vertex Il vertice corrente durante l'esplorazione.
     * @param visited Insieme di vertici già visitati durante la DFS.
     * @param scc L'insieme corrente della componente fortemente connessa.
     */
    private void dfsForSCC(String vertex, Set<String> visited, Set<String> scc)
    {
        // Segna il vertice come visitato e lo aggiunge alla SCC corrente
        visited.add(vertex);
        scc.add(vertex);
        
        // Itera attraverso i vertici adiacenti
        for(String adjacent : getAdjacent(vertex))
        {
            // Se il vertice adiacente non è già stato visitato, avvia la DFS ricorsiva
            if(!visited.contains(adjacent))
            {
                dfsForSCC(adjacent, visited, scc);
            }
        }
    }

    /**
     * Restituisce l'insieme delle componenti connesse del grafo. 
     * Poiché il grafo è orientato, questo metodo solleva sempre un'eccezione di UnsupportedOperationException.
     * 
     * @return Un insieme di insiemi di stringhe rappresentante le componenti connesse del grafo.
     * @throws UnsupportedOperationException Poiché il grafo è orientato, l'operazione non è supportata.
     */
    @Override
    public Set<Set<String>> connectedComponents() throws UnsupportedOperationException 
    {
        throw new UnsupportedOperationException("Un grafo orientato non supporta l'operazione");
    }

    /**
     * Restituisce il peso dell'arco tra due vertici specificati nel grafo.
     * 
     * @param sourceVertex Il vertice di origine dell'arco.
     * @param targetVertex Il vertice di destinazione dell'arco.
     * @return Il peso dell'arco tra i vertici specificati.
     * @throws IllegalArgumentException Se uno dei vertici specificati non appartiene al grafo.
     * @throws NoSuchElementException Se non c'è un arco tra i vertici specificati.
     */
    @Override
    public double getEdgeWeight(String sourceVertex, String targetVertex) throws IllegalArgumentException, NoSuchElementException 
    {
        Vertex source = vertexMap.get(sourceVertex);
        Vertex target = vertexMap.get(targetVertex);

        // Verifica se entrambi i vertici sono presenti nel grafo
        if (source == null || target == null) 
        {
            throw new IllegalArgumentException("Il vertice " + sourceVertex + " o " + targetVertex + " non appartiene al grafo");
        }

        // Verifica se esiste un arco tra i vertici specificati
        if (!source.isAdjacent(target)) 
        {
            throw new NoSuchElementException("Non c'è un arco tra " + sourceVertex + " e " + targetVertex);
        }

        // Restituisce il peso dell'arco
        return source.getEdgeWeight(target);
    }

    /**
     * Imposta il peso dell'arco tra due vertici specificati nel grafo.
     * 
     * @param sourceVertex Il vertice di origine dell'arco.
     * @param targetVertex Il vertice di destinazione dell'arco.
     * @param weight Il nuovo peso dell'arco.
     * @throws IllegalArgumentException Se uno dei vertici specificati non appartiene al grafo.
     * @throws NoSuchElementException Se non c'è un arco tra i vertici specificati.
     */
    @Override
    public void setEdgeWeight(String sourceVertex, String targetVertex, double weight) throws IllegalArgumentException, NoSuchElementException 
    {
        Vertex source = vertexMap.get(sourceVertex);
        Vertex target = vertexMap.get(targetVertex);

        // Verifica se entrambi i vertici sono presenti nel grafo
        if (source == null || target == null) 
        {
            throw new IllegalArgumentException("Il vertice " + sourceVertex + " o " + targetVertex + " non appartiene al grafo");
        }

        // Verifica se esiste un arco tra i vertici specificati
        if (!source.isAdjacent(target)) 
        {
            throw new NoSuchElementException("Non c'è un arco tra " + sourceVertex + " e " + targetVertex);
        }

        // Imposta il nuovo peso dell'arco
        source.adjacentVertices.put(target, weight);
    }

    /**
     * Calcola i cammini minimi da un vertice di partenza a tutti gli altri vertici nel grafo, utilizzando l'algoritmo di Bellman-Ford.
     * 
     * @param startingVertex il vertice di partenza da cui calcolare i cammini minimi
     * @return un nuovo grafo pesato che rappresenta i cammini minimi dal vertice di partenza a tutti gli altri vertici nel grafo
     * @throws UnsupportedOperationException se il grafo contiene un ciclo negativo
     * @throws IllegalArgumentException se il vertice di partenza specificato non appartiene al grafo
     */
    @Override
    public WeightedGraph getBellmanFordShortestPaths(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
        // Verifica se il vertice di partenza è valido
        if (!vertexMap.containsKey(startingVertex)) {
            throw new IllegalArgumentException("Il vertice specificato non appartiene al grafo");
        }

        VisitForest visit = new VisitForest(this, VisitType.DFS_TOT);
        for (String u : vertexMap.keySet()) {
            visit.setDistance(u, Double.POSITIVE_INFINITY);
        }
        visit.setDistance(startingVertex, 0);

        // Esegue il rilassamento degli archi per V-1 volte
        int V = vertexMap.size();
        for (int i = 1; i <= V - 1; i++) {
            for (String u : vertexMap.keySet()) {
                for (String v : vertexMap.keySet()) {
                    if (!v.equals(u) && this.containsEdge(u, v) && visit.getDistance(v) > visit.getDistance(u) + this.getEdgeWeight(u, v)) {
                        visit.setParent(v, u);
                        visit.setDistance(v, visit.getDistance(u) + this.getEdgeWeight(u, v));
                    }
                }
            }
        }

        // Controlla la presenza di cicli negativi
        for (String u : vertexMap.keySet()) {
            for (String v : vertexMap.keySet()) {
                if (!v.equals(u) && this.containsEdge(u, v) && visit.getDistance(v) > visit.getDistance(u) + this.getEdgeWeight(u, v)) {
                    throw new UnsupportedOperationException("Il grafo contiene un ciclo negativo");
                }
            }
        }

        // Costruisce il grafo dei cammini minimi
        WeightedGraph shortestPathsGraph = new AdjListDirWeight();
        createBFGraph(shortestPathsGraph, visit);

        return shortestPathsGraph;
    }

    /**
     * Crea un grafo dei cammini minimi utilizzando l'algoritmo di Bellman-Ford.
     * Aggiunge i vertici al grafo fornito e gli archi con i relativi pesi calcolati
     * in base alle distanze calcolate dall'algoritmo di Bellman-Ford.
     *
     * @param graph il grafo dei cammini minimi da popolare
     * @param visit l'albero di visita generato dall'algoritmo di Bellman-Ford
     */
    private void createBFGraph(WeightedGraph graph, VisitForest visit) 
    {
        for (String v : vertexMap.keySet()) 
        {
            graph.addVertex(v);
        }

        for (String u : vertexMap.keySet()) 
        {
            for (String v : vertexMap.keySet()) 
            {
                if (this.containsEdge(u, v) && !v.equals(u)) 
                {
                    double weight = visit.getDistance(v) - visit.getDistance(u);
                    graph.addEdge(u, v);
                    graph.setEdgeWeight(u, v, weight);
                }
            }
        }
    }

    /**
     * Restituisce un grafo pesato contenente i cammini minimi calcolati utilizzando l'algoritmo di Dijkstra.
     * 
     * @param startingVertex Il vertice di partenza per il calcolo dei cammini minimi.
     * @return Un grafo pesato con i cammini minimi calcolati.
     * @throws UnsupportedOperationException Poiché il metodo non è implementato.
     * @throws IllegalArgumentException Se il vertice specificato non appartiene al grafo.
     */
    @Override
    public WeightedGraph getDijkstraShortestPaths(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException 
    {
        throw new UnsupportedOperationException("Metodo non implementabile");
    }

    /**
     * Restituisce un albero ricoprente minimo (Minimum Spanning Tree, MST) utilizzando l'algoritmo di Prim a partire dal vertice specificato.
     * 
     * @param startingVertex Il vertice di partenza per l'algoritmo di Prim.
     * @return Un grafo pesato rappresentante l'albero ricoprente minimo.
     * @throws UnsupportedOperationException Poiché il metodo non è implementato.
     * @throws IllegalArgumentException Se il vertice specificato non appartiene al grafo.
     */
    @Override
    public WeightedGraph getPrimMST(String startingVertex)throws UnsupportedOperationException, IllegalArgumentException 
    {
        throw new UnsupportedOperationException("Metodo non implementabile");
    }

    /**
     * Restituisce un albero ricoprente minimo (Minimum Spanning Tree, MST) utilizzando l'algoritmo di Kruskal.
     * 
     * @return Un grafo pesato rappresentante l'albero ricoprente minimo.
     * @throws UnsupportedOperationException Poiché il metodo non è implementato.
     */
    @Override
    public WeightedGraph getKruskalMST() throws UnsupportedOperationException 
    {
        throw new UnsupportedOperationException("Metodo non implementabile");
    }

    /**
     * Restituisce un grafo pesato contenente tutte le coppie di cammini minimi calcolati utilizzando l'algoritmo di Floyd-Warshall.
     * 
     * @return Un grafo pesato con i cammini minimi calcolati per tutte le coppie di vertici.
     * @throws UnsupportedOperationException Poiché il metodo non è implementato.
     */
    @Override
    public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException 
    {
        throw new UnsupportedOperationException("Metodo non implementabile");
    }

}