package itba.andy.Parcial2.Y2023_Q2.ej2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

    private boolean isSimple;
    protected boolean isDirected;
    private boolean acceptSelfLoop;
    private boolean isWeighted;
    private Map<V, Collection<InternalEdge>> adjacencyList = new HashMap<>();

    protected Map<V, Collection<AdjacencyListGraph<V, E>.InternalEdge>> getAdjacencyList() {
        return adjacencyList;
    }

    public Collection<V> getVertices() {
        return getAdjacencyList().keySet();
    }

    protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
        this.isSimple = isSimple;
        this.isDirected = isDirected;
        this.acceptSelfLoop = acceptSelfLoop;
        this.isWeighted = isWeighted;
    }

    public int connectedComponentsQty() {
        Set<V> visited = new HashSet<>();
        int components = 0;

        for (V vertex : getVertices()) {
            if (!visited.contains(vertex)) {
                components++;
                dfs(vertex, visited);
            }
        }

        return components;
    }

    private void dfs(V start, Set<V> visited) {
        visited.add(start);
        for (InternalEdge edge : getAdjacencyList().get(start)) {
            V neighbor = edge.target;
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited);
            }
        }
    }

    public void addVertex(V aVertex) {

        if (aVertex == null)
            throw new IllegalArgumentException("addVertexParamCannotBeNull");

        // no edges yet
        getAdjacencyList().putIfAbsent(aVertex,
                new ArrayList<InternalEdge>());
    }

    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        // validation!!!!
        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException("addEdgeParamCannotBeNull");

        // es con peso? debe tener implementado el metodo double getWeight()
        if (isWeighted) {
            // reflection
            Class<? extends Object> c = theEdge.getClass();
            try {
                c.getDeclaredMethod("getWeight");
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException(
                        "Graph is weighted but the method double getWeighed() is not declared in theEdge");
            }
        }

        if (!acceptSelfLoop && aVertex.equals(otherVertex)) {
            throw new RuntimeException(String.format("Graph does not accept self loops between %s and %s",
                    aVertex, otherVertex));
        }

        // if any of the vertex is not presented, the node is created automatically
        addVertex(aVertex);
        addVertex(otherVertex);
    }

    class InternalEdge {
        E edge;
        V target;

        InternalEdge(E propEdge, V target) {
            this.target = target;
            this.edge = propEdge;
        }
    }

    public static void main(String[] args) {
        // Test case 1: Empty graph
        GraphService<String, Integer> emptyGraph = new GraphBuilder<String, Integer>()
                .withDirected(EdgeMode.UNDIRECTED)
                .build();
        System.out.println("Empty graph components: " + emptyGraph.connectedComponentsQty()); // Should be 0

        // Test case 2: Single vertex
        GraphService<String, Integer> singleVertexGraph = new GraphBuilder<String, Integer>()
                .withDirected(EdgeMode.UNDIRECTED)
                .build();
        singleVertexGraph.addVertex("A");
        System.out.println("Single vertex components: " + singleVertexGraph.connectedComponentsQty()); // Should be 1

        // Test case 3: Two disconnected vertices
        GraphService<String, Integer> disconnectedGraph = new GraphBuilder<String, Integer>()
                .withDirected(EdgeMode.UNDIRECTED)
                .build();
        disconnectedGraph.addVertex("A");
        disconnectedGraph.addVertex("B");
        System.out.println("Disconnected vertices components: " + disconnectedGraph.connectedComponentsQty()); // Should
                                                                                                               // be 2

        // Test case 4: Two connected vertices
        GraphService<String, Integer> connectedGraph = new GraphBuilder<String, Integer>()
                .withDirected(EdgeMode.UNDIRECTED)
                .build();
        connectedGraph.addVertex("A");
        connectedGraph.addVertex("B");
        connectedGraph.addEdge("A", "B", 1);
        System.out.println("Connected vertices components: " + connectedGraph.connectedComponentsQty()); // Should be 1

        // Test case 5: Complex graph with multiple components
        GraphService<String, Integer> complexGraph = new GraphBuilder<String, Integer>()
                .withDirected(EdgeMode.UNDIRECTED)
                .build();
        // First component: A-B-C
        complexGraph.addVertex("A");
        complexGraph.addVertex("B");
        complexGraph.addVertex("C");
        complexGraph.addEdge("A", "B", 1);
        complexGraph.addEdge("B", "C", 2);
        // Second component: D-E
        complexGraph.addVertex("D");
        complexGraph.addVertex("E");
        complexGraph.addEdge("D", "E", 3);
        // Third component: F (isolated)
        complexGraph.addVertex("F");
        System.out.println("Complex graph components: " + complexGraph.connectedComponentsQty()); // Should be 3
    }
}