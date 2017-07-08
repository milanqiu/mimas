package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.collect.tuple.StrStr;
import net.milanqiu.mimas.lang.StackTrace;

import java.util.ArrayList;
import java.util.List;

/**
 * A trace of program running. It is composed by a sequence of {@link RunningTraceElement} objects.
 * <p>
 * Creation Date: 2014-10-24
 * @author Milan Qiu
 */
public class RunningTrace {

    private final List<RunningTraceElement> elements = new ArrayList<>();

    /**
     * Tracks a record at the current running point.
     * @return the new trace record
     */
    public RunningTraceElement track() {
        RunningTraceElement result = new RunningTraceElement();
        elements.add(result);
        result.setStackTrace(StackTrace.createFromCurrentThread().removeTopElements(
                StackTrace.createFromMethodIdentifiers(StrStr.createList(
                                this.getClass().getName(), "track",
                                this.getClass().getName(), "trackMethodBeginning",
                                this.getClass().getName(), "trackMethodEnd")
                )));
        return result;
    }

    /**
     * Tracks a record at the current running point with the specified tag.
     * @param tag the tag of record
     * @return the new trace record
     */
    public RunningTraceElement track(String tag) {
        RunningTraceElement result = track();
        result.setTag(tag);
        return result;
    }

    /**
     * Tracks a record at the current running point. It should be at the beginning of a method.
     * @return the new trace record
     */
    public RunningTraceElement trackMethodBeginning() {
        RunningTraceElement result = track();
        result.setTrackingPoint(RunningTraceElement.TrackingPoint.METHOD_BEGINNING);
        return result;
    }

    /**
     * Tracks a record at the current running point with the specified tag. It should be at the beginning of a method.
     * @param tag the tag of record
     * @return the new trace record
     */
    public RunningTraceElement trackMethodBeginning(String tag) {
        RunningTraceElement result = track();
        result.setTrackingPoint(RunningTraceElement.TrackingPoint.METHOD_BEGINNING);
        result.setTag(tag);
        return result;
    }

    /**
     * Tracks a record at the current running point. It should be at the end of a method.
     * @return the new trace record
     */
    public RunningTraceElement trackMethodEnd() {
        RunningTraceElement result = track();
        result.setTrackingPoint(RunningTraceElement.TrackingPoint.METHOD_END);
        return result;
    }

    /**
     * Tracks a record at the current running point with the specified tag. It should be at the end of a method.
     * @param tag the tag of record
     * @return the new trace record
     */
    public RunningTraceElement trackMethodEnd(String tag) {
        RunningTraceElement result = track();
        result.setTrackingPoint(RunningTraceElement.TrackingPoint.METHOD_END);
        result.setTag(tag);
        return result;
    }

    /**
     * Clears the trace.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Returns the size of trace.
     * @return the size of trace
     */
    public int size() {
        return elements.size();
    }

    /**
     * Returns whether the trace is empty.
     * @return whether the trace is empty
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Returns a string representation of this object.
     * The string representation consists of a list of elements, leading by simple class name and colon(":").
     * Adjacent elements are separated by line separator.
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(":");
        for (RunningTraceElement element : elements) {
            sb.append(System.lineSeparator());
            sb.append(element);
        }
        return sb.toString();
    }

    /**
     * Returns a full string representation of this object.
     * The full string representation consists of a list of full string representations of elements,
     * leading by simple class name and colon(":").
     * Adjacent elements are separated by line separator.
     * @return a full string representation of this object
     */
    public String toFullString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(":");
        for (RunningTraceElement element : elements) {
            sb.append(System.lineSeparator());
            sb.append(element.toFullString());
        }
        return sb.toString();
    }

    /**
     * A comparison process of {@link RunningTrace}.
     */
    public class Comparison {
        private Comparison() {}

        private int cursor = 0;

        /**
         * Resets the comparison.
         */
        public void reset() {
            cursor = 0;
        }

        /**
         * Moves to the next element for comparison.
         */
        public void next() {
            cursor++;
        }

        /**
         * Moves to the next element with the specified step for comparison.
         * @param step the step to skip
         */
        public void next(int step) {
            cursor += step;
        }

        /**
         * Returns whether it reaches the end of running trace.
         * @return {@code true} if it reaches the end of running trace
         */
        public boolean isEnd() {
            return cursor == RunningTrace.this.elements.size();
        }

        /**
         * Returns the element of original running trace at the specified index.
         * @param index the index to get element
         * @return the element of original running trace at the specified index
         */
        private RunningTraceElement getElement(int index) {
            return RunningTrace.this.elements.get(index);
        }

        /**
         * Returns whether the current element is equal to the specified expected.
         * @param expected the expected running trace element to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if the current element is out of trace range
         */
        public boolean equalsExpected(RunningTraceElement.Expected expected) {
            return getElement(cursor).equalsExpected(expected);
        }

        /**
         * Returns whether the tag of current element is equal to the specified expected tag.
         * @param expectedTag the expected tag to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if the current element is out of trace range
         */
        public boolean equalsExpected(String expectedTag) {
            return getElement(cursor).equalsTag(expectedTag);
        }

        /**
         * Returns whether the current element is equal to the specified expected.
         * After comparison(regardless of success or failure), it will moves to the next element.
         * @param expected the expected running trace element to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if the current element is out of trace range
         */
        public boolean equalsExpectedAndNext(RunningTraceElement.Expected expected) {
            return getElement(cursor++).equalsExpected(expected);
        }

        /**
         * Returns whether the tag of current element is equal to the specified expected tag.
         * After comparison(regardless of success or failure), it will moves to the next element.
         * @param expectedTag the expected tag to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if the current element is out of trace range
         */
        public boolean equalsExpectedAndNext(String expectedTag) {
            return getElement(cursor++).equalsTag(expectedTag);
        }

        /**
         * Returns whether the next some elements are equal to the specified expected.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatch(RunningTraceElement.Expected... expectedSequence) {
            int tempCursor = cursor;
            for (RunningTraceElement.Expected expected : expectedSequence) {
                if (!getElement(tempCursor++).equalsExpected(expected))
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatch(Iterable<RunningTraceElement.Expected> expectedSequence) {
            int tempCursor = cursor;
            for (RunningTraceElement.Expected expected : expectedSequence) {
                if (!getElement(tempCursor++).equalsExpected(expected))
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the tags of next some elements are equal to the specified expected tag sequence.
         * @param expectedTagSequence the expected tag sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatch(String... expectedTagSequence) {
            int tempCursor = cursor;
            for (String expectedTag : expectedTagSequence) {
                if (!getElement(tempCursor++).equalsTag(expectedTag))
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected.
         * After comparison(regardless of success or failure), it will moves to the element next to all comparing elements.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatchAndNext(RunningTraceElement.Expected... expectedSequence) {
            int tempCursor = cursor;
            cursor += expectedSequence.length;
            for (RunningTraceElement.Expected expected : expectedSequence) {
                if (!getElement(tempCursor++).equalsExpected(expected))
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected.
         * After comparison(regardless of success or failure), it will moves to the element next to all comparing elements.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatchAndNext(List<RunningTraceElement.Expected> expectedSequence) {
            int tempCursor = cursor;
            cursor += expectedSequence.size();
            for (RunningTraceElement.Expected expected : expectedSequence) {
                if (!getElement(tempCursor++).equalsExpected(expected))
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the tags of next some elements are equal to the specified expected tag sequence.
         * After comparison(regardless of success or failure), it will moves to the element next to all comparing elements.
         * @param expectedTagSequence the expected tag sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatchAndNext(String... expectedTagSequence) {
            int tempCursor = cursor;
            cursor += expectedTagSequence.length;
            for (String expectedTag : expectedTagSequence) {
                if (!getElement(tempCursor++).equalsTag(expectedTag))
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected,
         * ignoring the order of expected running trace element sequence.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatchIgnoringOrder(RunningTraceElement.Expected... expectedSequence) {
            int count = expectedSequence.length;
            List<RunningTraceElement> actualSequence = new ArrayList<>(RunningTrace.this.elements.subList(cursor, cursor+count));
            if (actualSequence.size() != count)
                return false;
            for (RunningTraceElement.Expected expected : expectedSequence) {
                boolean found = false;
                for (int i = 0; i < count; i++) {
                    if (actualSequence.get(i) != null && actualSequence.get(i).equalsExpected(expected)) {
                        found = true;
                        actualSequence.set(i, null);
                        break;
                    }
                }
                if (!found)
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected,
         * ignoring the order of expected running trace element sequence.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatchIgnoringOrder(List<RunningTraceElement.Expected> expectedSequence) {
            int count = expectedSequence.size();
            List<RunningTraceElement> actualSequence = new ArrayList<>(RunningTrace.this.elements.subList(cursor, cursor+count));
            if (actualSequence.size() != count)
                return false;
            for (RunningTraceElement.Expected expected : expectedSequence) {
                boolean found = false;
                for (int i = 0; i < count; i++) {
                    if (actualSequence.get(i) != null && actualSequence.get(i).equalsExpected(expected)) {
                        found = true;
                        actualSequence.set(i, null);
                        break;
                    }
                }
                if (!found)
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the tags of next some elements are equal to the specified expected tag sequence,
         * ignoring the order of expected tag sequence.
         * @param expectedTagSequence the expected tag sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatchIgnoringOrder(String... expectedTagSequence) {
            int count = expectedTagSequence.length;
            List<RunningTraceElement> actualSequence = new ArrayList<>(RunningTrace.this.elements.subList(cursor, cursor+count));
            if (actualSequence.size() != count)
                return false;
            for (String expectedTag : expectedTagSequence) {
                boolean found = false;
                for (int i = 0; i < count; i++) {
                    if (actualSequence.get(i) != null && actualSequence.get(i).equalsTag(expectedTag)) {
                        found = true;
                        actualSequence.set(i, null);
                        break;
                    }
                }
                if (!found)
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected,
         * ignoring the order of expected running trace element sequence.
         * After comparison(regardless of success or failure), it will moves to the element next to all comparing elements.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatchIgnoringOrderAndNext(RunningTraceElement.Expected... expectedSequence) {
            int count = expectedSequence.length;
            List<RunningTraceElement> actualSequence = new ArrayList<>(RunningTrace.this.elements.subList(cursor, cursor+count));
            cursor += count;
            if (actualSequence.size() != count)
                return false;
            for (RunningTraceElement.Expected expected : expectedSequence) {
                boolean found = false;
                for (int i = 0; i < count; i++) {
                    if (actualSequence.get(i) != null && actualSequence.get(i).equalsExpected(expected)) {
                        found = true;
                        actualSequence.set(i, null);
                    }
                }
                if (!found)
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected,
         * ignoring the order of expected running trace element sequence.
         * After comparison(regardless of success or failure), it will moves to the element next to all comparing elements.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatchIgnoringOrderAndNext(List<RunningTraceElement.Expected> expectedSequence) {
            int count = expectedSequence.size();
            List<RunningTraceElement> actualSequence = new ArrayList<>(RunningTrace.this.elements.subList(cursor, cursor+count));
            cursor += count;
            if (actualSequence.size() != count)
                return false;
            for (RunningTraceElement.Expected expected : expectedSequence) {
                boolean found = false;
                for (int i = 0; i < count; i++) {
                    if (actualSequence.get(i) != null && actualSequence.get(i).equalsExpected(expected)) {
                        found = true;
                        actualSequence.set(i, null);
                    }
                }
                if (!found)
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the tags of next some elements are equal to the specified expected tag sequence,
         * ignoring the order of expected tag sequence.
         * After comparison(regardless of success or failure), it will moves to the element next to all comparing elements.
         * @param expectedTagSequence the expected tag sequence to be tested
         * @return equality result
         * @throws IndexOutOfBoundsException if any of next elements is out of trace range
         */
        public boolean equalsBatchIgnoringOrderAndNext(String... expectedTagSequence) {
            int count = expectedTagSequence.length;
            List<RunningTraceElement> actualSequence = new ArrayList<>(RunningTrace.this.elements.subList(cursor, cursor+count));
            cursor += count;
            if (actualSequence.size() != count)
                return false;
            for (String expectedTag : expectedTagSequence) {
                boolean found = false;
                for (int i = 0; i < count; i++) {
                    if (actualSequence.get(i) != null && actualSequence.get(i).equalsTag(expectedTag)) {
                        found = true;
                        actualSequence.set(i, null);
                    }
                }
                if (!found)
                    return false;
            }
            return true;
        }
    }

    /**
     * Creates and returns a new comparison process of {@code RunningTrace}.
     * @return the new comparison process
     */
    public Comparison newComparison() {
        Comparison result = new Comparison();
        result.reset();
        return result;
    }
}
