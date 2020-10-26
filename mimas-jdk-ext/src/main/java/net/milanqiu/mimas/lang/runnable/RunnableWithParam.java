package net.milanqiu.mimas.lang.runnable;

/**
 * Likes {@link java.lang.Runnable}, but accepts a parameter when running.
 * <p>
 * Creation Date: 2014-11-09
 * @author Milan Qiu
 */
@FunctionalInterface
public interface RunnableWithParam<T> {

    /**
     * May take any action whatsoever with the specified parameter.
     * @param param the parameter to be passed
     */
    void run(T param);

    /**
     * Likes {@link RunnableWithParam}, but accepts a specific character parameter to avoid wrapping.
     */
    @FunctionalInterface
    interface WithChar {
        /**
         * May take any action whatsoever with the specified parameter.
         * @param param the parameter to be passed
         */
        void run(char param);
    }

    /**
     * Likes {@link RunnableWithParam}, but accepts a specific byte parameter to avoid wrapping.
     */
    @FunctionalInterface
    interface WithByte {
        /**
         * May take any action whatsoever with the specified parameter.
         * @param param the parameter to be passed
         */
        void run(byte param);
    }

    /**
     * Likes {@link RunnableWithParam}, but accepts a specific short parameter to avoid wrapping.
     */
    @FunctionalInterface
    interface WithShort {
        /**
         * May take any action whatsoever with the specified parameter.
         * @param param the parameter to be passed
         */
        void run(short param);
    }

    /**
     * Likes {@link RunnableWithParam}, but accepts a specific integer parameter to avoid wrapping.
     */
    @FunctionalInterface
    interface WithInt {
        /**
         * May take any action whatsoever with the specified parameter.
         * @param param the parameter to be passed
         */
        void run(int param);
    }

    /**
     * Likes {@link RunnableWithParam}, but accepts a specific long parameter to avoid wrapping.
     */
    @FunctionalInterface
    interface WithLong {
        /**
         * May take any action whatsoever with the specified parameter.
         * @param param the parameter to be passed
         */
        void run(long param);
    }

    /**
     * Likes {@link RunnableWithParam}, but accepts a specific float parameter to avoid wrapping.
     */
    @FunctionalInterface
    interface WithFloat {
        /**
         * May take any action whatsoever with the specified parameter.
         * @param param the parameter to be passed
         */
        void run(float param);
    }

    /**
     * Likes {@link RunnableWithParam}, but accepts a specific double parameter to avoid wrapping.
     */
    @FunctionalInterface
    interface WithDouble {
        /**
         * May take any action whatsoever with the specified parameter.
         * @param param the parameter to be passed
         */
        void run(double param);
    }
}
