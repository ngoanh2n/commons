package io.github.ngoanh2n.commons;

import io.github.ngoanh2n.H2nException;

/**
 * Throws when could not find Java Resources
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2020-01-16
 */
public class ResourceNotFound extends H2nException {

    public ResourceNotFound(String resourceName) {
        super("Resource not found: " + resourceName);
    }

    public ResourceNotFound(Throwable cause) {
        super(cause);
    }
}
