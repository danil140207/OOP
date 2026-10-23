package graph;

import java.util.*;

public class IncidenceMatrixGraph extends AbstractGraph {

    private final Map<String, Integer> vIndex = new LinkedHashMap<>();
    private final List<String> names = new ArrayList<>();
    private final List<String[]> edges = new ArrayList<>();

    @Override public void addVertex(String v) {
        if (vIndex.containsKey(v)) return;
        vIndex.put(v, names.size());
        names.add(v);
    }

    @Override public void removeVertex(String v) {
        Integer idx = vIndex.get(v);
        if (idx == null) return;
        edges.removeIf(e -> e[0].equals(v) || e[1].equals(v));
        names.remove((int) idx);
        vIndex.clear();
        for (int i = 0; i < names.size(); i++) vIndex.put(names.get(i), i);
    }

    @Override public boolean hasVertex(String v) { return vIndex.containsKey(v); }
    @Override public Set<String> vertices() { return new LinkedHashSet<>(names); }

    @Override public void addEdge(String from, String to) {
        if (!hasVertex(from) || !hasVertex(to))
            throw new IllegalArgumentException("Нет вершины: " + from + " или " + to);
        for (String[] e : edges)
            if (e[0].equals(from) && e[1].equals(to)) return;
        edges.add(new String[]{from, to});
    }

    @Override public void removeEdge(String from, String to) {
        edges.removeIf(e -> e[0].equals(from) && e[1].equals(to));
    }

    @Override public boolean hasEdge(String from, String to) {
        for (String[] e : edges)
            if (e[0].equals(from) && e[1].equals(to)) return true;
        return false;
    }

    @Override public Set<String> neighbors(String v) {
        if (!hasVertex(v)) throw new IllegalArgumentException("Нет вершины: " + v);
        Set<String> res = new LinkedHashSet<>();
        for (String[] e : edges) if (e[0].equals(v)) res.add(e[1]);
        return res;
    }

    @Override protected Set<String> rawNeighbors(String v) { return neighbors(v); }
    @Override public int vertexCount() { return names.size(); }
    @Override public int edgeCount() { return edges.size(); }

    @Override public void loadFromFile(String filename) throws Exception {
        GraphIO.fillFromFile(this, filename);
    }
}
