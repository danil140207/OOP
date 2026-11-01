package hashtable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashTableTest {

    @Test
    void putAndGet() {
        HashTable<String, Number> t = new HashTable<>();
        t.put("one", 1);
        t.put("two", 2);
        assertEquals(2, t.size());
        assertEquals(1, t.get("one"));
        assertEquals(2, t.get("two"));
    }

    @Test
    void putReplacesValue() {
        HashTable<String, Number> t = new HashTable<>();
        t.put("one", 1);
        t.put("one", 2);
        assertEquals(1, t.size());
        assertEquals(2, t.get("one"));
    }

    @Test
    void updateExistingKey() {
        HashTable<String, Number> t = new HashTable<>();
        t.put("one", 1);
        Number old = t.update("one", 1.0);
        assertEquals(1, old);
        assertEquals(1.0, t.get("one"));
    }

    @Test
    void updateMissingKeyReturnsNull() {
        HashTable<String, Number> t = new HashTable<>();
        assertNull(t.update("missing", 1));
    }

    @Test
    void getMissingReturnsNull() {
        HashTable<String, Number> t = new HashTable<>();
        assertNull(t.get("missing"));
    }

    @Test
    void containsKey() {
        HashTable<String, Integer> t = new HashTable<>();
        t.put("a", 1);
        assertTrue(t.containsKey("a"));
        assertFalse(t.containsKey("b"));
    }

    @Test
    void removeByKeyAndValue() {
        HashTable<String, Integer> t = new HashTable<>();
        t.put("a", 1);
        t.put("b", 2);
        assertTrue(t.remove("a", 1));
        assertEquals(1, t.size());
        assertFalse(t.remove("a", 1));
        assertFalse(t.remove("b", 999));
    }

    @Test
    void exampleFromTask() {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.update("one", 1.0);
        assertEquals(1.0, hashTable.get("one"));
    }
}