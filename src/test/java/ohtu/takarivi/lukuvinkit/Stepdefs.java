package ohtu.takarivi.lukuvinkit;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
public class Stepdefs extends SpringBootTestBase {
    WebDriver driver;
    private static final int SLEEPING_TIME = 100;
    private static final int PAGE_LOAD_TIMEOUT = 10;
    private static final int ELEMENT_LOAD_TIMEOUT = 5;

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
        super.setUpMvc();
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
        driver.get(getBaseUrl() + "/login");
        Thread.sleep(SLEEPING_TIME);
    }

    @Given("^user is at the register page$")
    public void user_is_at_the_register_page() throws Throwable {
        driver.get(getBaseUrl() + "/register");
        Thread.sleep(SLEEPING_TIME);
    }

    @Given("^test user is logged in$")
    public void test_user_is_logged_in() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        Thread.sleep(SLEEPING_TIME);
        logInWith("nolla", "yksi");
        Thread.sleep(SLEEPING_TIME);
    }

    @Given("^test user is logged in and on the profile page$")
    public void test_user_is_logged_in_on_profile_page() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        Thread.sleep(SLEEPING_TIME);
        logInWith("nolla", "yksi");
        Thread.sleep(SLEEPING_TIME);
        browseTo("/profile");
        Thread.sleep(SLEEPING_TIME);
    }

    @Given("^test user is logged in and browsing book tips$")
    public void test_user_is_logged_in_on_book_tip_list() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        Thread.sleep(SLEEPING_TIME);
        logInWith("nolla", "yksi");
        Thread.sleep(SLEEPING_TIME);
        browseTo("/readingTips/books");
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^browsing book tips$")
    public void browse_book_tips() throws Throwable {
        browseTo("/readingTips/books");
    }

    @When("^browsing link tips$")
    public void browse_link_tips() throws Throwable {
        browseTo("/readingTips/links");
    }

    @When("^correct title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" and isbn \"([^\"]*)\" are given$")
    public void tip_with_valid_information_is_given(String title, String description, String url, String author, String isbn) throws Throwable {
        createBookTip(title, description, url, author, isbn);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^no title and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" and isbn \"([^\"]*)\" are given$")
    public void tip_with_invalid_title_is_given(String description, String url, String author, String isbn) throws Throwable {
        createBookTip("", description, url, author, isbn);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^title \"([^\"]*)\" and no description and url \"([^\"]*)\" and author \"([^\"]*)\" and isbn \"([^\"]*)\" are given$")
    public void tip_with_invalid_description_is_given(String title, String url, String author, String isbn) throws Throwable {
        createBookTip(title, "", url, author, isbn);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^title \"([^\"]*)\" and description \"([^\"]*)\" and no url and author \"([^\"]*)\" and isbn \"([^\"]*)\" are given$")
    public void tip_with_invalid_url_is_given(String title, String description, String author, String isbn) throws Throwable {
        createBookTip(title, description, "", author, isbn);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and no author and isbn \"([^\"]*)\" are given$")
    public void tip_with_invalid_author_is_given(String title, String description, String url, String isbn) throws Throwable {
        createBookTip(title, description, url, "", isbn);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" and no isbn are given$")
    public void tip_with_invalid_ISBN_is_given(String title, String description, String url, String author) throws Throwable {
        createBookTip(title, description, url, author, "");
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^book tip with title \"([^\"]*)\" is marked as read$")
    public void tip_mark_as_read(String title) throws Throwable {
        waitForElementWithId("loadfinish");
        driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttonread")).click();
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^book tip with title \"([^\"]*)\" is deleted$")
    public void tip_is_deleted(String title) throws Throwable {
        waitForElementWithId("loadfinish");
        driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttondelete")).click();
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

    @Then("^new book tip with title \"([^\"]*)\" and description \"([^\"]*)\" and author \"([^\"]*)\" is created$")
    public void new_tip_is_created(String title, String description, String author) throws Throwable {
        browseTo("/readingTips/books");
        List<WebElement> elements = driver.findElements(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']"));
        
        assertTrue(elements.size() > 0);
        elements.get(0).click();
        waitForPageChange();
        waitForElementWithId("tiptitle");
        
        assertEquals(title, driver.findElement(By.id("tiptitle")).getText());
        assertEquals(description, driver.findElement(By.id("tipdescription")).getText());
        assertEquals(author, driver.findElement(By.id("tipauthor")).getText());
    }

    @When("^new password \"([^\"]*)\" and for verifying new password \"([^\"]*)\" are given$")
    public void new_password_created(String password, String verifyPassword) throws Throwable {
        assertFalse(driver.findElements(By.id("buttonchangepassword")).isEmpty());
        driver.findElement(By.name("newPassword")).sendKeys(password);
        driver.findElement(By.name("verifyNewPassword")).sendKeys(verifyPassword);
        driver.findElement(By.id("buttonchangepassword")).click();
        waitForPageChange();
    }

    @Then("^new book tip with \"([^\"]*)\" and \"([^\"]*)\" is not created$")
    public void new_tip_is_not_created(String first, String second) throws Throwable {
        browseTo("/readingTips/books");
        assertFalse(driver.getPageSource().contains(first));
    }

    @Then("^tip with title \"([^\"]*)\" is visible$")
    public void tip_visible(String title) throws Throwable {
        assertTrue(driver.getPageSource().contains(title));
    }

    @Then("^book tip with title \"([^\"]*)\" is still visible$")
    public void book_tip_still_visible(String title) throws Throwable {
        browseTo("/readingTips/books");
        assertTrue(driver.getPageSource().contains(title));
    }

    @Then("^book tip with title \"([^\"]*)\" is not visible$")
    public void book_tip_not_visible(String title) throws Throwable {
        browseTo("/readingTips/books");
        assertFalse(driver.getPageSource().contains(title));
    }

    @Then("^book tip with title \"([^\"]*)\" is no longer visible$")
    public void book_tip_not_visible_(String title) throws Throwable {
        browseTo("/readingTips/books");
        assertFalse(driver.getPageSource().contains(title));
    }

    @Then("^book tip with title \"([^\"]*)\" has been marked as read$")
    public void book_tip_mark_as_read(String title) throws Throwable {
        browseTo("/readingTips/books");
        WebElement btn = driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttonread"));
        assertNotNull(btn.getAttribute("disabled"));
    }

    @Then("^book tip with title \"([^\"]*)\" has not been marked as read$")
    public void book_tip_not_mark_as_read(String title) throws Throwable {
        browseTo("/readingTips/books");
        WebElement btn = driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttonread"));
        assertNull(btn.getAttribute("disabled"));
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

    @Then("^the password is changed$")
    public void password_is_changed() throws Throwable {
        driver.findElement(By.id("buttonlogout")).click();
        waitForPageChange();
        logInWith("nolla", "yksi");
        waitForPageChange();
        assertFalse(driver.findElements(By.id("buttonlogout")).isEmpty());
    }

    /* helper methods */

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    private void browseTo(String string) throws InterruptedException {
        driver.navigate().to(getBaseUrl() + string);
        waitForPageChange();
    }

    private void waitForPageChange() throws InterruptedException {
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        Thread.sleep(SLEEPING_TIME);
    }

    private void waitForElementWithId(String id) throws InterruptedException {
        new WebDriverWait(driver, ELEMENT_LOAD_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }
    
    private void logInWith(String username, String password) throws InterruptedException {
        assertFalse(driver.findElements(By.id("buttonlogin")).isEmpty());
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("buttonlogin")).click();
        waitForPageChange();
    }

    private void createBookTip(String title, String description, String url, String author, String isbn) throws InterruptedException {
        browseTo("/readingTips/books/new");
        assertFalse(driver.findElements(By.id("buttonadd")).isEmpty());
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("description")).sendKeys(description);
        //driver.findElement(By.name("url")).sendKeys(url);
        driver.findElement(By.name("author")).sendKeys(author);
        driver.findElement(By.name("isbn")).sendKeys(isbn);
        driver.findElement(By.id("buttonadd")).click();
        waitForPageChange();
    }
}
