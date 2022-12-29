[![GitHub forks](https://img.shields.io/github/forks/ngoanh2n/utilities.svg?style=social&label=Fork&maxAge=2592000)](https://github.com/ngoanh2n/utilities/network/members/)
[![GitHub stars](https://img.shields.io/github/stars/ngoanh2n/utilities.svg?style=social&label=Star&maxAge=2592000)](https://github.com/ngoanh2n/utilities/stargazers/)
[![GitHub watchers](https://img.shields.io/github/watchers/ngoanh2n/utilities.svg?style=social&label=Watch&maxAge=2592000)](https://github.com/ngoanh2n/utilities/watchers/)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/utilities/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/utilities)
[![badge-jdk](https://img.shields.io/badge/jdk-8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Declarations](#declarations)
  - [Gradle](#gradle)
  - [Maven](#maven)
- [Usages](#usages)
  - [Resource](#resource)
  - [YamlData](#yamldata)
    - [Static APIs](#static-apis)
    - [Inheritance](#inheritance)
      - [Without annotation](#without-annotation)
      - [With annotation](#with-annotation)
  - [@RunOnProp](#runonprop)
  - [@SetProp](#setprop)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Declarations
## Gradle
_Add dependency to `build.gradle`_
```gradle
implementation("com.github.ngoanh2n:utilities:2.3.13")
```

## Maven
_Add dependency to `pom.xml`_
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>utilities</artifactId>
    <version>2.3.13</version>
</dependency>
```

# Usages
```
├── build
├── out
│   ├── production
│   |   └── resources
│   |       └── log4j.properties
│   └── test
│       └── resources
│           ├── user.yml
│           ├── categories.json
│           └── selenide.properties
├── src
│   ├── main
│   |   └── resources
│   |       └── log4j.properties
│   └── test
│       └── resources
│           ├── user.yml
│           ├── categories.json
│           └── selenide.properties
```

## Resource
_Get Java resource files by resource name._
_`Resource` class has a property named `ngoanh2n.resource.findOnClasspath` (Default to true)_
- When property is set to `true`: Get the resource on the classpath
    + `<PROJECT>/out/production/resources`
    + `<PROJECT>/out/test/resources`
- When property is set to `false`: Get the resource in root location
    + `<PROJECT>/src/production/resources`
    + `<PROJECT>/src/test/resources`

```java
File file = Resource.getFile("categories.json");
Path path = Resource.getPath("categories.json");
String content = Resource.getContent("categories.json");
InputStream is = Resource.getInputStream("categories.json");
```

## YamlData
### Static APIs
_Reads Yaml file to Map, List of Map._
```java
Map<String, Object> map = YamlData.toMapFromResource("user.yml")
Map<String, Object> map = YamlData.toMapFromFile("src/test/resources/user.yml")

List<Map<String, Object>> maps = YamlData.toMapsFromResource("user.yml")
List<Map<String, Object>> maps = YamlData.toMapsFromFile("src/test/resources/user.yml")
```

### Inheritance
_Reads Yaml file to Java Bean, List of Java Bean._

```yml
# Yaml File

username: ngoanh2n
notes:
  - note1
  - note2
companies:
  - name: Com1
    address: Addr1
  - name: Com2
    address: Addr3
```

```java
// Java Bean

public class User extends YamlData<User> {
  private String username;
  private List<String> notes;
  private List<Company> companies;

  ...GETTERS & SETTERS...
}
```

```java
// Java Bean

public class Company extends YamlData<Company> {
  private String name;
  private String address;

  ...GETTERS & SETTERS...
}
```

#### Without annotation
```
User user = new User().fromResource("user.yml").toModel();
// OR
User user = new User().fromFile("src/test/resources/user.yml").toModel();
```

#### With annotation
You should attach `com.github.ngoanh2n.YamlFrom` for Java Bean.

```java
// Java Bean

@YamlFrom(resource = "user.yml")
// OR
@YamlFrom(file = "src/test/resources/user.yml")
public class User extends YamlData<User> {
  ...
}
```
```
User user = new User().toModel();
// Replace declared value of @YamlFrom by calling fromResource() or fromFile() method.
```

## @RunOnProp
_Signal that the annotated JUnit5 test class or test method is enabled._

```java
// Junit5 test

public class SeleniumTest {
  // This means, test method will be enabled if satisfied following conditions:
  // JVM system property: os equals to one of macos, linux, windows
  // JVM system property: browser equals to chrome
  @Test
  @RunOnProp(name = "os", value = {"macos", "windows", "linux"})
  @RunOnProp(name = "browser", value = "chrome")
  public void chromeTest() {
    ...
  }

  // This means, test method will be enabled if satisfied following conditions:
  // JVM system property: os equals to macos, windows
  // JVM system property: browser equals to opera
  @Test
  @RunOnProp(name = "os", value = {"macos", "windows"})
  @RunOnProp(name = "browser", value = "opera")
  public void operaTest() {
    ...
  }
}
```

```
./gradlew test --tests SeleniumTest -Dos=windows -Dbrowser=chrome,opera
→ Tests will be enabled: SeleniumTest.chromeTest() & SeleniumTest.operaTest()

./gradlew test --tests SeleniumTest -Dos=macos -Dbrowser=opera
→ Tests will be enabled: SeleniumTest.operaTest()
```

## @SetProp
_Set value to JVM system property._

```java
// Test Class

@SetProp(name = "os", value = "windows")
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
  @SetProp(name = "os", value = "windows")
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
