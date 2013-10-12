package br.com.newbesources.pfu.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gabriel
 *
 * Oct 10, 2013
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Field {
	static final int RIGHT_PAD = 1;
	static final int LEFT_PAD = 0;
	
	static final String WHITE_SPACE = " ";
	static final String ZERO = "0";

	int firstPosition();
	int lastPosition();
	String pad() default WHITE_SPACE;
	int padDirection() default RIGHT_PAD;
}