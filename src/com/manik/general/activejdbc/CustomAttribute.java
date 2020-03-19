//$Id$
package com.manik.general.activejdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomAttribute {
	public String name();
	public String column() default "";
	public String columnType() default "";
	public String type() default "";
	public String cacheKey() default "";
}
