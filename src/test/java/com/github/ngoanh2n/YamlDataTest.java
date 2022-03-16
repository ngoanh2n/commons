package com.github.ngoanh2n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
public class YamlDataTest {

    private final String resource0 = "com/github/ngoanh2n/Data0.yml";
    private final String resource1 = "com/github/ngoanh2n/Data1.yml";
    private final String resource2 = "com/github/ngoanh2n/Data2.yml";
    private final String resource3 = "com/github/ngoanh2n/Data3.yml";

    @Test
    void toMap() {
        Assertions.assertThrows(RuntimeError.class, () -> YamlData.toMap(resource0));
        Assertions.assertEquals(2, YamlData.toMap(resource1).size());
        Assertions.assertThrows(RuntimeError.class, () -> YamlData.toMap(resource2));
    }

    @Test
    void toMaps() {
        Assertions.assertEquals(1, YamlData.toMaps(resource1).size());
        Assertions.assertEquals(2, YamlData.toMaps(resource2).size());
    }

    @Test
    void toModel() {
        Data.Data1 result = new Data.Data1().fromResource(resource1).toModel();
        Assertions.assertEquals("v1", result.getK1());
        Assertions.assertEquals("v2", result.getK2());
    }

    @Test
    void toModels() {
        List<Data.Data1> results = new Data.Data1().fromResource(resource2).toModels();
        Assertions.assertEquals(2, results.size());
    }

    @Test
    void toModelWrapper() {
        Data.Data3 data3 = new Data.Data3().fromResource(resource3).toModel();
        Assertions.assertEquals("v5", data3.getK5());
        Assertions.assertEquals("v3", data3.getK6().getK3());
        Assertions.assertEquals(2, data3.getK6().getK4().size());
    }

    @Test
    void toModelUsingAnnotation() {
        Data.Data4 data4 = new Data.Data4().toModel();
        Assertions.assertEquals("v1", data4.getK1());
        Assertions.assertEquals("v2", data4.getK2());
    }

    @Test
    void noneResource() {
        Assertions.assertThrows(ResourceNotFound.class, () -> new Data.Data2().toModel());
        Assertions.assertThrows(ResourceNotFound.class, () -> new Data.Data3().toModels());
    }
}
