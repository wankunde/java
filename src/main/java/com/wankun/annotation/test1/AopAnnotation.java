package com.wankun.annotation.test1;

public @interface AopAnnotation {

	boolean transaction() default false;

	String startlog();

	String endlog();
}
