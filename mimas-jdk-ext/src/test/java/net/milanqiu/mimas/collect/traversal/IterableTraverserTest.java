package net.milanqiu.mimas.collect.traversal;

import net.milanqiu.mimas.collect.CollectionUtils;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static net.milanqiu.mimas.collect.traversal.TraversableConsts.*;

/**
 * Creation Date: 2016-09-27
 * @author Milan Qiu
 */
public class IterableTraverserTest {

    @Before
    public void setUp() throws Exception {
        createTree();
        createGraph();
    }

    @Test
    public void test_preOrderTraversal_tree() throws Exception {
        Iterable<Traversable> iterable;
        Iterator<Traversable> iterator;

        // doesn't allow cycle
        iterable = IterableTraverser.create(false).preOrderTraversal(treeRoot);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(aaa, bbb, ccc, ddd, eee, fff, ggg), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(aaa, bbb, ccc, ddd, eee, fff, ggg), iterable));

        iterator = iterable.iterator();
        iterator.forEachRemaining((element) -> {});
        AssertExt.assertExceptionThrown(iterator::next, NoSuchElementException.class);

        // allows cycle
        iterable = IterableTraverser.create(true).preOrderTraversal(treeRoot);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(aaa, bbb, ccc, ddd, eee, fff, ggg), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(aaa, bbb, ccc, ddd, eee, fff, ggg), iterable));

        iterator = iterable.iterator();
        iterator.forEachRemaining((element) -> {});
        AssertExt.assertExceptionThrown(iterator::next, NoSuchElementException.class);
    }

    @Test
    public void test_preOrderTraversal_graph() throws Exception {
        Iterable<Traversable> iterable;
        Iterator<Traversable> iterator;

        // allows cycle
        IterableTraverser traverser = IterableTraverser.create(true);

        iterable = traverser.preOrderTraversal(ppp);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(ppp, rrr, sss, ttt, qqq), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(ppp, rrr, sss, ttt, qqq), iterable));

        iterable = traverser.preOrderTraversal(qqq);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(qqq, ttt, ppp, rrr, sss), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(qqq, ttt, ppp, rrr, sss), iterable));

        iterable = traverser.preOrderTraversal(rrr);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(rrr, ppp, ttt, qqq, sss), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(rrr, ppp, ttt, qqq, sss), iterable));

        iterable = traverser.preOrderTraversal(sss);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(sss, rrr, ppp, ttt, qqq), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(sss, rrr, ppp, ttt, qqq), iterable));

        iterable = traverser.preOrderTraversal(ttt);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(ttt, ppp, rrr, sss, qqq), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(ttt, ppp, rrr, sss, qqq), iterable));

        iterator = iterable.iterator();
        iterator.forEachRemaining((element) -> {});
        AssertExt.assertExceptionThrown(iterator::next, NoSuchElementException.class);

        // doesn't allow cycle, is meaningless and should be avoided
        traverser = IterableTraverser.create(false);

        iterable = traverser.preOrderTraversal(ppp);
        iterator = iterable.iterator();
        for (TraversableElement element : Arrays.asList(ppp, rrr, ppp, rrr, ppp, rrr, ppp, rrr)) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertTrue(iterator.hasNext());
            Assert.assertSame(element, iterator.next());
        }
        Assert.assertTrue(iterator.hasNext());

        iterable = traverser.preOrderTraversal(qqq);
        iterator = iterable.iterator();
        for (TraversableElement element : Arrays.asList(qqq, ttt, ppp, rrr, ppp, rrr, ppp, rrr)) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertTrue(iterator.hasNext());
            Assert.assertSame(element, iterator.next());
        }
        Assert.assertTrue(iterator.hasNext());
    }

    @Test
    public void test_postOrderTraversal_tree() throws Exception {
        Iterable<Traversable> iterable;
        Iterator<Traversable> iterator;

        // doesn't allow cycle
        iterable = IterableTraverser.create(false).postOrderTraversal(treeRoot);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(ccc, bbb, eee, fff, ddd, ggg, aaa), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(ccc, bbb, eee, fff, ddd, ggg, aaa), iterable));

        iterator = iterable.iterator();
        iterator.forEachRemaining((element) -> {});
        AssertExt.assertExceptionThrown(iterator::next, NoSuchElementException.class);

        // allows cycle
        iterable = IterableTraverser.create(true).postOrderTraversal(treeRoot);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(ccc, bbb, eee, fff, ddd, ggg, aaa), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(ccc, bbb, eee, fff, ddd, ggg, aaa), iterable));

        iterator = iterable.iterator();
        iterator.forEachRemaining((element) -> {});
        AssertExt.assertExceptionThrown(iterator::next, NoSuchElementException.class);
    }

    @Test
    public void test_postOrderTraversal_graph() throws Exception {
        Iterable<Traversable> iterable;
        Iterator<Traversable> iterator;

        // allows cycle
        IterableTraverser traverser = IterableTraverser.create(true);

        iterable = traverser.postOrderTraversal(ppp);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(sss, qqq, ttt, rrr, ppp), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(sss, qqq, ttt, rrr, ppp), iterable));

        iterable = traverser.postOrderTraversal(qqq);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(sss, rrr, ppp, ttt, qqq), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(sss, rrr, ppp, ttt, qqq), iterable));

        iterable = traverser.postOrderTraversal(rrr);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(qqq, ttt, ppp, sss, rrr), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(qqq, ttt, ppp, sss, rrr), iterable));

        iterable = traverser.postOrderTraversal(sss);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(qqq, ttt, ppp, rrr, sss), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(qqq, ttt, ppp, rrr, sss), iterable));

        iterable = traverser.postOrderTraversal(ttt);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(sss, rrr, ppp, qqq, ttt), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(sss, rrr, ppp, qqq, ttt), iterable));

        iterator = iterable.iterator();
        iterator.forEachRemaining((element) -> {});
        AssertExt.assertExceptionThrown(iterator::next, NoSuchElementException.class);

        // doesn't allow cycle, is meaningless and should be avoided
        traverser = IterableTraverser.create(false);

        iterable = traverser.postOrderTraversal(ppp);
        iterator = iterable.iterator();
        for (TraversableElement element : Arrays.asList(ppp, rrr, sss, ppp, ttt)) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertTrue(iterator.hasNext());
            Assert.assertSame(element, iterator.next());
        }
        Assert.assertTrue(iterator.hasNext());

        iterable = traverser.postOrderTraversal(qqq);
        iterator = iterable.iterator();
        for (TraversableElement element : Arrays.asList(ppp, rrr, sss, ttt, rrr)) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertTrue(iterator.hasNext());
            Assert.assertSame(element, iterator.next());
        }
        Assert.assertTrue(iterator.hasNext());
    }

    @Test
    public void test_breadthFirstTraversal_tree() throws Exception {
        Iterable<Traversable> iterable;
        Iterator<Traversable> iterator;

        // doesn't allow cycle
        iterable = IterableTraverser.create(false).breadthFirstTraversal(treeRoot);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(aaa, bbb, ddd, ggg, ccc, eee, fff), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(aaa, bbb, ddd, ggg, ccc, eee, fff), iterable));

        iterator = iterable.iterator();
        iterator.forEachRemaining((element) -> {});
        AssertExt.assertExceptionThrown(iterator::next, NoSuchElementException.class);

        // allows cycle
        iterable = IterableTraverser.create(true).breadthFirstTraversal(treeRoot);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(aaa, bbb, ddd, ggg, ccc, eee, fff), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(aaa, bbb, ddd, ggg, ccc, eee, fff), iterable));

        iterator = iterable.iterator();
        iterator.forEachRemaining((element) -> {});
        AssertExt.assertExceptionThrown(iterator::next, NoSuchElementException.class);
    }

    @Test
    public void test_breadthFirstTraversal_graph() throws Exception {
        Iterable<Traversable> iterable;
        Iterator<Traversable> iterator;

        // allows cycle
        IterableTraverser traverser = IterableTraverser.create(true);

        iterable = traverser.breadthFirstTraversal(ppp);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(ppp, rrr, ttt, sss, qqq), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(ppp, rrr, ttt, sss, qqq), iterable));

        iterable = traverser.breadthFirstTraversal(qqq);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(qqq, ttt, ppp, rrr, sss), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(qqq, ttt, ppp, rrr, sss), iterable));

        iterable = traverser.breadthFirstTraversal(rrr);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(rrr, ppp, sss, ttt, qqq), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(rrr, ppp, sss, ttt, qqq), iterable));

        iterable = traverser.breadthFirstTraversal(sss);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(sss, rrr, ppp, ttt, qqq), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(sss, rrr, ppp, ttt, qqq), iterable));

        iterable = traverser.breadthFirstTraversal(ttt);
        Assert.assertTrue(CollectionUtils.equals(Arrays.asList(ttt, ppp, qqq, rrr, sss), iterable));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(Arrays.asList(ttt, ppp, qqq, rrr, sss), iterable));

        iterator = iterable.iterator();
        iterator.forEachRemaining((element) -> {});
        AssertExt.assertExceptionThrown(iterator::next, NoSuchElementException.class);

        // doesn't allow cycle, is meaningless and should be avoided
        traverser = IterableTraverser.create(false);

        iterable = traverser.breadthFirstTraversal(ppp);
        iterator = iterable.iterator();
        for (TraversableElement element : Arrays.asList(ppp, rrr, ttt, ppp, sss, ttt, ppp, qqq, rrr)) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertTrue(iterator.hasNext());
            Assert.assertSame(element, iterator.next());
        }
        Assert.assertTrue(iterator.hasNext());

        iterable = traverser.breadthFirstTraversal(qqq);
        iterator = iterable.iterator();
        for (TraversableElement element : Arrays.asList(qqq, ttt, ppp, qqq, rrr, rrr, ttt)) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertTrue(iterator.hasNext());
            Assert.assertSame(element, iterator.next());
        }
        Assert.assertTrue(iterator.hasNext());
    }
}
