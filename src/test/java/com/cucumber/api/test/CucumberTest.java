package com.cucumber.api.test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;



@CucumberOptions(
        features = "src/main/resources/feature",
        glue= {"com.cucumber.api.test.stepdef"},
        plugin = { "html:target/cucumber-reports.html"},
        monochrome = true
)
public class CucumberTest extends AbstractTestNGCucumberTests {

}

