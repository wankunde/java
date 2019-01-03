// Generated from com/wankun/antlr4/Demo.g4 by ANTLR 4.7
package com.wankun.antlr4;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DemoParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DemoVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DemoParser#r}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitR(DemoParser.RContext ctx);
}