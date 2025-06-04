package TP6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import itba.andy.TP6.GraphBuilder;
import itba.andy.TP6.GraphService;
import itba.andy.TP6.GraphService.*;
import itba.andy.TP6.EmptyEdgeProp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdjacencyListGraphUnw {
    private GraphService<String, EmptyEdgeProp> graph;

    @BeforeEach
    void setUp() {
        // Create a simple, directed, weighted graph that accepts self-loops
        GraphBuilder<String, EmptyEdgeProp> builder = new GraphBuilder<>();

        // Dijkstra tests don't work with multiplicity, so we comment it out B)
        graph = builder.withAcceptWeight(Weight.NO).withAcceptSelfLoop(SelfLoop.YES).withDirected(EdgeMode.UNDIRECTED)
                /* .withMultiplicity(Multiplicity.MULTIPLE) */.build();
    }

    @Test
    void numberOfEdgesTest() {
        graph.addEdge("E", "B", new EmptyEdgeProp());
        graph.addEdge("A", "B", new EmptyEdgeProp());
        graph.addEdge("F", "B", new EmptyEdgeProp());
        graph.addVertex("D");
        graph.addVertex("G");
        graph.addEdge("E", "F", new EmptyEdgeProp());
        graph.addEdge("F", "A", new EmptyEdgeProp());
        graph.addEdge("F", "G", new EmptyEdgeProp());
        graph.addEdge("U", "G", new EmptyEdgeProp());
        graph.addEdge("T", "U", new EmptyEdgeProp());
        graph.addEdge("C", "G", new EmptyEdgeProp());
        graph.addEdge("F", "F", new EmptyEdgeProp());
        assertEquals(10, graph.numberOfEdges());
    }
}
