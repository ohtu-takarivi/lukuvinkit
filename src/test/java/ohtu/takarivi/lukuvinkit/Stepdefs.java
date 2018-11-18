package ohtu.takarivi.lukuvinkit;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.File;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Stepdefs extends SpringBootTestBase {
    WebDriver driver;
    private static final int SLEEP_BETWEEN_CLICKS = 50;
    private static final int SLEEPING_TIME = 100;
    private static final int PAGE_LOAD_SLEEP = 2000;
    private static final int NUMBER_OF_TRIALS = 5;

    public Stepdefs() {
        super();
        File file;
        
        if (System.getProperty("os.name").matches("Mac OS X")) {
            file = new File("lib/macgeckodriver");
        } else if (System.getProperty("os.name").startsWith("Windows")) {
            file = new File("lib/geckodriver.exe");
        } else {
            file = new File("lib/geckodriver");
        }
        String absolutePath = file.getAbsolutePath();
        
        System.setProperty("webdriver.gecko.driver", absolutePath);

        this.driver = new FirefoxDriver();
    }
    
    @Before
    public void setUp() {
        super.setUpTestData();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
    
    /* steps */

    @Given("^user is at the login page$")
    public void user_is_at_the_login_page() throws Throwable {
        driver.get("http://localhost:" + port + "/login");
        Thread.sleep(SLEEPING_TIME);        
    }

    @Given("^user is at the register page$")
    public void user_is_at_the_register_page() throws Throwable {
        driver.get("http://localhost:" + port + "/register");
        Thread.sleep(SLEEPING_TIME);        
    }

    @When("^username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_password_entered(String username, String password) throws Throwable {
        logInWith(username, password);
        Thread.sleep(SLEEPING_TIME);  
    }   

    @When("^correct username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void correct_username_password_entered(String username, String password) throws Throwable {
        username_password_entered(username, password);
    }   

    @When("^correct username \"([^\"]*)\" and incorrect password \"([^\"]*)\" are given$")
    public void username_incorrect_password_entered(String username, String password) throws Throwable {
        username_password_entered(username, password);
    }   

    @When("^incorrect username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void incorrect_username_password_entered(String username, String password) throws Throwable {
        username_password_entered(username, password);
    }   

    @When("^username \"([^\"]*)\" and password \"([^\"]*)\" are registered$")
    public void username_password_registered(String username, String password) throws Throwable {
        assertTrue(driver.getPageSource().contains("Register"));
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("verifyPassword")).sendKeys(password);
        driver.findElement(By.id("buttonregister")).click();
        waitForPageChange();
    }      

    @Then("^user is logged in$")
    public void logged_in() throws Throwable {
        assertTrue(driver.findElement(By.tagName("body"))
                .getText().contains("Lukuvinkkisi:"));
    } 
   
    @Then("^user is not logged in$")
    public void not_logged_in() throws Throwable {
        assertTrue(driver.getCurrentUrl().contains("/login?error"));
    }

    /* helper methods */
        
    private void logInWith(String username, String password) {
        assertTrue(driver.getPageSource().contains("Please sign in"));
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("buttonlogin")).click();
        waitForPageChange();
    } 

    private void waitForPageChange() {
        try {
            Thread.sleep(PAGE_LOAD_SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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