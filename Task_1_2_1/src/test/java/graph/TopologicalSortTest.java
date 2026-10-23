package graph;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortTest {
    @Test void simpleDag() {
        Graph g = new AdjacencyListGraph();
        for (String v : List.of("A", "B", "C", "D")) g.addVertex(v);
        g.addEdge("A", "B"); g.addEdge("A", "C");
        g.addEdge("B", "D"); g.addEdge("C", "D");
        List<String> order = g.topologicalSort();
        assertEquals(4, order.size());
        assertTrue(order.indexOf("A") < order.indexOf("B"));
        assertTrue(order.indexOf("B") < order.indexOf("D"));
    }
    @Test void cycleThrows() {
        Graph g = new AdjacencyMatrixGraph();
        g.addVertex("X"); g.addVertex("Y");
        g.addEdge("X", "Y"); g.addEdge("Y", "X");
        assertThrows(IllegalStateException.class, g::topologicalSort);
    }
    @Test void emptyGraph() {
        Graph g = new IncidenceMatrixGraph();
        assertTrue(g.topologicalSort().isEmpty());
    }
    @Test void sameAcrossRepresentations() {
        Graph list = new AdjacencyListGraph();
        Graph matrix = new AdjacencyMatrixGraph();
        Graph inc = new IncidenceMatrixGraph();
        for (Graph g : List.of(list, matrix, inc)) {
            g.addVertex("A"); g.addVertex("B"); g.addVertex("C");
            g.addEdge("A", "B"); g.addEdge("B", "C");
        }
        assertEquals(list.topologicalSort(), matrix.topologicalSort());
        assertEquals(list.topologicalSort(), inc.topologicalSort());
    }
}
