package net.milanqiu.mimas.collect.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class provides a detailed implementation of {@link TreeNode} interface.
 * <p>
 * In this class, children are implemented by a child list.
 * <p>
 * Creation Date: 2017-01-13
 * @author Milan Qiu
 * <p>
 * @param <D> the type of data held by this node
 */
public class ChildListTreeNode<D> extends AbstractTreeNode<D> {

    private ChildListTreeNode() {}

    /**
     * Creates and returns a new instance of {@code ChildListTreeNode} as a root without any relation.
     * @param <D> the type of data held by this node
     * @return the new instance of {@code ChildListTreeNode}
     */
    public static <D> ChildListTreeNode<D> createStandalone() {
        return new ChildListTreeNode<>();
    }

    /**
     * Creates and returns a new instance of {@code ChildListTreeNode} with the specified data, as a root without any relation.
     * @param data the expected data of the new instance of {@code ChildListTreeNode}
     * @param <D> the type of data held by this node
     * @return the new instance of {@code ChildListTreeNode}
     */
    public static <D> ChildListTreeNode<D> createStandalone(D data) {
        ChildListTreeNode<D> result = new ChildListTreeNode<>();
        result.setData(data);
        return result;
    }

    protected List<ChildListTreeNode<D>> childList = new LinkedList<>();

    @Override
    public ChildListTreeNode<D> getParent() {
        return (ChildListTreeNode<D>) super.getParent();
    }

    @Override
    public ChildListTreeNode<D> getRoot() {
        return (ChildListTreeNode<D>) super.getRoot();
    }

    @Override
    public ChildListTreeNode<D> getSibling(int index) throws IndexOutOfBoundsException {
        return (ChildListTreeNode<D>) super.getSibling(index);
    }

    @Override
    public ChildListTreeNode<D> getPrevSibling() {
        return (ChildListTreeNode<D>) super.getPrevSibling();
    }

    @Override
    public ChildListTreeNode<D> getNextSibling() {
        return (ChildListTreeNode<D>) super.getNextSibling();
    }

    @Override
    public ChildListTreeNode<D> getFirstSibling() {
        return (ChildListTreeNode<D>) super.getFirstSibling();
    }

    @Override
    public ChildListTreeNode<D> getLastSibling() {
        return (ChildListTreeNode<D>) super.getLastSibling();
    }

    @Override
    public boolean hasChild() {
        return !childList.isEmpty();
    }

    @Override
    public int getChildCount() {
        return childList.size();
    }

    @Override
    public List<TreeNode<D>> getChildList() {
        return Collections.unmodifiableList(childList);
    }

    @Override
    public ChildListTreeNode<D> getChild(int index) throws IndexOutOfBoundsException {
        return childList.get(index);
    }

    @Override
    public ChildListTreeNode<D> getFirstChild() {
        return childList.isEmpty() ? null : childList.get(0);
    }

    @Override
    public ChildListTreeNode<D> getLastChild() {
        return childList.isEmpty() ? null : childList.get(childList.size()-1);
    }

    @Override
    public int indexOfChild(TreeNode<D> treeNode) {
        return childList.indexOf(treeNode);
    }

    @Override
    public ChildListTreeNode<D> newStandalone() {
        return createStandalone();
    }

    @Override
    public ChildListTreeNode<D> newPrevSibling() throws AddSiblingToRootException {
        return (ChildListTreeNode<D>) super.newPrevSibling();
    }

    @Override
    public ChildListTreeNode<D> newNextSibling() throws AddSiblingToRootException {
        return (ChildListTreeNode<D>) super.newNextSibling();
    }

    @Override
    public ChildListTreeNode<D> newFirstSibling() throws AddSiblingToRootException {
        return (ChildListTreeNode<D>) super.newFirstSibling();
    }

    @Override
    public ChildListTreeNode<D> newLastSibling() throws AddSiblingToRootException {
        return (ChildListTreeNode<D>) super.newLastSibling();
    }

    @Override
    public ChildListTreeNode<D> newFirstChild() {
        return (ChildListTreeNode<D>) super.newFirstChild();
    }

    @Override
    public ChildListTreeNode<D> newLastChild() {
        return (ChildListTreeNode<D>) super.newLastChild();
    }

    @Override
    public ChildListTreeNode<D> newChild(int index) throws IndexOutOfBoundsException {
        return (ChildListTreeNode<D>) super.newChild(index);
    }

    @Override
    public void addPrevSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof ChildListTreeNode))
            throw new AddIncompatibleNodeException();
        if (isRoot())
            throw new AddSiblingToRootException();

        ChildListTreeNode<D> node = (ChildListTreeNode<D>) treeNode;
        node.parent = parent;
        node.prevSibling = prevSibling;
        node.nextSibling = this;
        if (getPrevSibling() != null) {
            getPrevSibling().nextSibling = node;
            getParent().childList.add(getParent().indexOfChild(getPrevSibling()), node);
        } else {
            getParent().childList.add(0, node);
        }
        prevSibling = node;
    }

    @Override
    public void addNextSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof ChildListTreeNode))
            throw new AddIncompatibleNodeException();
        if (isRoot())
            throw new AddSiblingToRootException();

        ChildListTreeNode<D> node = (ChildListTreeNode<D>) treeNode;
        node.parent = parent;
        node.prevSibling = this;
        node.nextSibling = nextSibling;
        if (getNextSibling() != null) {
            getNextSibling().prevSibling = node;
            getParent().childList.add(getParent().indexOfChild(getNextSibling()), node);
        } else {
            getParent().childList.add(node);
        }
        nextSibling = node;
    }

    @Override
    public void addFirstSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof ChildListTreeNode))
            throw new AddIncompatibleNodeException();
        if (isRoot())
            throw new AddSiblingToRootException();

        ChildListTreeNode<D> oldFirstSibling = getFirstSibling();
        ChildListTreeNode<D> node = (ChildListTreeNode<D>) treeNode;
        node.parent = parent;
        node.prevSibling = null;
        node.nextSibling = oldFirstSibling;
        oldFirstSibling.prevSibling = node;
        getParent().childList.add(0, node);
    }

    @Override
    public void addLastSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof ChildListTreeNode))
            throw new AddIncompatibleNodeException();
        if (isRoot())
            throw new AddSiblingToRootException();

        ChildListTreeNode<D> oldLastSibling = getLastSibling();
        ChildListTreeNode<D> node = (ChildListTreeNode<D>) treeNode;
        node.parent = parent;
        node.prevSibling = oldLastSibling;
        node.nextSibling = null;
        oldLastSibling.nextSibling = node;
        getParent().childList.add(node);
    }

    @Override
    public void addFirstChild(TreeNode<D> treeNode) throws AddIncompatibleNodeException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof ChildListTreeNode))
            throw new AddIncompatibleNodeException();

        ChildListTreeNode<D> oldFirstChild = getFirstChild();
        ChildListTreeNode<D> node = (ChildListTreeNode<D>) treeNode;
        node.parent = this;
        node.prevSibling = null;
        node.nextSibling = oldFirstChild;
        if (oldFirstChild != null)
            oldFirstChild.prevSibling = node;
        childList.add(0, node);
    }

    @Override
    public void addLastChild(TreeNode<D> treeNode) throws AddIncompatibleNodeException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof ChildListTreeNode))
            throw new AddIncompatibleNodeException();

        ChildListTreeNode<D> oldLastChild = getLastChild();
        ChildListTreeNode<D> node = (ChildListTreeNode<D>) treeNode;
        node.parent = this;
        node.prevSibling = oldLastChild;
        node.nextSibling = null;
        if (oldLastChild != null)
            oldLastChild.nextSibling = node;
        childList.add(node);
    }

    @Override
    public void addChild(TreeNode<D> treeNode, int index) throws AddIncompatibleNodeException, IndexOutOfBoundsException {
        if (treeNode == null)
            throw new IllegalArgumentException();
        if (!(treeNode instanceof ChildListTreeNode))
            throw new AddIncompatibleNodeException();

        if (index == 0) {
            addFirstChild(treeNode);
        } else {
            ChildListTreeNode<D> prevNode = getChild(index-1);
            prevNode.addNextSibling(treeNode);
        }
    }

    @Override
    public void removeSelfFromTree() throws RemoveRootException {
        if (isRoot())
            throw new RemoveRootException();

        getParent().childList.remove(this);
        if (getPrevSibling() != null)
            getPrevSibling().nextSibling = getNextSibling();
        if (getNextSibling() != null)
            getNextSibling().prevSibling = getPrevSibling();
        parent = null;
        prevSibling = null;
        nextSibling = null;
    }

    @Override
    public ChildListTreeNode<D> removePrevSibling() {
        return (ChildListTreeNode<D>) super.removePrevSibling();
    }

    @Override
    public ChildListTreeNode<D> removeNextSibling() {
        return (ChildListTreeNode<D>) super.removeNextSibling();
    }

    @Override
    public ChildListTreeNode<D> removeFirstSibling() throws RemoveRootException {
        return (ChildListTreeNode<D>) super.removeFirstSibling();
    }

    @Override
    public ChildListTreeNode<D> removeLastSibling() throws RemoveRootException {
        return (ChildListTreeNode<D>) super.removeLastSibling();
    }

    @Override
    public ChildListTreeNode<D> removeFirstChild() {
        return (ChildListTreeNode<D>) super.removeFirstChild();
    }

    @Override
    public ChildListTreeNode<D> removeLastChild() {
        return (ChildListTreeNode<D>) super.removeLastChild();
    }

    @Override
    public ChildListTreeNode<D> removeChild(int index) throws IndexOutOfBoundsException {
        return (ChildListTreeNode<D>) super.removeChild(index);
    }

    @Override
    public void clearSiblings() {
        if (!isRoot()) {
            getParent().childList.clear();
            getParent().childList.add(this);
        }
        prevSibling = null;
        nextSibling = null;
    }

    @Override
    public void clearChildren() {
        childList.clear();
    }
}
