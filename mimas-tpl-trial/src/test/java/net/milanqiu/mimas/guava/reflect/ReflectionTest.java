package net.milanqiu.mimas.guava.reflect;

import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.Reflection;
import net.milanqiu.mimas.instrumentation.RunningTrace;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Creation Date: 2015-01-05
 * @author Milan Qiu
 */
public class ReflectionTest {

    @Test
    public void test_getPackageName() throws Exception {
        // String getPackageName(Class<?> clazz)
        Assert.assertEquals("java.util", Reflection.getPackageName(Map.class));

        // String getPackageName(String classFullName)
        Assert.assertEquals("java.util", Reflection.getPackageName("java.util.Map"));
    }

    private static RunningTrace traceTestInitialize = new RunningTrace();

    private static class Foo {
        static {
            traceTestInitialize.track("Foo initializing");
        }
    }

    @Test
    public void test_initialize() throws Exception {
        Class<?> fooClass = Foo.class;
        Assert.assertTrue(traceTestInitialize.isEmpty());

        Reflection.initialize(Foo.class);
        RunningTrace.Comparison comparison = traceTestInitialize.newComparison();
        Assert.assertTrue(comparison.equalsExpectedAndNext("Foo initializing"));
        Assert.assertTrue(comparison.isEnd());
    }

    private static RunningTrace traceTestNewProxy = new RunningTrace();

    private static interface Task {
        public void execute();
    }

    private static class ActualWork implements Task {
        @Override
        public void execute() {
            traceTestNewProxy.track("ActualWork executing");
        }
    }

    private static class TaskHandler extends AbstractInvocationHandler {
        private Task actualWork;

        public TaskHandler(Task actualWork) {
            super();
            this.actualWork = actualWork;
        }

        @Override
        protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(actualWork, args);
        }
    }

    @Test
    public void test_newProxy() throws Exception {
        TaskHandler taskHandler = new TaskHandler(new ActualWork());
        Task task = Reflection.newProxy(Task.class, taskHandler);
        task.execute();

        RunningTrace.Comparison comparison = traceTestNewProxy.newComparison();
        Assert.assertTrue(comparison.equalsExpectedAndNext("ActualWork executing"));
        Assert.assertTrue(comparison.isEnd());
    }
}
