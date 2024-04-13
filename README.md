[![GitHub forks](https://img.shields.io/github/forks/ngoanh2n/commons.svg?style=social&label=Fork&maxAge=2592000)](https://github.com/ngoanh2n/commons/network/members/)
[![GitHub stars](https://img.shields.io/github/stars/ngoanh2n/commons.svg?style=social&label=Star&maxAge=2592000)](https://github.com/ngoanh2n/commons/stargazers/)
[![GitHub watchers](https://img.shields.io/github/watchers/ngoanh2n/commons.svg?style=social&label=Watch&maxAge=2592000)](https://github.com/ngoanh2n/commons/watchers/)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ngoanh2n/commons)
[![javadoc](https://javadoc.io/badge2/com.github.ngoanh2n/commons/javadoc.svg)](https://javadoc.io/doc/com.github.ngoanh2n/commons)
[![badge-jdk](https://img.shields.io/badge/jdk-17-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blueviolet.svg)](https://opensource.org/licenses/MIT)

**Table of Contents**
<!-- TOC -->
* [Declaration](#declaration)
  * [Gradle](#gradle)
  * [Maven](#maven)
* [Usage](#usage)
  * [Resources](#resources)
    * [System Property](#system-property)
  * [YamlData](#yamldata)
    * [Static](#static)
    * [Inheritance](#inheritance)
<!-- TOC -->

# Declaration
## Gradle
Add dependency to `build.gradle`.
```gradle
implementation("com.github.ngoanh2n:commons:1.5.0")
```

## Maven
Add dependency to `pom.xml`.
```xml
<dependency>
  <groupId>com.github.ngoanh2n</groupId>
  <artifactId>commons</artifactId>
  <version>1.5.0</version>
</dependency>
```

# Usage
```
├── build
├── out
│   ├── production
│   |   └── resources
│   |       └── log4j.properties
│   └── test
│       └── resources
│           ├── file.json
│           ├── user.yml
│           └── users.yml
├── src
│   ├── main
│   |   └── resources
│   |       └── log4j.properties
│   └── test
│       └── resources
│           ├── file.json
│           ├── user.yml
│           └── users.yml
```

## Resources
Find and read Java resources.
- `File file = Resources.getFile("file.json")`
- `Path path = Resources.getPath("file.json")`
- `String content = Resources.getContent("file.json")`
- `InputStream is = Resources.getInputStream("file.json")`

### System Property
- `ngoanh2n.findResourceOnClasspath`<br>
  _Indicate to find the resource file on classpath (Default to true)._
  + `true`: Get the resource on the classpath
    + `{project}/out/test/resources/`
    + `{project}/out/production/resources/`
  + `false`: Get the resource in root location
    + `{project}/src/test/resources/`
    + `{project}/src/main/resources/`

## YamlData
### Static
Reads Yaml file to `Map`, `List of Map`.
1. Read to `Map`
   - `Map<String, Object> map = YamlData.toMapFromResource("user.yml")`
   - `Map<String, Object> map = YamlData.toMapFromFile("src/test/resources/user.yml")`
2. Read to `List of Maps`
   - `List<Map<String, Object>> maps = YamlData.toMapsFromResource("user.yml")`
   - `List<Map<String, Object>> maps = YamlData.toMapsFromFile("src/test/resources/user.yml")`

### Inheritance
Read Yaml file to `Model`, `List of Models`.<br>
Model class must be `public` and has `setter` methods.
1. Read to `Model`
    ```yml
    # Yaml: user.yml
    username: usr1
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
    // Model: User.java
    public class User extends YamlData<User> {
      private String username;
      private List<String> notes;
      private List<Company> companies;
    
      ...GETTERS & SETTERS...
    }
    ```
    ```java
    // Model: Company.java
    public class Company extends YamlData<Company> {
      private String name;
      private String address;
    
      ...GETTERS & SETTERS...
    }
    ```
    - Without annotation
      - `User user = new User().fromResource("user.yml").toModel()`
      - `User user = new User().fromFile("src/test/resources/user.yml").toModel()`
    - With annotation<br>
      _Attach `com.github.ngoanh2n.YamlFrom` annotation for `Model`._
      - `YamlFrom.resource()`
        ```java
        @YamlFrom(resource = "user.yml")
        public class User extends YamlData<User> {
            ...  
        }
        ```
      - `YamlFrom.file()`
        ```java
        @YamlFrom(file = "src/test/resources/user.yml")
        public class User extends YamlData<User> {
            ...  
        }
        ```
      _Overwrite value of `com.github.ngoanh2n.YamlFrom` annotation by calling `fromResource(String)` or `fromFile(String)` method._
2. Read to `List of Models`
    ```yml
    # Yaml File: users.yml
    - username: usr1
      password: pwd1
    - username: usr2
      password: pwd2
    ```
    ```java
    // Model: User.java
    public class User extends YamlData<User> {
      private String username;
      private String password;
    
      ...GETTERS & SETTERS...
    }
    ```
    - Without annotation
        - `List<User> users = new User().fromResource("users.yml").toModels()`
        - `List<User> users = new User().fromFile("src/test/resources/users.yml").toModels()`
    - With annotation<br>
      _Attach `com.github.ngoanh2n.YamlFrom` annotation for `Model`._
        - `YamlFrom.resource()`
          ```java
          @YamlFrom(resource = "users.yml")
          public class User extends YamlData<User> {
              ...  
          }
          ```
        - `YamlFrom.file()`
          ```java
          @YamlFrom(file = "src/test/resources/users.yml")
          public class User extends YamlData<User> {
              ...  
          }
          ```
      _Overwrite value of `com.github.ngoanh2n.YamlFrom` annotation by calling `fromResource(String)` or `fromFile(String)` method._
