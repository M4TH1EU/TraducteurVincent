package ch.m4th1eu.traducteur_vincent.util;

public class FailException extends RuntimeException {
    /**
     * Normal constructor
     *
     * @param message The message
     */
    public FailException(String message) {
        super("Ups ! Looks like you failed : " + message);
    }

    /**
     * Constructor with a cause
     *
     * @param message The message
     * @param cause   The cause
     */
    public FailException(String message, Throwable cause) {
        super("Ups ! Looks like you failed : " + message, cause);
    }
}
