package net.milanqiu.mimas.collect.tree;

/**
 * {@code AddSiblingToRootException} is thrown when it tries to add a sibling node to the root of a tree.
 * <p>
 * Creation Date: 2016-12-14
 * @author Milan Qiu
 */
public class AddSiblingToRootException extends TreeException {

    /**
     * Constructs a new {@code AddSiblingToRootException} with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public AddSiblingToRootException() {
        super();
    }
}
