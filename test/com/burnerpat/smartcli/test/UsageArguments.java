package com.burnerpat.smartcli.test;

import com.burnerpat.smartcli.annotation.*;

@Arguments("test_app")
public class UsageArguments {
    @Switch
    @Documentation("switch argument")
    public boolean switchField;

    @Switch
    @Name("nSF")
    @Documentation("named switch argument")
    public boolean namedSwitchField;

    @Documentation(value = "boolean argument", format = "boolean")
    public boolean booleanField;

    @Documentation("string argument")
    @Required
    public String stringField;

    @Name({"nSS", "namedString"})
    @Documentation("named string argument")
    @Required
    public String namedStringField;

    @Default
    @Name({"input_1", "input_2"})
    public String[] defaultField;
}
