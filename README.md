# cucumber-api-test

### Overview
I am familiar with Java, so I use Cucumber+TestNG+REST Assured to build the API test framework.

[TestNG](https://mirrors.aliyun.com/android.googlesource.com.bak/external/testng/doc/) is a testing framework inspired from JUnit and NUnit but introducing some new functionalities that make it more powerful and easier to use

[REST Assured](https://rest-assured.io/) is a Java library that provides a domain-specific language (DSL) for writing powerful, maintainable tests for RESTful APIs. 

_btw I plan to study Kotlin recently_

### Pre-requisite
- JDK1.8+
- Maven

### Highlight
1. validate the response body structure by JSON Schema
2. easy to get value in response body using pojo classes
3. basic information is maintained in properties file
4. add some security cases
5. execution can be triggered by command line or testng.xml or CucumberTest.class

### Run Test

Open the command prompt and navigate to the folder in which pom.xml file is present.
Run the below Maven command.

    mvn clean test

Once the execution completes report will be generated in below folder.
    
    target/cucumber-reports.html