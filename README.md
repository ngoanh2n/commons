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

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Declarations
## Gradle
_Add dependency to `build.gradle`_
```gradle
implementation("com.github.ngoanh2n:utilities:2.3.11")
```

## Maven
_Add dependency to `pom.xml`_
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>utilities</artifactId>
    <version>2.3.11</version>
</dependency>
```

# Usages
## Resource
_Get Java resource files by resource name._
```
├── build
├── out
│   ├── production
│   |   └── resources
│   |       └── log4j.properties
│   └── test
│       └── resources
│           ├── config.yml
│           ├── categories.json
│           └── selenide.properties
├── src
│   ├── main
│   |   └── resources
│   |       └── log4j.properties
│   └── test
│       └── resources
│           ├── config.yml
│           ├── categories.json
│           └── selenide.properties
```
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
