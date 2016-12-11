package net.milanqiu.mimas.collect.traversal;

import net.milanqiu.mimas.instrumentation.DebugUtils;

import java.util.*;

/**
 * A traverser with pre-order, post-order and breadth-first traversals supporting.
 * Each traversal finishes a completed process over the elements in a traversable data structure.
 * There is a listener instance of {@link TraversalListener} to catch traversal events.
 * <p>
 * Creation Date: 2016-09-27
 * @author Milan Qiu
 */
public class CompletedTraverser {

    /**
     * The event listener during the traversal.
     */
    protected TraversalListener listener;
    /**
     * Whether the data structure to be traversed allows cycle.
     */
    protected boolean allowsCycle;

    /**
     * Returns the event listener during the traversal.
     * @return the event listener during the traversal
     */
    public TraversalListener getListener() {
        return listener;
    }
    /**
     * Returns whether the data structure to be traversed allows cycle.
     * If allows, the traversal will use more complex algorithms.
     * @return {@code true} if the data structure to be traversed allows cycle
     */
    public boolean isAllowsCycle() {
        return allowsCycle;
    }

    private CompletedTraverser() {}

    /**
     * Creates and returns a new instance of {@code CompletedTraverser} with the specified parameter.
     * @param listener the event listener during the traversal
     * @param allowsCycle whether the data structure to be traversed allows cycle
     * @return the new instance of {@code CompletedTraverser}
     */
    public static CompletedTraverser create(TraversalListener listener, boolean allowsCycle) {
        CompletedTraverser result = new CompletedTraverser();
        result.listener = listener;
        result.allowsCycle = allowsCycle;
        return result;
    }

    private void preOrderTraversal(Traversable element, int level) {
        listener.visitElement(level, element);
        Traversable prevAdjacency = null;
        for (Traversable adjacency : element.adjacencies()) {
            if (prevAdjacency == null) {
                level++;
                listener.enterNextLevel(level, element, adjacency);
            } else {
                listener.travelBetweenAdjacencies(level, prevAdjacency, adjacency);
            }
            prevAdjacency = adjacency;
            preOrderTraversal(adjacency, level);
        }
        if (prevAdjacency != null) {
            level--;
            listener.enterPrevLevel(level, prevAdjacency, element);
        }
    }

    private void preOrderTraversalAllowsCycle(Traversable element, int level, Set<Traversable> visitedElements) {
        visitedElements.add(element);
        listener.visitElement(level, element);
        Traversable prevAdjacency = null;
        for (Traversable adjacency : element.adjacencies()) {
            if (visitedElements.contains(adjacency))
                continue;
            if (prevAdjacency == null) {
                level++;
                listener.enterNextLevel(level, element, adjacency);
            } else {
                listener.travelBetweenAdjacencies(level, prevAdjacency, adjacency);
            }
            prevAdjacency = adjacency;
            preOrderTraversalAllowsCycle(adjacency, level, visitedElements);
        }
        if (prevAdjacency != null) {
            level--;
            listener.enterPrevLevel(level, prevAdjacency, element);
        }
    }

    /**
     * Finishes a completed process over the elements in a traversable data structure, using pre-order traversal.
     * @param startingElement the starting element of traversal
     */
    public void preOrderTraversal(Traversable startingElement) {
        if (allowsCycle) {
            Set<Traversable> visitedElements = new HashSet<>();
            preOrderTraversalAllowsCycle(startingElement, 0, visitedElements);
        } else {
            preOrderTraversal(startingElement, 0);
        }
    }

    private void postOrderTraversal(Traversable element, int level) {
        Traversable prevAdjacency = null;
        for (Traversable adjacency : element.adjacencies()) {
            if (prevAdjacency == null) {
                level++;
                listener.enterNextLevel(level, element, adjacency);
            } else {
                listener.travelBetweenAdjacencies(level, prevAdjacency, adjacency);
            }
            prevAdjacency = adjacency;
            postOrderTraversal(adjacency, level);
        }
        if (prevAdjacency != null) {
            level--;
            listener.enterPrevLevel(level, prevAdjacency, element);
        }
        listener.visitElement(level, element);
    }

    private void postOrderTraversalAllowsCycle(Traversable element, int level, Set<Traversable> visitedElements) {
        visitedElements.add(element);
        Traversable prevAdjacency = null;
        for (Traversable adjacency : element.adjacencies()) {
            if (visitedElements.contains(adjacency))
                continue;
            if (prevAdjacency == null) {
                level++;
                listener.enterNextLevel(level, element, adjacency);
            } else {
                listener.travelBetweenAdjacencies(level, prevAdjacency, adjacency);
            }
            prevAdjacency = adjacency;
            postOrderTraversalAllowsCycle(adjacency, level, visitedElements);
        }
        if (prevAdjacency != null) {
            level--;
            listener.enterPrevLevel(level, prevAdjacency, element);
        }
        listener.visitElement(level, element);
    }

    /**
     * Finishes a completed process over the elements in a traversable data structure, using post-order traversal.
     * @param startingElement the starting element of traversal
     */
    public void postOrderTraversal(Traversable startingElement) {
        if (allowsCycle) {
            Set<Traversable> visitedElements = new HashSet<>();
            postOrderTraversalAllowsCycle(startingElement, 0, visitedElements);
        } else {
            postOrderTraversal(startingElement, 0);
        }
    }

    private static class NavigatorSign implements Traversable {
        @Override
        public Iterable<Traversable> adjacencies() {
            return Collections.emptyList();
        }
    }

    private static final NavigatorSign ENTER_NEXT_LEVEL_SIGN = new NavigatorSign();
    private static final NavigatorSign TRAVEL_BETWEEN_ADJACENCIES_SIGN = new NavigatorSign();

    private void breadthFirstTraversalNoCycle(Traversable startingElement) {
        Queue<Traversable> queue = new ArrayDeque<>();
        queue.offer(startingElement);
        queue.offer(ENTER_NEXT_LEVEL_SIGN);

        int level = 0;
        Traversable prevElement = null;
        while (!queue.isEmpty()) {
            Traversable element = queue.poll();
            if (element == ENTER_NEXT_LEVEL_SIGN) {
                if (!queue.isEmpty()) {
                    level++;
                    listener.enterNextLevel(level, prevElement, queue.peek());
                    queue.offer(ENTER_NEXT_LEVEL_SIGN);
                }
            } else if (element == TRAVEL_BETWEEN_ADJACENCIES_SIGN) {
                if (!queue.isEmpty()) {
                    listener.travelBetweenAdjacencies(level, prevElement, queue.peek());
                } else {
                    DebugUtils.neverGoesHere();
                }
            } else {
                listener.visitElement(level, element);
                prevElement = element;
                boolean isFirstAdjacency = true;
                for (Traversable adjacency : element.adjacencies()) {
                    if (isFirstAdjacency)
                        isFirstAdjacency = false;
                    else
                        queue.offer(TRAVEL_BETWEEN_ADJACENCIES_SIGN);
                    queue.offer(adjacency);
                }
            }
        }
    }

    private void breadthFirstTraversalAllowsCycle(Traversable startingElement) {
        Queue<Traversable> queue = new ArrayDeque<>();
        queue.offer(startingElement);
        queue.offer(ENTER_NEXT_LEVEL_SIGN);

        int level = 0;
        Traversable prevElement = null;
        Set<Traversable> visitedElements = new HashSet<>();
        while (!queue.isEmpty()) {
            Traversable element = queue.poll();
            if (element == ENTER_NEXT_LEVEL_SIGN) {
                if (!queue.isEmpty()) {
                    level++;
                    listener.enterNextLevel(level, prevElement, queue.peek());
                    queue.offer(ENTER_NEXT_LEVEL_SIGN);
                }
            } else if (element == TRAVEL_BETWEEN_ADJACENCIES_SIGN) {
                if (!queue.isEmpty()) {
                    listener.travelBetweenAdjacencies(level, prevElement, queue.peek());
                } else {
                    DebugUtils.neverGoesHere();
                }
            } else {
                visitedElements.add(element);
                listener.visitElement(level, element);
                prevElement = element;
                boolean isFirstAdjacency = true;
                for (Traversable adjacency : element.adjacencies()) {
                    if (visitedElements.contains(adjacency) || queue.contains(adjacency))
                        continue;
                    if (isFirstAdjacency)
                        isFirstAdjacency = false;
                    else
                        queue.offer(TRAVEL_BETWEEN_ADJACENCIES_SIGN);
                    queue.offer(adjacency);
                }
            }
        }
    }

    /**
     * Finishes a completed process over the elements in a traversable data structure, using breadth-first traversal.
     * @param startingElement the starting element of traversal
     */
    public void breadthFirstTraversal(Traversable startingElement) {
        if (allowsCycle) {
            breadthFirstTraversalAllowsCycle(startingElement);
        } else {
            breadthFirstTraversalNoCycle(startingElement);
        }
    }
}
