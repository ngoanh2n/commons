package io.github.ngoanh2n;

/**
 * Base runtime exception for io.github.ngoanh2n
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-06
 */
public class H2nException extends RuntimeException {

    private static final long serialVersionUID = 635198548542132913L;

    public H2nException(String message) {
        super(message);
    }

    public H2nException(Throwable cause) {
        super(cause);
    }

    public H2nException(String message, Throwable cause) {
        super(message, cause);
    }
}
