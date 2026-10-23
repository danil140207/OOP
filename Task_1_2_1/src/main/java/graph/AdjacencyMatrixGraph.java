package graph;

import java.util.*;

public class AdjacencyMatrixGraph extends AbstractGraph {

    private final Map<String, Integer> index = new LinkedHashMap<>();
    private final List<String> names = new ArrayList<>();
    private boolean[][] matrix = new boolean[0][0];
    private int n = 0;

    private void ensureCapacity() {
        if (n == matrix.length) {
            int newSize = Math.max(4, matrix.length * 2);
            boolean[][] nm = new boolean[newSize][newSize];
            for (int i = 0; i < n; i++) System.arraycopy(matrix[i], 0, nm[i], 0, n);
            matrix = nm;
        }
    }

    @Override public void addVertex(String v) {
        if (index.containsKey(v)) return;
        ensureCapacity();
        index.put(v, n);
        names.add(v);
        n++;
    }

    @Override public void removeVertex(String v) {
        Integer idx = index.get(v);
        if (idx == null) return;
        for (int i = 0; i < n; i++)
            for (int j = idx; j < n - 1; j++) matrix[i][j] = matrix[i][j + 1];
        for (int i = idx; i < n - 1; i++)
            matrix[i] = Arrays.copyOf(matrix[i + 1], matrix[i + 1].length);
        Arrays.fill(matrix[n - 1], false);
        names.remove((int) idx);
        index.clear();
        for (int i = 0; i < names.size(); i++) index.put(names.get(i), i);
        n--;
    }

    @Override public boolean hasVertex(String v) { return index.containsKey(v); }
    @Override public Set<String> vertices() { return new LinkedHashSet<>(names); }

    @Override public void addEdge(String from, String to) {
        Integer a = index.get(from), b = index.get(to);
        if (a == null || b == null)
            throw new IllegalArgumentException("Нет вершины: " + (a == null ? from : to));
        matrix[a][b] = true;
    }

    @Override public void removeEdge(String from, String to) {
        Integer a = index.get(from), b = index.get(to);
        if (a != null && b != null) matrix[a][b] = false;
    }

    @Override public boolean hasEdge(String from, String to) {
        Integer a = index.get(from), b = index.get(to);
        return a != null && b != null && matrix[a][b];
    }

    @Override public Set<String> neighbors(String v) {
        Integer a = index.get(v);
        if (a == null) throw new IllegalArgumentException("Нет вершины: " + v);
        Set<String> res = new LinkedHashSet<>();
        for (int j = 0; j < n; j++) if (matrix[a][j]) res.add(names.get(j));
        return res;
    }

    @Override protected Set<String> rawNeighbors(String v) { return neighbors(v); }
    @Override public int vertexCount() { return n; }

    @Override public void loadFromFile(String filename) throws Exception {
        GraphIO.fillFromFile(this, filename);
    }
}
