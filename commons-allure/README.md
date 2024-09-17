[![Java](https://img.shields.io/badge/Java-17-orange)](https://adoptium.net)
[![Maven](https://img.shields.io/maven-central/v/com.github.ngoanh2n/commons-allure?label=Maven)](https://mvnrepository.com/artifact/com.github.ngoanh2n/commons-allure)
[![GitHub Actions](https://img.shields.io/github/actions/workflow/status/ngoanh2n/commons/test.yml?logo=github&label=GitHub%20Actions)](https://github.com/ngoanh2n/commons/actions/workflows/test.yml)
[![CircleCI](https://img.shields.io/circleci/build/github/ngoanh2n/commons?token=CCIPRJ_V9AVYTzVyEF9A9GMsVD9oF_2ce0fb3410ce42dfee9d8d854bae69d56f206df6&logo=circleci&label=CircleCI)
](https://dl.circleci.com/status-badge/redirect/gh/ngoanh2n/commons/tree/master)

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
implementation("com.github.ngoanh2n:commons-allure:1.7.0")
```

## Maven
Add to `pom.xml`
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>commons-allure</artifactId>
    <version>1.7.0</version>
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
