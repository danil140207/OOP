package hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import org.junit.jupiter.api.Test;

class HashTableIteratorTest {

    @Test
    void iterationReturnsAllPairs() {
        HashTable<String, Integer> t = new HashTable<>();
        t.put("a", 1);
        t.put("b", 2);
        t.put("c", 3);
        List<String> keys = new ArrayList<>();
        for (HashTable.Entry<String, Integer> e : t) {
            keys.add(e.getKey());
        }
        assertEquals(3, keys.size());
    }

    @Test
    void concurrentModificationThrows() {
        HashTable<String, Integer> t = new HashTable<>();
        t.put("a", 1);
        t.put("b", 2);
        assertThrows(ConcurrentModificationException.class, () -> {
            for (HashTable.Entry<String, Integer> e : t) {
                t.put("x" + e.getKey(), 99);
            }
        });
    }
}