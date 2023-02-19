package net.milanqiu.mimas.collect;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Utilities related to map.
 * <p>
 * Creation Date: 2023-02-19
 * @author Milan Qiu
 */
public class MapUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private MapUtils() {}

    /**
     * Returns a string representation of a typed map, whose value is an array.
     * @param map the typed map to be represented
     * @param <K> the class of the key in the map entry
     * @param <V> the element class of the value array in the map entry
     * @return a string representation of the typed map
     */
    public static <K, V> String toString(Map<K, V[]> map) {
        Iterator<Map.Entry<K, V[]>> itr = map.entrySet().iterator();
        if (!itr.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            Map.Entry<K, V[]> entry = itr.next();
            K key = entry.getKey();
            V[] value = entry.getValue();
            sb.append(key);
            sb.append('=');
            sb.append(Arrays.toString(value));
            if (!itr.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }
}
