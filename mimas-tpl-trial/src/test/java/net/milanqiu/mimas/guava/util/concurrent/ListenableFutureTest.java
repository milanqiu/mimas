package net.milanqiu.mimas.guava.util.concurrent;

import com.google.common.util.concurrent.*;
import net.milanqiu.mimas.instrumentation.RunningTrace;
import net.milanqiu.mimas.instrumentation.exception.DeliberateException;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Creation Date: 2014-10-24
 * @author Milan Qiu
 */
public class ListenableFutureTest {

    @SuppressWarnings("StatementWithEmptyBody")
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
                "Callable invoked",
                "Callable returned",
                "Listener invoked"
        ));
        Assert.assertTrue(comparison.isEnd());
    }

    @SuppressWarnings("StatementWithEmptyBody")
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
                "Callable invoked"
        ));
        // case 1: "Callable returned" tracks before "Callback invoked"
        // case 2: "Callback invoked" tracks before "Callable returned"
        // case 3: "Callable returned" only, "Callback invoked" has not been switched to yet
        if (comparison.equalsExpected("Callable returned")) {
            comparison.next();
            if (!comparison.isEnd()) {
                Assert.assertTrue(comparison.equalsExpectedAndNext(
                        "Callback invoked : Callable returned"
                ));
            }
        } else {
            Assert.assertTrue(comparison.equalsBatchAndNext(
                    "Callback invoked : Callable returned",
                    "Callable returned"
            ));
        }
        Assert.assertTrue(comparison.isEnd());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Test
    public void test_addCallback_onFailure() throws Exception {
        final RunningTrace runningTrace = new RunningTrace();
        final Exception deliberateException = new DeliberateException("Deliberate Exception");

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
        AssertExt.assertExceptionThrown(future::get, ExecutionException.class, deliberateException);
        TimeUnit.MILLISECONDS.sleep(10);

        RunningTrace.Comparison comparison = runningTrace.newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "Callable invoked",
                "Callback failed : Deliberate Exception"
        ));
        Assert.assertTrue(comparison.isEnd());
    }
}
