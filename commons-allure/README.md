[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons-allure/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons-allure)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/commons-allure/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/commons-allure)
[![badge-jdk](https://img.shields.io/badge/jdk-8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

**Table of Contents**
<!-- TOC -->
* [Declaration](#declaration)
  * [Gradle](#gradle)
  * [Maven](#maven)
* [Usage](#usage)
  * [AllureEnvironment](#allureenvironment)
<!-- TOC -->

# Declaration
## Gradle
Add to `build.gradle`
```gradle
implementation("com.github.ngoanh2n:commons-allure:1.1.3")
```

## Maven
Add to `pom.xml`
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>commons-allure</artifactId>
    <version>1.1.3</version>
</dependency>
```

# Usage
## AllureEnvironment
Write `environment.properties` to Allure results directory.

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
