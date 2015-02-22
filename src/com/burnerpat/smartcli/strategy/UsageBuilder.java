package com.burnerpat.smartcli.strategy;

import com.burnerpat.smartcli.argument.Argument;

import java.util.List;

/**
 * Interface for implementations that generate usage strings.
 */
public interface UsageBuilder {

    /**
     * Builds a usage string using the given parameters.
     *
     * @param appName The name of the application.
     * @param appDocumentation A short string describing purpose and functionality of the application.
     * @param prefix The argument prefix preceding every argument name.
     * @param switches The list of switch (or option) arguments. The switches are not guaranteed to be sorted in any way.
     * @param arguments The list of arguments. The arguments are not guaranteed to be sorted in any way.
     * @param defaultArgument The default argument object.
     *
     * @return The usage string for the given parameters.
     */
    public String buildUsage(String appName, String appDocumentation, String prefix, List<Argument> switches, List<Argument> arguments, Argument defaultArgument);
}
