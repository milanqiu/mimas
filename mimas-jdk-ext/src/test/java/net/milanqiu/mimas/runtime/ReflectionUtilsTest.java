package net.milanqiu.mimas.runtime;

import net.milanqiu.mimas.collect.LinkedProperties;
import net.milanqiu.mimas.collect.MapEntry;
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
    public void test_isAbstract() throws Exception {
        Assert.assertTrue(ReflectionUtils.isAbstract(TreeNode.class)); // interface
        Assert.assertTrue(ReflectionUtils.isAbstract(AbstractTreeNode.class)); // abstract class
        Assert.assertFalse(ReflectionUtils.isAbstract(LinkedTreeNode.class)); // non-abstract class
    }

    @Test
    public void test_hasDefaultConstructor() throws Exception {
        Assert.assertFalse(ReflectionUtils.hasDefaultConstructor(TreeNode.class)); // interface
        Assert.assertTrue(ReflectionUtils.hasDefaultConstructor(AbstractTreeNode.class)); // abstract class
        Assert.assertTrue(ReflectionUtils.hasDefaultConstructor(LinkedTreeNode.class)); // private default constructor
        Assert.assertFalse(ReflectionUtils.hasDefaultConstructor(MapEntry.class)); // only with-parameter constructor
        Assert.assertTrue(ReflectionUtils.hasDefaultConstructor(LinkedProperties.class)); // two constructors
    }

    @Test
    public void test_hasPublicDefaultConstructor() throws Exception {
        Assert.assertFalse(ReflectionUtils.hasPublicDefaultConstructor(TreeNode.class)); // interface
        Assert.assertTrue(ReflectionUtils.hasPublicDefaultConstructor(AbstractTreeNode.class)); // abstract class
        Assert.assertFalse(ReflectionUtils.hasPublicDefaultConstructor(LinkedTreeNode.class)); // private default constructor
        Assert.assertFalse(ReflectionUtils.hasPublicDefaultConstructor(MapEntry.class)); // only with-parameter constructor
        Assert.assertTrue(ReflectionUtils.hasPublicDefaultConstructor(LinkedProperties.class)); // two constructors
    }

    @Test
    public void test_getConstructorByParamCount() throws Exception {
        Assert.assertEquals(LinkedProperties.class.getConstructor(),
                ReflectionUtils.findConstructorByParamCount(LinkedProperties.class, 0));
        Assert.assertEquals(LinkedProperties.class.getConstructor(LinkedProperties.class),
                ReflectionUtils.findConstructorByParamCount(LinkedProperties.class, 1));
        Assert.assertEquals(null,
                ReflectionUtils.findConstructorByParamCount(LinkedProperties.class, 2));
    }
}
