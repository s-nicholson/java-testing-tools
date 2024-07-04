package com.example.testing.bdd;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * This class is just a way of running all the cucumber features as part of a
 * junit test run - this gets us test coverage in the jacoco report and will
 * fail the build if a scenario fails.
 * It's also possible to do this by setting up a "cucumber" task in gradle, but this
 * was easier.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class CucumberRunner {
}
