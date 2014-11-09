package net.milanqiu.mimas.lang;

/**
 * Like {@link java.lang.Runnable}, but accepts a parameter when running.
 *
 * <p>Creation Date: 2014-11-9
 * @author Milan Qiu
 */
public interface RunnableWithParam<T> {

    /**
     * May take any action whatsoever with the specified parameter..
     * @param param parameter to be passed
     */
    public void run(T param);

    /**
     * Like {@link net.milanqiu.mimas.lang.RunnableWithParam}, but accepts a specific character parameter to avoid wrapping..
     */
    public interface WithChar {
        /**
         * May take any action whatsoever with the specified parameter..
         * @param param parameter to be passed
         */
        public void run(char param);
    }

    /**
     * Like {@link net.milanqiu.mimas.lang.RunnableWithParam}, but accepts a specific byte parameter to avoid wrapping..
     */
    public interface WithByte {
        /**
         * May take any action whatsoever with the specified parameter..
         * @param param parameter to be passed
         */
        public void run(byte param);
    }

    /**
     * Like {@link net.milanqiu.mimas.lang.RunnableWithParam}, but accepts a specific short parameter to avoid wrapping..
     */
    public interface WithShort {
        /**
         * May take any action whatsoever with the specified parameter..
         * @param param parameter to be passed
         */
        public void run(short param);
    }

    /**
     * Like {@link net.milanqiu.mimas.lang.RunnableWithParam}, but accepts a specific integer parameter to avoid wrapping..
     */
    public interface WithInt {
        /**
         * May take any action whatsoever with the specified parameter..
         * @param param parameter to be passed
         */
        public void run(int param);
    }

    /**
     * Like {@link net.milanqiu.mimas.lang.RunnableWithParam}, but accepts a specific long parameter to avoid wrapping..
     */
    public interface WithLong {
        /**
         * May take any action whatsoever with the specified parameter..
         * @param param parameter to be passed
         */
        public void run(long param);
    }

    /**
     * Like {@link net.milanqiu.mimas.lang.RunnableWithParam}, but accepts a specific float parameter to avoid wrapping..
     */
    public interface WithFloat {
        /**
         * May take any action whatsoever with the specified parameter..
         * @param param parameter to be passed
         */
        public void run(float param);
    }

    /**
     * Like {@link net.milanqiu.mimas.lang.RunnableWithParam}, but accepts a specific double parameter to avoid wrapping..
     */
    public interface WithDouble {
        /**
         * May take any action whatsoever with the specified parameter..
         * @param param parameter to be passed
         */
        public void run(double param);
    }
}
