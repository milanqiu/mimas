package net.milanqiu.mimas.guava;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.TreeTraverser;
import net.milanqiu.mimas.collect.tree.LinkedTreeNode;
import net.milanqiu.mimas.collect.tree.TreeNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

/**
 * Creation Date: 2017-04-14
 * @author Milan Qiu
 */
public class TreeTraverserExtTest {

    private static TreeNode<String> aaa;
    private static TreeNode<String> bbb;
    private static TreeNode<String> ccc;
    private static TreeNode<String> ddd;
    private static TreeNode<String> eee;
    private static TreeNode<String> fff;
    private static TreeNode<String> ggg;

    private static void createTree(Supplier<TreeNode<String>> treeNodeFactory) {
        aaa = treeNodeFactory.get();
        bbb = aaa.newFirstChild();
        ddd = bbb.newNextSibling();
        ggg = ddd.newNextSibling();
        ccc = bbb.newFirstChild();
        eee = ddd.newFirstChild();
        fff = eee.newNextSibling();

        aaa.setData("aaa");
        bbb.setData("bbb");
        ccc.setData("ccc");
        ddd.setData("ddd");
        eee.setData("eee");
        fff.setData("fff");
        ggg.setData("ggg");

        // then the tree will be
        //                aaa
        //             /   |   \
        //          bbb   ddd   ggg
        //         /     /   \
        //      ccc   eee     fff
    }

    @Before
    public void setUp() throws Exception {
        createTree(LinkedTreeNode::createStandalone);
    }

    @Test
    public void test_overTreeNode() throws Exception {
        TreeTraverser<TreeNode<String>> tt = TreeTraverserExt.overTreeNode();

        // children()
        Assert.assertEquals(ImmutableList.of(bbb, ddd, ggg), tt.children(aaa));
        Assert.assertEquals(ImmutableList.of(ccc),           tt.children(bbb));
        Assert.assertEquals(ImmutableList.of(),              tt.children(ccc));
        Assert.assertEquals(ImmutableList.of(eee, fff),      tt.children(ddd));
        Assert.assertEquals(ImmutableList.of(),              tt.children(eee));
        Assert.assertEquals(ImmutableList.of(),              tt.children(fff));
        Assert.assertEquals(ImmutableList.of(),              tt.children(ggg));

        // preOrderTraversal()
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of(aaa, bbb, ccc, ddd, eee, fff, ggg),
                tt.preOrderTraversal(aaa)));

        // postOrderTraversal()
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of(ccc, bbb, eee, fff, ddd, ggg, aaa),
                tt.postOrderTraversal(aaa)));

        // breadthFirstTraversal()
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of(aaa, bbb, ddd, ggg, ccc, eee, fff),
                tt.breadthFirstTraversal(aaa)));
    }
}
