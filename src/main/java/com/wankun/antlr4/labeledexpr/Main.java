package com.wankun.antlr4.labeledexpr;

import com.wankun.antlr4.AstTreeUtil;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

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
        "a+b*3",
        "1+2"
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
    System.out.println(AstTreeUtil.dumpGraphviz(tree,ruleNames));
  }

}

