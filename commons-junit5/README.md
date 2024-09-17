[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons-junit5/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons-junit5)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/commons-junit5/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/commons-junit5)
[![badge-jdk](https://img.shields.io/badge/jdk-8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

**Table of Contents**
<!-- TOC -->
* [Declaration](#declaration)
  * [Gradle](#gradle)
  * [Maven](#maven)
* [Usage](#usage)
  * [WebDriverJUnit5](#webdriverjunit5)
  * [@EnabledIfProperty](#enabledifproperty)
    * [System Property](#system-property)
  * [@SetProperty](#setproperty)
    * [Test Class](#test-class)
    * [Test Method](#test-method)
<!-- TOC -->

# Declaration
## Gradle
Add to `build.gradle`.
```gradle
implementation("com.github.ngoanh2n:commons-junit5:1.7.0")
```

## Maven
Add to `pom.xml`.
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>commons-junit5</artifactId>
    <version>1.7.0</version>
</dependency>
```

# Usage
## WebDriverJUnit5
Lookup `WebDriver` from the current JUnit5 test using `org.junit.jupiter.api.extension.InvocationInterceptor`.

**Step 1:** Create a class that extends `com.github.ngoanh2n.WebDriverJUnit5`
```java
package com.company.project.impl;

import com.github.ngoanh2n.WebDriverJUnit5;

public class MyWebDriverLookup extends WebDriverJUnit5 {
    public WebDriver getWebDriver() {
        if (invocationContext != null) {
            lookupDriver(invocationContext, WebDriverJUnit5.BO);
        }
        return driver;
    }
}
```

**Step 2:** Create a provider configuration file
- Location: `resources/META-INF/services/`
- Name: `org.junit.jupiter.api.extension.Extension`
- Content: `com.company.project.impl.MyWebDriverLookup`

**Step 3:** Enable extensions auto-detection in `resources/junit-platform.properties`
```
junit.jupiter.extensions.autodetection.enabled=true
```

## @EnabledIfProperty
Signal that the annotated test class or test method is enabled.
- If there are multiple `@EnabledIfProperty`, then it will become `AND` condition to enable that test
- If `@EnabledIfProperty` has multiple values, that `@EnabledIfProperty` will be satisfied when value of JVM system property matched one of `@EnabledIfProperty` value
```java
import com.github.ngoanh2n.EnabledIfProperty;
import org.junit.jupiter.api.Test;

public class SeleniumTest {
  @Test
  @EnabledIfProperty(name = "os", value = {"macos", "windows"})
  @EnabledIfProperty(name = "browser", value = "opera")
  public void operaTest() {}
  
  @Test
  @EnabledIfProperty(name = "os", value = {"macos", "windows", "linux"})
  @EnabledIfProperty(name = "browser", value = "chrome")
  public void chromeTest() {}
}
```
> ./gradlew test --tests SeleniumTest -Dos=macos -Dbrowser=opera<br>
Tests will be enabled: `SeleniumTest.operaTest()`

> ./gradlew test --tests SeleniumTest -Dos=windows -Dbrowser=[chrome,opera]<br>
Tests will be enabled: `SeleniumTest.operaTest()` and `SeleniumTest.chromeTest()`

### System Property
- `ngoanh2n.junit5.multiValueEnabled`<br>
  Indicate to allow setting multiple value for a JVM system property. Default to `true`.<br>
  Example: `-Dmykey=[value1,value2]` → `mykey` has 2 values: `value1` and `value2`.
  + `true`: Extract values from the value of JVM system property.
  + `false`: Use the value of JVM system property directly.

## @SetProperty
Set JVM system property for the annotated test class or test method.

### Test Class
- JVM system property has value (not null) within test class scope
- Value of JVM system property will be found all signature annotations: `@BeforeAll`, `@BeforeEach`, `@Test`, `@AfterEach`, `@AfterAll`
- Value of JVM system property will be deleted after `@AfterAll` execution is finished
```java
import com.github.ngoanh2n.SetProperty;
import org.junit.jupiter.api.*;

@SetProperty(name = "browser", value = "safari")
public class SeleniumTest {
  @BeforeAll
  public static void beforeAll() {
    // System.getProperty("browser") -> safari
  }

  @BeforeEach
  public void beforeEach() {
    // System.getProperty("browser") -> safari
  }

  @Test
  public void test() {
    // System.getProperty("browser") -> safari
  }

  @AfterEach
  public void afterEach() {
    // System.getProperty("browser") -> safari
  }

  @AfterAll
  public static void afterAll() {
    // System.getProperty("browser") -> safari
  }
}
```
### Test Method
- JVM system property has value (not null) within test method scope
- Value of JVM system property will be found signature annotations: `@BeforeEach`, `@Test`, `@AfterEach`
- Value of JVM system property will be deleted after `@AfterEach` execution is finished
```java
import com.github.ngoanh2n.SetProperty;
import org.junit.jupiter.api.*;

public class SeleniumTest {
  @BeforeAll
  public static void beforeAll() {
    // System.getProperty("browser") -> null
  }

  @BeforeEach
  public void beforeEach() {
    // System.getProperty("browser") -> safari
  }

  @Test
  @SetProperty(name = "browser", value = "safari")
  public void test() {
    // System.getProperty("browser") -> safari
  }

  @AfterEach
  public void afterEach() {
    // System.getProperty("browser") -> safari
  }

  @AfterAll
  public static void afterAll() {
    // System.getProperty("browser") -> null
  }
}
```
