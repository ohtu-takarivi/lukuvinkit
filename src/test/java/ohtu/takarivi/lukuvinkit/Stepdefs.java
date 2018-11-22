package ohtu.takarivi.lukuvinkit;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
public class Stepdefs extends SpringBootTestBase {
    WebDriver driver;
    private static final int SLEEP_BETWEEN_CLICKS = 50;
    private static final int SLEEPING_TIME = 100;
    private static final int NUMBER_OF_TRIALS = 5;
    private static final int PAGE_LOAD_TIMEOUT = 10;

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

        this.driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true));
    }

    @Before
    public void setUp() {
        super.setUpTestData();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void removingThisBreaksEverything() throws Throwable {
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

    @Given("^test user is logged in$")
    public void test_user_is_logged_in() throws Throwable {
        driver.get("http://localhost:" + port + "/login");
        Thread.sleep(SLEEPING_TIME);
        logInWith("nolla", "yksi");
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^browsing tips$")
    public void browse_tips() throws Throwable {
    }

    @When("^correct title \"([^\"]*)\" and type \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" are given$")
    public void tip_with_valid_information_is_given(String title, String type, String description, String url, String author) throws Throwable {
        createTip(title, type, description, url, author);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^no title and type \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" are given$")
    public void tip_with_invalid_title_is_given(String type, String description, String url, String author) throws Throwable {
        createTip("", type, description, url, author);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^title \"([^\"]*)\" and no type and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" are given$")
    public void tip_with_invalid_type_is_given(String title, String description, String url, String author) throws Throwable {
        createTip(title, "", description, url, author);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^title \"([^\"]*)\" and type \"([^\"]*)\" and no description and url \"([^\"]*)\" and author \"([^\"]*)\" are given$")
    public void tip_with_invalid_description_is_given(String title, String type, String url, String author) throws Throwable {
        createTip(title, type, "", url, author);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^title \"([^\"]*)\" and type \"([^\"]*)\" and description \"([^\"]*)\" and no url and author \"([^\"]*)\" are given$")
    public void tip_with_invalid_url_is_given(String title, String type, String description, String author) throws Throwable {
        createTip(title, type, description, "", author);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^title \"([^\"]*)\" and type \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and no author are given$")
    public void tip_with_invalid_author_is_given(String title, String type, String description, String url) throws Throwable {
        createTip(title, type, description, url, "");
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^tip with title \"([^\"]*)\" is deleted$")
    public void tip_is_deleted(String title) throws Throwable {
        // driver.get("http://localhost:" + port + "/readingTips/" + type);
        driver.findElement(By.xpath("//td[@id='tiptitle']/a[text()='" + title + "']/../../td/form/button")).click();
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
        assertFalse(driver.findElements(By.id("buttonregister")).isEmpty());
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("verifyPassword")).sendKeys(password);
        driver.findElement(By.name("name")).sendKeys(username);
        driver.findElement(By.id("buttonregister")).click();
        waitForPageChange();
    }

    @Then("^new tip with title \"([^\"]*)\" and type \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" is created$")
    public void new_tip_is_created(String title, String type, String description, String url, String author) throws Throwable {
        driver.get("http://localhost:" + port + "/readingTips/" + type);
        List<WebElement> elements = driver.findElements(By.xpath("//td[@id='tiptitle']/a[text()='" + title + "']/../../td"));
        assertTrue(elements.get(0).getText().contains(title));
        assertTrue(elements.get(1).getText().contains(description));
        assertTrue(elements.get(2).getText().contains(url));
        assertTrue(elements.get(3).getText().contains(author));
    }

    @Then("^new tip with \"([^\"]*)\" and \"([^\"]*)\" is not created$")
    public void new_tip_is_created(String first, String second) throws Throwable {
        assertTrue(!driver.getPageSource().contains(first));
        assertTrue(!driver.getPageSource().contains(second));
    }

    @Then("^tip with title \"([^\"]*)\" is not visible$")
    public void tip_not_visible(String title) throws Throwable {
        assertTrue(!driver.getPageSource().contains(title));
    }

    @Then("^tip with title \"([^\"]*)\" is no longer visible$")
    public void tip_not_visible_(String title) throws Throwable {
        assertTrue(!driver.getPageSource().contains(title));
    }

    @Then("^user is logged in$")
    public void logged_in() throws Throwable {
        assertFalse(driver.findElements(By.id("buttonlogout")).isEmpty());
    }

    @Then("^user is not logged in$")
    public void not_logged_in() throws Throwable {
        assertTrue(driver.getCurrentUrl().contains("/login?error"));
    }

    @Then("^user account is created$")
    public void account_created() throws Throwable {
        assertFalse(driver.findElements(By.id("buttonlogin")).isEmpty());
    }

    @Then("^user account is not created$")
    public void account_not_created() throws Throwable {
        assertTrue(driver.findElements(By.id("buttonlogin")).isEmpty());
    }

    /* helper methods */

    private void logInWith(String username, String password) {
        assertFalse(driver.findElements(By.id("buttonlogin")).isEmpty());
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("buttonlogin")).click();
        waitForPageChange();
    }

    private void createTip(String title, String type, String description, String url, String author) {
        assertFalse(driver.findElements(By.id("buttonadd")).isEmpty());
        driver.findElement(By.name("title")).sendKeys(title);
        Select drpType = new Select(driver.findElement(By.name("type")));
        drpType.selectByValue(type);
        driver.findElement(By.name("description")).sendKeys(description);
        driver.findElement(By.name("url")).sendKeys(url);
        driver.findElement(By.name("author")).sendKeys(author);
        driver.findElement(By.id("buttonadd")).click();
        waitForPageChange();
    }

    private void waitForPageChange() {
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
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
