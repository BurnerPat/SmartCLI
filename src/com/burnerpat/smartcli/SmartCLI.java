package com.burnerpat.smartcli;

import com.burnerpat.smartcli.annotation.*;
import com.burnerpat.smartcli.argument.*;
import com.burnerpat.smartcli.strategy.DefaultStrategy;
import com.burnerpat.smartcli.strategy.UsageBuilder;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Main class of the SmartCLI API.
 *
 * @param <T> A properly annotated arguments class of your choice. See {@link com.burnerpat.smartcli.annotation.Arguments}.
 */
public class SmartCLI<T> {
    private static final DefaultStrategy DEFAULT_STRATEGY = new DefaultStrategy();

    private final HashMap<String, Argument> argumentMap = new HashMap<String, Argument>();
    private final List<Argument> arguments = new ArrayList<Argument>();

    private ArrayArgument<String> defaultArgument = null;

    private String prefix = null;
    private String appName = null;
    private String appDocumentation = null;

    private UsageBuilder usageBuilder;

    /**
     * Initializes the SmartCLI object using default strategy implementations.
     *
     * @see #SmartCLI(Class, com.burnerpat.smartcli.strategy.UsageBuilder)
     *
     * @param clazz The class of the arguments class.
     */
    public SmartCLI(Class<T> clazz) {
        this(clazz, DEFAULT_STRATEGY);
    }

    /**
     * Initializes the SmartCLI object using custom strategy implementations.
     *
     * During initialization, the object will be analyzed for the annotations, but no values will be set.
     *
     * @param clazz The class of the arguments class.
     * @param usageBuilder The {@link com.burnerpat.smartcli.strategy.UsageBuilder} object used to generate usage text.
     */
    public SmartCLI(Class<T> clazz, UsageBuilder usageBuilder) {
        this.usageBuilder = usageBuilder;

        if (!clazz.isAnnotationPresent(Arguments.class)) {
            throw new IllegalArgumentException("Type parameter of SmartCLI must be annotated with @Arguments");
        }

        Arguments argAnnotation = clazz.getAnnotation(Arguments.class);
        prefix = argAnnotation.prefix();
        appName = argAnnotation.value();
        appDocumentation = (argAnnotation.appDocumentation().trim().length() > 0) ? argAnnotation.appDocumentation() : null;

        for (Field field : clazz.getFields()) {
            if (field.isAnnotationPresent(Ignore.class)) {
                continue;
            }

            if (field.isAnnotationPresent(Default.class)) {
                if (defaultArgument != null) {
                    throwFieldError(field, "Only one single argument can be declared as default argument");
                }

                if (!field.getType().equals(String[].class)) {
                    throwFieldError(field, "The default argument must be declared as a String array");
                }

                if (field.isAnnotationPresent(Switch.class) || field.isAnnotationPresent(Array.class)) {
                    throwFieldError(field, "Invalid annotation present for default argument");
                }

                defaultArgument = new ArrayArgument<String>(Converter.STRING_CONVERTER);
                defaultArgument.setField(field);

                if (field.isAnnotationPresent(Name.class)) {
                    defaultArgument.setNames(field.getAnnotation(Name.class).value());
                }

                if (field.isAnnotationPresent(Documentation.class)) {
                    defaultArgument.setHelpText(field.getAnnotation(Documentation.class).value());
                }

                if (field.isAnnotationPresent(Required.class)) {
                    defaultArgument.setRequired(true);
                }

                continue;
            }

            Argument argument = null;
            Class type = field.getType();

            if (field.isAnnotationPresent(Switch.class)) {
                if (!type.equals(Boolean.class) && !type.equals(boolean.class)) {
                    throwFieldError(field, "A switch argument must be a boolean");
                }

                argument = new SwitchArgument();
            }
            else {
                if (type.equals(String.class)) {
                    argument = new SimpleArgument<String>(Converter.STRING_CONVERTER);
                }
                else if (type.equals(String[].class)) {
                    argument = new ArrayArgument<String>(Converter.STRING_CONVERTER);
                }
                else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                    argument = new SimpleArgument<Boolean>(Converter.BOOLEAN_CONVERTER);
                }
                else if (type.equals(Boolean[].class) || type.equals(boolean[].class)) {
                    argument = new ArrayArgument<Boolean>(Converter.BOOLEAN_CONVERTER);
                }
                else if (type.equals(Integer.class) || type.equals(int.class)) {
                    argument = new SimpleArgument<Integer>(Converter.INTEGER_CONVERTER);
                }
                else if (type.equals(Integer[].class) || type.equals(int[].class)) {
                    argument = new ArrayArgument<Integer>(Converter.INTEGER_CONVERTER);
                }
                else if (type.equals(Float.class) || type.equals(float.class)) {
                    argument = new SimpleArgument<Float>(Converter.FLOAT_CONVERTER);
                }
                else if (type.equals(Float[].class) || type.equals(float[].class)) {
                    argument = new ArrayArgument<Float>(Converter.FLOAT_CONVERTER);
                }
                else if (type.equals(Double.class) || type.equals(double.class) || type.equals(Number.class)) {
                    argument = new SimpleArgument<Double>(Converter.DOUBLE_CONVERTER);
                }
                else if (type.equals(Double[].class) || type.equals(double[].class) || type.equals(Number[].class)) {
                    argument = new ArrayArgument<Double>(Converter.DOUBLE_CONVERTER);
                }
                else {
                    throwFieldError(field, type.getName() + " is not a supported argument type");
                }
            }

            argument.setField(field);

            if (field.isAnnotationPresent(Name.class)) {
                argument.setNames(field.getAnnotation(Name.class).value());
            }
            else {
                argument.setNames(new String[] {field.getName()});
            }

            if (field.isAnnotationPresent(Documentation.class)) {
                argument.setHelpText(field.getAnnotation(Documentation.class).value());
            }

            argument.setRequired(field.isAnnotationPresent(Required.class));

            if (field.isAnnotationPresent(Array.class)) {
                if (!(argument.isArray() && argument instanceof ArrayArgument)) {
                    throwFieldError(field, "An argument must not be annotated with array options if it is not an array");
                }

                Array arrAnnotation = field.getAnnotation(Array.class);
                ((ArrayArgument)argument).setBounds(arrAnnotation.min(), arrAnnotation.max());
            }

            arguments.add(argument);

            for (String name : argument.getNames()) {
                argumentMap.put(name, argument);
            }
        }
    }

    private void throwFieldError(Field field, String message) {
        throw new IllegalArgumentException(message + " (" + field.getName() + ")");
    }

    /**
     * Parses the arguments array and sets the corresponding value in the arguments object.
     *
     * @param args The arguments array, as passed to your application by {@code public static void main(String[] args)}
     * @param obj The arguments object.
     *
     * @return The exact same arguments object as the {@code obj} parameter.
     *
     * @throws CLIException In any case of problems with the arguments array, or if any Java Reflections problems occur.
     */
    public T parse(String[] args, T obj) throws CLIException {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (!arg.startsWith(prefix)) {
                if (defaultArgument != null) {
                    defaultArgument.set(arg);
                } else {
                    throw new CLIUserException("Unexpected input: '" + arg + "'");
                }

                continue;
            }

            String key = arg.substring(prefix.length());

            if (!argumentMap.containsKey(key)) {
                throw new CLIUserException("Undefined argument: '" + arg + "'");
            }

            Argument argument = argumentMap.get(key);

            if (argument.isSwitch()) {
                ((SwitchArgument) argument).set(true);
                continue;
            }

            i++;

            if (i >= args.length) {
                throw new CLIUserException("No value provided for '" + arg + "'");
            }

            String val = args[i];

            if (val.trim().length() == 0) {
                throw new CLIUserException("Empty value provided for '" + arg + "'");
            }

            argument.set(val);
        }

        if (defaultArgument != null) {
            if (defaultArgument.isRequired() && !defaultArgument.isSet()) {
                throw new CLIUserException("Program argument missing");
            }

            defaultArgument.commit(obj);
        }

        for (Argument argument : arguments) {
            if (argument.isRequired() && !argument.isSet()) {
                throw new CLIUserException("Argument '" + argument.formatNames(prefix) + "' missing");
            }

            argument.commit(obj);
        }

        return obj;
    }

    /**
     * Retrieves the argument prefix, as set by the {@link com.burnerpat.smartcli.annotation.Arguments} annotation or by
     * calling {@link #setPrefix(String)}.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the argument prefix. If it was previously set by using the {@link com.burnerpat.smartcli.annotation.Arguments}
     * annotation, it will be overwritten.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Generates and returns the usage string using the currently active strategies.
     */
    public String usage() {
        List<Argument> switchList = new ArrayList<Argument>();
        List<Argument> argList = new ArrayList<Argument>();

        for (Argument argument : arguments) {
            if (argument.isSwitch()) {
                switchList.add(argument);
            }
            else {
                argList.add(argument);
            }
        }

        return usageBuilder.buildUsage(appName, appDocumentation, prefix, switchList, argList, defaultArgument);
    }

    /**
     * Prints the usage string to the specified {@link java.io.PrintStream}.
     *
     * @param out The print stream to print the usage string to.
     */
    public void printUsage(PrintStream out) {
        out.println(usage());
    }

    /**
     * Prints the usage string to the standard system output.
     * Calling this method is equal to calling {@code printUsage(System.out)}.
     */
    public void printUsage() {
        printUsage(System.out);
    }
}