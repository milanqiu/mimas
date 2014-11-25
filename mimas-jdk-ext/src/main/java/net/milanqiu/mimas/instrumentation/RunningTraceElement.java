package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.collect.ArrayUtils;
import net.milanqiu.mimas.lang.MethodIdentifier;
import net.milanqiu.mimas.string.StrUtils;

import java.util.Objects;

/**
 * An element in a running trace. Each element represents a single track of program running.
 * <p>
 * Creation Date: 2014-10-24
 * @author Milan Qiu
 */
public class RunningTraceElement {

    protected RunningTraceElement() {}

    /**
     * Enumerations of points where it tracks.
     */
    public enum TrackingPoint {
        /**
         * Also means "not a special point".
         */
        ANYWHERE {
            @Override
            public String toString() {
                return StrUtils.STR_EMPTY;
            }
        },
        /**
         * At the beginning of a method.
         */
        METHOD_BEGINNING {
            @Override
            public String toString() {
                return "Method Beginning";
            }
        },
        /**
         * At the end of a method.
         */
        METHOD_END {
            @Override
            public String toString() {
                return "Method End";
            }
        }
    }

    /**
     * The current stack trace when it tracks.
     */
    private StackTraceElement[] stack;
    /**
     * The point where it tracks.
     */
    private TrackingPoint trackingPoint = TrackingPoint.ANYWHERE;
    /**
     * The tag of track.
     */
    private String tag;

    /**
     * Returns the current stack trace when it tracks.
     * @return the current stack trace when it tracks
     */
    public StackTraceElement[] getStack() {
        return stack;
    }
    /**
     * A setter corresponding to the getter {@link #getStack()}.
     */
    protected void setStack(StackTraceElement[] stack) {
        this.stack = stack;
    }
    /**
     * Returns the point where it tracks.
     * @return the point where it tracks
     */
    public TrackingPoint getTrackingPoint() {
        return trackingPoint;
    }
    /**
     * A setter corresponding to the getter {@link #getTrackingPoint()}.
     */
    protected void setTrackingPoint(TrackingPoint trackingPoint) {
        this.trackingPoint = trackingPoint;
    }
    /**
     * Returns the tag of track.
     * @return the tag of track
     */
    public String getTag() {
        return tag;
    }
    /**
     * A setter corresponding to the getter {@link #getTag()}.
     */
    protected void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(stack[0]);
        String trackingPointStr = trackingPoint.toString();
        if (!trackingPointStr.isEmpty())
            sb.append(" ").append(trackingPointStr);
        if (tag != null && !tag.isEmpty())
            sb.append(" : ").append(tag);
        return sb.toString();
    }

    /**
     * An expected running trace element which stores only key fields.
     * For example, it doesn't store the full stack trace.
     * It is used to compare with actual {@code RunningTraceElement} for equality.
     */
    public interface Expected {
        /**
         * Returns whether this expected running trace element is equal to the specified {@code RunningTraceElement}
         * on key fields.
         * @param actual the actual {@code RunningTraceElement} to be tested
         * @return equality result
         */
        boolean equalsActual(RunningTraceElement actual);
    }

    /**
     * An expected running trace element which stores full key fields.
     */
    public static class FullyExpected implements Expected {
        private FullyExpected() {}

        /**
         * The expected method identifier of the top element of the stack trace.
         */
        private MethodIdentifier topMethod;
         /**
         * The expected tracking point.
         */
        private TrackingPoint trackingPoint = TrackingPoint.ANYWHERE;
        /**
         * The expected tag.
         */
        private String tag;

        /**
         * Creates and returns a new instance of {@code RunningTraceElement.FullyExpected} with the specified method
         * identifier.
         * @param topMethod the expected method identifier of the top element of the stack trace
         *                  of the new instance of {@code RunningTraceElement.FullyExpected}
         * @return the new instance of {@code RunningTraceElement.FullyExpected}
         */
        public static FullyExpected create(MethodIdentifier topMethod) {
            FullyExpected result = new FullyExpected();
            result.topMethod = topMethod;
            return result;
        }

        /**
         * Creates and returns a new instance of {@code RunningTraceElement.FullyExpected} with the specified method
         * identifier and tracking point.
         * @param topMethod the expected method identifier of the top element of the stack trace
         *                  of the new instance of {@code RunningTraceElement.FullyExpected}
         * @param trackingPoint the expected tracking point
         *                      of the new instance of {@code RunningTraceElement.FullyExpected}
         * @return the new instance of {@code RunningTraceElement.FullyExpected}
         */
        public static FullyExpected create(MethodIdentifier topMethod, TrackingPoint trackingPoint) {
            FullyExpected result = create(topMethod);
            result.trackingPoint = trackingPoint;
            return result;
        }

        /**
         * Creates and returns a new instance of {@code RunningTraceElement.FullyExpected} with the specified method
         * identifier and tag.
         * @param topMethod the expected method identifier of the top element of the stack trace
         *                  of the new instance of {@code RunningTraceElement.FullyExpected}
         * @param tag the expected tag
         *            of the new instance of {@code RunningTraceElement.FullyExpected}
         * @return the new instance of {@code RunningTraceElement.FullyExpected}
         */
        public static FullyExpected create(MethodIdentifier topMethod, String tag) {
            FullyExpected result = create(topMethod);
            result.tag = tag;
            return result;
        }

        /**
         * Creates and returns a new instance of {@code RunningTraceElement.FullyExpected} with the specified method
         * identifier, tracking point and tag.
         * @param topMethod the expected method identifier of the top element of the stack trace
         *                  of the new instance of {@code RunningTraceElement.FullyExpected}
         * @param trackingPoint the expected tracking point
         *                      of the new instance of {@code RunningTraceElement.FullyExpected}
         * @param tag the expected tag
         *            of the new instance of {@code RunningTraceElement.FullyExpected}
         * @return the new instance of {@code RunningTraceElement.FullyExpected}
         */
        public static FullyExpected create(MethodIdentifier topMethod, TrackingPoint trackingPoint, String tag) {
            FullyExpected result = create(topMethod);
            result.trackingPoint = trackingPoint;
            result.tag = tag;
            return result;
        }

        @Override
        public boolean equalsActual(RunningTraceElement actual) {
            return !ArrayUtils.isNullOrEmpty(actual.getStack()) &&
                    topMethod.equals(actual.getStack()[0]) &&
                    actual.getTrackingPoint().equals(trackingPoint) &&
                    Objects.equals(actual.getTag(), tag);
        }
    }

    /**
     * An expected running trace element which stores simple key fields.
     * Actually, it stores only {@code tag} field.
     */
    public static class SimplyExpected implements Expected {
        private SimplyExpected() {}

        /**
         * The expected tag.
         */
        private String tag;

        /**
         * Creates and returns a new instance of {@code RunningTraceElement.SimplyExpected} with the specified tag.
         * @param tag the expected tag of the new instance of {@code RunningTraceElement.SimplyExpected}
         * @return the new instance of {@code RunningTraceElement.SimplyExpected}
         */
        public static SimplyExpected create(String tag) {
            SimplyExpected result = new SimplyExpected();
            result.tag = tag;
            return result;
        }

        @Override
        public boolean equalsActual(RunningTraceElement actual) {
            return Objects.equals(actual.getTag(), tag);
        }
    }

    /**
     * Returns whether this object is equal to the specified expected running trace element on key fields.
     * @param expected the expected running trace element to be tested
     * @return equality result
     */
    public boolean equalsExpected(Expected expected) {
        return expected.equalsActual(this);
    }
}
