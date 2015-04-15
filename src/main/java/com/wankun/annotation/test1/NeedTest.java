package com.wankun.annotation.test1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
// 1. 声明注解的保留期限
@Target(ElementType.METHOD)
// 2. 声明可以使用该注解的目标类型
public @interface NeedTest { // 3. 定义注解

	/**
	 * 注解成员 限制：
	 * 
	 * 1. 无入参，无抛出异常声明
	 * 
	 * 2. 可以通过default 指定默认值
	 * 
	 * 3. 成员类型是受限的，合法的类型包括原始类型及其封装类、String
	 * 、Class、enums、注解类型，以及上述类型的数组类型。如ForumService value()、List foo()是非法的
	 * 
	 * @return
	 */
	boolean value() default true; // 4. 声明注解成员

}

/**
 * 1. 元注解
 */
