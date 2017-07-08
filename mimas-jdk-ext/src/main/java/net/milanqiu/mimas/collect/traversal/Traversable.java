package net.milanqiu.mimas.collect.traversal;

/**
 * If a data structure needs normal traversal supporting, such as pre-order, post-order and breadth-first, the element
 * of the data structure should implement this interface.
 * Common traversable data structures are tree and graph.
 * <p>
 * Creation Date: 2016-09-27
 * @author Milan Qiu
 */
@FunctionalInterface
public interface Traversable {

    /**
     * Returns the adjacent elements of this traversable element. It is used to enter next level during traversal.
     * @return the adjacent elements of this traversable element
     */
    Iterable<Traversable> adjacencies();
}
