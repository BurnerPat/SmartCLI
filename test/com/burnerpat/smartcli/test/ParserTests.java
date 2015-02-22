package com.burnerpat.smartcli.test;

import com.burnerpat.smartcli.CLIException;
import com.burnerpat.smartcli.SmartCLI;
import org.junit.Test;
import static org.junit.Assume.*;
import static org.junit.Assert.*;

public class ParserTests {
    @Test
    public void basicParserTest() {
        String[] args = {"-switch", "-string", "Hello World!", "-int", "-1337", "-float", "13.37", "-double", "13.37", "-boolean", "true"};
        SmartCLI<ParserArguments> cli = new SmartCLI<ParserArguments>(ParserArguments.class);
        ParserArguments obj = new ParserArguments();

        try {
            cli.parse(args, obj);
        }
        catch (CLIException ex) {
            assumeNoException(ex);
        }

        assertTrue(obj.switchArg);
        assertTrue(obj.booleanArg);
        assertTrue(obj.intArg == -1337);
        assertTrue(obj.floatArg == 13.37f);
        assertTrue(obj.doubleArg == 13.37);
        assertTrue(obj.stringArg.equals("Hello World!"));
    }

    @Test
    public void missingArgumentTest() {
        String[] args = {"-switch"};
        SmartCLI<ParserArguments> cli = new SmartCLI<ParserArguments>(ParserArguments.class);
        ParserArguments obj = new ParserArguments();

        try {
            cli.parse(args, obj);
        }
        catch (CLIException ex) {
            return;
        }

        assertTrue(false);
    }
}
