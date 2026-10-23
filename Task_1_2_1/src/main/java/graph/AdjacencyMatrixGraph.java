package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdjacencyMatrixGraph extends AbstractGraph {

    private final Map<String, Integer> index = new LinkedHashMap<>();
    private final List<String> names = new ArrayList<>();
    private boolean[][] matrix = new boolean[0][0];
    private int size = 0;

    private void ensureCapacity() {
        if (size == matrix.length) {
            int newSize = Math.max(4, matrix.length * 2);
            boolean[][] nm = new boolean[newSize][newSize];
            for (int i = 0; i < size; i++) {
                System.arraycopy(matrix[i], 0, nm[i], 0, size);
            }
            matrix = nm;
        }
    }

    @Override
    public void addVertex(String v) {
        if (index.containsKey(v)) {
            return;
        }
        ensureCapacity();
        index.put(v, size);
        names.add(v);
        size++;
    }

    @Override
    public void removeVertex(String v) {
        Integer idx = index.get(v);
        if (idx == null) {
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int j = idx; j < size - 1; j++) {
                matrix[i][j] = matrix[i][j + 1];
            }
        }
        for (int i = idx; i < size - 1; i++) {
            matrix[i] = Arrays.copyOf(matrix[i + 1], matrix[i + 1].length);
        }
        Arrays.fill(matrix[size - 1], false);
        names.remove((int) idx);
        index.clear();
        for (int i = 0; i < names.size(); i++) {
            index.put(names.get(i), i);
        }
        size--;
    }

    @Override
    public boolean hasVertex(String v) {
        return index.containsKey(v);
    }

    @Override
    public Set<String> vertices() {
        return new LinkedHashSet<>(names);
    }

    @Override
    public void addEdge(String from, String to) {
        Integer a = index.get(from);
        Integer b = index.get(to);
        if (a == null || b == null) {
            throw new IllegalArgumentException("Нет вершины: " + (a == null ? from : to));
        }
        matrix[a][b] = true;
    }

    @Override
    public void removeEdge(String from, String to) {
        Integer a = index.get(from);
        Integer b = index.get(to);
        if (a != null && b != null) {
            matrix[a][b] = false;
        }
    }

    @Override
    public boolean hasEdge(String from, String to) {
        Integer a = index.get(from);
        Integer b = index.get(to);
        return a != null && b != null && matrix[a][b];
    }

    @Override
    public Set<String> neighbors(String v) {
        Integer a = index.get(v);
        if (a == null) {
            throw new IllegalArgumentException("Нет вершины: " + v);
        }
        Set<String> res = new LinkedHashSet<>();
        for (int j = 0; j < size; j++) {
            if (matrix[a][j]) {
                res.add(names.get(j));
            }
        }
        return res;
    }

    @Override
    protected Set<String> rawNeighbors(String v) {
        return neighbors(v);
    }

    @Override
    public int vertexCount() {
        return size;
    }

    @Override
    public void loadFromFile(String filename) throws Exception {
        GraphIo.fillFromFile(this, filename);
    }
}
