package com.github.ngoanh2n;

/**
 * Base runtime exception for com.github.ngoanh2n
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-06
 */
public class RuntimeError extends RuntimeException {

    private static final long serialVersionUID = 635198548542132913L;

    public RuntimeError(String message) {
        super(message);
    }

    public RuntimeError(Throwable cause) {
        super(cause);
    }

    public RuntimeError(String message, Throwable cause) {
        super(message, cause);
    }
}
