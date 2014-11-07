package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.string.StrUtils;

import java.util.Objects;

/**
 * An element in a running trace. Each element represents a single track of program running.
 *
 * <p>Creation Date: 2014-10-24
 * @author Milan Qiu
 */
public class RunningTraceElement {

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
        };
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
     * Gets the current stack trace when it tracks.
     * @return the current stack trace when it tracks
     */
    public StackTraceElement[] getStack() {
        return stack;
    }
    protected void setStack(StackTraceElement[] stack) {
        this.stack = stack;
    }
    /**
     * Gets the point where it tracks.
     * @return the point where it tracks
     */
    public TrackingPoint getTrackingPoint() {
        return trackingPoint;
    }
    protected void setTrackingPoint(TrackingPoint trackingPoint) {
        this.trackingPoint = trackingPoint;
    }
    /**
     * Gets the tag of track.
     * @return the tag of track
     */
    public String getTag() {
        return tag;
    }
    protected void setTag(String tag) {
        this.tag = tag;
    }

    protected RunningTraceElement() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(stack[0]);
        String trackingPointStr = trackingPoint.toString();
        if (!trackingPointStr.isEmpty())
            sb.append(" ").append(trackingPointStr);
        if ((tag != null) && !tag.isEmpty())
            sb.append(" : ").append(tag);
        return sb.toString();
    }

    /**
     * An expected running trace element which stores just key fields.
     * For example, it doesn't store the full stack trace.
     * It is used to compare with actual {@code RunningTraceElement} for equality.
     */
    public interface Expected {
        /**
         * Whether the expected running trace element is equal to the actual {@code RunningTraceElement} on key fields.
         * @param actual the actual {@code RunningTraceElement} to be compared
         * @return equality result
         */
        boolean equalsActual(RunningTraceElement actual);
    }

    /**
     * An expected running trace element which stores full key fields.
     */
    public static class FullyExpected implements Expected {
        /**
         * The expected class name of the top element of stack trace.
         */
        private String className;
        /**
         * The expected method name of the top element of stack trace.
         */
        private String methodName;
        /**
         * The expected tracking point.
         */
        private TrackingPoint trackingPoint = TrackingPoint.ANYWHERE;
        /**
         * The expected tag.
         */
        private String tag;

        private FullyExpected() {
        }

        /**
         * Creates a new {@code RunningTraceElement.FullyExpected} object with specified fields.
         * @param className the specified expected class name of the top element of stack trace
         * @param methodName the specified expected method name of the top element of stack trace
         * @return the new created {@code RunningTraceElement.FullyExpected} object
         */
        public static FullyExpected create(String className, String methodName) {
            FullyExpected result = new FullyExpected();
            result.className = className;
            result.methodName = methodName;
            return result;
        }

        /**
         * Creates a new {@code RunningTraceElement.FullyExpected} object with specified fields.
         * @param className the specified expected class name of the top element of stack trace
         * @param methodName the specified expected method name of the top element of stack trace
         * @param trackingPoint the specified expected tracking point
         * @return the new created {@code RunningTraceElement.FullyExpected} object
         */
        public static FullyExpected create(String className, String methodName, TrackingPoint trackingPoint) {
            FullyExpected result = create(className, methodName);
            result.trackingPoint = trackingPoint;
            return result;
        }

        /**
         * Creates a new {@code RunningTraceElement.FullyExpected} object with specified fields.
         * @param className the specified expected class name of the top element of stack trace
         * @param methodName the specified expected method name of the top element of stack trace
         * @param tag the specified expected tag
         * @return the new created {@code RunningTraceElement.FullyExpected} object
         */
        public static FullyExpected create(String className, String methodName, String tag) {
            FullyExpected result = create(className, methodName);
            result.tag = tag;
            return result;
        }

        /**
         * Creates a new {@code RunningTraceElement.FullyExpected} object with specified fields.
         * @param className the specified expected class name of the top element of stack trace
         * @param methodName the specified expected method name of the top element of stack trace
         * @param trackingPoint the specified expected tracking point
         * @param tag the specified expected tag
         * @return the new created {@code RunningTraceElement.FullyExpected} object
         */
        public static FullyExpected create(String className, String methodName, TrackingPoint trackingPoint, String tag) {
            FullyExpected result = create(className, methodName);
            result.trackingPoint = trackingPoint;
            result.tag = tag;
            return result;
        }

        @Override
        public boolean equalsActual(RunningTraceElement actual) {
            return actual.getStack() != null &&
                    actual.getStack().length > 0 &&
                    actual.getStack()[0].getClassName().equals(className) &&
                    actual.getStack()[0].getMethodName().equals(methodName) &&
                    actual.getTrackingPoint().equals(trackingPoint) &&
                    Objects.equals(actual.getTag(), tag);
        }
    }

    /**
     * An expected running trace element which stores simple key fields.
     * Actually, it stores only {@code tag} field.
     */
    public static class SimplyExpected implements Expected {
        /**
         * The expected tag.
         */
        private String tag;

        private SimplyExpected() {
        }

        /**
         * Creates a new {@code RunningTraceElement.SimplyExpected} object with specified fields.
         * @param tag the specified expected tag
         * @return the new created {@code RunningTraceElement.SimplyExpected} object
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
     * Whether this object is equal to the expected running trace element on key fields.
     * @param expected the expected running trace element
     * @return equality result
     */
    public boolean equalsExpected(Expected expected) {
        return expected.equalsActual(this);
    }
}
