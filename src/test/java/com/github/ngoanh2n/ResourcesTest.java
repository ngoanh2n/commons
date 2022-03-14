package com.github.ngoanh2n;

import org.junit.jupiter.api.*;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourcesTest {

    private final String valid = "com/github/ngoanh2n/Data1.yml";
    private final String invalid = "com/github/ngoanh2n/Data.yml";

    @Test
    @Order(1)
    void getFile() {
        Assertions.assertNotNull(Resources.getFile(valid));
        Assertions.assertThrows(ResourceNotFound.class, () -> Resources.getPath(invalid));
    }

    @Test
    @Order(2)
    void getPath() {
        Assertions.assertNotNull(Resources.getPath(valid));
    }

    @Test
    @Order(3)
    void getInputStream() {
        Assertions.assertNotNull(Resources.getInputStream(valid));
    }

    @Test
    @Order(4)
    void getContent() {
        Assertions.assertNotNull(Resources.getContent(valid));
    }
}
