package com.burnerpat.smartcli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to declare the "default" argument.
 *
 * The default argument takes every argument passed to the command line without a preceding argument name.
 * For example, in the command line {@code "my_app -arg "value" file.txt"}, the default argument would be {@code "file.txt"}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Default {

}
