package com.wankun.antlr4;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Demo2Test {

  public static void run(String expr) throws Exception {
    //对每一个输入的字符串，构造一个 CharStreams 流 in
    CodePointCharStream charStream = CharStreams.fromString(expr);

    //用 in 构造词法分析器 lexer，词法分析的作用是产生记号
    Demo2Lexer lexer = new Demo2Lexer(charStream);

    //用词法分析器 lexer 构造一个记号流 tokens
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    //再使用 tokens 构造语法分析器 parser,至此已经完成词法分析和语法分析的准备工作
    Demo2Parser parser = new Demo2Parser(tokens);

    //最终调用语法分析器的规则 prog，完成对表达式的验证
    parser.prog();
  }

  public static void main(String[] args) throws Exception {

    String[] testStr = {
        "2",
        "a+b+3",
        "(a-b)+3",
        "a+(b*3)"
    };

    for (String s : testStr) {
      System.out.println("Input expr:" + s);
      run(s);
    }
  }
}
