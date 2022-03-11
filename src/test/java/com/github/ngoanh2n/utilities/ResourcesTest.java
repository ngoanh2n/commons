package com.github.ngoanh2n.utilities;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourcesTest {

    private final String validResource = "com/github/ngoanh2n/commons/file01.yml";
    private final String invalidResource = "io/github/ngoanh2n/commons/file02.yml";

    @Test
    @Order(1)
    void existsTest() {
        assertThrows(NullPointerException.class, () -> Resources.exists(null));
        assertThrows(IllegalArgumentException.class, () -> Resources.exists(""));
        assertTrue(Resources.exists(validResource));
    }

    @Test
    @Order(2)
    void pathTest() {
        assertNotNull(Resources.path(validResource));
        assertThrows(ResourceNotFound.class, () -> Resources.path(invalidResource));
    }

    @Test
    @Order(3)
    void fileTest() {
        assertNotNull(Resources.file(validResource));
    }

    @Test
    @Order(4)
    void inputStreamTest() {
        assertThrows(ResourceNotFound.class, () -> Resources.inputStream(invalidResource));
        assertNotNull(Resources.inputStream(validResource));
    }

    @Test
    @Order(5)
    void fileToStringTest() {
        assertThrows(ResourceNotFound.class, () -> Resources.fileToString(invalidResource));
        assertNotNull(Resources.fileToString(validResource));
    }
}
