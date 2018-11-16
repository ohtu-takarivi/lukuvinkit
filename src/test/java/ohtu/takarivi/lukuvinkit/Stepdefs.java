package ohtu.takarivi.lukuvinkit;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.File;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Stepdefs {
    private static final int SLEEP_BETWEEN_CLICKS = 100;

    WebDriver driver;
    private static final int LOCAL_PORT=8080;
    private static final int SLEEPING_TIME=1000;
    private static final int NUMBER_OF_TRIALS=5;

    public Stepdefs() {
        File file;
        if (System.getProperty("os.name").matches("Mac OS X")) {
            file = new File("lib/macgeckodriver");
        } else {
            file = new File("lib/geckodriver");
        }
        String absolutePath = file.getAbsolutePath();
        System.setProperty("webdriver.gecko.driver", absolutePath);

        this.driver = new FirefoxDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
    
    /// TODO

    @Given("^user is at the main page$")
    public void user_is_at_the_main_page() throws Throwable {
        driver.get("http://localhost:" + LOCAL_PORT + "/" );
        Thread.sleep(SLEEPING_TIME);        
    }

    @When("^a link is clicked$")
    public void a_link_is_clicked() throws Throwable {
        Thread.sleep(SLEEPING_TIME);  
        clickLinkWithText("linkki" );
        Thread.sleep(SLEEPING_TIME);  
    }    
   
    @Then("^\"([^\"]*)\" is shown$")
    public void is_shown(String arg1) throws Throwable {
        assertTrue(driver.findElement(By.tagName("body"))
                .getText().contains(arg1));
    }

    private void clickLinkWithText(String text) {
        int trials = 0;
        while( trials++<NUMBER_OF_TRIALS ) {
            try{
                WebElement element = driver.findElement(By.linkText(text));
                element.click();
                break;           
            } catch(Exception e) {
                System.out.println(e.getStackTrace());
                try {
                    Thread.sleep(SLEEP_BETWEEN_CLICKS);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    
}