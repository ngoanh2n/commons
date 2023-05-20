package com.github.ngoanh2n;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class PropTest {
    private static final String NAME = "prop";
    private static final String PASSED_VALUE = "passed";
    private static final String DEFAULT_VALUE = "default";
    private static final String ASSIGNED_VALUE = "assigned";

    @BeforeEach
    void initProperty() {
        System.setProperty(NAME, PASSED_VALUE);
    }

    @Test
    void twoArgs() {
        Prop<String> prop = Prop.string(NAME);

        Assertions.assertEquals(PASSED_VALUE, System.getProperty(NAME));
        Assertions.assertEquals(PASSED_VALUE, prop.getValue());
        Assertions.assertEquals(PASSED_VALUE, prop.getDefaultValue());

        prop.setValue(ASSIGNED_VALUE);

        Assertions.assertEquals(ASSIGNED_VALUE, System.getProperty(NAME));
        Assertions.assertEquals(ASSIGNED_VALUE, prop.getValue());
        Assertions.assertEquals(ASSIGNED_VALUE, Prop.string(NAME).getValue());
        Assertions.assertEquals(PASSED_VALUE, prop.getDefaultValue());
    }

    @Test
    void threeArgs() {
        Prop<String> prop = Prop.string(NAME, DEFAULT_VALUE);

        Assertions.assertEquals(PASSED_VALUE, System.getProperty(NAME));
        Assertions.assertEquals(PASSED_VALUE, prop.getValue());
        Assertions.assertEquals(DEFAULT_VALUE, prop.getDefaultValue());

        prop.setValue(ASSIGNED_VALUE);

        Assertions.assertEquals(ASSIGNED_VALUE, System.getProperty(NAME));
        Assertions.assertEquals(ASSIGNED_VALUE, prop.getValue());
        Assertions.assertEquals(ASSIGNED_VALUE, Prop.string(NAME).getValue());
        Assertions.assertEquals(DEFAULT_VALUE, prop.getDefaultValue());
    }

    @AfterEach
    void clearProperty() {
        Prop<String> prop = Prop.string(NAME);
        prop.clearValue();

        Assertions.assertNull(System.getProperty(NAME));
        Assertions.assertEquals(ASSIGNED_VALUE, prop.getValue());
        Assertions.assertNull(Prop.string(NAME).getValue());
    }
}
