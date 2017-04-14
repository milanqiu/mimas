package net.milanqiu.mimas.collect.tree;

import net.milanqiu.mimas.collect.CollectionUtils;
import net.milanqiu.mimas.collect.traversal.CompletedTraverser;
import net.milanqiu.mimas.collect.traversal.Traversable;
import net.milanqiu.mimas.collect.traversal.TraversalListener;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.string.StrUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class provides a skeletal implementation of {@link TreeNode} interface to minimize the effort required to
 * implement this interface backed.
 * <p>
 * It implements parent and siblings by three references of parent, previous sibling and next sibling.
 * <p>
 * Creation Date: 2016-12-20
 * @author Milan Qiu
 * <p>
 * @param <D> the type of data held by this node
 */
public abstract class AbstractTreeNode<D> implements TreeNode<D> {

    protected String outOfBoundsMsg(int index, int size) {
        return "Index: " + index + ", Size: " + size;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : StrUtils.STR_EMPTY;
    }

    @Override
    public Iterable<Traversable> adjacencies() {
        return CollectionUtils.convertIterable(getChildList());
    }

    protected D data;

    @Override
    public D getData() {
        return data;
    }

    @Override
    public void setData(D data) {
        this.data = data;
    }

    protected TreeNode<D> parent;
    protected TreeNode<D> prevSibling;
    protected TreeNode<D> nextSibling;

    @Override
    public TreeNode<D> getParent() {
        return parent;
    }

    @Override
    public boolean isAncestorOf(TreeNode<D> treeNode) {
        if (treeNode == null || treeNode == this)
            return false;

        TreeNode<D> node = treeNode;
        while (node.getParent() != null) {
            node = node.getParent();
            if (node == this)
                return true;
        }
        return false;
    }

    @Override
    public boolean isDescendantOf(TreeNode<D> treeNode) {
        if (treeNode == null || treeNode == this)
            return false;

        TreeNode<D> node = this;
        while (node.getParent() != null) {
            node = node.getParent();
            if (node == treeNode)
                return true;
        }
        return false;
    }

    @Override
    public boolean isRoot() {
        return getParent() == null;
    }

    @Override
    public TreeNode<D> getRoot() {
        TreeNode<D> result = this;
        while (result.getParent() != null) {
            result = result.getParent();
        }
        return result;
    }

    @Override
    public List<TreeNode<D>> getPathFromRoot() {
        LinkedList<TreeNode<D>> result = new LinkedList<>();
        TreeNode<D> node = this;
        result.add(node);
        while (node.getParent() != null) {
            node = node.getParent();
            result.addFirst(node);
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public int getLevel() {
        int result = 0;
        TreeNode<D> node = this;
        while (node.getParent() != null) {
            node = node.getParent();
            result++;
        }
        return result;
    }

    @Override
    public int getSiblingsCount() {
        return (getParent() == null) ? 1 : getParent().getChildCount();
    }

    @Override
    public List<TreeNode<D>> getSiblingsList() {
        return (getParent() == null) ? Collections.singletonList(this) : getParent().getChildList();
    }

    @Override
    public TreeNode<D> getSibling(int index) throws IndexOutOfBoundsException {
        if (getParent() == null) {
            if (index == 0)
                return this;
            else
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index, 0));
        } else {
            return getParent().getChild(index);
        }
    }

    @Override
    public TreeNode<D> getPrevSibling() {
        return prevSibling;
    }

    @Override
    public TreeNode<D> getNextSibling() {
        return nextSibling;
    }

    @Override
    public TreeNode<D> getFirstSibling() {
        return (getParent() == null) ? this : getParent().getFirstChild();
    }

    @Override
    public TreeNode<D> getLastSibling() {
        return (getParent() == null) ? this : getParent().getLastChild();
    }

    @Override
    public int getPositionAmongSiblings() {
        int result = indexOfSibling(this);
        DebugUtils.assertTrue("self not found in siblings", result != -1);
        return result;
    }

    @Override
    public int indexOfSibling(TreeNode<D> treeNode) {
        return (getParent() == null) ? (treeNode == this ? 0 : -1) : getParent().indexOfChild(treeNode);
    }

    @Override
    public TreeNode<D> newPrevSibling() throws AddSiblingToRootException {
        if (isRoot())
            throw new AddSiblingToRootException();
        TreeNode<D> result = newStandalone();
        addPrevSibling(result);
        return result;
    }

    @Override
    public TreeNode<D> newNextSibling() throws AddSiblingToRootException {
        if (isRoot())
            throw new AddSiblingToRootException();
        TreeNode<D> result = newStandalone();
        addNextSibling(result);
        return result;
    }

    @Override
    public TreeNode<D> newFirstSibling() throws AddSiblingToRootException {
        if (isRoot())
            throw new AddSiblingToRootException();
        TreeNode<D> result = newStandalone();
        addFirstSibling(result);
        return result;
    }

    @Override
    public TreeNode<D> newLastSibling() throws AddSiblingToRootException {
        if (isRoot())
            throw new AddSiblingToRootException();
        TreeNode<D> result = newStandalone();
        addLastSibling(result);
        return result;
    }

    @Override
    public TreeNode<D> newFirstChild() {
        TreeNode<D> result = newStandalone();
        addFirstChild(result);
        return result;
    }

    @Override
    public TreeNode<D> newLastChild() {
        TreeNode<D> result = newStandalone();
        addLastChild(result);
        return result;
    }

    @Override
    public TreeNode<D> newChild(int index) throws IndexOutOfBoundsException {
        TreeNode<D> result = newStandalone();
        addChild(result, index);
        return result;
    }

    @Override
    public TreeNode<D> removePrevSibling() {
        TreeNode<D> result = getPrevSibling();
        if (result != null) {
            result.removeSelfFromTree();
        }
        return result;
    }

    @Override
    public TreeNode<D> removeNextSibling() {
        TreeNode<D> result = getNextSibling();
        if (result != null) {
            result.removeSelfFromTree();
        }
        return result;
    }

    @Override
    public TreeNode<D> removeFirstSibling() throws RemoveRootException {
        TreeNode<D> result = getFirstSibling();
        if (result != null) {
            result.removeSelfFromTree();
        }
        return result;
    }

    @Override
    public TreeNode<D> removeLastSibling() throws RemoveRootException {
        TreeNode<D> result = getLastSibling();
        if (result != null) {
            result.removeSelfFromTree();
        }
        return result;
    }

    @Override
    public TreeNode<D> removeFirstChild() {
        TreeNode<D> result = getFirstChild();
        if (result != null) {
            result.removeSelfFromTree();
        }
        return result;
    }

    @Override
    public TreeNode<D> removeLastChild() {
        TreeNode<D> result = getLastChild();
        if (result != null) {
            result.removeSelfFromTree();
        }
        return result;
    }

    @Override
    public TreeNode<D> removeChild(int index) throws IndexOutOfBoundsException {
        TreeNode<D> result = getChild(index);
        if (result != null) {
            result.removeSelfFromTree();
        }
        return result;
    }

    @Override
    public String treeToString() {
        return treeToString("(", ", ", ")");
    }

    @Override
    public String treeToString(String childListHead, String childSeparator, String childListTail) {
        StringBuilder sb = new StringBuilder();
        CompletedTraverser.create(new TraversalListener() {
            @Override
            public void visitElement(int level, Traversable element) {
                sb.append(element);
            }

            @Override
            public void enterNextLevel(int toLevel, Traversable fromElement, Traversable toElement) {
                sb.append(childListHead);
            }

            @Override
            public void enterPrevLevel(int toLevel, Traversable fromElement, Traversable toElement) {
                sb.append(childListTail);
            }

            @Override
            public void travelBetweenAdjacencies(int level, Traversable fromElement, Traversable toElement) {
                sb.append(childSeparator);
            }
        }, false).preOrderTraversal(this);
        return sb.toString();
    }

    @Override
    public String treeToMultiLineString() {
        return treeToMultiLineString("\t");
    }

    @Override
    public String treeToMultiLineString(String levelMark) {
        StringBuilder sb = new StringBuilder();
        CompletedTraverser.create(new TraversalListener() {
            @Override
            public void visitElement(int level, Traversable element) {
                sb.append(element);
            }

            @Override
            public void enterNextLevel(int toLevel, Traversable fromElement, Traversable toElement) {
                sb.append(System.lineSeparator());
                for (int i = 0; i < toLevel; i++)
                    sb.append(levelMark);
            }

            @Override
            public void enterPrevLevel(int toLevel, Traversable fromElement, Traversable toElement) {
            }

            @Override
            public void travelBetweenAdjacencies(int level, Traversable fromElement, Traversable toElement) {
                sb.append(System.lineSeparator());
                for (int i = 0; i < level; i++)
                    sb.append(levelMark);
            }
        }, false).preOrderTraversal(this);
        return sb.toString();
    }
}
