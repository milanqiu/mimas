package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.lang.StackTrace;
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
    private StackTrace stackTrace;
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
    public StackTrace getStackTrace() {
        return stackTrace;
    }
    /**
     * A setter corresponding to the getter {@link #getStackTrace()}.
     */
    void setStackTrace(StackTrace stackTrace) {
        this.stackTrace = stackTrace;
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
    void setTrackingPoint(TrackingPoint trackingPoint) {
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
    void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Returns a string representation of this object.
     * The string representation consists of the top element of stack trace, the tracking point and the tag,
     * separated by spaces and colon if necessary.
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(stackTrace.getElement(0));
        String trackingPointStr = trackingPoint.toString();
        if (!trackingPointStr.isEmpty())
            sb.append(" ").append(trackingPointStr);
        if (tag != null && !tag.isEmpty())
            sb.append(" : ").append(tag);
        return sb.toString();
    }

    /**
     * Returns a full string representation of this object.
     * The full string representation consists of all elements of stack trace, the tracking point and the tag,
     * separated by spaces and colon if necessary.
     * The elements of stack trace are enclosed in square brackets("[]"), separated by line separator with indent.
     * @return a full string representation of this object
     */
    public String toFullString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < stackTrace.getElementCount(); i++) {
            sb.append(System.lineSeparator()).append("    ");
            sb.append(stackTrace.getElement(i));
        }
        sb.append(System.lineSeparator()).append(']');
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
    public static class Expected {
        private Expected() {}

        /**
         * The expected class name of the top element of stack trace.
         */
        private String topClassName;
        /**
         * The expected method name of the top element of stack trace.
         */
        private String topMethodName;
        /**
         * The expected tracking point.
         */
        private TrackingPoint trackingPoint = TrackingPoint.ANYWHERE;
        /**
         * The expected tag.
         */
        private String tag;

        /**
         * Creates and returns a new instance of {@code RunningTraceElement.Expected} with the specified method properties.
         * @param topClassName the expected class name of the top element of stack trace of the new instance of {@code RunningTraceElement.Expected}
         * @param topMethodName the expected method name of the top element of stack trace of the new instance of {@code RunningTraceElement.Expected}
         * @return the new instance of {@code RunningTraceElement.Expected}
         */
        public static Expected create(String topClassName, String topMethodName) {
            Expected result = new Expected();
            result.topClassName = topClassName;
            result.topMethodName = topMethodName;
            return result;
        }

        /**
         * Creates and returns a new instance of {@code RunningTraceElement.Expected} with the specified method properties and tracking point.
         * @param topClassName the expected class name of the top element of stack trace of the new instance of {@code RunningTraceElement.Expected}
         * @param topMethodName the expected method name of the top element of stack trace of the new instance of {@code RunningTraceElement.Expected}
         * @param trackingPoint the expected tracking point of the new instance of {@code RunningTraceElement.Expected}
         * @return the new instance of {@code RunningTraceElement.Expected}
         */
        public static Expected create(String topClassName, String topMethodName, TrackingPoint trackingPoint) {
            Expected result = new Expected();
            result.topClassName = topClassName;
            result.topMethodName = topMethodName;
            result.trackingPoint = trackingPoint;
            return result;
        }

        /**
         * Creates and returns a new instance of {@code RunningTraceElement.Expected} with the specified method properties and tag.
         * @param topClassName the expected class name of the top element of stack trace of the new instance of {@code RunningTraceElement.Expected}
         * @param topMethodName the expected method name of the top element of stack trace of the new instance of {@code RunningTraceElement.Expected}
         * @param tag the expected tag of the new instance of {@code RunningTraceElement.Expected}
         * @return the new instance of {@code RunningTraceElement.Expected}
         */
        public static Expected create(String topClassName, String topMethodName, String tag) {
            Expected result = new Expected();
            result.topClassName = topClassName;
            result.topMethodName = topMethodName;
            result.tag = tag;
            return result;
        }

        /**
         * Creates and returns a new instance of {@code RunningTraceElement.Expected} with the specified method properties, tracking point and tag.
         * @param topClassName the expected class name of the top element of stack trace of the new instance of {@code RunningTraceElement.Expected}
         * @param topMethodName the expected method name of the top element of stack trace of the new instance of {@code RunningTraceElement.Expected}
         * @param trackingPoint the expected tracking point of the new instance of {@code RunningTraceElement.Expected}
         * @param tag the expected tag of the new instance of {@code RunningTraceElement.Expected}
         * @return the new instance of {@code RunningTraceElement.Expected}
         */
        public static Expected create(String topClassName, String topMethodName, TrackingPoint trackingPoint, String tag) {
            Expected result = new Expected();
            result.topClassName = topClassName;
            result.topMethodName = topMethodName;
            result.trackingPoint = trackingPoint;
            result.tag = tag;
            return result;
        }
    }

    /**
     * Returns whether this object is equal to the specified running trace element on key fields.
     * @param expected the running trace element to be tested
     * @return equality result
     */
    public boolean equalsExpected(Expected expected) {
        return (stackTrace.getElementCount() > 0) &&
                stackTrace.getElement(0).getClassName().equals(expected.topClassName) &&
                stackTrace.getElement(0).getMethodName().equals(expected.topMethodName) &&
                trackingPoint.equals(expected.trackingPoint) &&
                Objects.equals(tag, expected.tag);
    }

    /**
     * Returns whether the tag of this object is equal to the specified tag.
     * @param tag the tag to be tested
     * @return equality result
     */
    public boolean equalsTag(String tag) {
        return Objects.equals(this.tag, tag);
    }
}
