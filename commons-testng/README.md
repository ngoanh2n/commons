[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons-testng/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons-testng)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/commons-testng/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/commons-testng)
[![badge-jdk](https://img.shields.io/badge/jdk-11-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

# Declarations
## Gradle
Add to `build.gradle`
```gradle
implementation("com.github.ngoanh2n:commons-testng:1.1.2")
```

## Maven
Add to `pom.xml`
```xml
<dependency>
    <groupId>com.github.ngoanh2n</groupId>
    <artifactId>commons-testng</artifactId>
    <version>1.1.2</version>
</dependency>
```

# Usages

## WebDriverTestNG
_Lookup `WebDriver` from the current TestNG test using `org.testng.IInvokedMethodListener`._

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
