package com.burnerpat.smartcli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to indicate that an argument is a "switch". The corresponding field has to be a boolean value.
 *
 * A switch argument (also called "option") is an argument without any value. If the argument passed, the switch
 * value will become {@code true}, otherwise {@code false}.
 *
 * For example, in the command line {@code "my_app -switch file.txt"}, {@code "-switch"} would be a switch argument.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Switch {

}
