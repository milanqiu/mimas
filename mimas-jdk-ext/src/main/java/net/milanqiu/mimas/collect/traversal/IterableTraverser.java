package net.milanqiu.mimas.collect.traversal;

import net.milanqiu.mimas.lang.SingletonCache;

import java.util.*;
import java.util.function.Predicate;

/**
 * A traverser with pre-order, post-order and breadth-first traversals supporting.
 * Each traversal returns an unmodifiable iterable over the elements in a traversable data structure.
 * No guarantees are made about the behavior of the traversal when elements change while iteration is in progress.
 * <p>
 * Creation Date: 2016-09-27
 * @author Milan Qiu
 */
public class IterableTraverser {

    /**
     * Whether the data structure to be traversed allows cycle.
     */
    protected boolean allowsCycle;

    /**
     * Returns whether the data structure to be traversed allows cycle.
     * If allows, the traversal will use more complex algorithms.
     * @return {@code true} if the data structure to be traversed allows cycle
     */
    public boolean isAllowsCycle() {
        return allowsCycle;
    }

    private IterableTraverser() {}

    /**
     * Creates and returns a new instance of {@code IterableTraverser} with the specified parameter.
     * @param allowsCycle whether the data structure to be traversed allows cycle
     * @return the new instance of {@code IterableTraverser}
     */
    public static IterableTraverser create(boolean allowsCycle) {
        IterableTraverser result = new IterableTraverser();
        result.allowsCycle = allowsCycle;
        return result;
    }

    /**
     * A last-in-first-out (LIFO) stack of iterators.
     */
    private static class StackOfIterator<T> extends ArrayDeque<Iterator<T>> {
        /**
         * Creates a new iterator containing only the specified element and pushes it into stack.
         * @param element the element to be contained by the new iterator
         */
        private void pushElement(T element) {
            push(Collections.singletonList(element).iterator());
        }

        /**
         * Pushes the specified iterator into stack.
         * @param iterator the iterator to be pushed
         */
        private void pushIterator(Iterator<T> iterator) {
            if (iterator.hasNext())
                push(iterator);
        }

        /**
         * Pops the next element of top iterator of stack.
         * After pop, it will remove the top iterator from the stack if it has no more elements.
         * @return the next element of top iterator of stack
         * @throws NoSuchElementException if stack empty, or the top iterator has no more elements
         */
        private T popElement() {
            if (isEmpty())
                throw new NoSuchElementException();
            T result = peek().next();
            trim();
            return result;
        }

        /**
         * Pops the next valid element in stack.
         * If all leaving elements are invalid, returns {@code null}.
         * During pop, it will remove all traversed iterators from the stack if they have no more elements.
         * @param isValid the predicate of valid
         * @return the next valid element in stack, or {@code null} if all leaving elements are invalid
         */
        private T popValidElement(Predicate<T> isValid) {
            while (!isEmpty()) {
                Iterator<T> elements = peek();
                while (elements.hasNext()) {
                    T result = elements.next();
                    if (isValid.test(result)) {
                        trim();
                        return result;
                    }
                }
                pop();
            }
            return null;
        }

        /**
         * Removes the top iterator from stack if it has no more elements.
         */
        private void trim() {
            if (!peek().hasNext())
                pop();
        }
    }

    private Iterator<Traversable> preOrderTraversalIterator(Traversable startingElement) {
        return new Iterator<Traversable>() {
            private StackOfIterator<Traversable> stack;

            {
                stack = new StackOfIterator<>();
                stack.pushElement(startingElement);
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public Traversable next() {
                Traversable result = stack.popElement();
                stack.pushIterator(result.adjacencies().iterator());
                return result;
            }
        };
    }

    private Iterator<Traversable> preOrderTraversalIteratorAllowsCycle(Traversable startingElement) {
        return new Iterator<Traversable>() {
            private StackOfIterator<Traversable> stack;
            private SingletonCache<Traversable> nextElementCache;
            private Set<Traversable> visitedElements;

            {
                stack = new StackOfIterator<>();
                stack.pushElement(startingElement);
                nextElementCache = new SingletonCache<>();
                visitedElements = new HashSet<>();
            }

            private Traversable nextElement() {
                return nextElementCache.getData(() -> stack.popValidElement((element) -> !visitedElements.contains(element)));
            }

            @Override
            public boolean hasNext() {
                return nextElement() != null;
            }

            @Override
            public Traversable next() {
                Traversable result = nextElement();
                if (result == null)
                    throw new NoSuchElementException();
                visitedElements.add(result);
                nextElementCache.disable();
                stack.pushIterator(result.adjacencies().iterator());
                return result;
            }
        };
    }

    /**
     * Returns an unmodifiable iterable over the elements in a traversable data structure, using pre-order traversal.
     * @param startingElement the starting element of traversal
     * @return an unmodifiable iterable over the elements in a traversable data structure, using pre-order traversal
     */
    public Iterable<Traversable> preOrderTraversal(Traversable startingElement) {
        return new Iterable<Traversable>() {
            @Override
            public Iterator<Traversable> iterator() {
                if (allowsCycle) {
                    return preOrderTraversalIteratorAllowsCycle(startingElement);
                } else {
                    return preOrderTraversalIterator(startingElement);
                }
            }
        };
    }

    private Iterator<Traversable> postOrderTraversalIterator(Traversable startingElement) {
        return new Iterator<Traversable>() {
            private StackOfIterator<Traversable> stack;
            private Set<Traversable> expandedElements;

            {
                stack = new StackOfIterator<>();
                stack.pushElement(startingElement);
                expandedElements = new HashSet<>();
            }

            private Traversable expand(Traversable element) {
                expandedElements.add(element);
                Iterator<Traversable> adjacencies = element.adjacencies().iterator();
                if (adjacencies.hasNext()) {
                    stack.pushElement(element);
                    stack.pushIterator(adjacencies);
                    return stack.popElement();
                } else {
                    return element;
                }
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public Traversable next() {
                Traversable result = stack.popElement();
                while (!expandedElements.contains(result)) {
                    result = expand(result);
                }
                return result;
            }
        };
    }

    private Iterator<Traversable> postOrderTraversalIteratorAllowsCycle(Traversable startingElement) {
        return new Iterator<Traversable>() {
            private StackOfIterator<Traversable> stack;
            private SingletonCache<Traversable> nextElementCache;
            private Set<Traversable> visitedElements;
            private Deque<Traversable> reservedElements;

            {
                stack = new StackOfIterator<>();
                stack.pushElement(startingElement);
                nextElementCache = new SingletonCache<>();
                visitedElements = new HashSet<>();
                reservedElements = new ArrayDeque<>();
            }

            private Traversable expand(Traversable element) {
                reservedElements.push(element);
                Iterator<Traversable> adjacencies = element.adjacencies().iterator();
                while (adjacencies.hasNext()) {
                    Traversable adjacency = adjacencies.next();
                    if (!visitedElements.contains(adjacency) && !reservedElements.contains(adjacency)) {
                        stack.pushElement(element);
                        stack.pushIterator(adjacencies);
                        return adjacency;
                    }
                }
                return element;
            }

            private Traversable nextElement() {
                if (nextElementCache.isEnabled()) {
                    return nextElementCache.getData();
                } else {
                    Traversable result = stack.popValidElement((element) ->
                            !visitedElements.contains(element) && (!reservedElements.contains(element) || reservedElements.peek() == element));
                    if (result != null) {
                        while (!reservedElements.contains(result)) {
                            result = expand(result);
                        }
                        if (reservedElements.peek() == result)
                            reservedElements.pop();
                    }
                    nextElementCache.setData(result);
                    return result;
                }
            }

            @Override
            public boolean hasNext() {
                return nextElement() != null;
            }

            @Override
            public Traversable next() {
                Traversable result = nextElement();
                if (result == null)
                    throw new NoSuchElementException();
                visitedElements.add(result);
                nextElementCache.disable();
                return result;
            }
        };
    }

    /**
     * Returns an unmodifiable iterable over the elements in a traversable data structure, using post-order traversal.
     * @param startingElement the starting element of traversal
     * @return an unmodifiable iterable over the elements in a traversable data structure, using post-order traversal
     */
    public Iterable<Traversable> postOrderTraversal(Traversable startingElement) {
        return new Iterable<Traversable>() {
            @Override
            public Iterator<Traversable> iterator() {
                if (allowsCycle) {
                    return postOrderTraversalIteratorAllowsCycle(startingElement);
                } else {
                    return postOrderTraversalIterator(startingElement);
                }
            }
        };
    }

    private Iterator<Traversable> breadthFirstTraversalIterator(Traversable startingElement) {
        return new Iterator<Traversable>() {
            private Queue<Traversable> queue;

            {
                queue = new ArrayDeque<>();
                queue.offer(startingElement);
            }

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public Traversable next() {
                Traversable result = queue.poll();
                if (result == null)
                    throw new NoSuchElementException();
                result.adjacencies().forEach((queue::offer));
                return result;
            }
        };
    }

    private Iterator<Traversable> breadthFirstTraversalIteratorAllowsCycle(Traversable startingElement) {
        return new Iterator<Traversable>() {
            private Queue<Traversable> queue;
            private SingletonCache<Traversable> nextElementCache;
            private Set<Traversable> visitedElements;

            {
                queue = new ArrayDeque<>();
                queue.offer(startingElement);
                nextElementCache = new SingletonCache<>();
                visitedElements = new HashSet<>();
            }

            private Traversable nextElement() {
                return nextElementCache.getData(() -> {
                    Traversable result;
                    do {
                        result = queue.poll();
                    } while (result != null && visitedElements.contains(result));
                    return result;
                });
            }

            @Override
            public boolean hasNext() {
                return nextElement() != null;
            }

            @Override
            public Traversable next() {
                Traversable result = nextElement();
                if (result == null)
                    throw new NoSuchElementException();
                visitedElements.add(result);
                nextElementCache.disable();
                result.adjacencies().forEach((queue::offer));
                return result;
            }
        };
    }

    /**
     * Returns an unmodifiable iterable over the elements in a traversable data structure, using breadth-first traversal.
     * @param startingElement the starting element of traversal
     * @return an unmodifiable iterable over the elements in a traversable data structure, using breadth-first traversal
     */
    public Iterable<Traversable> breadthFirstTraversal(Traversable startingElement) {
        return new Iterable<Traversable>() {
            @Override
            public Iterator<Traversable> iterator() {
                if (allowsCycle) {
                    return breadthFirstTraversalIteratorAllowsCycle(startingElement);
                } else {
                    return breadthFirstTraversalIterator(startingElement);
                }
            }
        };
    }
}
