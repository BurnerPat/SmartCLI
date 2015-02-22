package com.burnerpat.smartcli;

/**
 * Super class for every exception thrown by the SmartCLI API.
 */
public class CLIException extends Exception {
    public CLIException() {
        super();
    }

    public CLIException(String message) {
        super(message);
    }

    public CLIException(Throwable cause) {
        super(cause);
    }

    public CLIException(String message, Throwable cause) {
        super(message, cause);
    }
}
