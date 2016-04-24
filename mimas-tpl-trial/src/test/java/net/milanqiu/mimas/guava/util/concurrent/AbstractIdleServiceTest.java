package net.milanqiu.mimas.guava.util.concurrent;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.Service;
import net.milanqiu.mimas.concurrent.ConcurrentUtils;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.instrumentation.exception.DeliberateException;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class AbstractIdleServiceTest {

    private class DefaultService extends AbstractIdleService {
        @Override
        protected void startUp() throws Exception {
            TimeUnit.MILLISECONDS.sleep(10);
        }

        @Override
        protected void shutDown() throws Exception {
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    @Test
    public void test_Normal() throws Exception {
        AbstractIdleService service = new DefaultService();
        Assert.assertEquals(Service.State.NEW, service.state());

        service.startAsync();
        Assert.assertEquals(Service.State.STARTING, service.state());

        service.awaitRunning();
        Assert.assertEquals(Service.State.RUNNING, service.state());

        service.stopAsync();
        Assert.assertEquals(Service.State.STOPPING, service.state());

        service.awaitTerminated();
        Assert.assertEquals(Service.State.TERMINATED, service.state());
    }

    @Test
    public void test_StartUpFailed() throws Exception {
        ConcurrentUtils.blockUncaughtExceptions();
        try {
            final Exception deliberateException = new DeliberateException();
            AbstractIdleService service = new DefaultService() {
                @Override
                protected void startUp() throws Exception {
                    super.startUp();
                    throw deliberateException;
                }
            };

            service.startAsync();
            Assert.assertEquals(Service.State.STARTING, service.state());

            try {
                service.awaitRunning();
                DebugUtils.neverGoesHere();
            } catch (Exception e) {
                AssertExt.assertClassification(IllegalStateException.class, e);
                Assert.assertSame(deliberateException, e.getCause());
            }
            Assert.assertEquals(Service.State.FAILED, service.state());
            Assert.assertSame(deliberateException, service.failureCause());
        } finally {
            TimeUnit.MILLISECONDS.sleep(10);
            ConcurrentUtils.unblockUncaughtExceptions();
        }
    }

    @Test
    public void test_ShutDownFailed() throws Exception {
        ConcurrentUtils.blockUncaughtExceptions();
        try {
            final Exception deliberateException = new DeliberateException();
            AbstractIdleService service = new DefaultService() {
                @Override
                protected void shutDown() throws Exception {
                    super.shutDown();
                    throw deliberateException;
                }
            };

            service.startAsync();
            Assert.assertEquals(Service.State.STARTING, service.state());

            service.awaitRunning();
            Assert.assertEquals(Service.State.RUNNING, service.state());

            service.stopAsync();
            Assert.assertEquals(Service.State.STOPPING, service.state());

            try {
                service.awaitTerminated();
                DebugUtils.neverGoesHere();
            } catch (Exception e) {
                AssertExt.assertClassification(IllegalStateException.class, e);
                Assert.assertSame(deliberateException, e.getCause());
            }
            Assert.assertEquals(Service.State.FAILED, service.state());
            Assert.assertSame(deliberateException, service.failureCause());
        } finally {
            TimeUnit.MILLISECONDS.sleep(10);
            ConcurrentUtils.unblockUncaughtExceptions();
        }
    }
}
