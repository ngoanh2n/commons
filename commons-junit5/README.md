[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons-junit5/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons-junit5)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/commons-junit5/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/commons-junit5)
[![badge-jdk](https://img.shields.io/badge/jdk-11-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

# Declarations
## Gradle
Add to `build.gradle`
```gradle
implementation("com.github.ngoanh2n:commons-junit5:1.1.0")
```

## Maven
Add to `pom.xml`
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>commons-junit5</artifactId>
    <version>1.1.0</version>
</dependency>
```

# Usages

## @EnabledIfProperty
_Signal that the annotated JUnit5 test class or test method is enabled._

#### System Properties
- `ngoanh2n.propMultiValueEnabled`: Indicate to allow setting multiple value for a JVM system property (Default to true).
  + `true`: Extract values from the value of JVM system property.<br>
    E.g. `-Dmykey=[value1,value2]` → `mykey` has 2 values: `value1` and `value2`
  + `false`: Use the value of JVM system property directly.


```java
// Junit5 test

import com.github.ngoanh2n.EnabledIfProperty;
import com.github.ngoanh2n.SetProperty;
import org.junit.jupiter.api.Test;

public class SeleniumTest {
  // This means, test method will be enabled if satisfied following conditions:
  // JVM system property: `os` equals to one of `macos`, `linux`, `windows`
  // JVM system property: `browser` equals to `chrome`
  @Test
  @EnabledIfProperty(name = "os", value = {"macos", "windows", "linux"})
  @EnabledIfProperty(name = "browser", value = "chrome")
  public void chromeTest() {
    ...
  }

  // This means, test method will be enabled if satisfied following conditions:
  // JVM system property: `os` equals to `macos`, `windows`
  // JVM system property: `browser` equals to `opera`
  @Test
  @EnabledIfProperty(name = "os", value = {"macos", "windows"})
  @EnabledIfProperty(name = "browser", value = "opera")
  public void operaTest() {
    ...
  }
}
```

```
./gradlew test --tests SeleniumTest -Dos=windows -Dbrowser=[chrome,opera]
→ Tests will be enabled: SeleniumTest.chromeTest() & SeleniumTest.operaTest()

./gradlew test --tests SeleniumTest -Dos=macos -Dbrowser=opera
→ Tests will be enabled: SeleniumTest.operaTest()
```

## @SetProperty
_Set value to JVM system property._

```java
// Test Class

import com.github.ngoanh2n.SetProperty;
import org.junit.jupiter.api.*;

@SetProperty(name = "os", value = "windows")
public class SeleniumTest {
  @BeforeAll
  public static void beforeAll() {
    Assertions.assertEquals("windows", System.getProperty("os"));
  }

  @BeforeEach
  public void beforeEach() {
    Assertions.assertEquals("windows", System.getProperty("os"));
  }

  @Test
  public void test() {
    Assertions.assertEquals("windows", System.getProperty("os"));
  }

  @AfterEach
  public void afterEach() {
    Assertions.assertEquals("windows", System.getProperty("os"));
  }

  @AfterAll
  public static void afterAll() {
    Assertions.assertEquals("windows", System.getProperty("os"));
  }
}
```

```java
// Test Method

import com.github.ngoanh2n.SetProperty;
import org.junit.jupiter.api.*;

public class SeleniumTest {
  @BeforeAll
  public static void beforeAll() {
    Assertions.assertNotEquals("windows", System.getProperty("os"));
    // JVM system property `os` is not set in class scope
  }

  @BeforeEach
  public void beforeEach() {
    Assertions.assertEquals("windows", System.getProperty("os"));
  }

  @Test
  @SetProperty(name = "os", value = "windows")
  public void test() {
    Assertions.assertEquals("windows", System.getProperty("os"));
  }

  @AfterEach
  public void afterEach() {
    Assertions.assertEquals("windows", System.getProperty("os"));
  }

  @AfterAll
  public static void afterAll() {
    Assertions.assertNotEquals("windows", System.getProperty("os"));
    // JVM system property `os` is not set in class scope
  }
}
```
