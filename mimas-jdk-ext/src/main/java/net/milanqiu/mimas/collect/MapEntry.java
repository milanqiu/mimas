package net.milanqiu.mimas.collect;

import java.util.Map;
import java.util.Objects;

/**
 * A direct and visible implementation of {@link java.util.Map.Entry}.
 * <p>
 * All other implementations of {@link java.util.Map.Entry} are inner classes, such as {@link java.util.HashMap.Node}.
 * They are tightly coupling with outer classes and hard to be used.
 * <p>
 * Creation Date: 2014-07-25
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
     * Creates and returns a new instance of {@code MapEntry} with the specified key and value.
     * @param key the key of the new instance of {@code MapEntry}
     * @param value the value of the new instance of {@code MapEntry}
     * @return the new instance of {@code MapEntry}
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
    public final V setValue(V newValue) {
        // use the same implementation as HashMap.Node
        V oldValue = value;
        value = newValue;
        return oldValue;
    }

    @Override
    public boolean equals(Object o) {
        // use the same implementation as HashMap.Node
        if (o == this)
            return true;
        if (o instanceof Map.Entry) {
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            if (Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue()))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // use the same implementation as HashMap.Node
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    @Override
    public String toString() {
        // use the same implementation as HashMap.Node
        return key + "=" + value;
    }
}
