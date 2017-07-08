package net.milanqiu.mimas.lang;

/**
 * Utilities related to language.
 * <p>
 * Creation Date: 2017-05-18
 * @author Milan Qiu
 */
public class LangUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private LangUtils() {}

    /**
     * Chooses one from three values base on an integer comparison.
     * @param participantA the first participant of comparison
     * @param participantB the second participant of comparison
     * @param valueIfGreater the chosen value if {@code participantA} is greater than {@code participantB}
     * @param valueIfEqual the chosen value if {@code participantA} is equal to {@code participantB}
     * @param valueIfLess the chosen value if {@code participantA} is less than {@code participantB}
     * @return {@code valueIfGreater} if {@code participantA} is greater than {@code participantB},
     *         or {@code valueIfEqual} if equal to, or {@code valueIfLess} if less than
     */
    public static char choose(int participantA, int participantB, char valueIfGreater, char valueIfEqual, char valueIfLess) {
        if (participantA == participantB)
            return valueIfEqual;
        else if (participantA > participantB)
            return valueIfGreater;
        else
            return valueIfLess;
    }

    /**
     * Chooses one from three values base on an integer comparison.
     * @param participantA the first participant of comparison
     * @param participantB the second participant of comparison
     * @param valueIfGreater the chosen value if {@code participantA} is greater than {@code participantB}
     * @param valueIfEqual the chosen value if {@code participantA} is equal to {@code participantB}
     * @param valueIfLess the chosen value if {@code participantA} is less than {@code participantB}
     * @return {@code valueIfGreater} if {@code participantA} is greater than {@code participantB},
     *         or {@code valueIfEqual} if equal to, or {@code valueIfLess} if less than
     */
    public static byte choose(int participantA, int participantB, byte valueIfGreater, byte valueIfEqual, byte valueIfLess) {
        if (participantA == participantB)
            return valueIfEqual;
        else if (participantA > participantB)
            return valueIfGreater;
        else
            return valueIfLess;
    }

    /**
     * Chooses one from three values base on an integer comparison.
     * @param participantA the first participant of comparison
     * @param participantB the second participant of comparison
     * @param valueIfGreater the chosen value if {@code participantA} is greater than {@code participantB}
     * @param valueIfEqual the chosen value if {@code participantA} is equal to {@code participantB}
     * @param valueIfLess the chosen value if {@code participantA} is less than {@code participantB}
     * @return {@code valueIfGreater} if {@code participantA} is greater than {@code participantB},
     *         or {@code valueIfEqual} if equal to, or {@code valueIfLess} if less than
     */
    public static short choose(int participantA, int participantB, short valueIfGreater, short valueIfEqual, short valueIfLess) {
        if (participantA == participantB)
            return valueIfEqual;
        else if (participantA > participantB)
            return valueIfGreater;
        else
            return valueIfLess;
    }

    /**
     * Chooses one from three values base on an integer comparison.
     * @param participantA the first participant of comparison
     * @param participantB the second participant of comparison
     * @param valueIfGreater the chosen value if {@code participantA} is greater than {@code participantB}
     * @param valueIfEqual the chosen value if {@code participantA} is equal to {@code participantB}
     * @param valueIfLess the chosen value if {@code participantA} is less than {@code participantB}
     * @return {@code valueIfGreater} if {@code participantA} is greater than {@code participantB},
     *         or {@code valueIfEqual} if equal to, or {@code valueIfLess} if less than
     */
    public static int choose(int participantA, int participantB, int valueIfGreater, int valueIfEqual, int valueIfLess) {
        if (participantA == participantB)
            return valueIfEqual;
        else if (participantA > participantB)
            return valueIfGreater;
        else
            return valueIfLess;
    }

    /**
     * Chooses one from three values base on an integer comparison.
     * @param participantA the first participant of comparison
     * @param participantB the second participant of comparison
     * @param valueIfGreater the chosen value if {@code participantA} is greater than {@code participantB}
     * @param valueIfEqual the chosen value if {@code participantA} is equal to {@code participantB}
     * @param valueIfLess the chosen value if {@code participantA} is less than {@code participantB}
     * @return {@code valueIfGreater} if {@code participantA} is greater than {@code participantB},
     *         or {@code valueIfEqual} if equal to, or {@code valueIfLess} if less than
     */
    public static long choose(int participantA, int participantB, long valueIfGreater, long valueIfEqual, long valueIfLess) {
        if (participantA == participantB)
            return valueIfEqual;
        else if (participantA > participantB)
            return valueIfGreater;
        else
            return valueIfLess;
    }

    /**
     * Chooses one from three values base on an integer comparison.
     * @param participantA the first participant of comparison
     * @param participantB the second participant of comparison
     * @param valueIfGreater the chosen value if {@code participantA} is greater than {@code participantB}
     * @param valueIfEqual the chosen value if {@code participantA} is equal to {@code participantB}
     * @param valueIfLess the chosen value if {@code participantA} is less than {@code participantB}
     * @return {@code valueIfGreater} if {@code participantA} is greater than {@code participantB},
     *         or {@code valueIfEqual} if equal to, or {@code valueIfLess} if less than
     */
    public static float choose(int participantA, int participantB, float valueIfGreater, float valueIfEqual, float valueIfLess) {
        if (participantA == participantB)
            return valueIfEqual;
        else if (participantA > participantB)
            return valueIfGreater;
        else
            return valueIfLess;
    }

    /**
     * Chooses one from three values base on an integer comparison.
     * @param participantA the first participant of comparison
     * @param participantB the second participant of comparison
     * @param valueIfGreater the chosen value if {@code participantA} is greater than {@code participantB}
     * @param valueIfEqual the chosen value if {@code participantA} is equal to {@code participantB}
     * @param valueIfLess the chosen value if {@code participantA} is less than {@code participantB}
     * @return {@code valueIfGreater} if {@code participantA} is greater than {@code participantB},
     *         or {@code valueIfEqual} if equal to, or {@code valueIfLess} if less than
     */
    public static double choose(int participantA, int participantB, double valueIfGreater, double valueIfEqual, double valueIfLess) {
        if (participantA == participantB)
            return valueIfEqual;
        else if (participantA > participantB)
            return valueIfGreater;
        else
            return valueIfLess;
    }

    /**
     * Chooses one from three values base on an integer comparison.
     * @param participantA the first participant of comparison
     * @param participantB the second participant of comparison
     * @param valueIfGreater the chosen value if {@code participantA} is greater than {@code participantB}
     * @param valueIfEqual the chosen value if {@code participantA} is equal to {@code participantB}
     * @param valueIfLess the chosen value if {@code participantA} is less than {@code participantB}
     * @param <T> the type of the choosable values
     * @return {@code valueIfGreater} if {@code participantA} is greater than {@code participantB},
     *         or {@code valueIfEqual} if equal to, or {@code valueIfLess} if less than
     */
    public static <T> T choose(int participantA, int participantB, T valueIfGreater, T valueIfEqual, T valueIfLess) {
        if (participantA == participantB)
            return valueIfEqual;
        else if (participantA > participantB)
            return valueIfGreater;
        else
            return valueIfLess;
    }

    /**
     * Implements a dual conditional control.
     * @param conditionA the first condition
     * @param conditionB the second condition
     * @param ifBothTrue the sentences to run if both {@code conditionA} and {@code conditionB} are {@code true}
     * @param ifOnlyAIsTrue the sentences to run if {@code conditionA} is {@code true} and {@code conditionB} is {@code false}
     * @param ifOnlyBIsTrue the sentences to run if {@code conditionA} is {@code false} and {@code conditionB} is {@code true}
     * @param ifBothFalse the sentences to run if both {@code conditionA} and {@code conditionB} are {@code false}
     */
    public static void dualControl(boolean conditionA, boolean conditionB,
                                   Runnable ifBothTrue, Runnable ifOnlyAIsTrue, Runnable ifOnlyBIsTrue, Runnable ifBothFalse) {
        if (conditionA)
            if (conditionB)
                ifBothTrue.run();
            else
                ifOnlyAIsTrue.run();
        else
            if (conditionB)
                ifOnlyBIsTrue.run();
            else
                ifBothFalse.run();
    }
}
