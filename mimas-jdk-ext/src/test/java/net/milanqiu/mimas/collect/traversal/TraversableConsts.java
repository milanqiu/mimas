package net.milanqiu.mimas.collect.traversal;

import net.milanqiu.mimas.collect.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Creation Date: 2016-12-06
 * @author Milan Qiu
 */
public class TraversableConsts {

    static class TraversableElement implements Traversable {

        private String name;
        private List<TraversableElement> adjacencies;

        @Override
        public String toString() {
            return name;
        }

        @Override
        public Iterable<Traversable> adjacencies() {
            return CollectionUtils.convertIterable(adjacencies);
        }

        private TraversableElement(String name) {
            this.name = name;
            this.adjacencies = new ArrayList<>();
        }

        private void addAdjacencies(TraversableElement... adjacencies) {
            this.adjacencies.addAll(Arrays.asList(adjacencies));
        }
    }

    static TraversableElement aaa;
    static TraversableElement bbb;
    static TraversableElement ccc;
    static TraversableElement ddd;
    static TraversableElement eee;
    static TraversableElement fff;
    static TraversableElement ggg;
    static TraversableElement treeRoot;

    static TraversableElement ppp;
    static TraversableElement qqq;
    static TraversableElement rrr;
    static TraversableElement sss;
    static TraversableElement ttt;
    static TraversableElement[] graphVertexes;

    static void createTree() {
        aaa = new TraversableElement("aaa");
        bbb = new TraversableElement("bbb");
        ccc = new TraversableElement("ccc");
        ddd = new TraversableElement("ddd");
        eee = new TraversableElement("eee");
        fff = new TraversableElement("fff");
        ggg = new TraversableElement("ggg");
        aaa.addAdjacencies(bbb, ddd, ggg);
        bbb.addAdjacencies(ccc);
        ddd.addAdjacencies(eee, fff);
        treeRoot = aaa;

        // then the tree will be
        //                aaa
        //             /   |   \
        //          bbb   ddd   ggg
        //         /     /   \
        //      ccc   eee     fff
    }

    static void createGraph() {
        ppp = new TraversableElement("ppp");
        qqq = new TraversableElement("qqq");
        rrr = new TraversableElement("rrr");
        sss = new TraversableElement("sss");
        ttt = new TraversableElement("ttt");
        ppp.addAdjacencies(rrr, ttt);
        qqq.addAdjacencies(ttt);
        rrr.addAdjacencies(ppp, sss, ttt);
        sss.addAdjacencies(rrr);
        ttt.addAdjacencies(ppp, qqq, rrr);
        graphVertexes = new TraversableElement[5];
        graphVertexes[0] = ppp;
        graphVertexes[1] = qqq;
        graphVertexes[2] = rrr;
        graphVertexes[3] = sss;
        graphVertexes[4] = ttt;

        // then the graph will be
        //      ppp     qqq
        //       | \   /
        //       |  ttt
        //       | /
        //      rrr --- sss
    }
}
