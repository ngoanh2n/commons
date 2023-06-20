package com.github.ngoanh2n;

/**
 * Base runtime exception for {@code com.github.ngoanh2n}.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/commons">ngoanh2n/commons</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/commons">com.github.ngoanh2n:commons</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2019
 */
public class RuntimeError extends RuntimeException {
    /**
     * Construct a new runtime exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public RuntimeError(String message) {
        super(message);
    }

    /**
     * Construct a new runtime exception with the specified cause and a detail message.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public RuntimeError(Throwable cause) {
        super(cause);
    }

    /**
     * Construct a new runtime exception with the specified detail message and cause.
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
