package net.milanqiu.mimas.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.instrumentation.RunningTrace;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-12-30
 * @author Milan Qiu
 */
public class EventBusTest {

    private static class RealEvent {
        private int tag;
        public int getTag() {
            return tag;
        }
        public RealEvent(int tag) {
            this.tag = tag;
        }
    }

    private static class FakeEvent {
        private int tag;
        public int getTag() {
            return tag;
        }
        public FakeEvent(int tag) {
            this.tag = tag;
        }
    }

    private static class WildEvent {
        private int tag;
        public int getTag() {
            return tag;
        }
        public WildEvent(int tag) {
            this.tag = tag;
        }
    }

    private static class EventListener {
        @Subscribe
        public void listen(RealEvent event) {
            runningTrace.track("RealEvent listening");
            Assert.assertEquals(INT_0, event.getTag());
        }

        @Subscribe
        public void listen(FakeEvent event) {
            runningTrace.track("FakeEvent listening");
            DebugUtils.neverGoesHere();
        }

        @Subscribe
        public void listen(DeadEvent event) {
            runningTrace.track("DeadEvent listening");
            Assert.assertEquals(EventBusTest.eventBus, event.getSource());
            Assert.assertEquals(WildEvent.class, event.getEvent().getClass());
            Assert.assertEquals(INT_2, ((WildEvent) event.getEvent()).getTag());
        }
    }

    private static EventBus eventBus;
    private EventListener eventListener;

    private static RunningTrace runningTrace;

    @Before
    public void setUp() throws Exception {
        eventBus = new EventBus();
        eventListener = new EventListener();
        eventBus.register(eventListener);

        runningTrace = new RunningTrace();
    }

    @Test
    public void test_post() throws Exception {
        eventBus.post(new RealEvent(INT_0));

        RunningTrace.Comparison comparison = runningTrace.newComparison();
        Assert.assertTrue(comparison.equalsExpectedAndNext("RealEvent listening"));
        Assert.assertTrue(comparison.isEnd());
    }

    @Test
    public void test_unregister() throws Exception {
        eventBus.unregister(eventListener);
        eventBus.post(new FakeEvent(INT_1));

        Assert.assertTrue(runningTrace.isEmpty());
    }

    @Test
    public void test_DeadEvent() throws Exception {
        eventBus.post(new WildEvent(INT_2));

        RunningTrace.Comparison comparison = runningTrace.newComparison();
        Assert.assertTrue(comparison.equalsExpectedAndNext("DeadEvent listening"));
        Assert.assertTrue(comparison.isEnd());
    }
}
