package TP6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import itba.andy.TP6.GraphBuilder;
import itba.andy.TP6.GraphService;
import itba.andy.TP6.GraphService.*;
import itba.andy.TP6.WeightedEdge;
import itba.andy.TP6.DijkstraPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdjacencyListGraphTester {
    private GraphService<String, WeightedEdge> graph;

    @BeforeEach
    void setUp() {
        // Create a simple, directed, weighted graph that accepts self-loops
        GraphBuilder<String, WeightedEdge> builder = new GraphBuilder<>();

        // Dijkstra tests don't work with multiplicity, so we comment it out B)
        graph = builder.withAcceptWeight(Weight.YES).withAcceptSelfLoop(SelfLoop.YES).withDirected(EdgeMode.DIRECTED)
                /* .withMultiplicity(Multiplicity.MULTIPLE) */.build();
    }

    @Test
    void testAddVertex() {
        graph.addVertex("A");
        assertTrue(graph.getVertices().contains("A"));
        assertEquals(1, graph.numberOfVertices());

        // Test adding null vertex
        assertThrows(IllegalArgumentException.class, () -> {
            graph.addVertex(null);
        });
    }

    @Test
    void testAddEdge() {
        graph.addEdge("A", "B", new WeightedEdge(1));

        assertEquals(1, graph.numberOfEdges());
        assertEquals(1, graph.outDegree("A"));
        assertEquals(0, graph.inDegree("A"));
        assertEquals(0, graph.outDegree("B"));
        assertEquals(1, graph.inDegree("B"));
    }

    @Test
    void testRemoveVertex() {
        graph.addEdge("A", "B", new WeightedEdge(1));

        assertTrue(graph.removeVertex("A"));
        assertEquals(1, graph.numberOfVertices());
        assertEquals(0, graph.numberOfEdges());
        assertFalse(graph.getVertices().contains("A"));
    }

    @Test
    void testRemoveEdge() {
        graph.addEdge("A", "B", new WeightedEdge(1));

        assertTrue(graph.removeEdge("A", "B"));
        assertEquals(0, graph.numberOfEdges());
        assertEquals(0, graph.outDegree("A"));
        assertEquals(0, graph.inDegree("B"));
    }

    @Test
    void testDegree() {
        graph.addEdge("A", "B", new WeightedEdge(1));
        graph.addEdge("B", "A", new WeightedEdge(2));

        assertEquals(2, graph.degree("A")); // 1 in + 1 out
        assertEquals(2, graph.degree("B")); // 1 in + 1 out
    }

    @Test
    void testSelfLoop() {
        graph.addEdge("A", "A", new WeightedEdge(1));

        assertEquals(1, graph.numberOfEdges());
        assertEquals(1, graph.outDegree("A"));
        assertEquals(1, graph.inDegree("A"));
    }

    @Test
    void testDijkstraBasicPath() {
        // Create a simple path: A -> B -> C with weights 1 and 2
        graph.addEdge("A", "B", new WeightedEdge(1));
        graph.addEdge("B", "C", new WeightedEdge(2));

        DijkstraPath<String, WeightedEdge> result = graph.dijsktra("A");

        // Check paths and distances through toString()
        String output = result.toString();
        assertTrue(output.contains("0: [no path to A]"));
        assertTrue(output.contains("1: [A, B]"));
        assertTrue(output.contains("3: [A, B, C]"));
    }

    @Test
    void testDijkstraUnreachableVertex() {
        // Create two disconnected components: A->B and C->D
        graph.addEdge("A", "B", new WeightedEdge(1));
        graph.addEdge("C", "D", new WeightedEdge(1));

        DijkstraPath<String, WeightedEdge> result = graph.dijsktra("A");

        // Check that C and D are unreachable
        String output = result.toString();
        assertTrue(output.contains("INF: [no path to C]"));
        assertTrue(output.contains("INF: [no path to D]"));
    }

    @Test
    void testDijkstraNegativeWeight() {
        graph.addEdge("A", "B", new WeightedEdge(-1));

        // Should throw exception for negative weight
        assertThrows(IllegalArgumentException.class, () -> {
            graph.dijsktra("A");
        });
    }

    @Test
    void testDijkstraMultiplePaths() {
        // Create a graph with multiple paths to test shortest path selection
        // A -> B -> C (weight 3)
        // A -> D -> C (weight 2)
        graph.addEdge("A", "B", new WeightedEdge(1));
        graph.addEdge("B", "C", new WeightedEdge(2));
        graph.addEdge("A", "D", new WeightedEdge(1));
        graph.addEdge("D", "C", new WeightedEdge(1));

        DijkstraPath<String, WeightedEdge> result = graph.dijsktra("A");

        // Check that the shorter path A->D->C is chosen
        String output = result.toString();
        assertTrue(output.contains("2: [A, D, C]"));
    }

    @Test
    void testDijkstraWithSelfLoop() {
        // Create a path with a self-loop: A -> A -> B
        graph.addEdge("A", "A", new WeightedEdge(1));
        graph.addEdge("A", "B", new WeightedEdge(2));

        DijkstraPath<String, WeightedEdge> result = graph.dijsktra("A");

        // Check that the self-loop is not used in the shortest path
        String output = result.toString();
        assertTrue(output.contains("0: [no path to A]"));
        assertTrue(output.contains("2: [A, B]"));
    }

    @Test
    void testBFSLinearPath() {
        // Create a linear path: A -> B -> C -> D
        graph.addEdge("A", "B", new WeightedEdge(1));
        graph.addEdge("B", "C", new WeightedEdge(1));
        graph.addEdge("C", "D", new WeightedEdge(1));

        // BFS should visit nodes in order of distance from start
        var bfs = graph.getBFS("A");
        var iterator = bfs.iterator();

        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertEquals("C", iterator.next());
        assertEquals("D", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testBFSDisconnectedComponents() {
        // Create two disconnected components: A->B and C->D
        graph.addEdge("A", "B", new WeightedEdge(1));
        graph.addEdge("C", "D", new WeightedEdge(1));

        // BFS from A should only visit A and B
        var bfs = graph.getBFS("A");
        var iterator = bfs.iterator();

        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testBFSWithCycle() {
        // Create a cycle: A -> B -> C -> A
        graph.addEdge("A", "B", new WeightedEdge(1));
        graph.addEdge("B", "C", new WeightedEdge(1));
        graph.addEdge("C", "A", new WeightedEdge(1));

        // BFS should visit each node exactly once
        var bfs = graph.getBFS("A");
        var iterator = bfs.iterator();

        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertEquals("C", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDFSLinearPath() {
        // Create a linear path: A -> B -> C -> D
        graph.addEdge("A", "B", new WeightedEdge(1));
        graph.addEdge("B", "C", new WeightedEdge(1));
        graph.addEdge("C", "D", new WeightedEdge(1));

        // DFS should visit nodes in depth-first order
        var dfs = graph.getDFS("A");
        var iterator = dfs.iterator();

        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertEquals("C", iterator.next());
        assertEquals("D", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDFSDisconnectedComponents() {
        // Create two disconnected components: A->B and C->D
        graph.addEdge("A", "B", new WeightedEdge(1));
        graph.addEdge("C", "D", new WeightedEdge(1));

        // DFS from A should only visit A and B
        var dfs = graph.getDFS("A");
        var iterator = dfs.iterator();

        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDFSWithCycle() {
        // Create a cycle: A -> B -> C -> A
        graph.addEdge("A", "B", new WeightedEdge(1));
        graph.addEdge("B", "C", new WeightedEdge(1));
        graph.addEdge("C", "A", new WeightedEdge(1));

        // DFS should visit each node exactly once
        var dfs = graph.getDFS("A");
        var iterator = dfs.iterator();

        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertEquals("C", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testBFSInvalidStart() {
        assertThrows(IllegalArgumentException.class, () -> {
            graph.getBFS(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            graph.getBFS("X"); // X is not in the graph
        });
    }

    @Test
    void testDFSInvalidStart() {
        assertThrows(IllegalArgumentException.class, () -> {
            graph.getDFS(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            graph.getDFS("X"); // X is not in the graph
        });
    }
}
