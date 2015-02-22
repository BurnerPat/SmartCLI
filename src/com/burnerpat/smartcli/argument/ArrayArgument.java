package com.burnerpat.smartcli.argument;

import com.burnerpat.smartcli.CLIException;
import com.burnerpat.smartcli.CLIReflectionException;
import com.burnerpat.smartcli.CLIUserException;

import java.util.ArrayList;
import java.util.List;

public class ArrayArgument<T> extends Argument<T> {

    private final List<T> array = new ArrayList<T>();

    private int minSize = 0;
    private int maxSize = Integer.MAX_VALUE;

    public ArrayArgument(Converter<T> converter) {
        super(converter);
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public void set(String value) throws CLIException {
        if (array.size() >= maxSize) {
            throw new CLIUserException("Too many values provided for array");
        }

        try {
            array.add(getConverter().convert(value));
        }
        catch (NumberFormatException ex) {
            throw new CLIUserException("Invalid number format: '" + value + "'", ex);
        }
    }

    @Override
    public void commit(Object obj) throws CLIException {
        if ((isRequired() || array.size() > 0) && array.size() < minSize) {
            throw new CLIUserException("Not enough values provided for array");
        }

        try {
            if (!isSet()) {
                getField().set(obj, null);
            }
            else {
                getField().set(obj, array.toArray());
            }
        }
        catch (IllegalAccessException ex) {
            throw new CLIReflectionException(ex);
        }
    }

    @Override
    public boolean isSet() {
        return (array.size() > 0);
    }

    public void setBounds(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }
}
