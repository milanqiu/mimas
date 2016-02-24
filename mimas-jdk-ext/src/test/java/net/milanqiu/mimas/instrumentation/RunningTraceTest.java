package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.config.MimasJdkExtProjectConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Creation Date: 2014-10-24
 * @author Milan Qiu
 */
public class RunningTraceTest {

    RunningTrace trace;

    @Before
    public void setUp() throws Exception {
        trace = new RunningTrace();
    }

    void methodA() {
        trace.track();
        trace.track("exit methodA");
    }

    void methodB() {
        trace.trackMethodBeginning();
        methodA();
        trace.trackMethodEnd();
    }

    void methodC() {
        trace.trackMethodBeginning("enter methodC");
        methodB();
        trace.trackMethodEnd("exit methodC");
    }

    @Test
    public void test_size_isEmpty() throws Exception {
        methodC();
        Assert.assertEquals(6, trace.size());
        Assert.assertFalse(trace.isEmpty());

        RunningTrace emptyTrace = new RunningTrace();
        Assert.assertEquals(0, emptyTrace.size());
        Assert.assertTrue(emptyTrace.isEmpty());
    }

    @Test
    public void test_toString() throws Exception {
        methodC();
        MimasJdkExtProjectConfig.getSingleton().writeFileInTestOutDir(trace.toString());
    }

    @Test
    public void test_toFullString() throws Exception {
        methodC();
        MimasJdkExtProjectConfig.getSingleton().writeFileInTestOutDir(trace.toFullString());
    }

    @Test
    public void test_Comparison_reset_next_isEnd_equalsExpected_equalsExpectedAndNext() throws Exception {
        methodC();

        RunningTrace.Comparison comparison = trace.newComparison();
        {
            // boolean equalsExpected(RunningTraceElement.Expected expected)
            Assert.assertTrue(comparison.equalsExpected(RunningTraceElement.Expected.create(
                    "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC",
                    RunningTraceElement.TrackingPoint.METHOD_BEGINNING,
                    "enter methodC"
            )));
            comparison.next();
            Assert.assertTrue(comparison.equalsExpected(RunningTraceElement.Expected.create(
                    "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB",
                    RunningTraceElement.TrackingPoint.METHOD_BEGINNING
            )));
            comparison.next();
            Assert.assertTrue(comparison.equalsExpected(RunningTraceElement.Expected.create(
                    "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA"
            )));
            comparison.next();
            Assert.assertFalse(comparison.isEnd());

            // boolean equalsExpectedAndNext(RunningTraceElement.Expected expected)
            Assert.assertTrue(comparison.equalsExpectedAndNext(RunningTraceElement.Expected.create(
                    "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA",
                    "exit methodA"
            )));
            Assert.assertTrue(comparison.equalsExpectedAndNext(RunningTraceElement.Expected.create(
                    "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB",
                    RunningTraceElement.TrackingPoint.METHOD_END
            )));
            Assert.assertTrue(comparison.equalsExpectedAndNext(RunningTraceElement.Expected.create(
                    "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC",
                    RunningTraceElement.TrackingPoint.METHOD_END,
                    "exit methodC"
            )));
            Assert.assertTrue(comparison.isEnd());
        }

        comparison.reset();
        {
            // boolean equalsExpected(String expectedTag)
            Assert.assertTrue(comparison.equalsExpected("enter methodC"));
            comparison.next();
            Assert.assertTrue(comparison.equalsExpected((String) null));
            comparison.next();
            Assert.assertTrue(comparison.equalsExpected((String) null));
            comparison.next();
            Assert.assertFalse(comparison.isEnd());

            // boolean equalsExpectedAndNext(String expectedTag)
            Assert.assertTrue(comparison.equalsExpectedAndNext("exit methodA"));
            Assert.assertTrue(comparison.equalsExpectedAndNext((String) null));
            Assert.assertTrue(comparison.equalsExpectedAndNext("exit methodC"));
            Assert.assertTrue(comparison.isEnd());
        }
    }

    @Test
    public void test_Comparison_reset_next_isEnd_equalBatch_equalsBatchAndNext() throws Exception {
        methodC();

        RunningTrace.Comparison comparison = trace.newComparison();
        {
            // boolean equalsBatch(RunningTraceElement.Expected... expectedSequence)
            Assert.assertTrue(comparison.equalsBatch(
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC",
                            RunningTraceElement.TrackingPoint.METHOD_BEGINNING,
                            "enter methodC"
                    ),
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB",
                            RunningTraceElement.TrackingPoint.METHOD_BEGINNING
                    ),
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA"
                    )
            ));
            comparison.next(3);
            Assert.assertFalse(comparison.isEnd());

            // boolean equalsBatchAndNext(RunningTraceElement.Expected... expectedSequence)
            Assert.assertTrue(comparison.equalsBatchAndNext(
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA",
                            "exit methodA"
                    ),
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB",
                            RunningTraceElement.TrackingPoint.METHOD_END
                    ),
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC",
                            RunningTraceElement.TrackingPoint.METHOD_END,
                            "exit methodC"
                    )
            ));
            Assert.assertTrue(comparison.isEnd());
        }

        comparison.reset();
        {
            // boolean equalsBatch(String... expectedTagSequence)
            Assert.assertTrue(comparison.equalsBatch("enter methodC", null, null));
            comparison.next(3);
            Assert.assertFalse(comparison.isEnd());

            // boolean equalsBatchAndNext(String... expectedTagSequence)
            Assert.assertTrue(comparison.equalsBatchAndNext("exit methodA", null, "exit methodC"));
            Assert.assertTrue(comparison.isEnd());
        }
    }

    @Test
    public void test_Comparison_reset_next_isEnd_equalBatchIgnoringOrder_equalsBatchIgnoringOrderAndNext() throws Exception {
        methodC();

        RunningTrace.Comparison comparison = trace.newComparison();
        {
            // boolean equalsBatchIgnoringOrder(RunningTraceElement.Expected... expectedSequence)
            Assert.assertTrue(comparison.equalsBatchIgnoringOrder(
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA"
                    ),
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC",
                            RunningTraceElement.TrackingPoint.METHOD_BEGINNING,
                            "enter methodC"
                    ),
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB",
                            RunningTraceElement.TrackingPoint.METHOD_BEGINNING
                    )
            ));
            comparison.next(3);
            Assert.assertFalse(comparison.isEnd());

            // boolean equalsBatchIgnoringOrderAndNext(RunningTraceElement.Expected... expectedSequence)
            Assert.assertTrue(comparison.equalsBatchIgnoringOrderAndNext(
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC",
                            RunningTraceElement.TrackingPoint.METHOD_END,
                            "exit methodC"
                    ),
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB",
                            RunningTraceElement.TrackingPoint.METHOD_END
                    ),
                    RunningTraceElement.Expected.create(
                            "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA",
                            "exit methodA"
                    )
            ));
            Assert.assertTrue(comparison.isEnd());
        }

        comparison.reset();
        {
            // boolean equalsBatchIgnoringOrder(String... expectedTagSequence)
            Assert.assertTrue(comparison.equalsBatchIgnoringOrder(null, "enter methodC", null));
            comparison.next(3);
            Assert.assertFalse(comparison.isEnd());

            // boolean equalsBatchIgnoringOrderAndNext(String... expectedTagSequence)
            Assert.assertTrue(comparison.equalsBatchIgnoringOrderAndNext("exit methodC", null, "exit methodA"));
            Assert.assertTrue(comparison.isEnd());
        }
    }
}
