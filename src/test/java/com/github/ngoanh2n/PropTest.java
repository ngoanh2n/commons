package com.github.ngoanh2n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2022-04-11
 */
public class PropTest {
    @BeforeEach
    void setValue() {
        System.setProperty("prop", "passed");
    }

    @Test
    void twoArgs() {
        Prop<String> prop = new Prop<>("prop", String.class);

        Assertions.assertEquals("passed", prop.getValue());
        Assertions.assertEquals("passed", prop.getDefaultValue());

        prop.setValue("assigned");
        Assertions.assertEquals("assigned", prop.getValue());
        Assertions.assertEquals("passed", prop.getDefaultValue());
    }

    @Test
    void threeArgs() {
        Prop<String> prop = new Prop<>("prop", String.class, "default");

        Assertions.assertEquals("passed", prop.getValue());
        Assertions.assertEquals("default", prop.getDefaultValue());

        prop.setValue("assigned");
        Assertions.assertEquals("assigned", prop.getValue());
        Assertions.assertEquals("default", prop.getDefaultValue());
    }
}
