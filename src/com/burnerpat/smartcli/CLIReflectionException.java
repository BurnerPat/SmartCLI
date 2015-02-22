package com.burnerpat.smartcli;

/**
 * This exception will be thrown if the SmartCLI API encounters and Java Reflections errors.
 */
public class CLIReflectionException extends CLIException {
    public CLIReflectionException() {
        super();
    }

    public CLIReflectionException(String message) {
        super(message);
    }

    public CLIReflectionException(Throwable cause) {
        super(cause);
    }

    public CLIReflectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
