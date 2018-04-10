package com.example.testing;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		monochrome = true,
		plugin = {"pretty", "html:target/cucumber"},
        features = "src/main/resources/Features",
        glue = "com.example.testing"
)
public class PersonTest {
	
}