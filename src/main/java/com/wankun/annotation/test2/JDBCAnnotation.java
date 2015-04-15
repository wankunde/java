package com.wankun.annotation.test2;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JDBC annotation
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JDBCAnnotation {

	String driver() default "com.mysql.jdbc.Driver";

	String dbName() default "";

	String encoding() default "UTF-8";

	String port() default "3306";

	String host() default "localhost";

	String userName() default "root";

	String password() default "";

}