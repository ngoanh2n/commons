package com.github.ngoanh2n;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourcesTest {

    private final String validResource = "com/github/ngoanh2n/file01.yml";
    private final String invalidResource = "com/github/ngoanh2n/file02.yml";

    @Test
    @Order(1)
    void getPath() {
        assertNotNull(Resources.getPath(validResource));
        assertThrows(ResourceNotFound.class, () -> Resources.getPath(invalidResource));
    }

    @Test
    @Order(2)
    void getFile() {
        assertNotNull(Resources.getFile(validResource));
    }

    @Test
    @Order(3)
    void getInputStream() {
        assertThrows(ResourceNotFound.class, () -> Resources.getInputStream(invalidResource));
        assertNotNull(Resources.getInputStream(validResource));
    }

    @Test
    @Order(4)
    void getContent() {
        assertThrows(ResourceNotFound.class, () -> Resources.getContent(invalidResource));
        assertNotNull(Resources.getContent(validResource));
    }
}
