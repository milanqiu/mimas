package net.milanqiu.mimas.collect;

import java.util.*;

/**
 * An ordered {@link java.util.Properties}.
 * <p>
 * All traversals on properties will keep the same order as put.
 * <p>
 * Creation Date: 2014-12-20
 * @author Milan Qiu
 */
public class OrderedProperties extends Properties {

    private transient volatile LinkedHashSet<Object> orderedKeys = new LinkedHashSet<>();

    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(orderedKeys);
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        orderedKeys.add(key);
        return super.put(key, value);
    }

    @Override
    public Set<Object> keySet() {
        return new LinkedHashSet<>(orderedKeys);
    }

    @Override
    public Enumeration<?> propertyNames() {
        return Collections.enumeration(new LinkedHashSet<>(orderedKeys));
    }

    @Override
    public Set<String> stringPropertyNames() {
        Set<String> result = new LinkedHashSet<>();
        for (Object key : orderedKeys) {
            result.add((String) key);
        }
        return result;
    }
}
