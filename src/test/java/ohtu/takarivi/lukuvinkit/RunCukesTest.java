package ohtu.takarivi.lukuvinkit;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, glue = {"ohtu.takarivi.lukuvinkit"})
public class RunCukesTest {
}