package hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HashTableEqualsTest {

    @Test
    void equalTables() {
        HashTable<String, Integer> a = new HashTable<>();
        HashTable<String, Integer> b = new HashTable<>();
        a.put("x", 1);
        a.put("y", 2);
        b.put("y", 2);
        b.put("x", 1);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void differentTables() {
        HashTable<String, Integer> a = new HashTable<>();
        HashTable<String, Integer> b = new HashTable<>();
        a.put("x", 1);
        b.put("x", 2);
        assertNotEquals(a, b);
    }

    @Test
    void toStringContainsPairs() {
        HashTable<String, Integer> t = new HashTable<>();
        t.put("a", 1);
        t.put("b", 2);
        String s = t.toString();
        assertTrue(s.contains("a=1"));
        assertTrue(s.contains("b=2"));
    }

    @Test
    void manyElementsTriggerResize() {
        HashTable<Integer, Integer> t = new HashTable<>();
        for (int i = 0; i < 100; i++) {
            t.put(i, i * 10);
        }
        assertEquals(100, t.size());
        for (int i = 0; i < 100; i++) {
            assertEquals(i * 10, t.get(i));
        }
    }

    @Test
    void nullKeySupported() {
        HashTable<String, Integer> t = new HashTable<>();
        t.put(null, 42);
        assertEquals(42, t.get(null));
        assertTrue(t.containsKey(null));
    }
}