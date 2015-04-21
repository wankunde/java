package com.wankun.annotation.java8;

import java.lang.annotation.Repeatable;

public @interface Hints {
	Hint[] value();
}

@Repeatable(Hints.class)
@interface Hint {
	String value();
}