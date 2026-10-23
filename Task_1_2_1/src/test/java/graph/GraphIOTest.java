package graph;

import org.junit.jupiter.api.Test;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.*;

class GraphIOTest {
    private String resourcePath(String name) {
        URL url = getClass().getClassLoader().getResource(name);
        assertNotNull(url, "Ресурс не найден: " + name);
        return url.getPath();
    }
    @Test void loadsValidFile() throws Exception {
        Graph g = new AdjacencyListGraph();
        g.loadFromFile(resourcePath("graph.txt"));
        assertEquals(5, g.vertexCount());
        assertEquals(5, g.edgeCount());
        assertTrue(g.hasEdge("A", "B"));
        assertTrue(g.hasEdge("D", "E"));
    }
    @Test void missingFileThrows() {
        Graph g = new AdjacencyListGraph();
        assertThrows(Exception.class,
                () -> g.loadFromFile("src/test/resources/nonexistent.txt"));
    }
    @Test void worksWithAllRepresentations() throws Exception {
        for (Graph g : new Graph[]{
                new AdjacencyListGraph(),
                new AdjacencyMatrixGraph(),
                new IncidenceMatrixGraph()}) {
            g.loadFromFile(resourcePath("graph.txt"));
            assertEquals(5, g.vertexCount());
            assertEquals(5, g.edgeCount());
        }
    }
}
