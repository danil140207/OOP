package graph;

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class IncidenceMatrixGraphTest {
    @Test void basicOps() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex("A"); g.addVertex("B");
        g.addEdge("A", "B"); g.addEdge("A", "B");
        assertEquals(1, g.edgeCount());
        assertEquals(Set.of("B"), g.neighbors("A"));
    }
    @Test void removeVertexRemovesEdges() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex("A"); g.addVertex("B"); g.addVertex("C");
        g.addEdge("A", "B"); g.addEdge("B", "C");
        g.removeVertex("B");
        assertEquals(2, g.vertexCount());
        assertEquals(0, g.edgeCount());
    }
    @Test void removeEdge() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex("A"); g.addVertex("B");
        g.addEdge("A", "B");
        g.removeEdge("A", "B");
        assertFalse(g.hasEdge("A", "B"));
    }
    @Test void exceptions() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex("A");
        assertThrows(IllegalArgumentException.class, () -> g.addEdge("A", "B"));
        assertThrows(IllegalArgumentException.class, () -> g.neighbors("X"));
    }
}
