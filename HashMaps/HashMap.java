import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of HashMap.
 * 
 * @author Karim Layoun
 * @userid klayoun3
 * @GTID 903210227
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        this.table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or Value is null");
        }
        size++;
        if (((float) size / table.length) > 0.67) {
            resizeBackingTable(2 * table.length + 1);
        }
        int index = Math.abs(key.hashCode() % table.length);
        if (table[index] == null) {
            MapEntry<K, V> entry = new MapEntry<>(key, value);
            table[index] = entry;
            return null;
        }
        MapEntry<K, V> temp = table[index];
        while (temp != null) {
            if (temp.getKey().equals(key)) {
                V ret = temp.getValue();
                temp.setValue(value);
                size--;
                return ret;
            }
            temp = temp.getNext();
        }
        MapEntry<K, V> entry = new MapEntry<>(key, value, table[index]);
        table[index] = entry;
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The Key is null");
        }
        int index = Math.abs(key.hashCode() % table.length);
        if (table[index] == null) {
            throw new NoSuchElementException("The given Key does not"
                    + " correspond to a Value in the current Map");
        }
        if (table[index].getNext() == null) {
            V ret = table[index].getValue();
            table[index] = null;
            size--;
            return ret;
        }
        MapEntry<K, V> temp = table[index];
        if (table[index].getKey().equals(key)) {
            V ret = table[index].getValue();
            table[index] = table[index].getNext();
            size--;
            return ret;
        }
        while (temp.getNext() != null) {
            if (temp.getNext().getKey().equals(key)) {
                V ret = temp.getNext().getValue();
                size--;
                temp.setNext(temp.getNext().getNext());
                return ret;
            }
            temp = temp.getNext();
        }
        throw new NoSuchElementException("The given Key does not"
                + " correspond to a Value in the current Map");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The Key is null");
        }
        int index = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> curr = table[index];
        while (curr != null) {
            if (curr.getKey().equals(key)) {
                return curr.getValue();
            }
            curr = curr.getNext();
        }
        throw new NoSuchElementException("The given Key does not "
                + "correspond to a Value in the current Map");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The Key is null");
        }
        return table[Math.abs(key.hashCode() % table.length)] != null;
    }

    @Override
    public void clear() {
        this.table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> ret = new HashSet<>();
        for (MapEntry<K, V> el: this.table) {
            while (el != null) {
                ret.add(el.getKey());
                el = el.getNext();
            }
        }
        return ret;
    }

    @Override
    public List<V> values() {
        List<V> ret = new ArrayList<>();
        for (MapEntry<K, V> el: this.table) {
            while (el != null) {
                ret.add(el.getValue());
                el = el.getNext();
            }
        }
        return ret;
    }

    /**
     * put as a helper method for resizeBackingTable
     *
     * @param entry the entry to be added
     * @param map the resized backing table
     */
    private void put(MapEntry<K, V> entry, MapEntry<K, V>[] map) {
        int index = Math.abs(entry.getKey().hashCode() % map.length);
        entry.setNext(map[index]);
        map[index] = entry;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length < 0 || length < this.size) {
            throw new IllegalArgumentException("The given length is not"
                    + " suitable for a proper table resizing");
        }
        MapEntry<K, V>[] temp = (MapEntry<K, V>[]) new MapEntry[length];
        for (MapEntry<K, V> el: table) {
            while (el != null) {
                this.put(new MapEntry<K, V>(el.getKey(), el.getValue()), temp);
                el = el.getNext();
            }
        }
        this.table = temp;
    }
    
    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
