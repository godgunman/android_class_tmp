package com.example.database;
public @interface DBAnnotation {
	String type() default "_default";
	String name() default "_default";
}
