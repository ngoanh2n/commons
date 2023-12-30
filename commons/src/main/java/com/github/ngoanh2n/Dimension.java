package com.github.ngoanh2n;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A copy of java.awt.Dimension.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/commons">ngoanh2n/commons</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/commons">com.github.ngoanh2n:commons</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2021
 */
@CanIgnoreReturnValue
@ParametersAreNonnullByDefault
public class Dimension {
    private int w;
    private int h;

    /**
     * Construct a new {@link Dimension} by {@code w}, {@code h}.
     *
     * @param w The width of this {@code Dimension}.
     * @param h The height of this {@code Dimension}.
     */
    public Dimension(int w, int h) {
        this.w = w;
        this.h = h;
    }

    /**
     * Construct a new {@link Dimension} by other {@link Dimension}.
     *
     * @param other The source {@code Dimension}.
     */
    public Dimension(Dimension other) {
        this(other.w, other.h);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get width of this {@code Dimension}.
     *
     * @return The width of this {@code Dimension}.
     */
    public int getWidth() {
        return w;
    }

    /**
     * Set width for this {@code Dimension}.
     *
     * @param w The new width for this {@code Dimension}.
     * @return This {@code Dimension}.
     */
    public Dimension setWidth(int w) {
        this.w = w;
        return this;
    }

    /**
     * Get height of this {@code Dimension}.
     *
     * @return The height of this {@code Dimension}.
     */
    public int getHeight() {
        return h;
    }

    /**
     * Set height for this {@code Dimension}.
     *
     * @param h The new height for this {@code Dimension}.
     * @return This {@code Dimension}.
     */
    public Dimension setHeight(int h) {
        this.h = h;
        return this;
    }

    /**
     * Increase width for this {@code Dimension} by {@code value}.
     *
     * @param value The value to be added to width of this {@code Dimension}.
     * @return This {@code Dimension}.
     */
    public Dimension incW(int value) {
        w += value;
        return this;
    }

    /**
     * Decrease width for this {@code Dimension} by {@code value}.
     *
     * @param value The value to be subtracted to width of this {@code Dimension}.
     * @return This {@code Dimension}.
     */
    public Dimension decW(int value) {
        w -= value;
        return this;
    }

    /**
     * Increase height for this {@code Dimension} by {@code value}.
     *
     * @param value The value to be added to height of this {@code Dimension}.
     * @return This {@code Dimension}.
     */
    public Dimension incH(int value) {
        h += value;
        return this;
    }

    /**
     * Decrease height for this {@code Dimension} by {@code value}.
     *
     * @param value The value to be subtracted to height of this {@code Dimension}.
     * @return This {@code Dimension}.
     */
    public Dimension decH(int value) {
        h -= value;
        return this;
    }

    /**
     * Check whether the current {@code Dimension} equals to other {@code Dimension}.
     *
     * @param other {@code Dimension} to be compared.
     * @return {@code true} if the current {@code Dimension} equals to other {@code Dimension}; {@code false} otherwise.
     */
    public boolean equals(Dimension other) {
        return w == other.w && h == other.h;
    }

    /**
     * Returns a string representation of this {@code Dimension}.
     *
     * @return A string representation of this {@code Dimension}.
     */
    @Override
    public String toString() {
        return String.format("%dx%d", w, h);
    }
}
