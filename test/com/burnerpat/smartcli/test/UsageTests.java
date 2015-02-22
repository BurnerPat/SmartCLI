package com.burnerpat.smartcli.test;

import com.burnerpat.smartcli.SmartCLI;
import org.junit.Test;

public class UsageTests {
    @Test
    public void basicUsageTest() {
        SmartCLI<UsageArguments> cli = new SmartCLI<UsageArguments>(UsageArguments.class);
        System.out.println(cli.usage());
    }
}
