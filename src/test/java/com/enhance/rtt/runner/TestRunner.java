package com.enhance.rtt.runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features ="FeatureFiles", glue = "com.enhance.rtt.stepdefiniton")
public class TestRunner {

}

