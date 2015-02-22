package com.burnerpat.smartcli.argument;

import com.burnerpat.smartcli.CLIException;

public class SwitchArgument extends SimpleArgument<Boolean> {
    public SwitchArgument() {
        super(Converter.BOOLEAN_CONVERTER);

        data = false;
    }

    public void set(boolean value) {
        locked = true;
        data = value;
    }

    @Override
    public void set(String value) throws CLIException {
        throw new UnsupportedOperationException("Cannot call set(String) on a switch argument");
    }

    @Override
    public boolean isSet() {
        return data;
    }

    @Override
    public boolean isSwitch() {
        return true;
    }

    @Override
    public void setRequired(boolean isRequired) {
        if (isRequired) {
            throw new IllegalArgumentException("A switch argument must not be required");
        }
    }

    @Override
    public boolean isRequired() {
        return false;
    }
}
