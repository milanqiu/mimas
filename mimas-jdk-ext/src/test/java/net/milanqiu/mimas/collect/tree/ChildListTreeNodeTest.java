package net.milanqiu.mimas.collect.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

import static net.milanqiu.mimas.collect.tree.TreeNodeConsts.*;

/**
 * Creation Date: 2017-02-04
 * @author Milan Qiu
 */
public class ChildListTreeNodeTest extends TreeNodeTest {

    @Override
    protected Supplier<TreeNode<String>> getTreeNodeFactory() {
        return ChildListTreeNode::createStandalone;
    }

    @Override
    protected Supplier<TreeNode<String>> getIncompatibleTreeNodeFactory() {
        return LinkedTreeNode::createStandalone;
    }

    @Override
    protected void assertActualClass(TreeNode<String> node) {
        Assert.assertEquals(ChildListTreeNode.class, node.getClass());
    }

    @Before
    public void setUp() throws Exception {
        createTree(getTreeNodeFactory());
    }

    @Test
    @Override
    public void test_toString() throws Exception {
        super.test_toString();
    }

    @Test
    @Override
    public void test_adjacencies() throws Exception {
        super.test_adjacencies();
    }

    @Test
    @Override
    public void test_getData_setData() throws Exception {
        super.test_getData_setData();
    }

    @Test
    @Override
    public void test_getParent() throws Exception {
        super.test_getParent();
    }

    @Test
    @Override
    public void test_isAncestorOf() throws Exception {
        super.test_isAncestorOf();
    }

    @Test
    @Override
    public void test_isDescendantOf() throws Exception {
        super.test_isDescendantOf();
    }

    @Test
    @Override
    public void test_isRoot() throws Exception {
        super.test_isRoot();
    }

    @Test
    @Override
    public void test_getRoot() throws Exception {
        super.test_getRoot();
    }

    @Test
    @Override
    public void test_getPathFromRoot() throws Exception {
        super.test_getPathFromRoot();
    }

    @Test
    @Override
    public void test_getLevel() throws Exception {
        super.test_getLevel();
    }

    @Test
    @Override
    public void test_getSiblingsCount() throws Exception {
        super.test_getSiblingsCount();
    }

    @Test
    @Override
    public void test_getSiblingsList() throws Exception {
        super.test_getSiblingsList();
    }

    @Test
    @Override
    public void test_getSibling() throws Exception {
        super.test_getSibling();
    }

    @Test
    @Override
    public void test_getPrevSibling() throws Exception {
        super.test_getPrevSibling();
    }

    @Test
    @Override
    public void test_getNextSibling() throws Exception {
        super.test_getNextSibling();
    }

    @Test
    @Override
    public void test_getFirstSibling() throws Exception {
        super.test_getFirstSibling();
    }

    @Test
    @Override
    public void test_getLastSibling() throws Exception {
        super.test_getLastSibling();
    }

    @Test
    @Override
    public void test_getPositionAmongSiblings() throws Exception {
        super.test_getPositionAmongSiblings();
    }

    @Test
    @Override
    public void test_indexOfSibling() throws Exception {
        super.test_indexOfSibling();
    }

    @Test
    @Override
    public void test_hasChild() throws Exception {
        super.test_hasChild();
    }

    @Test
    @Override
    public void test_getChildCount() throws Exception {
        super.test_getChildCount();
    }

    @Test
    @Override
    public void test_getChildList() throws Exception {
        super.test_getChildList();
    }

    @Test
    @Override
    public void test_getChild() throws Exception {
        super.test_getChild();
    }

    @Test
    @Override
    public void test_getFirstChild() throws Exception {
        super.test_getFirstChild();
    }

    @Test
    @Override
    public void test_getLastChild() throws Exception {
        super.test_getLastChild();
    }

    @Test
    @Override
    public void test_indexOfChild() throws Exception {
        super.test_indexOfChild();
    }

    @Test
    @Override
    public void test_newStandalone() throws Exception {
        super.test_newStandalone();
    }

    @Test
    @Override
    public void test_newPrevSibling() throws Exception {
        super.test_newPrevSibling();
    }

    @Test
    @Override
    public void test_newNextSibling() throws Exception {
        super.test_newNextSibling();
    }

    @Test
    @Override
    public void test_newFirstSibling() throws Exception {
        super.test_newFirstSibling();
    }

    @Test
    @Override
    public void test_newLastSibling() throws Exception {
        super.test_newLastSibling();
    }

    @Test
    @Override
    public void test_newFirstChild() throws Exception {
        super.test_newFirstChild();
    }

    @Test
    @Override
    public void test_newLastChild() throws Exception {
        super.test_newLastChild();
    }

    @Test
    @Override
    public void test_newChild() throws Exception {
        super.test_newChild();
    }

    @Test
    @Override
    public void test_addPrevSibling() throws Exception {
        super.test_addPrevSibling();
    }

    @Test
    @Override
    public void test_addNextSibling() throws Exception {
        super.test_addNextSibling();
    }

    @Test
    @Override
    public void test_addFirstSibling() throws Exception {
        super.test_addFirstSibling();
    }

    @Test
    @Override
    public void test_addLastSibling() throws Exception {
        super.test_addLastSibling();
    }

    @Test
    @Override
    public void test_addFirstChild() throws Exception {
        super.test_addFirstChild();
    }

    @Test
    @Override
    public void test_addLastChild() throws Exception {
        super.test_addLastChild();
    }

    @Test
    @Override
    public void test_addChild() throws Exception {
        super.test_addChild();
    }

    @Test
    @Override
    public void test_removeSelfFromTree() throws Exception {
        super.test_removeSelfFromTree();
    }

    @Test
    @Override
    public void test_removePrevSibling() throws Exception {
        super.test_removePrevSibling();
    }

    @Test
    @Override
    public void test_removeNextSibling() throws Exception {
        super.test_removeNextSibling();
    }

    @Test
    @Override
    public void test_removeFirstSibling() throws Exception {
        super.test_removeFirstSibling();
    }

    @Test
    @Override
    public void test_removeLastSibling() throws Exception {
        super.test_removeLastSibling();
    }

    @Test
    @Override
    public void test_removeFirstChild() throws Exception {
        super.test_removeFirstChild();
    }

    @Test
    @Override
    public void test_removeLastChild() throws Exception {
        super.test_removeLastChild();
    }

    @Test
    @Override
    public void test_removeChild() throws Exception {
        super.test_removeChild();
    }

    @Test
    @Override
    public void test_clearSiblings() throws Exception {
        super.test_clearSiblings();
    }

    @Test
    @Override
    public void test_clearChildren() throws Exception {
        super.test_clearChildren();
    }

    @Test
    @Override
    public void test_treeToString() throws Exception {
        super.test_treeToString();
    }

    @Test
    @Override
    public void test_treeToMultiLineString() throws Exception {
        super.test_treeToMultiLineString();
    }
}
