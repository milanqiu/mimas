package net.milanqiu.mimas.lang;

/**
 * This class holds an object, just like a pointer.
 * <p>
 * A typical usage is when a variable is required to be final, such as in lambda expression,
 * this class is efficient to box the variable, making it final in form but modifiable in fact.
 * <p>
 * Creation Date: 2016-08-04
 * @author Milan Qiu
 */
public final class Pointer<D> {

    /**
     * Contained data.
     */
    private D data;

    /**
     * Constructs a pointer with the specified contained data.
     * @param data the contained data
     */
    public Pointer(D data) {
        this.data = data;
    }

    /**
     * Returns the contained data.
     * @return the contained data
     */
    public D get() {
        return data;
    }

    /**
     * A setter corresponding to the getter {@link #get()}.
     * @param data the new value of the contained data
     * @return the old value of the contained data
     */
    public D set(D data) {
        D result = this.data;
        this.data = data;
        return result;
    }

    /**
     * Compares the specified object with this pointer for equality.
     * Return {@code true} if contained data of the given object is equal to contained data of this pointer.
     * @param o the object to be tested for equality with this pointer
     * @return equality result
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pointer<?> pointer = (Pointer<?>) o;

        return !(data != null ? !data.equals(pointer.data) : pointer.data != null);
    }

    /**
     * Returns the hash code value for this pointer.
     * @return the hash code value for this pointer
     */
    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }

    /**
     * Returns a string representation of this object, which is equal to string representation of the contained data.
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return data != null ? data.toString() : "";
    }
}
