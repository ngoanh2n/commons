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
     */
    public RuntimeError(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified cause and a detail message.
     */
    public RuntimeError(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     */
    public RuntimeError(String message, Throwable cause) {
        super(message, cause);
    }
}
