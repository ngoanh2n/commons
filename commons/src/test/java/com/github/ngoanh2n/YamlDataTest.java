package com.github.ngoanh2n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author ngoanh2n
 */
public class YamlDataTest {
    private final String RESOURCE_0 = "com/github/ngoanh2n/Data0.yml";
    private final String RESOURCE_1 = "com/github/ngoanh2n/Data1.yml";
    private final String RESOURCE_2 = "com/github/ngoanh2n/Data2.yml";
    private final String RESOURCE_3 = "com/github/ngoanh2n/Data3.yml";

    private final String FILE_0 = "src/test/resources/com/github/ngoanh2n/Data0.yml";
    private final String FILE_1 = "src/test/resources/com/github/ngoanh2n/Data1.yml";
    private final String FILE_2 = "src/test/resources/com/github/ngoanh2n/Data2.yml";
    private final String FILE_3 = "src/test/resources/com/github/ngoanh2n/Data3.yml";

    @Test
    void toMap() {
        // Empty content file
        Assertions.assertThrows(RuntimeError.class, () -> YamlData.toMapFromResource(RESOURCE_0));
        Assertions.assertThrows(RuntimeError.class, () -> YamlData.toMapFromFile(FILE_0));

        // Try read list as one
        Assertions.assertThrows(RuntimeError.class, () -> YamlData.toMapFromResource(RESOURCE_2));
        Assertions.assertThrows(RuntimeError.class, () -> YamlData.toMapFromFile(FILE_2));

        Assertions.assertEquals(2, YamlData.toMapFromResource(RESOURCE_1).size());
        Assertions.assertEquals(2, YamlData.toMapFromFile(FILE_1).size());
    }

    @Test
    void toMaps() {
        Assertions.assertEquals(1, YamlData.toMapsFromResource(RESOURCE_1).size());
        Assertions.assertEquals(2, YamlData.toMapsFromResource(RESOURCE_2).size());

        Assertions.assertEquals(1, YamlData.toMapsFromFile(FILE_1).size());
        Assertions.assertEquals(2, YamlData.toMapsFromFile(FILE_2).size());
    }

    @Test
    void toModel() {
        // No file or resource
        Assertions.assertThrows(RuntimeError.class, () -> new Data.Data2().toModel());

        Data.Data1 fromResource = new Data.Data1().fromResource(RESOURCE_1).toModel();
        Assertions.assertEquals("v1", fromResource.getK1());
        Assertions.assertEquals("v2", fromResource.getK2());

        Data.Data1 fromFile = new Data.Data1().fromFile(FILE_1).toModel();
        Assertions.assertEquals("v1", fromFile.getK1());
        Assertions.assertEquals("v2", fromFile.getK2());
    }

    @Test
    void toModels() {
        // No file or resource
        Assertions.assertThrows(RuntimeError.class, () -> new Data.Data3().toModels());

        List<Data.Data1> fromResource = new Data.Data1().fromResource(RESOURCE_2).toModels();
        Assertions.assertEquals(2, fromResource.size());

        List<Data.Data1> fromFile = new Data.Data1().fromFile(FILE_2).toModels();
        Assertions.assertEquals(2, fromFile.size());
    }

    @Test
    void toModelInner() {
        Data.Data3 fromResource = new Data.Data3().fromResource(RESOURCE_3).toModel();
        Assertions.assertEquals("v5", fromResource.getK5());
        Assertions.assertEquals("v3", fromResource.getK6().getK3());
        Assertions.assertEquals(2, fromResource.getK6().getK4().size());

        Data.Data3 fromFile = new Data.Data3().fromFile(FILE_3).toModel();
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
