package graph;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс ориентированного графа.
 */
public interface Graph {

    /**
     * Добавляет вершину.
     *
     * @param v имя вершины
     */
    void addVertex(String v);

    /**
     * Удаляет вершину и все инцидентные рёбра.
     *
     * @param v имя вершины
     */
    void removeVertex(String v);

    /**
     * Проверяет наличие вершины.
     *
     * @param v имя вершины
     * @return true, если вершина есть
     */
    boolean hasVertex(String v);

    /**
     * Возвращает все вершины.
     *
     * @return множество вершин
     */
    Set<String> vertices();

    /**
     * Добавляет ребро.
     *
     * @param from начало
     * @param to   конец
     */
    void addEdge(String from, String to);

    /**
     * Удаляет ребро.
     *
     * @param from начало
     * @param to   конец
     */
    void removeEdge(String from, String to);

    /**
     * Проверяет наличие ребра.
     *
     * @param from начало
     * @param to   конец
     * @return true, если ребро есть
     */
    boolean hasEdge(String from, String to);

    /**
     * Возвращает исходящих соседей.
     *
     * @param v имя вершины
     * @return множество соседей
     */
    Set<String> neighbors(String v);

    /**
     * Загружает граф из файла.
     *
     * @param filename путь к файлу
     * @throws Exception при ошибке чтения
     */
    void loadFromFile(String filename) throws Exception;

    /**
     * Топологическая сортировка.
     *
     * @return список вершин в топологическом порядке
     */
    List<String> topologicalSort();

    /**
     * Количество вершин.
     *
     * @return число вершин
     */
    int vertexCount();

    /**
     * Количество рёбер.
     *
     * @return число рёбер
     */
    int edgeCount();
}
