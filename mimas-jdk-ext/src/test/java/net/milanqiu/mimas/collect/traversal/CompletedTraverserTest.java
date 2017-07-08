package net.milanqiu.mimas.collect.traversal;

import net.milanqiu.mimas.instrumentation.RunningTrace;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static net.milanqiu.mimas.collect.traversal.TraversableConsts.*;

/**
 * Creation Date: 2016-12-08
 * @author Milan Qiu
 */
public class CompletedTraverserTest {

    private static class TracedTraversalListener implements TraversalListener {

        private RunningTrace runningTrace = new RunningTrace();

        public RunningTrace getRunningTrace() {
            return runningTrace;
        }

        @Override
        public void visitElement(int level, Traversable element) {
            runningTrace.track("visitElement                level: " + level + "    element: " + element);
        }

        @Override
        public void enterNextLevel(int toLevel, Traversable fromElement, Traversable toElement) {
            runningTrace.track("enterNextLevel            toLevel: " + toLevel + "    fromElement: " + fromElement + "    toElement: " + toElement);
        }

        @Override
        public void enterPrevLevel(int toLevel, Traversable fromElement, Traversable toElement) {
            runningTrace.track("enterPrevLevel            toLevel: " + toLevel + "    fromElement: " + fromElement + "    toElement: " + toElement);
        }

        @Override
        public void travelBetweenAdjacencies(int level, Traversable fromElement, Traversable toElement) {
            runningTrace.track("travelBetweenAdjacencies    level: " + level + "    fromElement: " + fromElement + "    toElement: " + toElement);
        }
    }

    private TracedTraversalListener listener;

    @Before
    public void setUp() throws Exception {
        createTree();
        createGraph();
        listener = new TracedTraversalListener();
    }

    @Test
    public void test_preOrderTraversal_tree() throws Exception {
        // doesn't allow cycle
        listener.getRunningTrace().clear();
        CompletedTraverser.create(listener, false).preOrderTraversal(treeRoot);
        RunningTrace.Comparison comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: aaa",
                "enterNextLevel            toLevel: 1    fromElement: aaa    toElement: bbb",
                "visitElement                level: 1    element: bbb",
                "enterNextLevel            toLevel: 2    fromElement: bbb    toElement: ccc",
                "visitElement                level: 2    element: ccc",
                "enterPrevLevel            toLevel: 1    fromElement: ccc    toElement: bbb",
                "travelBetweenAdjacencies    level: 1    fromElement: bbb    toElement: ddd",
                "visitElement                level: 1    element: ddd",
                "enterNextLevel            toLevel: 2    fromElement: ddd    toElement: eee",
                "visitElement                level: 2    element: eee",
                "travelBetweenAdjacencies    level: 2    fromElement: eee    toElement: fff",
                "visitElement                level: 2    element: fff",
                "enterPrevLevel            toLevel: 1    fromElement: fff    toElement: ddd",
                "travelBetweenAdjacencies    level: 1    fromElement: ddd    toElement: ggg",
                "visitElement                level: 1    element: ggg",
                "enterPrevLevel            toLevel: 0    fromElement: ggg    toElement: aaa"
        ));
        Assert.assertTrue(comparison.isEnd());

        // allows cycle
        listener.getRunningTrace().clear();
        CompletedTraverser.create(listener, true).preOrderTraversal(treeRoot);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: aaa",
                "enterNextLevel            toLevel: 1    fromElement: aaa    toElement: bbb",
                "visitElement                level: 1    element: bbb",
                "enterNextLevel            toLevel: 2    fromElement: bbb    toElement: ccc",
                "visitElement                level: 2    element: ccc",
                "enterPrevLevel            toLevel: 1    fromElement: ccc    toElement: bbb",
                "travelBetweenAdjacencies    level: 1    fromElement: bbb    toElement: ddd",
                "visitElement                level: 1    element: ddd",
                "enterNextLevel            toLevel: 2    fromElement: ddd    toElement: eee",
                "visitElement                level: 2    element: eee",
                "travelBetweenAdjacencies    level: 2    fromElement: eee    toElement: fff",
                "visitElement                level: 2    element: fff",
                "enterPrevLevel            toLevel: 1    fromElement: fff    toElement: ddd",
                "travelBetweenAdjacencies    level: 1    fromElement: ddd    toElement: ggg",
                "visitElement                level: 1    element: ggg",
                "enterPrevLevel            toLevel: 0    fromElement: ggg    toElement: aaa"
        ));
        Assert.assertTrue(comparison.isEnd());
    }

    @Test
    public void test_preOrderTraversal_graph() throws Exception {
        // allows cycle
        CompletedTraverser traverser = CompletedTraverser.create(listener, true);

        listener.getRunningTrace().clear();
        traverser.preOrderTraversal(ppp);
        RunningTrace.Comparison comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: ppp",
                "enterNextLevel            toLevel: 1    fromElement: ppp    toElement: rrr",
                "visitElement                level: 1    element: rrr",
                "enterNextLevel            toLevel: 2    fromElement: rrr    toElement: sss",
                "visitElement                level: 2    element: sss",
                "travelBetweenAdjacencies    level: 2    fromElement: sss    toElement: ttt",
                "visitElement                level: 2    element: ttt",
                "enterNextLevel            toLevel: 3    fromElement: ttt    toElement: qqq",
                "visitElement                level: 3    element: qqq",
                "enterPrevLevel            toLevel: 2    fromElement: qqq    toElement: ttt",
                "enterPrevLevel            toLevel: 1    fromElement: ttt    toElement: rrr",
                "enterPrevLevel            toLevel: 0    fromElement: rrr    toElement: ppp"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.preOrderTraversal(qqq);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: qqq",
                "enterNextLevel            toLevel: 1    fromElement: qqq    toElement: ttt",
                "visitElement                level: 1    element: ttt",
                "enterNextLevel            toLevel: 2    fromElement: ttt    toElement: ppp",
                "visitElement                level: 2    element: ppp",
                "enterNextLevel            toLevel: 3    fromElement: ppp    toElement: rrr",
                "visitElement                level: 3    element: rrr",
                "enterNextLevel            toLevel: 4    fromElement: rrr    toElement: sss",
                "visitElement                level: 4    element: sss",
                "enterPrevLevel            toLevel: 3    fromElement: sss    toElement: rrr",
                "enterPrevLevel            toLevel: 2    fromElement: rrr    toElement: ppp",
                "enterPrevLevel            toLevel: 1    fromElement: ppp    toElement: ttt",
                "enterPrevLevel            toLevel: 0    fromElement: ttt    toElement: qqq"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.preOrderTraversal(rrr);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: rrr",
                "enterNextLevel            toLevel: 1    fromElement: rrr    toElement: ppp",
                "visitElement                level: 1    element: ppp",
                "enterNextLevel            toLevel: 2    fromElement: ppp    toElement: ttt",
                "visitElement                level: 2    element: ttt",
                "enterNextLevel            toLevel: 3    fromElement: ttt    toElement: qqq",
                "visitElement                level: 3    element: qqq",
                "enterPrevLevel            toLevel: 2    fromElement: qqq    toElement: ttt",
                "enterPrevLevel            toLevel: 1    fromElement: ttt    toElement: ppp",
                "travelBetweenAdjacencies    level: 1    fromElement: ppp    toElement: sss",
                "visitElement                level: 1    element: sss",
                "enterPrevLevel            toLevel: 0    fromElement: sss    toElement: rrr"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.preOrderTraversal(sss);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: sss",
                "enterNextLevel            toLevel: 1    fromElement: sss    toElement: rrr",
                "visitElement                level: 1    element: rrr",
                "enterNextLevel            toLevel: 2    fromElement: rrr    toElement: ppp",
                "visitElement                level: 2    element: ppp",
                "enterNextLevel            toLevel: 3    fromElement: ppp    toElement: ttt",
                "visitElement                level: 3    element: ttt",
                "enterNextLevel            toLevel: 4    fromElement: ttt    toElement: qqq",
                "visitElement                level: 4    element: qqq",
                "enterPrevLevel            toLevel: 3    fromElement: qqq    toElement: ttt",
                "enterPrevLevel            toLevel: 2    fromElement: ttt    toElement: ppp",
                "enterPrevLevel            toLevel: 1    fromElement: ppp    toElement: rrr",
                "enterPrevLevel            toLevel: 0    fromElement: rrr    toElement: sss"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.preOrderTraversal(ttt);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: ttt",
                "enterNextLevel            toLevel: 1    fromElement: ttt    toElement: ppp",
                "visitElement                level: 1    element: ppp",
                "enterNextLevel            toLevel: 2    fromElement: ppp    toElement: rrr",
                "visitElement                level: 2    element: rrr",
                "enterNextLevel            toLevel: 3    fromElement: rrr    toElement: sss",
                "visitElement                level: 3    element: sss",
                "enterPrevLevel            toLevel: 2    fromElement: sss    toElement: rrr",
                "enterPrevLevel            toLevel: 1    fromElement: rrr    toElement: ppp",
                "travelBetweenAdjacencies    level: 1    fromElement: ppp    toElement: qqq",
                "visitElement                level: 1    element: qqq",
                "enterPrevLevel            toLevel: 0    fromElement: qqq    toElement: ttt"
        ));
        Assert.assertTrue(comparison.isEnd());

        // doesn't allow cycle, is meaningless and endless and should be avoided
        //CompletedTraverser.create(listener, false).preOrderTraversal(ppp);
    }

    @Test
    public void test_postOrderTraversal_tree() throws Exception {
        // doesn't allow cycle
        listener.getRunningTrace().clear();
        CompletedTraverser.create(listener, false).postOrderTraversal(treeRoot);
        RunningTrace.Comparison comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "enterNextLevel            toLevel: 1    fromElement: aaa    toElement: bbb",
                "enterNextLevel            toLevel: 2    fromElement: bbb    toElement: ccc",
                "visitElement                level: 2    element: ccc",
                "enterPrevLevel            toLevel: 1    fromElement: ccc    toElement: bbb",
                "visitElement                level: 1    element: bbb",
                "travelBetweenAdjacencies    level: 1    fromElement: bbb    toElement: ddd",
                "enterNextLevel            toLevel: 2    fromElement: ddd    toElement: eee",
                "visitElement                level: 2    element: eee",
                "travelBetweenAdjacencies    level: 2    fromElement: eee    toElement: fff",
                "visitElement                level: 2    element: fff",
                "enterPrevLevel            toLevel: 1    fromElement: fff    toElement: ddd",
                "visitElement                level: 1    element: ddd",
                "travelBetweenAdjacencies    level: 1    fromElement: ddd    toElement: ggg",
                "visitElement                level: 1    element: ggg",
                "enterPrevLevel            toLevel: 0    fromElement: ggg    toElement: aaa",
                "visitElement                level: 0    element: aaa"
        ));
        Assert.assertTrue(comparison.isEnd());

        // allows cycle
        listener.getRunningTrace().clear();
        CompletedTraverser.create(listener, true).postOrderTraversal(treeRoot);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "enterNextLevel            toLevel: 1    fromElement: aaa    toElement: bbb",
                "enterNextLevel            toLevel: 2    fromElement: bbb    toElement: ccc",
                "visitElement                level: 2    element: ccc",
                "enterPrevLevel            toLevel: 1    fromElement: ccc    toElement: bbb",
                "visitElement                level: 1    element: bbb",
                "travelBetweenAdjacencies    level: 1    fromElement: bbb    toElement: ddd",
                "enterNextLevel            toLevel: 2    fromElement: ddd    toElement: eee",
                "visitElement                level: 2    element: eee",
                "travelBetweenAdjacencies    level: 2    fromElement: eee    toElement: fff",
                "visitElement                level: 2    element: fff",
                "enterPrevLevel            toLevel: 1    fromElement: fff    toElement: ddd",
                "visitElement                level: 1    element: ddd",
                "travelBetweenAdjacencies    level: 1    fromElement: ddd    toElement: ggg",
                "visitElement                level: 1    element: ggg",
                "enterPrevLevel            toLevel: 0    fromElement: ggg    toElement: aaa",
                "visitElement                level: 0    element: aaa"
        ));
        Assert.assertTrue(comparison.isEnd());
    }

    @Test
    public void test_postOrderTraversal_graph() throws Exception {
        // allows cycle
        CompletedTraverser traverser = CompletedTraverser.create(listener, true);

        listener.getRunningTrace().clear();
        traverser.postOrderTraversal(ppp);
        RunningTrace.Comparison comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "enterNextLevel            toLevel: 1    fromElement: ppp    toElement: rrr",
                "enterNextLevel            toLevel: 2    fromElement: rrr    toElement: sss",
                "visitElement                level: 2    element: sss",
                "travelBetweenAdjacencies    level: 2    fromElement: sss    toElement: ttt",
                "enterNextLevel            toLevel: 3    fromElement: ttt    toElement: qqq",
                "visitElement                level: 3    element: qqq",
                "enterPrevLevel            toLevel: 2    fromElement: qqq    toElement: ttt",
                "visitElement                level: 2    element: ttt",
                "enterPrevLevel            toLevel: 1    fromElement: ttt    toElement: rrr",
                "visitElement                level: 1    element: rrr",
                "enterPrevLevel            toLevel: 0    fromElement: rrr    toElement: ppp",
                "visitElement                level: 0    element: ppp"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.postOrderTraversal(qqq);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "enterNextLevel            toLevel: 1    fromElement: qqq    toElement: ttt",
                "enterNextLevel            toLevel: 2    fromElement: ttt    toElement: ppp",
                "enterNextLevel            toLevel: 3    fromElement: ppp    toElement: rrr",
                "enterNextLevel            toLevel: 4    fromElement: rrr    toElement: sss",
                "visitElement                level: 4    element: sss",
                "enterPrevLevel            toLevel: 3    fromElement: sss    toElement: rrr",
                "visitElement                level: 3    element: rrr",
                "enterPrevLevel            toLevel: 2    fromElement: rrr    toElement: ppp",
                "visitElement                level: 2    element: ppp",
                "enterPrevLevel            toLevel: 1    fromElement: ppp    toElement: ttt",
                "visitElement                level: 1    element: ttt",
                "enterPrevLevel            toLevel: 0    fromElement: ttt    toElement: qqq",
                "visitElement                level: 0    element: qqq"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.postOrderTraversal(rrr);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "enterNextLevel            toLevel: 1    fromElement: rrr    toElement: ppp",
                "enterNextLevel            toLevel: 2    fromElement: ppp    toElement: ttt",
                "enterNextLevel            toLevel: 3    fromElement: ttt    toElement: qqq",
                "visitElement                level: 3    element: qqq",
                "enterPrevLevel            toLevel: 2    fromElement: qqq    toElement: ttt",
                "visitElement                level: 2    element: ttt",
                "enterPrevLevel            toLevel: 1    fromElement: ttt    toElement: ppp",
                "visitElement                level: 1    element: ppp",
                "travelBetweenAdjacencies    level: 1    fromElement: ppp    toElement: sss",
                "visitElement                level: 1    element: sss",
                "enterPrevLevel            toLevel: 0    fromElement: sss    toElement: rrr",
                "visitElement                level: 0    element: rrr"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.postOrderTraversal(sss);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "enterNextLevel            toLevel: 1    fromElement: sss    toElement: rrr",
                "enterNextLevel            toLevel: 2    fromElement: rrr    toElement: ppp",
                "enterNextLevel            toLevel: 3    fromElement: ppp    toElement: ttt",
                "enterNextLevel            toLevel: 4    fromElement: ttt    toElement: qqq",
                "visitElement                level: 4    element: qqq",
                "enterPrevLevel            toLevel: 3    fromElement: qqq    toElement: ttt",
                "visitElement                level: 3    element: ttt",
                "enterPrevLevel            toLevel: 2    fromElement: ttt    toElement: ppp",
                "visitElement                level: 2    element: ppp",
                "enterPrevLevel            toLevel: 1    fromElement: ppp    toElement: rrr",
                "visitElement                level: 1    element: rrr",
                "enterPrevLevel            toLevel: 0    fromElement: rrr    toElement: sss",
                "visitElement                level: 0    element: sss"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.postOrderTraversal(ttt);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "enterNextLevel            toLevel: 1    fromElement: ttt    toElement: ppp",
                "enterNextLevel            toLevel: 2    fromElement: ppp    toElement: rrr",
                "enterNextLevel            toLevel: 3    fromElement: rrr    toElement: sss",
                "visitElement                level: 3    element: sss",
                "enterPrevLevel            toLevel: 2    fromElement: sss    toElement: rrr",
                "visitElement                level: 2    element: rrr",
                "enterPrevLevel            toLevel: 1    fromElement: rrr    toElement: ppp",
                "visitElement                level: 1    element: ppp",
                "travelBetweenAdjacencies    level: 1    fromElement: ppp    toElement: qqq",
                "visitElement                level: 1    element: qqq",
                "enterPrevLevel            toLevel: 0    fromElement: qqq    toElement: ttt",
                "visitElement                level: 0    element: ttt"
        ));
        Assert.assertTrue(comparison.isEnd());

        // doesn't allow cycle, is meaningless and endless and should be avoided
        //CompletedTraverser.create(listener, false).postOrderTraversal(ppp);
    }

    @Test
    public void test_breadthFirstTraversal_tree() throws Exception {
        // doesn't allow cycle
        listener.getRunningTrace().clear();
        CompletedTraverser.create(listener, false).breadthFirstTraversal(treeRoot);
        RunningTrace.Comparison comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: aaa",
                "enterNextLevel            toLevel: 1    fromElement: aaa    toElement: bbb",
                "visitElement                level: 1    element: bbb",
                "travelBetweenAdjacencies    level: 1    fromElement: bbb    toElement: ddd",
                "visitElement                level: 1    element: ddd",
                "travelBetweenAdjacencies    level: 1    fromElement: ddd    toElement: ggg",
                "visitElement                level: 1    element: ggg",
                "enterNextLevel            toLevel: 2    fromElement: ggg    toElement: ccc",
                "visitElement                level: 2    element: ccc",
                "visitElement                level: 2    element: eee",
                "travelBetweenAdjacencies    level: 2    fromElement: eee    toElement: fff",
                "visitElement                level: 2    element: fff"
        ));
        Assert.assertTrue(comparison.isEnd());

        // allows cycle
        listener.getRunningTrace().clear();
        CompletedTraverser.create(listener, true).breadthFirstTraversal(treeRoot);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: aaa",
                "enterNextLevel            toLevel: 1    fromElement: aaa    toElement: bbb",
                "visitElement                level: 1    element: bbb",
                "travelBetweenAdjacencies    level: 1    fromElement: bbb    toElement: ddd",
                "visitElement                level: 1    element: ddd",
                "travelBetweenAdjacencies    level: 1    fromElement: ddd    toElement: ggg",
                "visitElement                level: 1    element: ggg",
                "enterNextLevel            toLevel: 2    fromElement: ggg    toElement: ccc",
                "visitElement                level: 2    element: ccc",
                "visitElement                level: 2    element: eee",
                "travelBetweenAdjacencies    level: 2    fromElement: eee    toElement: fff",
                "visitElement                level: 2    element: fff"
        ));
        Assert.assertTrue(comparison.isEnd());
    }

    @Test
    public void test_breadthFirstTraversal_graph() throws Exception {
        // allows cycle
        CompletedTraverser traverser = CompletedTraverser.create(listener, true);

        listener.getRunningTrace().clear();
        traverser.breadthFirstTraversal(ppp);
        RunningTrace.Comparison comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: ppp",
                "enterNextLevel            toLevel: 1    fromElement: ppp    toElement: rrr",
                "visitElement                level: 1    element: rrr",
                "travelBetweenAdjacencies    level: 1    fromElement: rrr    toElement: ttt",
                "visitElement                level: 1    element: ttt",
                "enterNextLevel            toLevel: 2    fromElement: ttt    toElement: sss",
                "visitElement                level: 2    element: sss",
                "visitElement                level: 2    element: qqq"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.breadthFirstTraversal(qqq);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: qqq",
                "enterNextLevel            toLevel: 1    fromElement: qqq    toElement: ttt",
                "visitElement                level: 1    element: ttt",
                "enterNextLevel            toLevel: 2    fromElement: ttt    toElement: ppp",
                "visitElement                level: 2    element: ppp",
                "travelBetweenAdjacencies    level: 2    fromElement: ppp    toElement: rrr",
                "visitElement                level: 2    element: rrr",
                "enterNextLevel            toLevel: 3    fromElement: rrr    toElement: sss",
                "visitElement                level: 3    element: sss"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.breadthFirstTraversal(rrr);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: rrr",
                "enterNextLevel            toLevel: 1    fromElement: rrr    toElement: ppp",
                "visitElement                level: 1    element: ppp",
                "travelBetweenAdjacencies    level: 1    fromElement: ppp    toElement: sss",
                "visitElement                level: 1    element: sss",
                "travelBetweenAdjacencies    level: 1    fromElement: sss    toElement: ttt",
                "visitElement                level: 1    element: ttt",
                "enterNextLevel            toLevel: 2    fromElement: ttt    toElement: qqq",
                "visitElement                level: 2    element: qqq"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.breadthFirstTraversal(sss);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: sss",
                "enterNextLevel            toLevel: 1    fromElement: sss    toElement: rrr",
                "visitElement                level: 1    element: rrr",
                "enterNextLevel            toLevel: 2    fromElement: rrr    toElement: ppp",
                "visitElement                level: 2    element: ppp",
                "travelBetweenAdjacencies    level: 2    fromElement: ppp    toElement: ttt",
                "visitElement                level: 2    element: ttt",
                "enterNextLevel            toLevel: 3    fromElement: ttt    toElement: qqq",
                "visitElement                level: 3    element: qqq"
        ));
        Assert.assertTrue(comparison.isEnd());

        listener.getRunningTrace().clear();
        traverser.breadthFirstTraversal(ttt);
        comparison = listener.getRunningTrace().newComparison();
        Assert.assertTrue(comparison.equalsBatchAndNext(
                "visitElement                level: 0    element: ttt",
                "enterNextLevel            toLevel: 1    fromElement: ttt    toElement: ppp",
                "visitElement                level: 1    element: ppp",
                "travelBetweenAdjacencies    level: 1    fromElement: ppp    toElement: qqq",
                "visitElement                level: 1    element: qqq",
                "travelBetweenAdjacencies    level: 1    fromElement: qqq    toElement: rrr",
                "visitElement                level: 1    element: rrr",
                "enterNextLevel            toLevel: 2    fromElement: rrr    toElement: sss",
                "visitElement                level: 2    element: sss"
        ));
        Assert.assertTrue(comparison.isEnd());

        // doesn't allow cycle, is meaningless and endless and should be avoided
        //CompletedTraverser.create(listener, false).breadthFirstTraversal(ppp);
    }
}
