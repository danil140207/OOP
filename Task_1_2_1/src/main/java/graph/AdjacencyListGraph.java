package graph;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AdjacencyListGraph extends AbstractGraph {

    private final Map<String, Set<String>> adj = new LinkedHashMap<>();

    @Override
    public void addVertex(String v) {
        adj.computeIfAbsent(v, k -> new LinkedHashSet<>());
    }

    @Override
    public void removeVertex(String v) {
        if (adj.remove(v) == null) {
            return;
        }
        for (Set<String> s : adj.values()) {
            s.remove(v);
        }
    }

    @Override
    public boolean hasVertex(String v) {
        return adj.containsKey(v);
    }

    @Override
    public Set<String> vertices() {
        return new LinkedHashSet<>(adj.keySet());
    }

    @Override
    public void addEdge(String from, String to) {
        if (!adj.containsKey(from) || !adj.containsKey(to)) {
            throw new IllegalArgumentException("Нет вершины: " + from + " или " + to);
        }
        adj.get(from).add(to);
    }

    @Override
    public void removeEdge(String from, String to) {
        Set<String> s = adj.get(from);
        if (s != null) {
            s.remove(to);
        }
    }

    @Override
    public boolean hasEdge(String from, String to) {
        Set<String> s = adj.get(from);
        return s != null && s.contains(to);
    }

    @Override
    public Set<String> neighbors(String v) {
        Set<String> s = adj.get(v);
        if (s == null) {
            throw new IllegalArgumentException("Нет вершины: " + v);
        }
        return Collections.unmodifiableSet(s);
    }

    @Override
    protected Set<String> rawNeighbors(String v) {
        return neighbors(v);
    }

    @Override
    public int vertexCount() {
        return adj.size();
    }

    @Override
    public void loadFromFile(String filename) throws Exception {
        GraphIo.fillFromFile(this, filename);
    }
}
