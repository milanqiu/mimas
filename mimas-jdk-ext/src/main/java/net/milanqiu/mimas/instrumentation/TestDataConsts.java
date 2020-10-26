package net.milanqiu.mimas.instrumentation;

/**
 * Declares a variety of data constants used for unit tests and other tests. Many of them are stored in test database.
 * <p>
 * Creation Date: 2018-09-07
 * @author Milan Qiu
 */
public class TestDataConsts {
    /**
     * Constant class is forbidden to be instantiated.
     */
    private TestDataConsts() {}

    public static final int TEACHER_COUNT = 4;
    public static final int[] TEACHER_IDS = {2, 4, 6, 8};
    public static final String[] TEACHER_NAMES = {"Yang", "Chen", "Wang", "Qu"};
    public static final String[] TEACHER_GENDER_STRS = {"FEMALE", "FEMALE", "MALE", "FEMALE"};
    public static final int[][] TEACHER_LESSONS = {{0, 1, 2}, {3}, {}, {4, 5}};

    public static final int LESSON_COUNT = 6;
    public static final int[] LESSON_IDS = {111, 222, 333, 444, 555, 666};
    public static final String[] LESSON_NAMES = {"Computer Science Principle", "Data Structure", "Software Engineering",
                                                 "Operating System", "Discrete Mathematics", "Analysis of Algorithms"};
    public static final String[] LESSON_PASS_RATE_STRS = {"0.98", "0.9", "0.95", "0.82", "0.85", "0.925"};
}
