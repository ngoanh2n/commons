package com.github.ngoanh2n;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author ngoanh2n
 */
public class ResourcesTest {
    private final String valid = "testng.css";
    private final String invalid = "testing.css";

    @Test
    void getFile() {
        Assert.assertNotNull(Resources.getFile(valid));
        Assert.assertThrows(RuntimeError.class, () -> Resources.getFile(invalid));
    }

    @Test
    void getPath() {
        Assert.assertNotNull(Resources.getPath(valid));
    }

    @Test
    void getInputStream() {
        Assert.assertNotNull(Resources.getInputStream(valid));
    }

    @Test
    void getContent() {
        Assert.assertNotNull(Resources.getContent(valid));
    }
}
