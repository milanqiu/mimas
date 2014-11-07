package net.milanqiu.mimas.guava.concurrent;

import com.google.common.util.concurrent.*;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.instrumentation.RunningTrace;
import net.milanqiu.mimas.instrumentation.RunningTraceElement;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-10-24
 * @author Milan Qiu
 */
public class ListenableFutureTest {

    @Test
    public void test_addListener() throws Exception {
        final RunningTrace runningTrace = new RunningTrace();

        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        ListenableFuture<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                runningTrace.track("Callable invoked");
                TimeUnit.MILLISECONDS.sleep(10);
                return "Callable returned";
            }
        });
        future.addListener(new Runnable() {
            @Override
            public void run() {
                runningTrace.track("Listener invoked");
            }
        }, executorService);
        while (!future.isDone());
        runningTrace.track(future.get());
        TimeUnit.MILLISECONDS.sleep(10);

        RunningTrace.Comparison comparison = runningTrace.newComparison();
        // it seems "Callable returned" always tracks before "Listener invoked"
        Assert.assertTrue(comparison.equalsBatchAndNext(
                RunningTraceElement.SimplyExpected.create("Callable invoked"),
                RunningTraceElement.SimplyExpected.create("Callable returned"),
                RunningTraceElement.SimplyExpected.create("Listener invoked")
        ));
        Assert.assertTrue(comparison.isEnd());
    }

    @Test
    public void test_addCallback_onSuccess() throws Exception {
        final RunningTrace runningTrace = new RunningTrace();

        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        ListenableFuture<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                runningTrace.track("Callable invoked");
                TimeUnit.MILLISECONDS.sleep(10);
                return "Callable returned";
            }
        });
        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(String s) {
                runningTrace.track("Callback invoked : " + s);
            }

            @Override
            public void onFailure(Throwable throwable) {
                runningTrace.track("Callback failed : " + throwable.getMessage());
            }
        });
        while (!future.isDone());
        runningTrace.track(future.get());
        TimeUnit.MILLISECONDS.sleep(10);

        RunningTrace.Comparison comparison = runningTrace.newComparison();
        Assert.assertTrue(comparison.equalsExpectedAndNext(
                RunningTraceElement.SimplyExpected.create("Callable invoked")
        ));
        // when runs this test case in IDE, "Callback invoked" tracks before "Callable returned"
        // when runs this test case in Maven, "Callable returned" tracks before "Callback invoked"
        Assert.assertTrue(comparison.equalsBatchIgnoringOrderAndNext(
                RunningTraceElement.SimplyExpected.create("Callback invoked : Callable returned"),
                RunningTraceElement.SimplyExpected.create("Callable returned")
        ));
        Assert.assertTrue(comparison.isEnd());
    }

    @Test
    public void test_addCallback_onFailure() throws Exception {
        final RunningTrace runningTrace = new RunningTrace();
        final Exception deliberateException = new Exception("Deliberate Exception");

        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        ListenableFuture<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                runningTrace.track("Callable invoked");
                TimeUnit.MILLISECONDS.sleep(10);
                throw deliberateException;
            }
        });
        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(String s) {
                runningTrace.track("Callback invoked : " + s);
            }

            @Override
            public void onFailure(Throwable throwable) {
                runningTrace.track("Callback failed : " + throwable.getMessage());
            }
        });
        while (!future.isDone());
        try {
            future.get();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ExecutionException);
            Assert.assertSame(deliberateException, e.getCause());
        }
        TimeUnit.MILLISECONDS.sleep(10);

        RunningTrace.Comparison comparison = runningTrace.newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                RunningTraceElement.SimplyExpected.create("Callable invoked"),
                RunningTraceElement.SimplyExpected.create("Callback failed : Deliberate Exception")
        ));
        Assert.assertTrue(comparison.isEnd());
    }
}
