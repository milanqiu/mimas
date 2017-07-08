package net.milanqiu.mimas.collect.traversal;

/**
 * Event listener during the traversal. Used in {@link CompletedTraverser}.
 * <p>
 * Creation Date: 2016-09-27
 * @author Milan Qiu
 */
public interface TraversalListener {

    /**
     * Invoked when the traversal visits an element.
     * @param level the level of the element
     * @param element the element to be visited
     */
    void visitElement(int level, Traversable element);

    /**
     * Invoked when the traversal enters next level.
     * @param toLevel the next level to be entered
     * @param fromElement the element of previous level, which the entry comes from
     * @param toElement the element of next level, which the entry goes to
     */
    void enterNextLevel(int toLevel, Traversable fromElement, Traversable toElement);

    /**
     * Invoked when the traversal enters previous level.
     * @param toLevel the previous level to be entered
     * @param fromElement the element of next level, which the entry comes from
     * @param toElement the element of previous level, which the entry goes to
     */
    void enterPrevLevel(int toLevel, Traversable fromElement, Traversable toElement);

    /**
     * Invoked when the traversal travels between adjacencies of any element.
     * @param level the level of the travel
     * @param fromElement the element which the travel comes from
     * @param toElement the element which the travel goes to
     */
    void travelBetweenAdjacencies(int level, Traversable fromElement, Traversable toElement);
}
