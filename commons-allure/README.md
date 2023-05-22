[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons-allure/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons-allure)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/commons-allure/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/commons-allure)
[![badge-jdk](https://img.shields.io/badge/jdk-11-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

# Declarations
## Gradle
Add to `build.gradle`
```gradle
implementation("com.github.ngoanh2n:commons-allure:1.1.0")
```

## Maven
Add to `pom.xml`
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>commons-allure</artifactId>
    <version>1.1.0</version>
</dependency>
```

# Usages

## AllureEnvironment
_Write `environment.properties` to Allure results directory._

1. Write to the default directory: `build/allure-results/`
    ```java
    // Write Properties object
    Properties props = new Properties();
    AllureEnvironment.write(props)

    // Write properties file is existing in resources folder
    String props = "selenide.properties";
    AllureEnvironment.write(props)
    ```
2. Write to specified directory
    ```java
    // Write Properties object
    Path location = Paths.get("build/custom/directory");
    Properties props = new Properties();
    AllureEnvironment.write(location, props)

    // Write properties file is existing in resources folder
    Path location = Paths.get("build/custom/directory");
    String props = "selenide.properties";
    AllureEnvironment.write(location, props)
    ```
