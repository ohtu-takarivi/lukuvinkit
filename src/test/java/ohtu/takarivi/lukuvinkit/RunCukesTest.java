package ohtu.takarivi.lukuvinkit;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
public class RunCukesTest {
    private static final int SERVER_PORT = 8080;
    @ClassRule
    public static ServerRule server = new ServerRule(SERVER_PORT);
}