package com.burnerpat.smartcli;

/**
 * Exception thrown by the SmartCLI API in any case of user (input) caused error.
 */
public class CLIUserException extends CLIException {
    public CLIUserException() {
        super();
    }

    public CLIUserException(String message) {
        super(message);
    }

    public CLIUserException(Throwable cause) {
        super(cause);
    }

    public CLIUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
