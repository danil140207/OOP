package hashtable;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Хеш-таблица с разрешением коллизий цепочками.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Entry<K, V>[] table;
    private int size;
    private int modCount;

    /**
     * Создаёт пустую хеш-таблицу.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        this.table = new Entry[INITIAL_CAPACITY];
    }

    /**
     * Возвращает количество элементов.
     *
     * @return размер
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли таблица.
     *
     * @return true, если пусто
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Хеш ключа.
     *
     * @param key ключ
     * @return хеш
     */
    private int hash(Object key) {
        if (key == null) {
            return 0;
        }
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    /**
     * Индекс бакета для хеша.
     *
     * @param hash хеш
     * @return индекс
     */
    private int bucketIndex(int hash) {
        return hash & (table.length - 1);
    }

    /**
     * Добавляет пару ключ-значение. Если ключ есть — заменяет значение.
     *
     * @param key   ключ
     * @param value значение
     */
    public void put(K key, V value) {
        if (size + 1 > table.length * LOAD_FACTOR) {
            resize();
        }
        int hash = hash(key);
        int idx = bucketIndex(hash);
        Entry<K, V> current = table[idx];
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        Entry<K, V> entry = new Entry<>(hash, key, value, table[idx]);
        table[idx] = entry;
        size++;
        modCount++;
    }

    /**
     * Возвращает значение по ключу или null.
     *
     * @param key ключ
     * @return значение
     */
    public V get(K key) {
        int hash = hash(key);
        int idx = bucketIndex(hash);
        Entry<K, V> current = table[idx];
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Проверяет наличие ключа.
     *
     * @param key ключ
     * @return true, если ключ есть
     */
    public boolean containsKey(K key) {
        int hash = hash(key);
        int idx = bucketIndex(hash);
        Entry<K, V> current = table[idx];
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Обновляет значение по ключу.
     *
     * @param key   ключ
     * @param value новое значение
     * @return старое значение или null
     */
    public V update(K key, V value) {
        int hash = hash(key);
        int idx = bucketIndex(hash);
        Entry<K, V> current = table[idx];
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                V old = current.value;
                current.value = value;
                return old;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Удаляет пару ключ-значение, если значение совпадает.
     *
     * @param key   ключ
     * @param value значение
     * @return true, если удалено
     */
    public boolean remove(K key, V value) {
        int hash = hash(key);
        int idx = bucketIndex(hash);
        Entry<K, V> current = table[idx];
        Entry<K, V> prev = null;
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                if (!Objects.equals(current.value, value)) {
                    return false;
                }
                if (prev == null) {
                    table[idx] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                modCount++;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    /**
     * Увеличивает размер таблицы вдвое.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] old = table;
        table = new Entry[old.length * 2];
        for (Entry<K, V> head : old) {
            Entry<K, V> current = head;
            while (current != null) {
                Entry<K, V> next = current.next;
                int idx = bucketIndex(current.hash);
                current.next = table[idx];
                table[idx] = current;
                current = next;
            }
        }
        modCount++;
    }

    /**
     * Итератор по парам (k, v).
     *
     * @return итератор
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashIterator();
    }

    /**
     * Возвращает значение по ключу без привязки к параметру K (для equals).
     *
     * @param key ключ
     * @return значение или null
     */
    private Object getEntryValue(Object key) {
        int hash = hash(key);
        int idx = bucketIndex(hash);
        Entry<K, V> current = table[idx];
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Сравнение с другой таблицей.
     *
     * @param o другая таблица
     * @return true, если одинаковые пары
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HashTable)) {
            return false;
        }
        HashTable<?, ?> other = (HashTable<?, ?>) o;
        if (size != other.size) {
            return false;
        }
        for (Entry<K, V> head : table) {
            Entry<K, V> current = head;
            while (current != null) {
                Object otherValue = other.getEntryValue(current.key);
                if (!Objects.equals(current.value, otherValue)) {
                    return false;
                }
                current = current.next;
            }
        }
        return true;
    }

    /**
     * Хеш-код таблицы.
     *
     * @return хеш
     */
    @Override
    public int hashCode() {
        int h = 0;
        for (Entry<K, V> head : table) {
            Entry<K, V> current = head;
            while (current != null) {
                h += Objects.hashCode(current.key) ^ Objects.hashCode(current.value);
                current = current.next;
            }
        }
        return h;
    }

    /**
     * Строковое представление.
     *
     * @return строка
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Entry<K, V> head : table) {
            Entry<K, V> current = head;
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.key).append('=').append(current.value);
                first = false;
                current = current.next;
            }
        }
        return sb.append('}').toString();
    }

    /**
     * Пара ключ-значение.
     *
     * @param <K> тип ключа
     * @param <V> тип значения
     */
    public static class Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Entry<K, V> next;

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Возвращает ключ.
         *
         * @return ключ
         */
        public K getKey() {
            return key;
        }

        /**
         * Возвращает значение.
         *
         * @return значение
         */
        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private final class HashIterator implements Iterator<Entry<K, V>> {
        private int expectedModCount = modCount;
        private int bucket;
        private Entry<K, V> next;
        private Entry<K, V> current;

        HashIterator() {
            advance();
        }

        private void advance() {
            while (bucket < table.length) {
                Entry<K, V> head = table[bucket];
                if (head != null) {
                    next = head;
                    bucket++;
                    return;
                }
                bucket++;
            }
            next = null;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Entry<K, V> next() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException("Таблица изменена во время итерации");
            }
            if (next == null) {
                throw new NoSuchElementException();
            }
            current = next;
            Entry<K, V> result = current;
            if (current.next != null) {
                next = current.next;
            } else {
                advance();
            }
            return result;
        }
    }
}