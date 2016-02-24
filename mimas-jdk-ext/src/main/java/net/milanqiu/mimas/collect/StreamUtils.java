package net.milanqiu.mimas.collect;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utilities related to stream.
 * <p>
 * Creation Date: 2016-02-24
 * @author Milan Qiu
 */
public class StreamUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private StreamUtils() {}

    /**
     * Creates and returns a new sequential {@link java.util.stream.Stream} from an {@link java.lang.Iterable}.
     * @param itr the {@link java.lang.Iterable} describing the stream elements
     * @param <T> the type of stream elements
     * @return a new sequential {@link java.util.stream.Stream}
     */
    public static <T> Stream<T> streamOf(Iterable<T> itr) {
        return StreamSupport.stream(itr.spliterator(), false);
    }

    /**
     * Creates and returns a new parallel {@link java.util.stream.Stream} from an {@link java.lang.Iterable}.
     * @param itr the {@link java.lang.Iterable} describing the stream elements
     * @param <T> the type of stream elements
     * @return a new parallel {@link java.util.stream.Stream}
     */
    public static <T> Stream<T> parallelStreamOf(Iterable<T> itr) {
        return StreamSupport.stream(itr.spliterator(), true);
    }
}
