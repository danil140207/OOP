package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AdjacencyListGraphTest {

    @Test
    void addAndRemoveVertices() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("A");
        assertEquals(2, g.vertexCount());
        g.removeVertex("A");
        assertEquals(1, g.vertexCount());
    }

    @Test
    void addAndRemoveEdges() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        assertTrue(g.hasEdge("A", "B"));
        g.removeEdge("A", "B");
        assertFalse(g.hasEdge("A", "B"));
    }

    @Test
    void neighborsWorks() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        assertEquals(Set.of("B", "C"), g.neighbors("A"));
    }

    @Test
    void addEdgeToMissingThrows() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");
        assertThrows(IllegalArgumentException.class, () -> g.addEdge("A", "B"));
    }

    @Test
    void neighborsOfMissingThrows() {
        Graph g = new AdjacencyListGraph();
        assertThrows(IllegalArgumentException.class, () -> g.neighbors("X"));
    }

    @Test
    void equalsAcrossRepresentations() {
        Graph a = new AdjacencyListGraph();
        Graph b = new AdjacencyMatrixGraph();
        for (Graph g : List.of(a, b)) {
            g.addVertex("A");
            g.addVertex("B");
            g.addEdge("A", "B");
        }
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toStringContainsVertices() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        String s = g.toString();
        assertTrue(s.contains("A"));
        assertTrue(s.contains("B"));
    }
}