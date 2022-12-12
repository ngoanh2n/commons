[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/utilities/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/utilities)
[![badge-jdk](https://img.shields.io/badge/jdk-8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

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

1. File
    ```java
    File file = Resource.getFile("config.yml");
    ```
2. Path
    ```java
    Path path = Resource.getPath("config.yml");
    ```
3. InputStream
    ```java
    InputStream is = Resource.getInputStream("config.yml");
    ```
4. Content
    ```java
    String content = Resource.getContent("config.yml");
    ```
