package com.github.ngoanh2n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
public class YamlDataTest {

    private final String r1 = "com/github/ngoanh2n/Data1.yml";
    private final String r2 = "com/github/ngoanh2n/Data2.yml";
    private final String r3 = "com/github/ngoanh2n/Data3.yml";

    @Test
    void toMap() {
        Map<String, Object> result = YamlData.toMap(r1);
        Assertions.assertEquals("v1", result.get("f1"));
        Assertions.assertEquals("v2", result.get("f2"));
    }

    @Test
    void toModel() {
        Data.Data1 result = new Data.Data1().fromResource(r1).toModel();
        Assertions.assertEquals("v1", result.getF1());
        Assertions.assertEquals("v2", result.getF2());
    }

    @Test
    void toStream() {
        List<Data.Data1> results = new Data.Data1().fromResource(r2).toModels();
        Assertions.assertEquals(2, results.size());
    }

    @Test
    void toModelWrapper() {
        Data.Data3 result = new Data.Data3().fromResource(r3).toModel();
        Assertions.assertEquals("v5", result.getF5());
        Assertions.assertEquals("v3", result.getF6().getF3());
        Assertions.assertEquals(2, result.getF6().getF4().size());
    }

    @Test
    void toModelUsingAnnotation() {
        Data.Data4 result = new Data.Data4().toModel();
        Assertions.assertEquals("v1", result.getF1());
        Assertions.assertEquals("v2", result.getF2());
    }

    @Test
    void noneResource() {
        Assertions.assertThrows(ResourceNotFound.class, () -> new Data.Data2().toModel());
        Assertions.assertThrows(ResourceNotFound.class, () -> new Data.Data3().toModels());
    }
}
