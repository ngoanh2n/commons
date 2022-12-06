package com.github.ngoanh2n;

import org.junit.jupiter.api.*;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourceTest {
    private final String valid = "com/github/ngoanh2n/Data1.yml";
    private final String invalid = "com/github/ngoanh2n/Data.yml";

    @Test
    @Order(1)
    void getFile() {
        Assertions.assertNotNull(Resource.getFile(valid));
        Assertions.assertThrows(RuntimeError.class, () -> Resource.getPath(invalid));
    }

    @Test
    @Order(2)
    void getPath() {
        Assertions.assertNotNull(Resource.getPath(valid));
    }

    @Test
    @Order(3)
    void getInputStream() {
        Assertions.assertNotNull(Resource.getInputStream(valid));
    }

    @Test
    @Order(4)
    void getContent() {
        Assertions.assertNotNull(Resource.getContent(valid));
    }
}
