package net.milanqiu.mimas.lang;

import java.util.NoSuchElementException;

/**
 * A cache containing only one data object.
 * <p>
 * Creation Date: 2016-10-07
 * @author Milan Qiu
 */
public final class SingletonCache<D> {

    /**
     * Whether the cache is enabled.
     */
    private boolean enabled = false;
    /**
     * Cached data.
     */
    private D data;

    /**
     * Constructs a singleton cache with no data so that the cache is disabled.
     */
    public SingletonCache() {}

    /**
     * Constructs a singleton cache with the specified cached data so that the cache is enabled.
     * @param data the cached data
     */
    public SingletonCache(D data) {
        setData(data);
    }

    /**
     * Returns whether the cache is enabled.
     * @return whether the cache is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Disables the cache.
     */
    public void disable() {
        enabled = false;
    }

    /**
     * Returns the cached data.
     * @return the cached data
     * @throws NoSuchElementException if the cache is disabled
     */
    public D getData() {
        if (!enabled)
            throw new NoSuchElementException("SingletonCache disabled");
        return data;
    }

    /**
     * Assigns the cached data.
     * It will enable the cache at the same time.
     * @param data the new value of the cached data
     */
    public void setData(D data) {
        enabled = true;
        this.data = data;
    }

    /**
     * Compares the specified object with this singleton cache for equality.
     * Return {@code true} if both caches are disabled, or both caches are enabled and cached data of the given object
     * is equal to cached data of this singleton cache.
     * @param o the object to be tested for equality with this singleton cache
     * @return equality result
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingletonCache<?> cache = (SingletonCache<?>) o;

        if (enabled && cache.enabled) {
            return !(data != null ? !data.equals(cache.data) : cache.data != null);
        } else {
            return !enabled && !cache.enabled;
        }
    }

    /**
     * Returns the hash code value for this singleton cache.
     * @return the hash code value for this singleton cache
     */
    @Override
    public int hashCode() {
        int result = (enabled ? 1 : 0);
        if (enabled) {
            result = 31 * result + (data != null ? data.hashCode() : 0);
        }
        return result;
    }

    /**
     * Returns a string representation of this object, which includes enabled flag and string representation of the cached data.
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "SingletonCache " + (enabled ? ("enabled : " + (data != null ? data.toString() : "")) : "disabled");
    }
}
