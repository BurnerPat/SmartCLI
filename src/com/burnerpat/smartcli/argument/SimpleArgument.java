package com.burnerpat.smartcli.argument;

import com.burnerpat.smartcli.CLIException;
import com.burnerpat.smartcli.CLIReflectionException;
import com.burnerpat.smartcli.CLIUserException;

public class SimpleArgument<T> extends Argument<T> {

    protected T data;
    protected boolean locked;

    public SimpleArgument(Converter<T> converter) {
        super(converter);

        locked = false;
    }

    @Override
    public void set(String value) throws CLIException {
        if (locked) {
            throw new CLIUserException("Argument cannot be set twice");
        }

        locked = true;
        data = getConverter().convert(value);
    }

    @Override
    public void commit(Object obj) throws CLIException {
        try {
            if (!isSet()) {
                getField().set(obj, null);
            } else {
                getField().set(obj, data);
            }
        }
        catch (IllegalAccessException ex) {
            throw new CLIReflectionException(ex);
        }
    }

    @Override
    public boolean isSet() {
        return locked;
    }
}
