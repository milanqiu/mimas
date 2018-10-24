package net.milanqiu.mimas.runtime;

import net.milanqiu.mimas.collect.tree.AbstractTreeNode;
import net.milanqiu.mimas.collect.tree.LinkedTreeNode;
import net.milanqiu.mimas.collect.tree.TreeNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2018-10-24
 * @author Milan Qiu
 */
public class ReflectionUtilsTest {

    @Test
    public void test_isNonAbstract() throws Exception {
        Assert.assertFalse(ReflectionUtils.isNonAbstract(TreeNode.class));
        Assert.assertFalse(ReflectionUtils.isNonAbstract(AbstractTreeNode.class));
        Assert.assertTrue(ReflectionUtils.isNonAbstract(LinkedTreeNode.class));
    }
}
