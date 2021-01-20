package io.github.ngoanh2n;

/**
 * Base runtime exception for io.github.ngoanh2n
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-06
 */
public class NgRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 635198548542132913L;

    public NgRuntimeException(String message) {
        super(message);
    }

    public NgRuntimeException(Throwable cause) {
        super(cause);
    }

    public NgRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
