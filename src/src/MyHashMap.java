public class MyHashMap<K, V> {

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    Node<K, V>[] table;
    int size;
    int threshold;

    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final String toString() {
            return key + "=" + value;
        }
    }

    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public V get(Object key) {
        Node<K, V> e;
        return (e = getNode(key)) == null ? null : e.value;
    }

    private Node<K, V> getNode(Object key) {
        Node<K, V>[] tab; Node<K, V> first, e; int n, hash; K k;
        if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & (hash = hash(key))]) != null) {
            if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                do {
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    public V put(K key, V value) {
        int hash = hash(key); Node<K, V>[] tab; Node<K, V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0) n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & hash]) == null) tab[i] = new Node<>(hash, key, value, null);
        else {
            Node<K, V> e; K k;
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) e = p;
            else {
                for (; ; ) {
                    if ((e = p.next) == null) {
                        p.next = new Node<>(hash, key, value, null);
                        break;
                    }
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        if (++size > threshold)
            resize();
        return null;
    }

    private Node<K, V>[] resize() {
        Node<K, V>[] oldTable = table;
        int oldCapacity = (oldTable == null) ? 0 : oldTable.length;
        int oldThreshold = threshold;
        int newCapacity, newThreshold = 0;
        if (oldCapacity > 0) {
            if (oldCapacity >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTable;
            } else if ((newCapacity = oldCapacity << 1) < MAXIMUM_CAPACITY && oldCapacity >= DEFAULT_INITIAL_CAPACITY)
                newThreshold = oldThreshold << 1;
        } else if (oldThreshold > 0) newCapacity = oldThreshold;
        else {
            newCapacity = DEFAULT_INITIAL_CAPACITY;
            newThreshold = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThreshold == 0) {
            float ft = (float) newCapacity * DEFAULT_LOAD_FACTOR;
            newThreshold =
                    (newCapacity < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ? (int) ft : Integer.MAX_VALUE);
        }
        threshold = newThreshold;
        @SuppressWarnings({"unchecked"})
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];
        table = newTable;
        if (oldTable != null) {
            for (int j = 0; j < oldCapacity; ++j) {
                Node<K, V> e;
                if ((e = oldTable[j]) != null) {
                    oldTable[j] = null;
                    if (e.next == null) newTable[e.hash & (newCapacity - 1)] = e;
                    else {
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCapacity) == 0) {
                                if (loTail == null) loHead = e;
                                else loTail.next = e;
                                loTail = e;
                            } else {
                                if (hiTail == null) hiHead = e;
                                else hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTable[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTable[j + oldCapacity] = hiHead;
                        }
                    }
                }
            }
        }
        return newTable;
    }

    public V remove(Object key) {
        Node<K, V> e;
        return (e = removeNode(hash(key), key, null)) == null ? null : e.value;
    }

    private Node<K, V> removeNode(int hash, Object key, Object value) {
        Node<K, V>[] tab; Node<K, V> p; int n, index;
        if ((tab = table) != null && (n = tab.length) > 0 && (p = tab[index = (n - 1) & hash]) != null) {
            Node<K, V> node = null, e; K k; V v;
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) node = p;
            else if ((e = p.next) != null) {
                do {
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                        node = e;
                        break;
                    }
                    p = e;
                } while ((e = e.next) != null);
            }
            if (node != null) {
                if (node == p) tab[index] = node.next;
                else p.next = node.next;
                --size;
                return node;
            }
        }
        return null;
    }
}
