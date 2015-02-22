package com.burnerpat.smartcli.argument;

/**
 * Class used to convert a string value to a specified format.
 *
 * The class holds several default implementations as static fields for common data types.
 *
 * @param <T> The format the string will be converted into.
 */
public abstract class Converter<T> {
    /**
     * Converts the given string into the format as specified by {@link T}
     */
    public abstract T convert(String str);

    /**
     * Returns the argument type as known by the SmartCLI API.
     */
    public abstract ArgumentType getType();

    public static final Converter<Boolean> BOOLEAN_CONVERTER = new Converter<Boolean>() {
        @Override
        public Boolean convert(String str) {
            return Boolean.parseBoolean(str);
        }

        @Override
        public ArgumentType getType() {
            return ArgumentType.BOOLEAN;
        }
    };

    public static final Converter<Integer> INTEGER_CONVERTER = new Converter<Integer>() {
        @Override
        public Integer convert(String str) {
            return Integer.parseInt(str);
        }

        @Override
        public ArgumentType getType() {
            return ArgumentType.INTEGER;
        }
    };

    public static final Converter<Float> FLOAT_CONVERTER = new Converter<Float>() {
        @Override
        public Float convert(String str) {
            return Float.parseFloat(str);
        }

        @Override
        public ArgumentType getType() {
            return ArgumentType.FLOAT;
        }
    };

    public static final Converter<Double> DOUBLE_CONVERTER = new Converter<Double>() {
        @Override
        public Double convert(String str) {
            return Double.parseDouble(str);
        }

        @Override
        public ArgumentType getType() {
            return ArgumentType.DOUBLE;
        }
    };

    public static final Converter<String> STRING_CONVERTER = new Converter<String>() {
        @Override
        public String convert(String str) {
            return str;
        }

        @Override
        public ArgumentType getType() {
            return ArgumentType.STRING;
        }
    };
}
