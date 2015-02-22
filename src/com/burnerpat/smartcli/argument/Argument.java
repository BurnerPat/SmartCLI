package com.burnerpat.smartcli.argument;

import com.burnerpat.smartcli.CLIException;

import java.lang.reflect.Field;

/**
 * Base class of every argument object.
 *
 * @param <T> The type of the data value of the argument.
 */
public abstract class Argument<T> {
    private Field field;

    private String[] names;
    private String helpText;
    private String formatHint;

    private boolean isRequired;

    private final Converter<T> converter;

    /**
     * Initializes the object using the given {@link com.burnerpat.smartcli.argument.Converter} object.
     */
    public Argument(Converter<T> converter) {
        this.converter = converter;
    }

    public Converter<T> getConverter() {
        return converter;
    }

    /**
     * Sets the internal value of the argument, but does not commit the changes to the field of the argument.
     * Implementations should check the format of the value and convert it to the target format.
     *
     * @param value The value of the argument as provided by the command line.
     *
     * @throws CLIException In any case of format or conversion error.
     */
    public abstract void set(String value) throws CLIException;

    /**
     * Commits the value to the field of the argument using Java Reflections.
     *
     * @param obj The object holding the field.
     *
     * @throws CLIException In any case of Java Reflections error.
     */
    public abstract void commit(Object obj) throws CLIException;

    /**
     * Returns {@code true}, if the argument value has been set by calling {@link #set(String)} with a valid value.
     */
    public abstract boolean isSet();

    public ArgumentType getType() {
        return converter.getType();
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public String getFormatHint() {
        return formatHint;
    }

    public void setFormatHint(String formatHint) {
        this.formatHint = formatHint;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isSwitch() {
        return false;
    }

    /**
     * Convenience method that returns a string containing every name for the argument.
     *
     * @param prefix The argument prefix preceding every argument name.
     */
    public String formatNames(String prefix) {
        StringBuilder nameBuilder = new StringBuilder();

        for (int j = 0; j < getNames().length; j++) {
            if (j > 0) {
                nameBuilder.append(", ");
            }

            nameBuilder.append(prefix);
            nameBuilder.append(getNames()[j]);
        }

        return nameBuilder.toString();
    }
}
