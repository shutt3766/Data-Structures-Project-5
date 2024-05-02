public class Table<K, E> {
    private int manyItems;
    private Object[] keys;
    private Object[] data;
    private boolean[] hasBeenUsed;
    private int collisions; // New instance variable to track collisions

    public Table(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity is negative");
        keys = new Object[capacity];
        data = new Object[capacity];
        hasBeenUsed = new boolean[capacity];
    }

    public boolean containsKey(K key) {
        return findIndex(key) != -1;
    }

    private int findIndex(K key) {
        int count = 0;
        int i = hash(key);

        while (count < data.length && hasBeenUsed[i]) {
            if (key.equals(keys[i]))
                return i;
            count++;
            i = nextIndex(i);
        }

        return -1;
    }

    public E get(K key) {
        int index = findIndex(key);

        if (index == -1)
            return null;
        else
            return (E) data[index];
    }

    private int hash(Object key) {
        return Math.abs(key.hashCode()) % data.length;
    }

    private int nextIndex(int i) {
        if (i + 1 == data.length)
            return 0;
        else
            return i + 1;
    }

    public E put(K key, E element) {
        int index = findIndex(key);
        E answer;

        if (index != -1) {
            answer = (E) data[index];
            data[index] = element;
            return answer;
        } else if (manyItems < data.length) {
            index = hash(key);
            while (keys[index] != null) {
                index = nextIndex(index);
                collisions++; // Increment collision count
            }
            keys[index] = key;
            data[index] = element;
            hasBeenUsed[index] = true;
            manyItems++;
            return null;
        } else {
            throw new IllegalStateException("Table is full.");
        }
    }

    public E remove(K key) {
        int index = findIndex(key);
        E answer = null;

        if (index != -1) {
            answer = (E) data[index];
            keys[index] = null;
            data[index] = null;
            manyItems--;
        }

        return answer;
    }

    public int getCollisions() {
        return collisions;
    }
}



           
