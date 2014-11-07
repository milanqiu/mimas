package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.string.StrUtils;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * A trace of program running. It is composed by a sequence of {@code RunningTraceElement} objects.
 *
 * <p>Creation Date: 2014-10-24
 * @author Milan Qiu
 */
public class RunningTrace {

    private List<RunningTraceElement> elements = new ArrayList<>();

    private static StackTraceElement[] removeTrackingElements(StackTraceElement[] stackTrace) {
        int index = -1;
        String className;
        String methodName;
        do {
            index++;
            if (index >= stackTrace.length)
                break;
            className = stackTrace[index].getClassName();
            methodName = stackTrace[index].getMethodName();
        } while ((className.equals(Thread.class.getName())       && methodName.equals("getStackTrace")) ||
                 (className.equals(RunningTrace.class.getName()) && methodName.startsWith("track")));
        return Arrays.copyOfRange(stackTrace, index, stackTrace.length);
    }

    /**
     * Tracks a record at the current point.
     * @return the new track
     */
    public RunningTraceElement track() {
        RunningTraceElement result = new RunningTraceElement();
        elements.add(result);
        result.setStack(removeTrackingElements(Thread.currentThread().getStackTrace()));
        return result;
    }

    /**
     * Tracks a record at the current point.
     * @param tag the tag of track
     * @return the new track
     */
    public RunningTraceElement track(String tag) {
        RunningTraceElement result = track();
        result.setTag(tag);
        return result;
    }

    /**
     * Tracks a record at the current point. It should be at the beginning of a method.
     * @return the new track
     */
    public RunningTraceElement trackMethodBeginning() {
        RunningTraceElement result = track();
        result.setTrackingPoint(RunningTraceElement.TrackingPoint.METHOD_BEGINNING);
        return result;
    }

    /**
     * Tracks a record at the current point. It should be at the beginning of a method.
     * @param tag the tag of track
     * @return the new track
     */
    public RunningTraceElement trackMethodBeginning(String tag) {
        RunningTraceElement result = track();
        result.setTrackingPoint(RunningTraceElement.TrackingPoint.METHOD_BEGINNING);
        result.setTag(tag);
        return result;
    }

    /**
     * Tracks a record at the current point. It should be at the end of a method.
     * @return the new track
     */
    public RunningTraceElement trackMethodEnd() {
        RunningTraceElement result = track();
        result.setTrackingPoint(RunningTraceElement.TrackingPoint.METHOD_END);
        return result;
    }

    /**
     * Tracks a record at the current point. It should be at the end of a method.
     * @param tag the tag of track
     * @return the new track
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
     * A comparison process of {@code RunningTrace}.
     */
    public class Comparison {
        private Comparison() {
        }

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
         * Whether the current element is equal to the expected.
         * @param expected the expected running trace element
         * @return equality result
         */
        public boolean equalsExpected(RunningTraceElement.Expected expected) {
            return RunningTrace.this.elements.get(cursor).equalsExpected(expected);
        }

        /**
         * Whether the current element is equal to the expected.
         * After compare, it will moves to the next element.
         * @param expected the expected running trace element
         * @return equality result
         */
        public boolean equalsExpectedAndNext(RunningTraceElement.Expected expected) {
            return RunningTrace.this.elements.get(cursor++).equalsExpected(expected);
        }

        /**
         * Whether the following multiple elements are equal to the expected.
         * @param expectedSequence the expected running trace element sequence
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
         * Whether the following multiple elements are equal to the expected.
         * @param expectedSequence the expected running trace element sequence
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
         * Whether the following multiple elements are equal to the expected.
         * After compare, it will moves to the next element, skipping all compared elements. .
         * @param expectedSequence the expected running trace element sequence
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
         * Whether the following multiple elements are equal to the expected.
         * After compare, it will moves to the next element, skipping all compared elements. .
         * @param expectedSequence the expected running trace element sequence
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
         * Whether the following multiple elements are equal to the expected, ignoring the order of expected running
         * trace element sequence.
         * @param expectedSequence the expected running trace element sequence
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
         * Whether the following multiple elements are equal to the expected, ignoring the order of expected running
         * trace element sequence.
         * @param expectedSequence the expected running trace element sequence
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
         * Whether the following multiple elements are equal to the expected, ignoring the order of expected running
         * trace element sequence.
         * After compare, it will moves to the next element, skipping all compared elements. .
         * @param expectedSequence the expected running trace element sequence
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
         * Whether the following multiple elements are equal to the expected, ignoring the order of expected running
         * trace element sequence.
         * After compare, it will moves to the next element, skipping all compared elements. .
         * @param expectedSequence the expected running trace element sequence
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
         * Whether it comes to the end of running trace.
         * @return judging result
         */
        public boolean isEnd() {
            return cursor == RunningTrace.this.elements.size();
        }
    }

    /**
     * Creates a new comparison process of {@code RunningTrace}.
     * @return the new created {@code RunningTrace.Comparison} object
     */
    public Comparison newComparison() {
        Comparison result = new Comparison();
        result.reset();
        return result;
    }
}
