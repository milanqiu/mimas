package net.milanqiu.mimas.lang;

import java.util.Map;

/**
 * A direct and visible implementation of Map.Entry.
 * All other implementations of Map.Entry are inner classes, such as HashMap.Entry. They are tightly coupling with
 * outer classes and hard to be used.
 *
 * <p>Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class MapEntry<K, V> implements Map.Entry<K, V> {
    protected K key;
    protected V value;

    private MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Creates a new <code>MapEntry</code> object with the specified key and value.
     * @param key the specified key
     * @param value the specified value
     * @return the new created <code>MapEntry</code> object
     */
    public static <K, V> MapEntry<K, V> create(K key, V value) {
        return new MapEntry<>(key, value);
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        // use the same implementation as HashMap.Entry
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        // use the same implementation as HashMap.Entry
        if (!(o instanceof Map.Entry))
            return false;
        Map.Entry e = (Map.Entry)o;
        Object k1 = getKey();
        Object k2 = e.getKey();
        if (k1 == k2 || (k1 != null && k1.equals(k2))) {
            Object v1 = getValue();
            Object v2 = e.getValue();
            if (v1 == v2 || (v1 != null && v1.equals(v2)))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // use the same implementation as HashMap.Entry
        return (key==null   ? 0 : key.hashCode()) ^
               (value==null ? 0 : value.hashCode());
    }

    @Override
    public String toString() {
        // use the same implementation as HashMap.Entry
        return getKey() + "=" + getValue();
    }
}
