package graph;

import java.util.List;
import java.util.Set;

public interface Graph {
    void addVertex(String v);
    void removeVertex(String v);
    boolean hasVertex(String v);
    Set<String> vertices();
    void addEdge(String from, String to);
    void removeEdge(String from, String to);
    boolean hasEdge(String from, String to);
    Set<String> neighbors(String v);
    void loadFromFile(String filename) throws Exception;
    List<String> topologicalSort();
    int vertexCount();
    int edgeCount();
}
