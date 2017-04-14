package net.milanqiu.mimas.collect.tree;

/**
 * {@code RemoveRootException} is thrown when it tries to remove the root from a tree.
 * <p>
 * Creation Date: 2016-12-14
 * @author Milan Qiu
 */
public class RemoveRootException extends TreeException {

    /**
     * Constructs a new {@code RemoveRootException} with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public RemoveRootException() {
        super();
    }
}
