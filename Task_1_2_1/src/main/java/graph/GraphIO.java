package graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class GraphIO {

    private GraphIO() {}

    public static void fillFromFile(Graph g, String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            if (line == null || !line.trim().startsWith("V"))
                throw new IOException("Ожидалась строка 'V <count>'");
            int vcount = Integer.parseInt(line.trim().split("\\s+")[1]);

            line = br.readLine();
            if (line == null) throw new IOException("Ожидался список вершин");
            String[] vs = line.trim().split("\\s+");
            if (vs.length != vcount)
                throw new IOException("Ожидалось вершин: " + vcount + ", получено: " + vs.length);
            for (String v : vs) g.addVertex(v);

            line = br.readLine();
            if (line == null || !line.trim().startsWith("E"))
                throw new IOException("Ожидалась строка 'E <count>'");
            int ecount = Integer.parseInt(line.trim().split("\\s+")[1]);

            for (int i = 0; i < ecount; i++) {
                line = br.readLine();
                if (line == null) throw new IOException("Не хватает рёбер");
                String[] parts = line.trim().split("\\s+");
                if (parts.length != 2) throw new IOException("Ребро должно быть парой: " + line);
                g.addEdge(parts[0], parts[1]);
            }
        }
    }
}
