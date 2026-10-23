package graph;

import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdjacencyMatrixGraphTest {

    @Test
    void basicOps() {
        Graph g = new AdjacencyMatrixGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        assertTrue(g.hasEdge("A", "B"));
        assertEquals(Set.of("B"), g.neighbors("A"));
        assertEquals(2, g.edgeCount());
        assertEquals(3, g.vertexCount());
    }

    @Test
    void removeVertexClearsEdges() {
        Graph g = new AdjacencyMatrixGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        g.addEdge("B", "A");
        g.removeVertex("A");
        assertEquals(1, g.vertexCount());
        assertEquals(0, g.edgeCount());
    }

    @Test
    void removeEdgeWorks() {
        Graph g = new AdjacencyMatrixGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        g.removeEdge("A", "B");
        assertFalse(g.hasEdge("A", "B"));
        g.removeEdge("X", "Y");
    }

    @Test
    void missingVertices() {
        Graph g = new AdjacencyMatrixGraph();
        assertThrows(IllegalArgumentException.class, () -> g.addEdge("A", "B"));
        assertThrows(IllegalArgumentException.class, () -> g.neighbors("Z"));
    }

    @Test
    void manyVerticesTriggerResize() {
        Graph g = new AdjacencyMatrixGraph();
        for (int i = 0; i < 20; i++) {
            g.addVertex("V" + i);
        }
        assertEquals(20, g.vertexCount());
    }
}