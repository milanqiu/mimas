package net.milanqiu.mimas.collect.tree;

import net.milanqiu.mimas.collect.traversal.Traversable;

import java.util.List;

/**
 * Represents a node in a tree.
 * <p>
 * Creation Date: 2016-12-20
 * @author Milan Qiu
 * <p>
 * @param <D> the type of data held by this node
 */
public interface TreeNode<D> extends Traversable {

    /**
     * Returns the data held by this node.
     * @return the data held by this node
     */
    D getData();

    /**
     * A setter corresponding to the getter {@link #getData()}.
     */
    void setData(D data);

    /**
     * Returns the parent of this node.
     * @return the parent of this node
     */
    TreeNode<D> getParent();

    /**
     * Returns whether this node is ancestor of the specified node.
     * @param treeNode the node expected to be descendant of this node
     * @return {@code true} if this node is ancestor of the specified node
     */
    boolean isAncestorOf(TreeNode<D> treeNode);

    /**
     * Returns whether this node is descendant of the specified node.
     * @param treeNode the node expected to be ancestor of this node
     * @return {@code true} if this node is descendant of the specified node
     */
    boolean isDescendantOf(TreeNode<D> treeNode);

    /**
     * Returns whether this node is root of the tree.
     * @return {@code true} if this node is root of the tree
     */
    boolean isRoot();

    /**
     * Returns the root of the tree this node lives in.
     * @return the root of the tree this node lives in
     */
    TreeNode<D> getRoot();

    /**
     * Returns a list representing the path from root to this node.
     * @return a list representing the path from root to this node
     */
    List<TreeNode<D>> getPathFromRoot();

    /**
     * Returns the level of this node. Leve of root is 0.
     * @return the level of this node
     */
    int getLevel();

    /**
     * Returns the count of siblings of this node, including itself.
     * @return the count of siblings of this node
     */
    int getSiblingsCount();

    /**
     * Returns the list of siblings of this node, including itself.
     * @return the list of siblings of this node
     */
    List<TreeNode<D>> getSiblingsList();

    /**
     * Returns the sibling of this node at the specified position.
     * @param index index of the sibling to return
     * @return the sibling of this node at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range (<code>index &lt; 0 || index &gt;= getSiblingsCount()</code>)
     */
    TreeNode<D> getSibling(int index) throws IndexOutOfBoundsException;

    /**
     * Returns the previous sibling of this node.
     * @return the previous sibling of this node
     */
    TreeNode<D> getPrevSibling();

    /**
     * Returns the next sibling of this node.
     * @return the next sibling of this node
     */
    TreeNode<D> getNextSibling();

    /**
     * Returns the first sibling of this node.
     * @return the first sibling of this node
     */
    TreeNode<D> getFirstSibling();

    /**
     * Returns the last sibling of this node.
     * @return the last sibling of this node
     */
    TreeNode<D> getLastSibling();

    /**
     * Returns the position of this node among the siblings.
     * @return the position of this node among the siblings
     */
    int getPositionAmongSiblings();

    /**
     * Returns the position of the specified node among the siblings of this node.
     * If not found, returns {@code -1}.
     * @param treeNode the node to be found
     * @return the position of the specified node among the siblings of this node
     */
    int indexOfSibling(TreeNode<D> treeNode);

    /**
     * Returns whether this node has child.
     * @return whether this node has child
     */
    boolean hasChild();

    /**
     * Returns the count of children of this node.
     * @return the count of children of this node
     */
    int getChildCount();

    /**
     * Returns the list of children of this node.
     * @return the list of children of this node
     */
    List<TreeNode<D>> getChildList();

    /**
     * Returns the child of this node at the specified position.
     * @param index index of the child to return
     * @return the child of this node at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range (<code>index &lt; 0 || index &gt;= getChildCount()</code>)
     */
    TreeNode<D> getChild(int index) throws IndexOutOfBoundsException;

    /**
     * Returns the first child of this node.
     * @return the first child of this node
     */
    TreeNode<D> getFirstChild();

    /**
     * Returns the last child of this node.
     * @return the last child of this node
     */
    TreeNode<D> getLastChild();

    /**
     * Returns the position of the specified node among the children of this node.
     * If not found, returns {@code -1}.
     * @param treeNode the node to be found
     * @return the position of the specified node among the children of this node
     */
    int indexOfChild(TreeNode<D> treeNode);

    /**
     * Creates and returns a new node as a root without any relation.
     * @return the new created node
     */
    TreeNode<D> newStandalone();

    /**
     * Creates and returns a new node as the previous sibling of this node.
     * @return the new created node
     * @throws AddSiblingToRootException if this node is root
     */
    TreeNode<D> newPrevSibling() throws AddSiblingToRootException;

    /**
     * Creates and returns a new node as the next sibling of this node.
     * @return the new created node
     * @throws AddSiblingToRootException if this node is root
     */
    TreeNode<D> newNextSibling() throws AddSiblingToRootException;

    /**
     * Creates and returns a new node as the first sibling of this node.
     * @return the new created node
     * @throws AddSiblingToRootException if this node is root
     */
    TreeNode<D> newFirstSibling() throws AddSiblingToRootException;

    /**
     * Creates and returns a new node as the last sibling of this node.
     * @return the new created node
     * @throws AddSiblingToRootException if this node is root
     */
    TreeNode<D> newLastSibling() throws AddSiblingToRootException;

    /**
     * Creates and returns a new node as the first child of this node.
     * @return the new created node
     */
    TreeNode<D> newFirstChild();

    /**
     * Creates and returns a new node as the last child of this node.
     * @return the new created node
     */
    TreeNode<D> newLastChild();

    /**
     * Creates and returns a new node as a child of this node at the specified position.
     * @param index index of the new created node among the children of this node
     * @return the new created node
     * @throws IndexOutOfBoundsException if the index is out of range (<code>index &lt; 0 || index &gt; getChildCount()</code>)
     */
    TreeNode<D> newChild(int index) throws IndexOutOfBoundsException;

    /**
     * Adds the specified node into the tree as the previous sibling of this node.
     * @param treeNode the node to be added
     * @throws AddIncompatibleNodeException if the actual types of the added node and this node are incompatible
     * @throws AddSiblingToRootException if this node is root
     */
    void addPrevSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException;

    /**
     * Adds the specified node into the tree as the next sibling of this node.
     * @param treeNode the node to be added
     * @throws AddIncompatibleNodeException if the actual types of the added node and this node are incompatible
     * @throws AddSiblingToRootException if this node is root
     */
    void addNextSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException;

    /**
     * Adds the specified node into the tree as the first sibling of this node.
     * @param treeNode the node to be added
     * @throws AddIncompatibleNodeException if the actual types of the added node and this node are incompatible
     * @throws AddSiblingToRootException if this node is root
     */
    void addFirstSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException;

    /**
     * Adds the specified node into the tree as the last sibling of this node.
     * @param treeNode the node to be added
     * @throws AddIncompatibleNodeException if the actual types of the added node and this node are incompatible
     * @throws AddSiblingToRootException if this node is root
     */
    void addLastSibling(TreeNode<D> treeNode) throws AddIncompatibleNodeException, AddSiblingToRootException;

    /**
     * Adds the specified node into the tree as the first child of this node.
     * @param treeNode the node to be added
     * @throws AddIncompatibleNodeException if the actual types of the added node and this node are incompatible
     */
    void addFirstChild(TreeNode<D> treeNode) throws AddIncompatibleNodeException;

    /**
     * Adds the specified node into the tree as the last child of this node.
     * @param treeNode the node to be added
     * @throws AddIncompatibleNodeException if the actual types of the added node and this node are incompatible
     */
    void addLastChild(TreeNode<D> treeNode) throws AddIncompatibleNodeException;

    /**
     * Adds the specified node into the tree as a child of this node at the specified position.
     * @param treeNode the node to be added
     * @param index index of the added node among the children of this node
     * @throws AddIncompatibleNodeException if the actual types of the added node and this node are incompatible
     * @throws IndexOutOfBoundsException if the index is out of range (<code>index &lt; 0 || index &gt; getChildCount()</code>)
     */
    void addChild(TreeNode<D> treeNode, int index) throws AddIncompatibleNodeException, IndexOutOfBoundsException;

    /**
     * Removes this node from the tree this node lives in.
     * @throws RemoveRootException if this node is root
     */
    void removeSelfFromTree() throws RemoveRootException;

    /**
     * Removes the previous sibling of this node from the tree.
     * @return the removed node
     */
    TreeNode<D> removePrevSibling();

    /**
     * Removes the next sibling of this node from the tree.
     * @return the removed node
     */
    TreeNode<D> removeNextSibling();

    /**
     * Removes the first sibling of this node from the tree.
     * @return the removed node
     * @throws RemoveRootException if this node is root
     */
    TreeNode<D> removeFirstSibling() throws RemoveRootException;

    /**
     * Removes the last sibling of this node from the tree.
     * @return the removed node
     * @throws RemoveRootException if this node is root
     */
    TreeNode<D> removeLastSibling() throws RemoveRootException;

    /**
     * Removes the first child of this node from the tree.
     * @return the removed node
     */
    TreeNode<D> removeFirstChild();

    /**
     * Removes the last child of this node from the tree.
     * @return the removed node
     */
    TreeNode<D> removeLastChild();

    /**
     * Removes the child at the specified position of this node from the tree.
     * @param index index of the removed node among the children of this node
     * @return the removed node
     * @throws IndexOutOfBoundsException if the index is out of range (<code>index &lt; 0 || index &gt;= getChildCount()</code>)
     */
    TreeNode<D> removeChild(int index) throws IndexOutOfBoundsException;

    /**
     * Clears all siblings of this node.
     */
    void clearSiblings();

    /**
     * Clears all children of this node.
     */
    void clearChildren();

    /**
     * Returns a string representation of the whole tree with this node as root.
     * The string representation consists of all elements of the whole tree. Each element leads its child list with
     * a head mark equaling to "(", a tail mark equaling to ")", and separators equaling to "," between every two adjacent children.
     * <br>
     * For example, if the tree is
     * <br>
     * <pre><tt>
     *                aaa
     *             /   |   \
     *          bbb   ddd   ggg
     *         /     /   \
     *      ccc   eee     fff
     * </tt></pre>
     * Then the result may be <i><tt>aaa(bbb(ccc), ddd(eee, fff), ggg)</tt></i>.
     * <br>
     * Actually, it is a specialized variant of {@link #treeToString(String, String, String)}.
     * @return a string representation of the whole tree with this node as root
     */
    String treeToString();

    /**
     * Returns a string representation of the whole tree with this node as root.
     * The string representation consists of all elements of the whole tree. Each element leads its child list with
     * a head mark, a tail mark, and separators between every two adjacent children.
     * <br>
     * For example, if the head mark is "(", the separator is ",", the tail mark is ")", the tree is
     * <br>
     * <pre><tt>
     *                aaa
     *             /   |   \
     *          bbb   ddd   ggg
     *         /     /   \
     *      ccc   eee     fff
     * </tt></pre>
     * Then the result may be <i><tt>aaa(bbb(ccc), ddd(eee, fff), ggg)</tt></i>.
     * @param childListHead the head mark of child list
     * @param childSeparator the separator between every two adjacent children
     * @param childListTail the tail mark of child list
     * @return a string representation of the whole tree with this node as root
     */
    String treeToString(String childListHead, String childSeparator, String childListTail);

    /**
     * Returns a multiline string representation of the whole tree with this node as root.
     * The string representation consists of all elements of the whole tree. Each element occupies a single line,
     * leading by duplicates of level mark equaling to "\t", following by its child list.
     * <br>
     * For example, if the tree is
     * <br>
     * <pre><tt>
     *                aaa
     *             /   |   \
     *          bbb   ddd   ggg
     *         /     /   \
     *      ccc   eee     fff
     * </tt></pre>
     * Then the result may be
     * <br>
     * <pre><tt>
     *     aaa
     *     \tbbb
     *     \t\tccc
     *     \tddd
     *     \t\teee
     *     \t\tfff
     *     \tggg
     * </tt></pre>
     * Actually, it is a specialized variant of {@link #treeToMultiLineString(String)}.
     * @return a multiline string representation of the whole tree with this node as root
     */
    String treeToMultiLineString();

    /**
     * Returns a multiline string representation of the whole tree with this node as root.
     * The string representation consists of all elements of the whole tree. Each element occupies a single line,
     * leading by duplicates of level mark, following by its child list.
     * <br>
     * For example, if the level mark is "--", the tree is
     * <br>
     * <pre><tt>
     *                aaa
     *             /   |   \
     *          bbb   ddd   ggg
     *         /     /   \
     *      ccc   eee     fff
     * </tt></pre>
     * Then the result may be
     * <br>
     * <pre><tt>
     *     aaa
     *     --bbb
     *     ----ccc
     *     --ddd
     *     ----eee
     *     ----fff
     *     --ggg
     * </tt></pre>
     * @param levelMark the level mark leading a single line of element
     * @return a multiline string representation of the whole tree with this node as root
     */
    String treeToMultiLineString(String levelMark);
}
