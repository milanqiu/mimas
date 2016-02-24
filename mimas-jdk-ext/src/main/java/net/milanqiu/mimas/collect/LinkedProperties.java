package net.milanqiu.mimas.collect;

import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * An linked {@link java.util.Properties} with predictable iteration order according to insertion order.
 * <p>
 * It embeds a {@link java.util.LinkedHashMap} object to implement the request,
 * overriding all methods of inherited {@link java.util.Hashtable}.
 * <p>
 * Creation Date: 2014-12-20
 * @author Milan Qiu
 */
public class LinkedProperties extends Properties {

    private transient LinkedHashMap<Object, Object> linkedCopy = new LinkedHashMap<>();

    /**
     * Constructs a new {@code LinkedProperties} with no default values.
     */
    public LinkedProperties() {
        super();
    }

    /**
     * Constructs a new {@code LinkedProperties} with the specified defaults.
     * @param defaults the defaults
     */
    public LinkedProperties(LinkedProperties defaults) {
        super(defaults);
    }

    /*
        Overrides of methods from Hashtable.
     */

    @Override
    public synchronized int size() {
        return linkedCopy.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return linkedCopy.isEmpty();
    }

    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(linkedCopy.keySet());
    }

    @Override
    public synchronized Enumeration<Object> elements() {
        return Collections.enumeration(linkedCopy.values());
    }

    @Override
    public synchronized boolean contains(Object value) {
        return linkedCopy.containsValue(value);
    }

    @Override
    public boolean containsValue(Object value) {
        return linkedCopy.containsValue(value);
    }

    @Override
    public synchronized boolean containsKey(Object key) {
        return linkedCopy.containsKey(key);
    }

    @Override
    public synchronized Object get(Object key) {
        return linkedCopy.get(key);
    }

    @Override
    protected void rehash() {
        super.rehash();
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        super.put(key, value);
        return linkedCopy.put(key, value);
    }

    @Override
    public synchronized Object remove(Object key) {
        super.remove(key);
        return linkedCopy.remove(key);
    }

    @Override
    public synchronized void putAll(Map<?, ?> t) {
        super.putAll(t);
        linkedCopy.putAll(t);
    }

    @Override
    public synchronized void clear() {
        super.clear();
        linkedCopy.clear();
    }

    @Override
    public synchronized Object clone() {
        LinkedProperties result = (LinkedProperties) super.clone();
        result.linkedCopy.putAll(linkedCopy);
        return result;
    }

    @Override
    public synchronized String toString() {
        return linkedCopy.toString();
    }

    @Override
    public Set<Object> keySet() {
        return linkedCopy.keySet();
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return linkedCopy.entrySet();
    }

    @Override
    public Collection<Object> values() {
        return linkedCopy.values();
    }

    @Override
    public synchronized boolean equals(Object o) {
        return linkedCopy.equals(((LinkedProperties) o).linkedCopy);
    }

    @Override
    public synchronized int hashCode() {
        return linkedCopy.hashCode();
    }

    @Override
    public synchronized Object getOrDefault(Object key, Object defaultValue) {
        return linkedCopy.getOrDefault(key, defaultValue);
    }

    @Override
    public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
        linkedCopy.forEach(action);
    }

    @Override
    public synchronized void replaceAll(BiFunction<? super Object, ? super Object, ?> function) {
        super.replaceAll(function);
        linkedCopy.replaceAll(function);
    }

    @Override
    public synchronized Object putIfAbsent(Object key, Object value) {
        super.putIfAbsent(key, value);
        return linkedCopy.putIfAbsent(key, value);
    }

    @Override
    public synchronized boolean remove(Object key, Object value) {
        super.remove(key, value);
        return linkedCopy.remove(key, value);
    }

    @Override
    public synchronized boolean replace(Object key, Object oldValue, Object newValue) {
        super.replace(key, oldValue, newValue);
        return linkedCopy.replace(key, oldValue, newValue);
    }

    @Override
    public synchronized Object replace(Object key, Object value) {
        super.replace(key, value);
        return linkedCopy.replace(key, value);
    }

    @Override
    public synchronized Object computeIfAbsent(Object key, Function<? super Object, ?> mappingFunction) {
        super.computeIfAbsent(key, mappingFunction);
        return linkedCopy.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public synchronized Object computeIfPresent(Object key, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        super.computeIfPresent(key, remappingFunction);
        return linkedCopy.computeIfPresent(key, remappingFunction);
    }

    @Override
    public synchronized Object compute(Object key, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        super.compute(key, remappingFunction);
        return linkedCopy.compute(key, remappingFunction);
    }

    @Override
    public synchronized Object merge(Object key, Object value, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        super.merge(key, value, remappingFunction);
        return linkedCopy.merge(key, value, remappingFunction);
    }

    /*
        Overrides of methods from Properties.
     */

    @Override
    public Enumeration<?> propertyNames() {
        if (defaults == null)
            return keys();
        else {
            LinkedHashSet<Object> mergedKeys = new LinkedHashSet<>();
            mergedKeys.addAll(defaults.keySet());
            mergedKeys.addAll(keySet());
            return Collections.enumeration(mergedKeys);
        }
    }

    @Override
    public Set<String> stringPropertyNames() {
        Set<String> result = new LinkedHashSet<>();
        if (defaults != null)
            defaults.keySet().forEach(key -> result.add((String) key));
        keySet().forEach(key -> result.add((String) key));
        return result;
    }

    @Override
    public void list(PrintStream out) {
        out.println("-- listing properties --");
        LinkedHashMap<Object, Object> mergedMap = new LinkedHashMap<>();
        if (defaults != null)
            mergedMap.putAll(((LinkedProperties) defaults).linkedCopy);
        mergedMap.putAll(linkedCopy);
        mergedMap.entrySet().forEach(e -> {
            String key = (String) e.getKey();
            String val = (String) e.getValue();
            if (val.length() > 40) {
                val = val.substring(0, 37) + "...";
            }
            out.println(key + "=" + val);
        });
    }

    @Override
    public void list(PrintWriter out) {
        out.println("-- listing properties --");
        LinkedHashMap<Object, Object> mergedMap = new LinkedHashMap<>();
        if (defaults != null)
            mergedMap.putAll(((LinkedProperties) defaults).linkedCopy);
        mergedMap.putAll(linkedCopy);
        mergedMap.entrySet().forEach(e -> {
            String key = (String) e.getKey();
            String val = (String) e.getValue();
            if (val.length() > 40) {
                val = val.substring(0, 37) + "...";
            }
            out.println(key + "=" + val);
        });
    }
}
