package net.milanqiu.mimas.collect.tree;

import java.util.function.Supplier;

/**
 * Creation Date: 2016-12-20
 * @author Milan Qiu
 */
public class TreeNodeConsts {

    static TreeNode<String> aaa;
    static TreeNode<String> bbb;
    static TreeNode<String> ccc;
    static TreeNode<String> ddd;
    static TreeNode<String> eee;
    static TreeNode<String> fff;
    static TreeNode<String> ggg;

    static void createTree(Supplier<TreeNode<String>> treeNodeFactory) {
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
}
