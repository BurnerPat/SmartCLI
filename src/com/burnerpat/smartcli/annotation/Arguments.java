package com.burnerpat.smartcli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to declare an arguments class.
 *
 * The annotation will be processed by SmartCLI at runtime and is required for SmartCLI to accept the arguments class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Arguments {
    /**
     * The prefix the user is supposed to provide with the argument names; default is "-".
     */
    public String prefix() default "-";

    /**
     * The display name of the application.
     *
     * Mostly used during the generation of the usage text.
     */
    public String value();

    /**
     * A short string describing the purpose and functionality of the application.
     *
     * Mostly used during the generation of the usage text.
     */
    public String appDocumentation() default "";
}
