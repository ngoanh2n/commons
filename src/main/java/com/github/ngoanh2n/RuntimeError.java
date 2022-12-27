package com.github.ngoanh2n;

/**
 * Base runtime exception for com.github.ngoanh2n
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-06
 */
public class RuntimeError extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public RuntimeError(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified cause and a detail message.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public RuntimeError(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public RuntimeError(String message, Throwable cause) {
        super(message, cause);
    }
}
