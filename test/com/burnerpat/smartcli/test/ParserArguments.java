package com.burnerpat.smartcli.test;

import com.burnerpat.smartcli.annotation.Arguments;
import com.burnerpat.smartcli.annotation.Name;
import com.burnerpat.smartcli.annotation.Required;
import com.burnerpat.smartcli.annotation.Switch;

@Arguments("test_app")
public class ParserArguments {
    @Switch
    @Name("switch")
    public boolean switchArg;

    @Required
    @Name("string")
    public String stringArg;

    @Name("boolean")
    public boolean booleanArg;

    @Name("int")
    public int intArg;

    @Name("float")
    public float floatArg;

    @Name("double")
    public double doubleArg;
}
