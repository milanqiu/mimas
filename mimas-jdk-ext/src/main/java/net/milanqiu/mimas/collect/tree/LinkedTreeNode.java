package net.milanqiu.mimas.collect.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class provides a linked implementation of {@link TreeNode} interface.
 * <p>
 * In this class, children are implemented by two references of first child and last child.
 * <p>
 * Creation Date: 2016-12-20
 * @author Milan Qiu
 * <p>
 * @param <D> the type of data held by this node
 */
public class LinkedTreeNode<D> extends AbstractTreeNode<D> {

    private LinkedTreeNode() {}

    /**
     * Creates and returns a new instance of {@code LinkedTreeNode} as a root without any relation.
     * @param <D> the type of data held by this node
     * @return the new instance of {@code LinkedTreeNode}
     */
    public static <D> LinkedTreeNode<D> createStandalone() {
        return new LinkedTreeNode<>();
    }

    /**
     * Creates and returns a new instance of {@code LinkedTreeNode} with the specified data, as a root without any relation.
     * @param data the expected data of the new instance of {@code LinkedTreeNode}
     * @param <D> the type of data held by this node
     * @return the new instance of {@code LinkedTreeNode}
     */
    public static <D> LinkedTreeNode<D> createStandalone(D data) {
        LinkedTreeNode<D> result = new LinkedTreeNode<>();
        result.setData(data);
        return result;
    }

    protected LinkedTreeNode<D> firstChild;
    protected LinkedTreeNode<D> lastChild;

    @Override
    public LinkedTreeNode<D> getParent() {
        return (LinkedTreeNode<D>) super.getParent();
    }

    @Override
    public LinkedTreeNode<D> getRoot() {
        return (LinkedTreeNode<D>) super.getRoot();
    }

    @Override
    public LinkedTreeNode<D> getSibling(int index) throws IndexOutOfBoundsException {
        return (LinkedTreeNode<D>) super.getSibling(index);
    }

    @Override
    public LinkedTreeNode<D> getPrevSibling() {
        return (LinkedTreeNode<D>) super.getPrevSibling();
    }

    @Override
    public LinkedTreeNode<D> getNextSibling() {
        return (LinkedTreeNode<D>) super.getNextSibling();
    }

    @Override
    public LinkedTreeNode<D> getFirstSibling() {
        return (LinkedTreeNode<D>) super.getFirstSibling();
    }

    @Override
    public LinkedTreeNode<D> getLastSibling() {
        return (LinkedTreeNode<D>) super.getLastSibling();
    }

    @Override
    public boolean hasChild() {
        return getFirstChild() != null;
    }

    @Override
    public int getChildCount() {
        int result = 0;
        TreeNode<D> node = getFirstChild();
        while (node != null) {
            result++;
            node = node.getNextSibling();
        }
        return result;
    }

    @Override
    public List<TreeNode<D>> getChildList() {
        List<TreeNode<D>> result = new LinkedList<>();
        TreeNode<D> node = getFirstChild();
        while (node != null) {
            result.add(node);
            node = node.getNextSibling();
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public LinkedTreeNode<D> getChild(int index) throws IndexOutOfBoundsException {
        if (index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index, getChildCount()));
        int cursor = 0;
        LinkedTreeNode<D> node = getFirstChild();
        while (node != null) {
            if (cursor++ == index)
                return node;
            node = node.getNextSibling();
        }
        throw new IndexOutOfBoundsException(outOfBoundsMsg(index, getChildCount()));
    }

    @Override
    public LinkedTreeNode<D> getFirstChild() {
        return firstChild;
    }

    @Override
    public LinkedTreeNode<D> getLastChild() {
        return lastChild;
    }

    @Override
    public int indexOfChild(TreeNode<D> treeNode) {
        int result = 0;
        TreeNode<D> node = getFirstChild();
        while (node != null) {
            if (node == treeNode)
                return result;
            result++;
            node = node.getNextSibling();
        }
        return -1;
    }

    @Override
    public LinkedTreeNode<D> newStandalone() {
        return createStandalone();
    }

    @Override
    public LinkedTreeNode<D> newPrevSibling() throws AddSiblingToRootException {
        return (LinkedTreeNode<D>) super.newPrevSibling();
    }

    @Override
    public LinkedTreeNode<D> newNextSibling() throws AddSiblingToRootException {
        return (LinkedTreeNode<D>) super.newNextSibling();
    }

    @Override
    public LinkedTreeNode<D> newFirstSibling() throws AddSiblingToRootException {
        return (LinkedTreeNode<D>) super.newFirstSibling();
    }

    @Override
    public LinkedTreeNode<D> newLastSibling() throws AddSiblingToRootException {
        return (LinkedTreeNode<D>) super.newLastSibling();
    }

    @Override
    public LinkedTreeNode<D> newFirstChild() {
        return (LinkedTreeNode<D>) super.newFirstChild();
    }

    @Override
    public LinkedTreeNode<D> newLastChild() {
        return (LinkedTreeNode<D>) super.newLastChild();
    }

    @Override
    public LinkedTreeNode<D> newChild(int index) throws IndexOutOfBoundsException {
        return (LinkedTreeNode<D>) super.newChild(index);
    }

    @Override
    public void addPrevSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof LinkedTreeNode))
            throw new AddIncompatibleNodeException();
        if (isRoot())
            throw new AddSiblingToRootException();

        LinkedTreeNode<D> node = (LinkedTreeNode<D>) treeNode;
        node.parent = parent;
        node.prevSibling = prevSibling;
        node.nextSibling = this;
        if (getPrevSibling() != null) {
            getPrevSibling().nextSibling = node;
        }
        prevSibling = node;
        if (getParent().firstChild == this)
            getParent().firstChild = node;
    }

    @Override
    public void addNextSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof LinkedTreeNode))
            throw new AddIncompatibleNodeException();
        if (isRoot())
            throw new AddSiblingToRootException();

        LinkedTreeNode<D> node = (LinkedTreeNode<D>) treeNode;
        node.parent = parent;
        node.prevSibling = this;
        node.nextSibling = nextSibling;
        if (getNextSibling() != null) {
            getNextSibling().prevSibling = node;
        }
        nextSibling = node;
        if (getParent().lastChild == this)
            getParent().lastChild = node;
    }

    @Override
    public void addFirstSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof LinkedTreeNode))
            throw new AddIncompatibleNodeException();
        if (isRoot())
            throw new AddSiblingToRootException();

        LinkedTreeNode<D> oldFirstSibling = getFirstSibling();
        LinkedTreeNode<D> node = (LinkedTreeNode<D>) treeNode;
        node.parent = parent;
        node.prevSibling = null;
        node.nextSibling = oldFirstSibling;
        oldFirstSibling.prevSibling = node;
        getParent().firstChild = node;
    }

    @Override
    public void addLastSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof LinkedTreeNode))
            throw new AddIncompatibleNodeException();
        if (isRoot())
            throw new AddSiblingToRootException();

        LinkedTreeNode<D> oldLastSibling = getLastSibling();
        LinkedTreeNode<D> node = (LinkedTreeNode<D>) treeNode;
        node.parent = parent;
        node.prevSibling = oldLastSibling;
        node.nextSibling = null;
        oldLastSibling.nextSibling = node;
        getParent().lastChild = node;
    }

    @Override
    public void addFirstChild(TreeNode<D> treeNode) throws AddIncompatibleNodeException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof LinkedTreeNode))
            throw new AddIncompatibleNodeException();

        LinkedTreeNode<D> node = (LinkedTreeNode<D>) treeNode;
        node.parent = this;
        node.prevSibling = null;
        node.nextSibling = firstChild;
        if (firstChild != null)
            firstChild.prevSibling = node;
        firstChild = node;
        if (lastChild == null)
            lastChild = node;
    }

    @Override
    public void addLastChild(TreeNode<D> treeNode) throws AddIncompatibleNodeException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof LinkedTreeNode))
            throw new AddIncompatibleNodeException();

        LinkedTreeNode<D> node = (LinkedTreeNode<D>) treeNode;
        node.parent = this;
        node.prevSibling = lastChild;
        node.nextSibling = null;
        if (lastChild != null)
            lastChild.nextSibling = node;
        lastChild = node;
        if (firstChild == null)
            firstChild = node;
    }

    @Override
    public void addChild(TreeNode<D> treeNode, int index) throws AddIncompatibleNodeException, IndexOutOfBoundsException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof LinkedTreeNode))
            throw new AddIncompatibleNodeException();

        if (index == 0) {
            addFirstChild(treeNode);
        } else {
            LinkedTreeNode<D> prevNode = getChild(index-1);
            prevNode.addNextSibling(treeNode);
        }
    }

    @Override
    public void removeSelfFromTree() throws RemoveRootException {
        if (isRoot())
            throw new RemoveRootException();

        if (getParent().firstChild == this)
            getParent().firstChild = getNextSibling();
        if (getParent().lastChild == this)
            getParent().lastChild = getPrevSibling();
        if (getPrevSibling() != null)
            getPrevSibling().nextSibling = getNextSibling();
        if (getNextSibling() != null)
            getNextSibling().prevSibling = getPrevSibling();
        parent = null;
        prevSibling = null;
        nextSibling = null;
    }

    @Override
    public LinkedTreeNode<D> removePrevSibling() {
        return (LinkedTreeNode<D>) super.removePrevSibling();
    }

    @Override
    public LinkedTreeNode<D> removeNextSibling() {
        return (LinkedTreeNode<D>) super.removeNextSibling();
    }

    @Override
    public LinkedTreeNode<D> removeFirstSibling() throws RemoveRootException {
        return (LinkedTreeNode<D>) super.removeFirstSibling();
    }

    @Override
    public LinkedTreeNode<D> removeLastSibling() throws RemoveRootException {
        return (LinkedTreeNode<D>) super.removeLastSibling();
    }

    @Override
    public LinkedTreeNode<D> removeFirstChild() {
        return (LinkedTreeNode<D>) super.removeFirstChild();
    }

    @Override
    public LinkedTreeNode<D> removeLastChild() {
        return (LinkedTreeNode<D>) super.removeLastChild();
    }

    @Override
    public LinkedTreeNode<D> removeChild(int index) throws IndexOutOfBoundsException {
        return (LinkedTreeNode<D>) super.removeChild(index);
    }

    @Override
    public void clearSiblings() {
        if (!isRoot()) {
            getParent().firstChild = this;
            getParent().lastChild = this;
        }
        prevSibling = null;
        nextSibling = null;
    }

    @Override
    public void clearChildren() {
        firstChild = null;
        lastChild = null;
    }
}
