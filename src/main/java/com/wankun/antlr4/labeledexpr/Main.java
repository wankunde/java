package com.wankun.antlr4.labeledexpr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
    String[] exprs = {
        "3*5+3",
        "(3+4)*4/5",
        "a=1",
        "b=2",
        "a+b*3"
    };

    StringBuffer buffer = new StringBuffer();
    Stream.of(exprs).forEach(x -> buffer.append(x + "\n"));
    CodePointCharStream charStream = CharStreams.fromString(buffer.toString());
    LabeledExprLexer lexer = new LabeledExprLexer(charStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    LabeledExprParser parser = new LabeledExprParser(tokens);
    ParseTree tree = parser.prog(); // parse
    EvalVisitor eval = new EvalVisitor();
    eval.visit(tree); // -> tree.accept(eval)

    System.out.println(tree.toStringTree(parser));

    List<String> ruleNames = Arrays.asList(parser.getRuleNames());
  }

  // 直接观察 hive 的解析树也凑活，这个暂时不处理
  public static String dumpGraphviz(final Tree t, final List<String> ruleNames) {

    String s = Utils.escapeWhitespace(Trees.getNodeText(t, ruleNames), false);
    if ( t.getChildCount()==0 ) return s;
    StringBuilder buf = new StringBuilder();
    buf.append("(");
    s = Utils.escapeWhitespace(Trees.getNodeText(t, ruleNames), false);
    buf.append(s);
    buf.append(' ');
    for (int i = 0; i<t.getChildCount(); i++) {
      if ( i>0 ) buf.append(' ');
      buf.append(dumpGraphviz(t.getChild(i), ruleNames));
    }
    buf.append(")");
    return buf.toString();
  }
}
