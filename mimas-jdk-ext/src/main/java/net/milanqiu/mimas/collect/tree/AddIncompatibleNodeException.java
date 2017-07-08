package net.milanqiu.mimas.collect.tree;

/**
 * {@code AddIncompatibleNodeException} is thrown when it tries to add an incompatible node into a tree.
 * <p>
 * Creation Date: 2016-12-20
 * @author Milan Qiu
 */
public class AddIncompatibleNodeException extends TreeException {

    /**
     * Constructs a new {@code AddIncompatibleNodeException} with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public AddIncompatibleNodeException() {
        super();
    }
}
