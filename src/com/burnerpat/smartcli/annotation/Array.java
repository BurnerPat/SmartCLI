package com.burnerpat.smartcli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to declare upper and lower bounds for the amount of elements in an array.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Array {
    /**
     * The minimum amount of elements in the array.
     *
     * The value has to be a positive integer, or 0.
     */
    public int min() default 0;

    /**
     * The maximum amount of elements in the array.
     *
     * The value has to be greater than the minimum value.
     */
    public int max() default Integer.MAX_VALUE;
}
