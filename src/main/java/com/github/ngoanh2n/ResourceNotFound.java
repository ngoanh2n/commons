package com.github.ngoanh2n;

/**
 * Throws when could not find Java Resources
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
public class ResourceNotFound extends RuntimeError {
    public ResourceNotFound(String message) {
        super(message);
    }

    public ResourceNotFound(Throwable cause) {
        super(cause);
    }
}
