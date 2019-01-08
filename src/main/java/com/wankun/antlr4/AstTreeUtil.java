package com.wankun.antlr4;

import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class AstTreeUtil {

  public static String dumpGraphviz(Tree Tree,List<String> ruleNames) {
    List<Tree[]> relations = new ArrayList<>();
    Map<Tree, Integer> nodeMap = new LinkedHashMap<>();
    int p = 0;

    String buf = "digraph ast{ \n";
    buf += "  node [shape=plaintext]; \n";
    buf += "  \n";

    Stack<Tree> toVisit = new Stack<>();
    toVisit.push(Tree);
    nodeMap.put(Tree, p++);
    while (!toVisit.empty()) {
      Tree currNode = toVisit.pop();
      if (nodeMap.get(currNode) == null) {
        nodeMap.put(currNode, p++);
      }
      for (int i = 0; i < currNode.getChildCount(); i++) {
        Tree child = currNode.getChild(i);
        toVisit.push((Tree) child);
        if (nodeMap.get(child) == null)
          nodeMap.put((Tree) child, p++);
        relations.add(new Tree[]{currNode, (Tree) child});
      }
    }

    for (Map.Entry<Tree, Integer> en : nodeMap.entrySet()) {
      String nodeName = en.getKey() != null ? en.getKey().toString() : "Nil";
      buf += "  p" + en.getValue() + "[label=\"" + Utils.escapeWhitespace(Trees.getNodeText(en.getKey(), ruleNames), false) + " \"]" + ";  \n";
    }
    for (Tree[] r : relations) {
      buf += "  p" + nodeMap.get(r[0]) + " -> p" + nodeMap.get(r[1]) + "; \n";
    }
    buf += "} \n";
    return buf;
  }
}
