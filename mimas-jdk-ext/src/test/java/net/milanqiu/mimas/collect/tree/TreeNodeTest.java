package net.milanqiu.mimas.collect.tree;

import net.milanqiu.mimas.collect.CollectionUtils;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;

import java.util.Arrays;
import java.util.function.Supplier;

import static net.milanqiu.mimas.collect.tree.TreeNodeConsts.*;
import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2016-12-20
 * @author Milan Qiu
 */
public abstract class TreeNodeTest {

    protected abstract Supplier<TreeNode<String>> getTreeNodeFactory();

    protected abstract Supplier<TreeNode<String>> getIncompatibleTreeNodeFactory();

    protected abstract void assertActualClass(TreeNode<String> node);

    public void test_toString() throws Exception {
        Assert.assertEquals("aaa", aaa.toString());
        Assert.assertEquals("ccc", ccc.toString());
        Assert.assertEquals("", getTreeNodeFactory().get().toString());
    }

    public void test_adjacencies() throws Exception {
        Assert.assertTrue(CollectionUtils.equals(aaa.adjacencies(), Arrays.asList(bbb, ddd, ggg)));
        Assert.assertTrue(CollectionUtils.equals(bbb.adjacencies(), Arrays.asList(ccc)));
        Assert.assertTrue(CollectionUtils.equals(ccc.adjacencies(), Arrays.asList()));
        Assert.assertTrue(CollectionUtils.equals(ddd.adjacencies(), Arrays.asList(eee, fff)));
        Assert.assertTrue(CollectionUtils.equals(eee.adjacencies(), Arrays.asList()));
        Assert.assertTrue(CollectionUtils.equals(fff.adjacencies(), Arrays.asList()));
        Assert.assertTrue(CollectionUtils.equals(ggg.adjacencies(), Arrays.asList()));
    }

    public void test_getData_setData() throws Exception {
        aaa.setData(STR_0);
        Assert.assertEquals(STR_0, aaa.getData());

        AssertExt.assertDefault(getTreeNodeFactory().get().getData());
    }

    public void test_getParent() throws Exception {
        Assert.assertEquals(null, aaa.getParent());
        Assert.assertEquals(aaa, bbb.getParent());
        Assert.assertEquals(bbb, ccc.getParent());
        Assert.assertEquals(aaa, ddd.getParent());
        Assert.assertEquals(ddd, eee.getParent());
        Assert.assertEquals(ddd, fff.getParent());
        Assert.assertEquals(aaa, ggg.getParent());
    }

    public void test_isAncestorOf() throws Exception {
        Assert.assertTrue(aaa.isAncestorOf(bbb));
        Assert.assertTrue(aaa.isAncestorOf(eee));
        Assert.assertFalse(bbb.isAncestorOf(eee));
        Assert.assertFalse(bbb.isAncestorOf(aaa));
        Assert.assertFalse(aaa.isAncestorOf(aaa));
    }

    public void test_isDescendantOf() throws Exception {
        Assert.assertTrue(bbb.isDescendantOf(aaa));
        Assert.assertTrue(eee.isDescendantOf(aaa));
        Assert.assertFalse(eee.isDescendantOf(bbb));
        Assert.assertFalse(aaa.isDescendantOf(bbb));
        Assert.assertFalse(aaa.isDescendantOf(aaa));
    }

    public void test_isRoot() throws Exception {
        Assert.assertTrue(aaa.isRoot());
        Assert.assertFalse(bbb.isRoot());
        Assert.assertFalse(eee.isRoot());
    }

    public void test_getRoot() throws Exception {
        Assert.assertEquals(aaa, aaa.getRoot());
        Assert.assertEquals(aaa, bbb.getRoot());
        Assert.assertEquals(aaa, eee.getRoot());
    }

    public void test_getPathFromRoot() throws Exception {
        Assert.assertEquals(aaa.getPathFromRoot(), Arrays.asList(aaa));
        Assert.assertEquals(bbb.getPathFromRoot(), Arrays.asList(aaa, bbb));
        Assert.assertEquals(eee.getPathFromRoot(), Arrays.asList(aaa, ddd, eee));
    }

    public void test_getLevel() throws Exception {
        Assert.assertEquals(0, aaa.getLevel());
        Assert.assertEquals(1, bbb.getLevel());
        Assert.assertEquals(2, eee.getLevel());
    }

    public void test_getSiblingsCount() throws Exception {
        Assert.assertEquals(1, aaa.getSiblingsCount());
        Assert.assertEquals(3, bbb.getSiblingsCount());
        Assert.assertEquals(1, ccc.getSiblingsCount());
        Assert.assertEquals(3, ddd.getSiblingsCount());
        Assert.assertEquals(2, eee.getSiblingsCount());
        Assert.assertEquals(2, fff.getSiblingsCount());
        Assert.assertEquals(3, ggg.getSiblingsCount());
    }

    public void test_getSiblingsList() throws Exception {
        Assert.assertEquals(aaa.getSiblingsList(), Arrays.asList(aaa));
        Assert.assertEquals(bbb.getSiblingsList(), Arrays.asList(bbb, ddd, ggg));
        Assert.assertEquals(ccc.getSiblingsList(), Arrays.asList(ccc));
        Assert.assertEquals(ddd.getSiblingsList(), Arrays.asList(bbb, ddd, ggg));
        Assert.assertEquals(eee.getSiblingsList(), Arrays.asList(eee, fff));
        Assert.assertEquals(fff.getSiblingsList(), Arrays.asList(eee, fff));
        Assert.assertEquals(ggg.getSiblingsList(), Arrays.asList(bbb, ddd, ggg));
    }

    public void test_getSibling() throws Exception {
        Assert.assertEquals(aaa, aaa.getSibling(0));
        Assert.assertEquals(bbb, bbb.getSibling(0));
        Assert.assertEquals(ddd, bbb.getSibling(1));
        Assert.assertEquals(ggg, bbb.getSibling(2));

        AssertExt.assertExceptionThrown(() -> bbb.getSibling(-1), IndexOutOfBoundsException.class, "Index: -1, Size: 3");
        AssertExt.assertExceptionThrown(() -> bbb.getSibling(3), IndexOutOfBoundsException.class, "Index: 3, Size: 3");
    }

    public void test_getPrevSibling() throws Exception {
        Assert.assertEquals(null, aaa.getPrevSibling());
        Assert.assertEquals(null, bbb.getPrevSibling());
        Assert.assertEquals(null, ccc.getPrevSibling());
        Assert.assertEquals(bbb,  ddd.getPrevSibling());
        Assert.assertEquals(null, eee.getPrevSibling());
        Assert.assertEquals(eee,  fff.getPrevSibling());
        Assert.assertEquals(ddd,  ggg.getPrevSibling());
    }

    public void test_getNextSibling() throws Exception {
        Assert.assertEquals(null, aaa.getNextSibling());
        Assert.assertEquals(ddd,  bbb.getNextSibling());
        Assert.assertEquals(null, ccc.getNextSibling());
        Assert.assertEquals(ggg,  ddd.getNextSibling());
        Assert.assertEquals(fff,  eee.getNextSibling());
        Assert.assertEquals(null, fff.getNextSibling());
        Assert.assertEquals(null, ggg.getNextSibling());
    }

    public void test_getFirstSibling() throws Exception {
        Assert.assertEquals(aaa, aaa.getFirstSibling());
        Assert.assertEquals(bbb, bbb.getFirstSibling());
        Assert.assertEquals(ccc, ccc.getFirstSibling());
        Assert.assertEquals(bbb, ddd.getFirstSibling());
        Assert.assertEquals(eee, eee.getFirstSibling());
        Assert.assertEquals(eee, fff.getFirstSibling());
        Assert.assertEquals(bbb, ggg.getFirstSibling());
    }

    public void test_getLastSibling() throws Exception {
        Assert.assertEquals(aaa, aaa.getLastSibling());
        Assert.assertEquals(ggg, bbb.getLastSibling());
        Assert.assertEquals(ccc, ccc.getLastSibling());
        Assert.assertEquals(ggg, ddd.getLastSibling());
        Assert.assertEquals(fff, eee.getLastSibling());
        Assert.assertEquals(fff, fff.getLastSibling());
        Assert.assertEquals(ggg, ggg.getLastSibling());
    }

    public void test_getPositionAmongSiblings() throws Exception {
        Assert.assertEquals(0, aaa.getPositionAmongSiblings());
        Assert.assertEquals(0, bbb.getPositionAmongSiblings());
        Assert.assertEquals(0, ccc.getPositionAmongSiblings());
        Assert.assertEquals(1, ddd.getPositionAmongSiblings());
        Assert.assertEquals(0, eee.getPositionAmongSiblings());
        Assert.assertEquals(1, fff.getPositionAmongSiblings());
        Assert.assertEquals(2, ggg.getPositionAmongSiblings());
    }

    public void test_indexOfSibling() throws Exception {
        Assert.assertEquals(0,  aaa.indexOfSibling(aaa));
        Assert.assertEquals(-1, aaa.indexOfSibling(bbb));
        Assert.assertEquals(0,  bbb.indexOfSibling(bbb));
        Assert.assertEquals(1,  bbb.indexOfSibling(ddd));
        Assert.assertEquals(2,  bbb.indexOfSibling(ggg));
        Assert.assertEquals(-1, bbb.indexOfSibling(aaa));
    }

    public void test_hasChild() throws Exception {
        Assert.assertTrue(aaa.hasChild());
        Assert.assertTrue(bbb.hasChild());
        Assert.assertFalse(ccc.hasChild());
        Assert.assertTrue(ddd.hasChild());
        Assert.assertFalse(eee.hasChild());
        Assert.assertFalse(fff.hasChild());
        Assert.assertFalse(ggg.hasChild());

    }

    public void test_getChildCount() throws Exception {
        Assert.assertEquals(3, aaa.getChildCount());
        Assert.assertEquals(1, bbb.getChildCount());
        Assert.assertEquals(0, ccc.getChildCount());
        Assert.assertEquals(2, ddd.getChildCount());
        Assert.assertEquals(0, eee.getChildCount());
        Assert.assertEquals(0, fff.getChildCount());
        Assert.assertEquals(0, ggg.getChildCount());
    }

    public void test_getChildList() throws Exception {
        Assert.assertEquals(aaa.getChildList(), Arrays.asList(bbb, ddd, ggg));
        Assert.assertEquals(bbb.getChildList(), Arrays.asList(ccc));
        Assert.assertEquals(ccc.getChildList(), Arrays.asList());
        Assert.assertEquals(ddd.getChildList(), Arrays.asList(eee, fff));
        Assert.assertEquals(eee.getChildList(), Arrays.asList());
        Assert.assertEquals(fff.getChildList(), Arrays.asList());
        Assert.assertEquals(ggg.getChildList(), Arrays.asList());
    }

    public void test_getChild() throws Exception {
        Assert.assertEquals(bbb, aaa.getChild(0));
        Assert.assertEquals(ddd, aaa.getChild(1));
        Assert.assertEquals(ggg, aaa.getChild(2));
        Assert.assertEquals(ccc, bbb.getChild(0));

        AssertExt.assertExceptionThrown(() -> aaa.getChild(-1), IndexOutOfBoundsException.class, "Index: -1, Size: 3");
        AssertExt.assertExceptionThrown(() -> aaa.getChild(3), IndexOutOfBoundsException.class, "Index: 3, Size: 3");
        AssertExt.assertExceptionThrown(() -> ggg.getChild(0), IndexOutOfBoundsException.class, "Index: 0, Size: 0");
    }

    public void test_getFirstChild() throws Exception {
        Assert.assertEquals(bbb, aaa.getFirstChild());
        Assert.assertEquals(ccc, bbb.getFirstChild());
        Assert.assertEquals(null, ccc.getFirstChild());
    }

    public void test_getLastChild() throws Exception {
        Assert.assertEquals(ggg, aaa.getLastChild());
        Assert.assertEquals(ccc, bbb.getLastChild());
        Assert.assertEquals(null, ccc.getLastChild());
    }

    public void test_indexOfChild() throws Exception {
        Assert.assertEquals(0, aaa.indexOfChild(bbb));
        Assert.assertEquals(1, aaa.indexOfChild(ddd));
        Assert.assertEquals(2, aaa.indexOfChild(ggg));
        Assert.assertEquals(-1, aaa.indexOfChild(ccc));
        Assert.assertEquals(-1, ccc.indexOfChild(aaa));
    }

    public void test_newStandalone() throws Exception {
        TreeNode<String> newNode = bbb.newStandalone();
        assertActualClass(newNode);
        Assert.assertEquals(null, newNode.getParent());
        Assert.assertEquals(null, newNode.getPrevSibling());
        Assert.assertEquals(null, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
    }

    public void test_newPrevSibling() throws Exception {
        TreeNode<String> newNode = bbb.newPrevSibling();
        assertActualClass(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(null, newNode.getPrevSibling());
        Assert.assertEquals(bbb, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, bbb.getPrevSibling());
        Assert.assertEquals(newNode, aaa.getFirstChild());

        newNode = fff.newPrevSibling();
        assertActualClass(newNode);
        Assert.assertEquals(ddd, newNode.getParent());
        Assert.assertEquals(eee, newNode.getPrevSibling());
        Assert.assertEquals(fff, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, eee.getNextSibling());
        Assert.assertEquals(newNode, fff.getPrevSibling());

        AssertExt.assertExceptionThrown(aaa::newPrevSibling, AddSiblingToRootException.class);
    }

    public void test_newNextSibling() throws Exception {
        TreeNode<String> newNode = ggg.newNextSibling();
        assertActualClass(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(ggg, newNode.getPrevSibling());
        Assert.assertEquals(null, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, ggg.getNextSibling());
        Assert.assertEquals(newNode, aaa.getLastChild());

        newNode = eee.newNextSibling();
        assertActualClass(newNode);
        Assert.assertEquals(ddd, newNode.getParent());
        Assert.assertEquals(eee, newNode.getPrevSibling());
        Assert.assertEquals(fff, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, eee.getNextSibling());
        Assert.assertEquals(newNode, fff.getPrevSibling());

        AssertExt.assertExceptionThrown(aaa::newNextSibling, AddSiblingToRootException.class);
    }

    public void test_newFirstSibling() throws Exception {
        TreeNode<String> newNode = ggg.newFirstSibling();
        assertActualClass(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(null, newNode.getPrevSibling());
        Assert.assertEquals(bbb, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, bbb.getPrevSibling());
        Assert.assertEquals(newNode, aaa.getFirstChild());

        AssertExt.assertExceptionThrown(aaa::newFirstSibling, AddSiblingToRootException.class);
    }

    public void test_newLastSibling() throws Exception {
        TreeNode<String> newNode = bbb.newLastSibling();
        assertActualClass(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(ggg, newNode.getPrevSibling());
        Assert.assertEquals(null, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, ggg.getNextSibling());
        Assert.assertEquals(newNode, aaa.getLastChild());

        AssertExt.assertExceptionThrown(aaa::newLastSibling, AddSiblingToRootException.class);
    }

    public void test_newFirstChild() throws Exception {
        TreeNode<String> newNode = aaa.newFirstChild();
        assertActualClass(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(null, newNode.getPrevSibling());
        Assert.assertEquals(bbb, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, bbb.getPrevSibling());
        Assert.assertEquals(newNode, aaa.getFirstChild());
    }

    public void test_newLastChild() throws Exception {
        TreeNode<String> newNode = aaa.newLastChild();
        assertActualClass(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(ggg, newNode.getPrevSibling());
        Assert.assertEquals(null, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, ggg.getNextSibling());
        Assert.assertEquals(newNode, aaa.getLastChild());
    }

    public void test_newChild() throws Exception {
        TreeNode<String> newNode = aaa.newChild(3);
        assertActualClass(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(ggg, newNode.getPrevSibling());
        Assert.assertEquals(null, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, ggg.getNextSibling());
        Assert.assertEquals(newNode, aaa.getLastChild());

        newNode = aaa.newChild(0);
        assertActualClass(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(null, newNode.getPrevSibling());
        Assert.assertEquals(bbb, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, bbb.getPrevSibling());
        Assert.assertEquals(newNode, aaa.getFirstChild());

        newNode = ddd.newChild(1);
        assertActualClass(newNode);
        Assert.assertEquals(ddd, newNode.getParent());
        Assert.assertEquals(eee, newNode.getPrevSibling());
        Assert.assertEquals(fff, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, eee.getNextSibling());
        Assert.assertEquals(newNode, fff.getPrevSibling());

        AssertExt.assertExceptionThrown(() -> ccc.newChild(1), IndexOutOfBoundsException.class, "Index: 0, Size: 0");
    }

    public void test_addPrevSibling() throws Exception {
        TreeNode<String> newNode = getTreeNodeFactory().get();
        bbb.addPrevSibling(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(null, newNode.getPrevSibling());
        Assert.assertEquals(bbb, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, bbb.getPrevSibling());
        Assert.assertEquals(newNode, aaa.getFirstChild());

        newNode = getTreeNodeFactory().get();
        fff.addPrevSibling(newNode);
        Assert.assertEquals(ddd, newNode.getParent());
        Assert.assertEquals(eee, newNode.getPrevSibling());
        Assert.assertEquals(fff, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, eee.getNextSibling());
        Assert.assertEquals(newNode, fff.getPrevSibling());

        AssertExt.assertExceptionThrown(() -> aaa.addPrevSibling(getTreeNodeFactory().get()), AddSiblingToRootException.class);
        AssertExt.assertExceptionThrown(() -> bbb.addPrevSibling(getIncompatibleTreeNodeFactory().get()), AddIncompatibleNodeException.class);
    }

    public void test_addNextSibling() throws Exception {
        TreeNode<String> newNode = getTreeNodeFactory().get();
        ggg.addNextSibling(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(ggg, newNode.getPrevSibling());
        Assert.assertEquals(null, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, ggg.getNextSibling());
        Assert.assertEquals(newNode, aaa.getLastChild());

        newNode = getTreeNodeFactory().get();
        eee.addNextSibling(newNode);
        Assert.assertEquals(ddd, newNode.getParent());
        Assert.assertEquals(eee, newNode.getPrevSibling());
        Assert.assertEquals(fff, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, eee.getNextSibling());
        Assert.assertEquals(newNode, fff.getPrevSibling());

        AssertExt.assertExceptionThrown(() -> aaa.addNextSibling(getTreeNodeFactory().get()), AddSiblingToRootException.class);
        AssertExt.assertExceptionThrown(() -> bbb.addNextSibling(getIncompatibleTreeNodeFactory().get()), AddIncompatibleNodeException.class);
    }

    public void test_addFirstSibling() throws Exception {
        TreeNode<String> newNode = getTreeNodeFactory().get();
        ggg.addFirstSibling(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(null, newNode.getPrevSibling());
        Assert.assertEquals(bbb, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, bbb.getPrevSibling());
        Assert.assertEquals(newNode, aaa.getFirstChild());

        AssertExt.assertExceptionThrown(() -> aaa.addFirstSibling(getTreeNodeFactory().get()), AddSiblingToRootException.class);
        AssertExt.assertExceptionThrown(() -> bbb.addFirstSibling(getIncompatibleTreeNodeFactory().get()), AddIncompatibleNodeException.class);
    }

    public void test_addLastSibling() throws Exception {
        TreeNode<String> newNode = getTreeNodeFactory().get();
        ggg.addLastSibling(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(ggg, newNode.getPrevSibling());
        Assert.assertEquals(null, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, ggg.getNextSibling());
        Assert.assertEquals(newNode, aaa.getLastChild());

        AssertExt.assertExceptionThrown(() -> aaa.addLastSibling(getTreeNodeFactory().get()), AddSiblingToRootException.class);
        AssertExt.assertExceptionThrown(() -> bbb.addLastSibling(getIncompatibleTreeNodeFactory().get()), AddIncompatibleNodeException.class);
    }

    public void test_addFirstChild() throws Exception {
        TreeNode<String> newNode = getTreeNodeFactory().get();
        aaa.addFirstChild(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(null, newNode.getPrevSibling());
        Assert.assertEquals(bbb, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, bbb.getPrevSibling());
        Assert.assertEquals(newNode, aaa.getFirstChild());

        AssertExt.assertExceptionThrown(() -> bbb.addFirstChild(getIncompatibleTreeNodeFactory().get()), AddIncompatibleNodeException.class);
    }

    public void test_addLastChild() throws Exception {
        TreeNode<String> newNode = getTreeNodeFactory().get();
        aaa.addLastChild(newNode);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(ggg, newNode.getPrevSibling());
        Assert.assertEquals(null, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, ggg.getNextSibling());
        Assert.assertEquals(newNode, aaa.getLastChild());

        AssertExt.assertExceptionThrown(() -> bbb.addLastChild(getIncompatibleTreeNodeFactory().get()), AddIncompatibleNodeException.class);
    }

    public void test_addChild() throws Exception {
        TreeNode<String> newNode = getTreeNodeFactory().get();
        aaa.addChild(newNode, 3);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(ggg, newNode.getPrevSibling());
        Assert.assertEquals(null, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, ggg.getNextSibling());
        Assert.assertEquals(newNode, aaa.getLastChild());

        newNode = getTreeNodeFactory().get();
        aaa.addChild(newNode, 0);
        Assert.assertEquals(aaa, newNode.getParent());
        Assert.assertEquals(null, newNode.getPrevSibling());
        Assert.assertEquals(bbb, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, bbb.getPrevSibling());
        Assert.assertEquals(newNode, aaa.getFirstChild());

        newNode = getTreeNodeFactory().get();
        ddd.addChild(newNode, 1);
        Assert.assertEquals(ddd, newNode.getParent());
        Assert.assertEquals(eee, newNode.getPrevSibling());
        Assert.assertEquals(fff, newNode.getNextSibling());
        Assert.assertEquals(null, newNode.getFirstChild());
        Assert.assertEquals(null, newNode.getLastChild());
        Assert.assertEquals(newNode, eee.getNextSibling());
        Assert.assertEquals(newNode, fff.getPrevSibling());

        AssertExt.assertExceptionThrown(() -> ccc.addChild(getTreeNodeFactory().get(), 1), IndexOutOfBoundsException.class, "Index: 0, Size: 0");
        AssertExt.assertExceptionThrown(() -> aaa.addChild(getIncompatibleTreeNodeFactory().get(), 0), AddIncompatibleNodeException.class);
    }

    public void test_removeSelfFromTree() throws Exception {
        ddd.removeSelfFromTree();
        Assert.assertEquals(bbb, aaa.getFirstChild());
        Assert.assertEquals(ggg, aaa.getLastChild());
        Assert.assertEquals(null, bbb.getPrevSibling());
        Assert.assertEquals(ggg, bbb.getNextSibling());
        Assert.assertEquals(bbb, ggg.getPrevSibling());
        Assert.assertEquals(null, ggg.getNextSibling());
        Assert.assertEquals(null, ddd.getParent());
        Assert.assertEquals(null, ddd.getPrevSibling());
        Assert.assertEquals(null, ddd.getNextSibling());

        AssertExt.assertExceptionThrown(aaa::removeSelfFromTree, RemoveRootException.class);
    }

    public void test_removePrevSibling() throws Exception {
        Assert.assertEquals(ddd, ggg.removePrevSibling());
        Assert.assertEquals(bbb, aaa.getFirstChild());
        Assert.assertEquals(ggg, aaa.getLastChild());
        Assert.assertEquals(null, bbb.getPrevSibling());
        Assert.assertEquals(ggg, bbb.getNextSibling());
        Assert.assertEquals(bbb, ggg.getPrevSibling());
        Assert.assertEquals(null, ggg.getNextSibling());
        Assert.assertEquals(null, ddd.getParent());
        Assert.assertEquals(null, ddd.getPrevSibling());
        Assert.assertEquals(null, ddd.getNextSibling());

        Assert.assertEquals(null, ccc.removePrevSibling());
        Assert.assertEquals(ccc, bbb.getFirstChild());
        Assert.assertEquals(ccc, bbb.getLastChild());
        Assert.assertEquals(null, ccc.getPrevSibling());
        Assert.assertEquals(null, ccc.getNextSibling());

        Assert.assertEquals(null, aaa.removePrevSibling());
    }

    public void test_removeNextSibling() throws Exception {
        Assert.assertEquals(ddd, bbb.removeNextSibling());
        Assert.assertEquals(bbb, aaa.getFirstChild());
        Assert.assertEquals(ggg, aaa.getLastChild());
        Assert.assertEquals(null, bbb.getPrevSibling());
        Assert.assertEquals(ggg, bbb.getNextSibling());
        Assert.assertEquals(bbb, ggg.getPrevSibling());
        Assert.assertEquals(null, ggg.getNextSibling());
        Assert.assertEquals(null, ddd.getParent());
        Assert.assertEquals(null, ddd.getPrevSibling());
        Assert.assertEquals(null, ddd.getNextSibling());

        Assert.assertEquals(fff, eee.removeNextSibling());
        Assert.assertEquals(eee, ddd.getFirstChild());
        Assert.assertEquals(eee, ddd.getLastChild());
        Assert.assertEquals(null, eee.getPrevSibling());
        Assert.assertEquals(null, eee.getNextSibling());
        Assert.assertEquals(null, fff.getParent());
        Assert.assertEquals(null, fff.getPrevSibling());
        Assert.assertEquals(null, fff.getNextSibling());

        Assert.assertEquals(null, aaa.removeNextSibling());
    }

    public void test_removeFirstSibling() throws Exception {
        Assert.assertEquals(bbb, ggg.removeFirstSibling());
        Assert.assertEquals(ddd, aaa.getFirstChild());
        Assert.assertEquals(ggg, aaa.getLastChild());
        Assert.assertEquals(null, ddd.getPrevSibling());
        Assert.assertEquals(ggg, ddd.getNextSibling());
        Assert.assertEquals(ddd, ggg.getPrevSibling());
        Assert.assertEquals(null, ggg.getNextSibling());
        Assert.assertEquals(null, bbb.getParent());
        Assert.assertEquals(null, bbb.getPrevSibling());
        Assert.assertEquals(null, bbb.getNextSibling());

        Assert.assertEquals(ccc, ccc.removeFirstSibling());
        Assert.assertEquals(null, bbb.getFirstChild());
        Assert.assertEquals(null, bbb.getLastChild());
        Assert.assertEquals(null, ccc.getParent());
        Assert.assertEquals(null, ccc.getPrevSibling());
        Assert.assertEquals(null, ccc.getNextSibling());

        AssertExt.assertExceptionThrown(aaa::removeFirstSibling, RemoveRootException.class);
    }

    public void test_removeLastSibling() throws Exception {
        Assert.assertEquals(ggg, bbb.removeLastSibling());
        Assert.assertEquals(bbb, aaa.getFirstChild());
        Assert.assertEquals(ddd, aaa.getLastChild());
        Assert.assertEquals(null, bbb.getPrevSibling());
        Assert.assertEquals(ddd, bbb.getNextSibling());
        Assert.assertEquals(bbb, ddd.getPrevSibling());
        Assert.assertEquals(null, ddd.getNextSibling());
        Assert.assertEquals(null, ggg.getParent());
        Assert.assertEquals(null, ggg.getPrevSibling());
        Assert.assertEquals(null, ggg.getNextSibling());

        Assert.assertEquals(fff, fff.removeLastSibling());
        Assert.assertEquals(eee, ddd.getFirstChild());
        Assert.assertEquals(eee, ddd.getLastChild());
        Assert.assertEquals(null, fff.getParent());
        Assert.assertEquals(null, fff.getPrevSibling());
        Assert.assertEquals(null, fff.getNextSibling());

        AssertExt.assertExceptionThrown(aaa::removeLastSibling, RemoveRootException.class);
    }

    public void test_removeFirstChild() throws Exception {
        Assert.assertEquals(bbb, aaa.removeFirstChild());
        Assert.assertEquals(ddd, aaa.getFirstChild());
        Assert.assertEquals(ggg, aaa.getLastChild());
        Assert.assertEquals(null, ddd.getPrevSibling());
        Assert.assertEquals(ggg, ddd.getNextSibling());
        Assert.assertEquals(ddd, ggg.getPrevSibling());
        Assert.assertEquals(null, ggg.getNextSibling());
        Assert.assertEquals(null, bbb.getParent());
        Assert.assertEquals(null, bbb.getPrevSibling());
        Assert.assertEquals(null, bbb.getNextSibling());

        Assert.assertEquals(ccc, bbb.removeFirstChild());
        Assert.assertEquals(null, bbb.getFirstChild());
        Assert.assertEquals(null, bbb.getLastChild());
        Assert.assertEquals(null, ccc.getParent());
        Assert.assertEquals(null, ccc.getPrevSibling());
        Assert.assertEquals(null, ccc.getNextSibling());

        Assert.assertEquals(null, ccc.removeFirstChild());
    }

    public void test_removeLastChild() throws Exception {
        Assert.assertEquals(ggg, aaa.removeLastChild());
        Assert.assertEquals(bbb, aaa.getFirstChild());
        Assert.assertEquals(ddd, aaa.getLastChild());
        Assert.assertEquals(null, bbb.getPrevSibling());
        Assert.assertEquals(ddd, bbb.getNextSibling());
        Assert.assertEquals(bbb, ddd.getPrevSibling());
        Assert.assertEquals(null, ddd.getNextSibling());
        Assert.assertEquals(null, ggg.getParent());
        Assert.assertEquals(null, ggg.getPrevSibling());
        Assert.assertEquals(null, ggg.getNextSibling());

        Assert.assertEquals(fff, ddd.removeLastChild());
        Assert.assertEquals(eee, ddd.getFirstChild());
        Assert.assertEquals(eee, ddd.getLastChild());
        Assert.assertEquals(null, eee.getPrevSibling());
        Assert.assertEquals(null, eee.getNextSibling());
        Assert.assertEquals(null, fff.getParent());
        Assert.assertEquals(null, fff.getPrevSibling());
        Assert.assertEquals(null, fff.getNextSibling());

        Assert.assertEquals(null, fff.removeLastChild());
    }

    public void test_removeChild() throws Exception {
        Assert.assertEquals(ddd, aaa.removeChild(1));
        Assert.assertEquals(bbb, aaa.getFirstChild());
        Assert.assertEquals(ggg, aaa.getLastChild());
        Assert.assertEquals(null, bbb.getPrevSibling());
        Assert.assertEquals(ggg, bbb.getNextSibling());
        Assert.assertEquals(bbb, ggg.getPrevSibling());
        Assert.assertEquals(null, ggg.getNextSibling());
        Assert.assertEquals(null, ddd.getParent());
        Assert.assertEquals(null, ddd.getPrevSibling());
        Assert.assertEquals(null, ddd.getNextSibling());

        Assert.assertEquals(ccc, bbb.removeChild(0));
        Assert.assertEquals(null, bbb.getFirstChild());
        Assert.assertEquals(null, bbb.getLastChild());
        Assert.assertEquals(null, ccc.getParent());
        Assert.assertEquals(null, ccc.getPrevSibling());
        Assert.assertEquals(null, ccc.getNextSibling());

        AssertExt.assertExceptionThrown(() -> ddd.removeChild(2), IndexOutOfBoundsException.class, "Index: 2, Size: 2");
    }

    public void test_clearSiblings() throws Exception {
        ddd.clearSiblings();
        Assert.assertEquals(null, aaa.getPrevSibling());
        Assert.assertEquals(null, aaa.getNextSibling());
        Assert.assertEquals(ddd, aaa.getFirstChild());
        Assert.assertEquals(ddd, aaa.getLastChild());
        Assert.assertEquals(null, ddd.getPrevSibling());
        Assert.assertEquals(null, ddd.getNextSibling());
        Assert.assertEquals(eee, ddd.getFirstChild());
        Assert.assertEquals(fff, ddd.getLastChild());

        aaa.clearSiblings();
        Assert.assertEquals(null, aaa.getPrevSibling());
        Assert.assertEquals(null, aaa.getNextSibling());
        Assert.assertEquals(ddd, aaa.getFirstChild());
        Assert.assertEquals(ddd, aaa.getLastChild());
    }

    public void test_clearChildren() throws Exception {
        ddd.clearChildren();
        Assert.assertEquals(null, aaa.getPrevSibling());
        Assert.assertEquals(null, aaa.getNextSibling());
        Assert.assertEquals(bbb, aaa.getFirstChild());
        Assert.assertEquals(ggg, aaa.getLastChild());
        Assert.assertEquals(bbb, ddd.getPrevSibling());
        Assert.assertEquals(ggg, ddd.getNextSibling());
        Assert.assertEquals(null, ddd.getFirstChild());
        Assert.assertEquals(null, ddd.getLastChild());

        aaa.clearChildren();
        Assert.assertEquals(null, aaa.getPrevSibling());
        Assert.assertEquals(null, aaa.getNextSibling());
        Assert.assertEquals(null, aaa.getFirstChild());
        Assert.assertEquals(null, aaa.getLastChild());
    }

    public void test_treeToString() throws Exception {
        Assert.assertEquals("aaa(bbb(ccc), ddd(eee, fff), ggg)", aaa.treeToString());
        Assert.assertEquals("bbb(ccc)",                          bbb.treeToString());
        Assert.assertEquals("ccc",                               ccc.treeToString());
        Assert.assertEquals("ddd(eee, fff)",                     ddd.treeToString());
        Assert.assertEquals("eee",                               eee.treeToString());
        Assert.assertEquals("fff",                               fff.treeToString());
        Assert.assertEquals("ggg",                               ggg.treeToString());
    }

    public void test_treeToMultiLineString() throws Exception {
        Assert.assertEquals("aaa" + System.lineSeparator() +
                            "\tbbb" + System.lineSeparator() +
                            "\t\tccc" + System.lineSeparator() +
                            "\tddd" + System.lineSeparator() +
                            "\t\teee" + System.lineSeparator() +
                            "\t\tfff" + System.lineSeparator() +
                            "\tggg"
                , aaa.treeToMultiLineString());
        Assert.assertEquals("bbb" + System.lineSeparator() +
                            "\tccc"
                , bbb.treeToMultiLineString());
        Assert.assertEquals("ccc"
                , ccc.treeToMultiLineString());
        Assert.assertEquals("ddd" + System.lineSeparator() +
                            "\teee" + System.lineSeparator() +
                            "\tfff"
                , ddd.treeToMultiLineString());
        Assert.assertEquals("eee"
                , eee.treeToMultiLineString());
        Assert.assertEquals("fff"
                , fff.treeToMultiLineString());
        Assert.assertEquals("ggg"
                , ggg.treeToMultiLineString());
    }
}
