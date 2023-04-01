package com.github.ngoanh2n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class YamlDataTest {
    private final String resource0 = "com/github/ngoanh2n/Data0.yml";
    private final String resource1 = "com/github/ngoanh2n/Data1.yml";
    private final String resource2 = "com/github/ngoanh2n/Data2.yml";
    private final String resource3 = "com/github/ngoanh2n/Data3.yml";

    private final String file0 = "src/test/resources/com/github/ngoanh2n/Data0.yml";
    private final String file1 = "src/test/resources/com/github/ngoanh2n/Data1.yml";
    private final String file2 = "src/test/resources/com/github/ngoanh2n/Data2.yml";
    private final String file3 = "src/test/resources/com/github/ngoanh2n/Data3.yml";

    @Test
    void toMap() {
        // Empty content file
        Assertions.assertThrows(RuntimeError.class, () -> YamlData.toMapFromResource(resource0));
        Assertions.assertThrows(RuntimeError.class, () -> YamlData.toMapFromFile(file0));

        // Try read list as one
        Assertions.assertThrows(RuntimeError.class, () -> YamlData.toMapFromResource(resource2));
        Assertions.assertThrows(RuntimeError.class, () -> YamlData.toMapFromFile(file2));

        Assertions.assertEquals(2, YamlData.toMapFromResource(resource1).size());
        Assertions.assertEquals(2, YamlData.toMapFromFile(file1).size());
    }

    @Test
    void toMaps() {
        Assertions.assertEquals(1, YamlData.toMapsFromResource(resource1).size());
        Assertions.assertEquals(2, YamlData.toMapsFromResource(resource2).size());

        Assertions.assertEquals(1, YamlData.toMapsFromFile(file1).size());
        Assertions.assertEquals(2, YamlData.toMapsFromFile(file2).size());
    }

    @Test
    void toModel() {
        // No file or resource
        Assertions.assertThrows(RuntimeError.class, () -> new Data.Data2().toModel());

        Data.Data1 fromResource = new Data.Data1().fromResource(resource1).toModel();
        Assertions.assertEquals("v1", fromResource.getK1());
        Assertions.assertEquals("v2", fromResource.getK2());

        Data.Data1 fromFile = new Data.Data1().fromFile(file1).toModel();
        Assertions.assertEquals("v1", fromFile.getK1());
        Assertions.assertEquals("v2", fromFile.getK2());
    }

    @Test
    void toModels() {
        // No file or resource
        Assertions.assertThrows(RuntimeError.class, () -> new Data.Data3().toModels());

        List<Data.Data1> fromResource = new Data.Data1().fromResource(resource2).toModels();
        Assertions.assertEquals(2, fromResource.size());

        List<Data.Data1> fromFile = new Data.Data1().fromFile(file2).toModels();
        Assertions.assertEquals(2, fromFile.size());
    }

    @Test
    void toModelInner() {
        Data.Data3 fromResource = new Data.Data3().fromResource(resource3).toModel();
        Assertions.assertEquals("v5", fromResource.getK5());
        Assertions.assertEquals("v3", fromResource.getK6().getK3());
        Assertions.assertEquals(2, fromResource.getK6().getK4().size());

        Data.Data3 fromFile = new Data.Data3().fromFile(file3).toModel();
        Assertions.assertEquals("v5", fromFile.getK5());
        Assertions.assertEquals("v3", fromFile.getK6().getK3());
        Assertions.assertEquals(2, fromFile.getK6().getK4().size());
    }

    @Test
    void toModelUsingAnnotation() {
        Data.Data4 fromResource = new Data.Data4().toModel();
        Assertions.assertEquals("v1", fromResource.getK1());
        Assertions.assertEquals("v2", fromResource.getK2());

        Data.Data5 fromFile = new Data.Data5().toModel();
        Assertions.assertEquals("v1", fromFile.getK1());
        Assertions.assertEquals("v2", fromFile.getK2());
    }
}
