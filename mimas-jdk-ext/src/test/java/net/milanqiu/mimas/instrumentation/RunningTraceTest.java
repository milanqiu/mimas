package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.lang.MethodIdentifier;
import net.milanqiu.mimas.system.MimasJdkExtConvention;
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
    public void test_toString() throws Exception {
        methodC();

        StringBuilder sb = new StringBuilder();
        sb.append(trace.toString());
        MimasJdkExtConvention.getSingleton().writeWorkFileInTestOutDir(sb);
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
    public void test_Comparison_next_isEnd_equalsExpected_equalsExpectedAndNext() throws Exception {
        methodC();

        {
            // variant of RunningTraceElement.Expected parameter
            RunningTrace.Comparison comparison = trace.newComparison();
            Assert.assertTrue(comparison.equalsExpected(RunningTraceElement.FullyExpected.create(
                    MethodIdentifier.create("net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC"),
                    RunningTraceElement.TrackingPoint.METHOD_BEGINNING,
                    "enter methodC"
            )));
            comparison.next();
            Assert.assertTrue(comparison.equalsExpected(RunningTraceElement.FullyExpected.create(
                    MethodIdentifier.create("net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB"),
                    RunningTraceElement.TrackingPoint.METHOD_BEGINNING
            )));
            comparison.next();
            Assert.assertTrue(comparison.equalsExpected(RunningTraceElement.FullyExpected.create(
                    MethodIdentifier.create("net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA")
            )));
            comparison.next();
            Assert.assertTrue(comparison.equalsExpectedAndNext(RunningTraceElement.SimplyExpected.create("exit methodA")));
            Assert.assertTrue(comparison.equalsExpectedAndNext(RunningTraceElement.SimplyExpected.create(null)));
            Assert.assertTrue(comparison.equalsExpectedAndNext(RunningTraceElement.SimplyExpected.create("exit methodC")));
            Assert.assertTrue(comparison.isEnd());
        }

        {
            // variant of String parameter
            RunningTrace.Comparison comparison = trace.newComparison();
            Assert.assertTrue(comparison.equalsExpected("enter methodC"));
            comparison.next();
            Assert.assertTrue(comparison.equalsExpected((String) null));
            comparison.next();
            Assert.assertTrue(comparison.equalsExpected((String) null));
            comparison.next();
            Assert.assertTrue(comparison.equalsExpectedAndNext("exit methodA"));
            Assert.assertTrue(comparison.equalsExpectedAndNext((String) null));
            Assert.assertTrue(comparison.equalsExpectedAndNext("exit methodC"));
            Assert.assertTrue(comparison.isEnd());
        }
    }

    @Test
    public void test_Comparison_next_isEnd_equalBatch_equalsBatchAndNext() throws Exception {
        methodC();

        {
            // variant of RunningTraceElement.Expected parameter
            RunningTrace.Comparison comparison = trace.newComparison();
            Assert.assertTrue(comparison.equalsBatch(
                    RunningTraceElement.FullyExpected.create(
                            MethodIdentifier.create("net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC"),
                            RunningTraceElement.TrackingPoint.METHOD_BEGINNING,
                            "enter methodC"
                    ),
                    RunningTraceElement.FullyExpected.create(
                            MethodIdentifier.create("net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB"),
                            RunningTraceElement.TrackingPoint.METHOD_BEGINNING
                    ),
                    RunningTraceElement.FullyExpected.create(
                            MethodIdentifier.create("net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA")
                    )
            ));
            comparison.next(3);
            Assert.assertTrue(comparison.equalsBatchAndNext(
                    RunningTraceElement.SimplyExpected.create("exit methodA"),
                    RunningTraceElement.SimplyExpected.create(null),
                    RunningTraceElement.SimplyExpected.create("exit methodC")
            ));
            Assert.assertTrue(comparison.isEnd());
        }

        {
            // variant of String parameter
            RunningTrace.Comparison comparison = trace.newComparison();
            Assert.assertTrue(comparison.equalsBatch("enter methodC", null, null));
            comparison.next(3);
            Assert.assertTrue(comparison.equalsBatchAndNext("exit methodA", null, "exit methodC"));
            Assert.assertTrue(comparison.isEnd());
        }
    }

    @Test
    public void test_Comparison_next_isEnd_equalBatchIgnoringOrder_equalsBatchIgnoringOrderAndNext() throws Exception {
        methodC();

        {
            // variant of RunningTraceElement.Expected parameter
            RunningTrace.Comparison comparison = trace.newComparison();
            Assert.assertTrue(comparison.equalsBatchIgnoringOrder(
                    RunningTraceElement.FullyExpected.create(
                            MethodIdentifier.create("net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA")
                    ),
                    RunningTraceElement.FullyExpected.create(
                            MethodIdentifier.create("net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC"),
                            RunningTraceElement.TrackingPoint.METHOD_BEGINNING,
                            "enter methodC"
                    ),
                    RunningTraceElement.FullyExpected.create(
                            MethodIdentifier.create("net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB"),
                            RunningTraceElement.TrackingPoint.METHOD_BEGINNING
                    )
            ));
            comparison.next(3);
            Assert.assertTrue(comparison.equalsBatchIgnoringOrderAndNext(
                    RunningTraceElement.SimplyExpected.create("exit methodC"),
                    RunningTraceElement.SimplyExpected.create(null),
                    RunningTraceElement.SimplyExpected.create("exit methodA")
            ));
            Assert.assertTrue(comparison.isEnd());
        }

        {
            // variant of String parameter
            RunningTrace.Comparison comparison = trace.newComparison();
            Assert.assertTrue(comparison.equalsBatchIgnoringOrder(null, "enter methodC", null));
            comparison.next(3);
            Assert.assertTrue(comparison.equalsBatchIgnoringOrderAndNext("exit methodC", null, "exit methodA"));
            Assert.assertTrue(comparison.isEnd());
        }
    }
}
