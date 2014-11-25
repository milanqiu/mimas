package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.lang.MethodIdentifierList;
import net.milanqiu.mimas.string.StrUtils;

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
        result.setStack(DebugUtils.removeStackTraceTopElements(Thread.currentThread().getStackTrace(),
                MethodIdentifierList.create(
                        this.getClass().getName(), "track",
                        this.getClass().getName(), "trackMethodBeginning",
                        this.getClass().getName(), "trackMethodEnd"
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(":");
        for (RunningTraceElement element : elements) {
            sb.append(StrUtils.LINE_SEPARATOR);
            sb.append(element);
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
         * Returns whether the current element is equal to the specified expected.
         * @param expected the expected running trace element to be tested
         * @return equality result
         */
        public boolean equalsExpected(RunningTraceElement.Expected expected) {
            return RunningTrace.this.elements.get(cursor).equalsExpected(expected);
        }

        /**
         * Returns whether the current element is equal to the specified expected. After comparison, it will moves to
         * the next element.
         * @param expected the expected running trace element to be tested
         * @return equality result
         */
        public boolean equalsExpectedAndNext(RunningTraceElement.Expected expected) {
            return RunningTrace.this.elements.get(cursor++).equalsExpected(expected);
        }

        /**
         * Returns whether the next some elements are equal to the specified expected.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         */
        public boolean equalsBatch(RunningTraceElement.Expected... expectedSequence) {
            int tempCursor = cursor;
            for (RunningTraceElement.Expected expected : expectedSequence) {
                if (!RunningTrace.this.elements.get(tempCursor++).equalsExpected(expected))
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         */
        public boolean equalsBatch(Iterable<RunningTraceElement.Expected> expectedSequence) {
            int tempCursor = cursor;
            for (RunningTraceElement.Expected expected : expectedSequence) {
                if (!RunningTrace.this.elements.get(tempCursor++).equalsExpected(expected))
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected. After comparison(regardless of
         * success or failure), it will moves to the element next to all comparing elements.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         */
        public boolean equalsBatchAndNext(RunningTraceElement.Expected... expectedSequence) {
            int tempCursor = cursor;
            cursor += expectedSequence.length;
            for (RunningTraceElement.Expected expected : expectedSequence) {
                if (!RunningTrace.this.elements.get(tempCursor++).equalsExpected(expected))
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected. After comparison(regardless of
         * success or failure), it will moves to the element next to all comparing elements.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
         */
        public boolean equalsBatchAndNext(List<RunningTraceElement.Expected> expectedSequence) {
            int tempCursor = cursor;
            cursor += expectedSequence.size();
            for (RunningTraceElement.Expected expected : expectedSequence) {
                if (!RunningTrace.this.elements.get(tempCursor++).equalsExpected(expected))
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected, ignoring the order of expected
         * running trace element sequence.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
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
                    }
                }
                if (!found)
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected, ignoring the order of expected
         * running trace element sequence.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
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
                    }
                }
                if (!found)
                    return false;
            }
            return true;
        }

        /**
         * Returns whether the next some elements are equal to the specified expected, ignoring the order of expected
         * running trace element sequence. After comparison(regardless of success or failure), it will moves to the
         * element next to all comparing elements.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
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
         * Returns whether the next some elements are equal to the specified expected, ignoring the order of expected
         * running trace element sequence. After comparison(regardless of success or failure), it will moves to the
         * element next to all comparing elements.
         * @param expectedSequence the expected running trace element sequence to be tested
         * @return equality result
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
         * Returns whether it reaches the end of running trace.
         * @return {@code true} if it reaches the end of running trace
         */
        public boolean isEnd() {
            return cursor == RunningTrace.this.elements.size();
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
