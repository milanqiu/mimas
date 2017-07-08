package net.milanqiu.mimas.guava;

import com.google.common.collect.TreeTraverser;
import net.milanqiu.mimas.collect.tree.TreeNode;

/**
 * An extension of {@link com.google.common.collect.TreeTraverser} to provide more utilities.
 * <p>
 * Creation Date: 2017-04-14
 * @author Milan Qiu
 */
public class TreeTraverserExt {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private TreeTraverserExt() {}

    /**
     * Creates and returns a new instance of {@link com.google.common.collect.TreeTraverser} over tree nodes of
     * {@link net.milanqiu.mimas.collect.tree.TreeNode}.
     * @param <D> the type of data held by tree nodes
     * @return the new instance of {@link com.google.common.collect.TreeTraverser}
     */
    public static <D> TreeTraverser<TreeNode<D>> overTreeNode() {
        return new TreeTraverser<TreeNode<D>>() {
            @Override
            public Iterable<TreeNode<D>> children(TreeNode<D> root) {
                return root.getChildList();
            }
        };
    }
}
