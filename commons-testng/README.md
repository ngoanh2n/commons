[![Java](https://img.shields.io/badge/Java-17-orange)](https://adoptium.net)
[![Maven](https://img.shields.io/maven-central/v/com.github.ngoanh2n/commons-testng?label=Maven)](https://mvnrepository.com/artifact/com.github.ngoanh2n/commons-testng)
[![GitHub Actions](https://img.shields.io/github/actions/workflow/status/ngoanh2n/commons/test.yml?logo=github&label=GitHub%20Actions)](https://github.com/ngoanh2n/commons/actions/workflows/test.yml)
[![CircleCI](https://img.shields.io/circleci/build/github/ngoanh2n/commons?token=CCIPRJ_V9AVYTzVyEF9A9GMsVD9oF_2ce0fb3410ce42dfee9d8d854bae69d56f206df6&logo=circleci&label=CircleCI)
](https://dl.circleci.com/status-badge/redirect/gh/ngoanh2n/commons/tree/master)

**Table of Contents**
<!-- TOC -->
* [Declaration](#declaration)
  * [Gradle](#gradle)
  * [Maven](#maven)
* [Usage](#usage)
  * [WebDriverTestNG](#webdrivertestng)
<!-- TOC -->

# Declaration
## Gradle
Add to `build.gradle`
```gradle
implementation("com.github.ngoanh2n:commons-testng:1.7.0")
```

## Maven
Add to `pom.xml`
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>commons-testng</artifactId>
    <version>1.7.0</version>
</dependency>
```

# Usage
## WebDriverTestNG
Lookup `WebDriver` from the current TestNG test using `org.testng.IInvokedMethodListener`.

**Step 1:** Create a class that extends `com.github.ngoanh2n.WebDriverTestNG`
```java
package com.company.project.impl;

import com.github.ngoanh2n.WebDriverTestNG;

public class MyWebDriverLookup extends WebDriverTestNG {
    public WebDriver getWebDriver() {
        if (iTestResult != null) {
            lookupDriver(iTestResult, WebDriverTestNG.BO);
        }
        return driver;
    }
}
```

**Step 2:** Create a provider configuration file
- Location: `resources/META-INF/services/`
- Name: `org.testng.ITestNGListener`
- Content: `com.company.project.impl.MyWebDriverLookup`
