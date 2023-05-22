package com.github.ngoanh2n;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class PropertyTest {
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
        Property<String> property = Property.ofString(NAME);

        Assertions.assertEquals(PASSED_VALUE, System.getProperty(NAME));
        Assertions.assertEquals(PASSED_VALUE, property.getValue());
        Assertions.assertEquals(PASSED_VALUE, property.getDefaultValue());

        property.setValue(ASSIGNED_VALUE);

        Assertions.assertEquals(ASSIGNED_VALUE, System.getProperty(NAME));
        Assertions.assertEquals(ASSIGNED_VALUE, property.getValue());
        Assertions.assertEquals(ASSIGNED_VALUE, Property.ofString(NAME).getValue());
        Assertions.assertEquals(PASSED_VALUE, property.getDefaultValue());
    }

    @Test
    void threeArgs() {
        Property<String> property = Property.ofString(NAME, DEFAULT_VALUE);

        Assertions.assertEquals(PASSED_VALUE, System.getProperty(NAME));
        Assertions.assertEquals(PASSED_VALUE, property.getValue());
        Assertions.assertEquals(DEFAULT_VALUE, property.getDefaultValue());

        property.setValue(ASSIGNED_VALUE);

        Assertions.assertEquals(ASSIGNED_VALUE, System.getProperty(NAME));
        Assertions.assertEquals(ASSIGNED_VALUE, property.getValue());
        Assertions.assertEquals(ASSIGNED_VALUE, Property.ofString(NAME).getValue());
        Assertions.assertEquals(DEFAULT_VALUE, property.getDefaultValue());
    }

    @AfterEach
    void clearProperty() {
        Property<String> property = Property.ofString(NAME);
        property.clearValue();

        Assertions.assertNull(System.getProperty(NAME));
        Assertions.assertEquals(ASSIGNED_VALUE, property.getValue());
        Assertions.assertNull(Property.ofString(NAME).getValue());
    }
}
