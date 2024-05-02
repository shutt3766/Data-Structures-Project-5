import java.util.LinkedList;

public class TableChainHash<K, E> {
    private int manyItems;
    private int collisions; // Track collisions
    private LinkedList<Entry<K, E>>[] table;

    public TableChainHash(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity is negative");
        table = new LinkedList[capacity];
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public boolean containsKey(K key) {
        int index = hash(key);
        LinkedList<Entry<K, E>> chain = table[index];
        for (Entry<K, E> entry : chain) {
            if (entry.getKey().equals(key))
                return true;
        }
        return false;
    }

    public E get(K key) {
        int index = hash(key);
        LinkedList<Entry<K, E>> chain = table[index];
        for (Entry<K, E> entry : chain) {
            if (entry.getKey().equals(key))
                return entry.getValue();
        }
        return null;
    }

    public E put(K key, E element) {
        int index = hash(key);
        LinkedList<Entry<K, E>> chain = table[index];
        for (Entry<K, E> entry : chain) {
            if (entry.getKey().equals(key)) {
                E oldValue = entry.getValue();
                entry.setValue(element);
                return oldValue;
            }
        }
        chain.add(new Entry<>(key, element));
        manyItems++;

        // Update collision count
        if (chain.size() > 1) {
            collisions++;
        }
        
        return null;
    }

    public E remove(K key) {
        int index = hash(key);
        LinkedList<Entry<K, E>> chain = table[index];
        for (Entry<K, E> entry : chain) {
            if (entry.getKey().equals(key)) {
                chain.remove(entry);
                manyItems--;

                // Update collision count
                if (chain.size() > 1) {
                    collisions--;
                }

                return entry.getValue();
            }
        }
        return null;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    public int getCollisions() {
        return collisions;
    }

    private static class Entry<K, E> {
        private K key;
        private E value;

        public Entry(K key, E value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }
    }
}


