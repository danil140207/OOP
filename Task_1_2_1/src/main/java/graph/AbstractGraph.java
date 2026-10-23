package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Базовый класс графа.
 */
public abstract class AbstractGraph implements Graph {

    protected abstract Set<String> rawNeighbors(String v);

    protected List<String> sortedVertices() {
        List<String> list = new ArrayList<>(vertices());
        Collections.sort(list);
        return list;
    }

    @Override
    public int edgeCount() {
        int count = 0;
        for (String v : vertices()) {
            count += neighbors(v).size();
        }
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph g = (Graph) o;
        if (!vertices().equals(g.vertices())) {
            return false;
        }
        if (edgeCount() != g.edgeCount()) {
            return false;
        }
        for (String v : vertices()) {
            if (!neighbors(v).equals(g.neighbors(v))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices(), edgeCount());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append(" {\n");
        for (String v : sortedVertices()) {
            List<String> ns = new ArrayList<>(neighbors(v));
            Collections.sort(ns);
            sb.append("  ").append(v).append(" -> ").append(ns).append('\n');
        }
        return sb.append('}').toString();
    }

    @Override
    public List<String> topologicalSort() {
        Map<String, Integer> indeg = new HashMap<>();
        for (String v : vertices()) {
            indeg.put(v, 0);
        }
        for (String v : vertices()) {
            for (String u : rawNeighbors(v)) {
                if (!indeg.containsKey(u)) {
                    throw new IllegalStateException("Ребро в неизвестную вершину: " + u);
                }
                indeg.merge(u, 1, Integer::sum);
            }
        }
        Queue<String> queue = new PriorityQueue<>();
        for (Map.Entry<String, Integer> e : indeg.entrySet()) {
            if (e.getValue() == 0) {
                queue.add(e.getKey());
            }
        }
        List<String> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            String v = queue.poll();
            order.add(v);
            for (String u : rawNeighbors(v)) {
                if (indeg.merge(u, -1, Integer::sum) == 0) {
                    queue.add(u);
                }
            }
        }
        if (order.size() != vertices().size()) {
            throw new IllegalStateException("Граф содержит цикл");
        }
        return order;
    }
}
