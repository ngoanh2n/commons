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
        System.setProperty("prop", "passedValue");
    }

    @Test
    void twoArgs() {
        Prop<String> prop = new Prop<>("prop", String.class);

        Assertions.assertEquals("passedValue", prop.getValue());
        Assertions.assertEquals("passedValue", prop.getDefaultValue());

        prop.setValue("assignedValue");
        Assertions.assertEquals("assignedValue", prop.getValue());
        Assertions.assertEquals("passedValue", prop.getDefaultValue());
    }

    @Test
    void threeArgs() {
        Prop<String> prop = new Prop<>("prop", String.class, "defaultValue");

        Assertions.assertEquals("passedValue", prop.getValue());
        Assertions.assertEquals("defaultValue", prop.getDefaultValue());

        prop.setValue("assignedValue");
        Assertions.assertEquals("assignedValue", prop.getValue());
        Assertions.assertEquals("defaultValue", prop.getDefaultValue());
    }
}
