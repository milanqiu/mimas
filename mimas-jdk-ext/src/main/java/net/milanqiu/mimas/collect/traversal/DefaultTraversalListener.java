package net.milanqiu.mimas.collect.traversal;

/**
 * Stub implementation of {@link TraversalListener} with all method bodies are empty.
 * <p>
 * Creation Date: 2016-12-09
 * @author Milan Qiu
 */
public class DefaultTraversalListener implements TraversalListener {

    @Override
    public void visitElement(int level, Traversable element) {}

    @Override
    public void enterNextLevel(int toLevel, Traversable fromElement, Traversable toElement) {}

    @Override
    public void enterPrevLevel(int toLevel, Traversable fromElement, Traversable toElement) {}

    @Override
    public void travelBetweenAdjacencies(int level, Traversable fromElement, Traversable toElement) {}
}
