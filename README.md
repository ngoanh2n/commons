[![GitHub forks](https://img.shields.io/github/forks/ngoanh2n/commons.svg?style=social&label=Fork&maxAge=2592000)](https://github.com/ngoanh2n/commons/network/members/)
[![GitHub stars](https://img.shields.io/github/stars/ngoanh2n/commons.svg?style=social&label=Star&maxAge=2592000)](https://github.com/ngoanh2n/commons/stargazers/)
[![GitHub watchers](https://img.shields.io/github/watchers/ngoanh2n/commons.svg?style=social&label=Watch&maxAge=2592000)](https://github.com/ngoanh2n/commons/watchers/)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons)
[![badge-jdk](https://img.shields.io/badge/jdk-11-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Declarations](#declarations)
  - [Gradle](#gradle)
  - [Maven](#maven)
- [Usages](#usages)
  - [Resources](#resources)
      - [System Properties](#system-properties)
  - [YamlData](#yamldata)
      - [Static APIs](#static-apis)
      - [Inheritance](#inheritance)
        - [Without annotation](#without-annotation)
        - [With annotation](#with-annotation)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Declarations
## Gradle
_Add dependency to `build.gradle`_
```gradle
implementation("com.github.ngoanh2n:commons:1.1.0")
```

## Maven
_Add dependency to `pom.xml`_
```xml
<dependency>
  <groupId>com.github.ngoanh2n</groupId>
  <artifactId>commons</artifactId>
  <version>1.1.0</version>
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

## Resources
_Get Java resource files by resource name._

#### System Properties
- `ngoanh2n.findResourceOnClasspath`: Indicate to find the resource file on classpath (Default to true).
  + `true`: Get the resource on the classpath
    + `<PROJECT>/out/production/resources`
    + `<PROJECT>/out/test/resources`
  + `false`: Get the resource in root location
    + `<PROJECT>/src/production/resources`
    + `<PROJECT>/src/test/resources`

```java
File file = Resources.getFile("categories.json");
Path path = Resources.getPath("categories.json");
String content = Resources.getContent("categories.json");
InputStream is = Resources.getInputStream("categories.json");
```

## YamlData
#### Static APIs
_Reads Yaml file to Map, List of Map._
```java
Map<String, Object> map = YamlData.toMapFromResource("user.yml")
Map<String, Object> map = YamlData.toMapFromFile("src/test/resources/user.yml")

List<Map<String, Object>> maps = YamlData.toMapsFromResource("user.yml")
List<Map<String, Object>> maps = YamlData.toMapsFromFile("src/test/resources/user.yml")
```

#### Inheritance
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

##### Without annotation
```
User user = new User().fromResource("user.yml").toModel();
// OR
User user = new User().fromFile("src/test/resources/user.yml").toModel();
```

##### With annotation
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
