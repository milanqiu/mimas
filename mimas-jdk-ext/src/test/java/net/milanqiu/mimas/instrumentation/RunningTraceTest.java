package net.milanqiu.mimas.instrumentation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-10-24
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
        //System.out.println(trace.toString());
    }

    @Test
    public void test_Comparison_next_isEnd_equalsExpected_equalsExpectedAndNext() throws Exception {
        methodC();

        RunningTrace.Comparison comparison = trace.newComparison();
        Assert.assertTrue(comparison.equalsExpected(RunningTraceElement.FullyExpected.create(
                "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC",
                RunningTraceElement.TrackingPoint.METHOD_BEGINNING,
                "enter methodC"
        )));
        comparison.next();
        Assert.assertTrue(comparison.equalsExpected(RunningTraceElement.FullyExpected.create(
                "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB",
                RunningTraceElement.TrackingPoint.METHOD_BEGINNING
        )));
        comparison.next();
        Assert.assertTrue(comparison.equalsExpected(RunningTraceElement.FullyExpected.create(
                "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA"
        )));
        comparison.next();
        Assert.assertTrue(comparison.equalsExpectedAndNext(RunningTraceElement.SimplyExpected.create("exit methodA")));
        Assert.assertTrue(comparison.equalsExpectedAndNext(RunningTraceElement.SimplyExpected.create(null)));
        Assert.assertTrue(comparison.equalsExpectedAndNext(RunningTraceElement.SimplyExpected.create("exit methodC")));
        Assert.assertTrue(comparison.isEnd());
    }

    @Test
    public void test_Comparison_next_isEnd_equalBatch_equalsBatchAndNext() throws Exception {
        methodC();

        RunningTrace.Comparison comparison = trace.newComparison();
        Assert.assertTrue(comparison.equalsBatch(
                RunningTraceElement.FullyExpected.create(
                        "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC",
                        RunningTraceElement.TrackingPoint.METHOD_BEGINNING,
                        "enter methodC"
                ),
                RunningTraceElement.FullyExpected.create(
                        "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB",
                        RunningTraceElement.TrackingPoint.METHOD_BEGINNING
                ),
                RunningTraceElement.FullyExpected.create(
                        "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA"
                )
        ));
        comparison.next();
        comparison.next();
        comparison.next();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                RunningTraceElement.SimplyExpected.create("exit methodA"),
                RunningTraceElement.SimplyExpected.create(null),
                RunningTraceElement.SimplyExpected.create("exit methodC")
        ));
        Assert.assertTrue(comparison.isEnd());
    }

    @Test
    public void test_Comparison_next_isEnd_equalBatchIgnoringOrder_equalsBatchIgnoringOrderAndNext() throws Exception {
        methodC();

        RunningTrace.Comparison comparison = trace.newComparison();
        Assert.assertTrue(comparison.equalsBatchIgnoringOrder(
                RunningTraceElement.FullyExpected.create(
                        "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodA"
                ),
                RunningTraceElement.FullyExpected.create(
                        "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodC",
                        RunningTraceElement.TrackingPoint.METHOD_BEGINNING,
                        "enter methodC"
                ),
                RunningTraceElement.FullyExpected.create(
                        "net.milanqiu.mimas.instrumentation.RunningTraceTest", "methodB",
                        RunningTraceElement.TrackingPoint.METHOD_BEGINNING
                )
        ));
        comparison.next();
        comparison.next();
        comparison.next();
        Assert.assertTrue(comparison.equalsBatchIgnoringOrderAndNext(
                RunningTraceElement.SimplyExpected.create("exit methodC"),
                RunningTraceElement.SimplyExpected.create(null),
                RunningTraceElement.SimplyExpected.create("exit methodA")
        ));
        Assert.assertTrue(comparison.isEnd());
    }
}
