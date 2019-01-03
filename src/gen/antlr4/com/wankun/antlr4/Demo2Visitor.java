// Generated from com/wankun/antlr4/Demo2.g4 by ANTLR 4.7
package com.wankun.antlr4;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Demo2Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Demo2Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Demo2Parser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(Demo2Parser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link Demo2Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(Demo2Parser.StatContext ctx);
	/**
	 * Visit a parse tree produced by {@link Demo2Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(Demo2Parser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link Demo2Parser#multExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExpr(Demo2Parser.MultExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link Demo2Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(Demo2Parser.AtomContext ctx);
}