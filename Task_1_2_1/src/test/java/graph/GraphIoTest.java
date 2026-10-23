package graph;

import java.net.URL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphIoTest {

    private String resourcePath(String name) {
        URL url = getClass().getClassLoader().getResource(name);
        assertNotNull(url, "Ресурс не найден: " + name);
        return url.getPath();
    }

    @Test
    void loadsValidFile() throws Exception {
        Graph g = new AdjacencyListGraph();
        g.loadFromFile(resourcePath("graph.txt"));
        assertEquals(5, g.vertexCount());
        assertEquals(5, g.edgeCount());
        assertTrue(g.hasEdge("A", "B"));
        assertTrue(g.hasEdge("D", "E"));
    }

    @Test
    void missingFileThrows() {
        Graph g = new AdjacencyListGraph();
        assertThrows(Exception.class,
                () -> g.loadFromFile("src/test/resources/nonexistent.txt"));
    }

    @Test
    void worksWithAllRepresentations() throws Exception {
        Graph[] graphs = new Graph[]{
            new AdjacencyListGraph(),
            new AdjacencyMatrixGraph(),
            new IncidenceMatrixGraph()
        };
        for (Graph g : graphs) {
            g.loadFromFile(resourcePath("graph.txt"));
            assertEquals(5, g.vertexCount());
            assertEquals(5, g.edgeCount());
        }
    }
}