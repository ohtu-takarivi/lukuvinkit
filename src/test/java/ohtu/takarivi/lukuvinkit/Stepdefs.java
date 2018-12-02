package ohtu.takarivi.lukuvinkit;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ohtu.takarivi.lukuvinkit.controller.MiscController;

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
import org.openqa.selenium.UnhandledAlertException;
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
    private static final int SLEEPING_TIME = 200;
    private static final int FETCH_TIMEOUT = 5000;
    private static final int PAGE_LOAD_TIMEOUT = 15;
    private static final int ELEMENT_LOAD_TIMEOUT = 15;

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

    @Given("^test user is logged in and creating a book tip$")
    public void test_user_is_logged_in_creating_book_tip() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        Thread.sleep(SLEEPING_TIME);
        logInWith("nolla", "yksi");
        Thread.sleep(SLEEPING_TIME);
        browseTo("/readingTips/books/add");
        Thread.sleep(SLEEPING_TIME);
    }

    @Given("^test user is logged in and creating a link tip$")
    public void test_user_is_logged_in_creating_link_tip() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        Thread.sleep(SLEEPING_TIME);
        logInWith("nolla", "yksi");
        Thread.sleep(SLEEPING_TIME);
        browseTo("/readingTips/links/add");
        Thread.sleep(SLEEPING_TIME);
    }

    @Given("^test data entry is enabled$")
    public void test_data_entry_is_enabled() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        Thread.sleep(SLEEPING_TIME);
        logInWith("nolla", "yksi");
        Thread.sleep(SLEEPING_TIME);
        MiscController.enableTestdataEntry();
    }

    @When("^browsing book tips$")
    public void browse_book_tips() throws Throwable {
        browseTo("/readingTips/books");
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^browsing link tips$")
    public void browse_link_tips() throws Throwable {
        browseTo("/readingTips/links");
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^viewing the book tip with title \"([^\"]*)\"$")
    public void view_book_tip(String title) throws Throwable {
        browseTo("/readingTips/books");
        Thread.sleep(SLEEPING_TIME);
        openTipWithTitle(title);
    }

    @When("^viewing the link tip with title \"([^\"]*)\"$")
    public void view_link_tip(String title) throws Throwable {
        browseTo("/readingTips/links");
        Thread.sleep(SLEEPING_TIME);
        openTipWithTitle(title);
    }
    
    @When("^selecting tip with title \"([^\"]*)\" and browsing is selected$")
    public void select_tip_and_browse_selected(String title) throws Throwable {
        waitForElementWithId("loadfinish");
        driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttonselect")).click();
        
        Thread.sleep(SLEEPING_TIME);
        browseTo("/readingTips/selected");
    }
    
    @When("^selecting tip with title \"([^\"]*)\" and exportText is selected$")
    public void select_tip_and_exportText_selected(String title) throws Throwable {
        waitForElementWithId("loadfinish");
        driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttonselect")).click();
        
        Thread.sleep(SLEEPING_TIME);
        browseTo("/readingTips/exportText");
    }
    
    @When("^selecting tip with title \"([^\"]*)\" and exportHTML is selected$")
    public void select_tip_and_exportHTML_selected(String title) throws Throwable {
        waitForElementWithId("loadfinish");
        driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttonselect")).click();
        
        Thread.sleep(SLEEPING_TIME);
        browseTo("/readingTips/exportHTML");
    }

    @When("^creating a book tip and correct title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" and isbn \"([^\"]*)\" are given$")
    public void book_tip_with_valid_information_is_given(String title, String description, String url, String author, String isbn) throws Throwable {
        createBookTip(title, description, url, author, isbn);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^creating a book tip and no title and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" and isbn \"([^\"]*)\" are given$")
    public void book_tip_with_invalid_title_is_given(String description, String url, String author, String isbn) throws Throwable {
        createBookTip("", description, url, author, isbn);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^creating a book tip and title \"([^\"]*)\" and no description and url \"([^\"]*)\" and author \"([^\"]*)\" and isbn \"([^\"]*)\" are given$")
    public void book_tip_with_invalid_description_is_given(String title, String url, String author, String isbn) throws Throwable {
        createBookTip(title, "", url, author, isbn);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^creating a book tip and title \"([^\"]*)\" and description \"([^\"]*)\" and no url and author \"([^\"]*)\" and isbn \"([^\"]*)\" are given$")
    public void book_tip_with_invalid_url_is_given(String title, String description, String author, String isbn) throws Throwable {
        createBookTip(title, description, "", author, isbn);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^creating a book tip and title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and no author and isbn \"([^\"]*)\" are given$")
    public void book_tip_with_invalid_author_is_given(String title, String description, String url, String isbn) throws Throwable {
        createBookTip(title, description, url, "", isbn);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^creating a book tip and title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" and no isbn are given$")
    public void book_tip_with_invalid_ISBN_is_given(String title, String description, String url, String author) throws Throwable {
        createBookTip(title, description, url, author, "");
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^creating a link tip and title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" are given$")
    public void link_tip_with_valid_information_is_given(String title, String description, String url, String author) throws Throwable {
        createLinkTip(title, description, url, author);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^creating a link tip and title \"([^\"]*)\" and description \"([^\"]*)\" and no url and author \"([^\"]*)\" are given$")
    public void link_tip_with_invalid_url(String title, String description, String author) throws Throwable {
        createLinkTip(title, description, "", author);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^creating a video tip and title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)\" are given$")
    public void video_tip_with_valid_information_is_given(String title, String description, String url, String author) throws Throwable {
        createVideoTip(title, description, url, author);
        Thread.sleep(SLEEPING_TIME);
    }

    @When("^creating an article tip and correct title \"([^\"]*)\" and description \"([^\"]*)\" and author \"([^\"]*)\" are given$")
    public void article_tip_with_valid_information_is_given(String title, String description, String author) throws Throwable {
        createArticleTip(title, description, author);
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

    @When("^searching for tips with \"([^\"]*)\"$")
    public void search_keyword(String keyword) throws Throwable {
        driver.findElement(By.id("keyword")).sendKeys(keyword);
        driver.findElement(By.id("buttonquicksearch")).click();
        waitForPageChange();
    }

    @When("^searching for tips with title \"([^\"]*)\"$")
    public void search_advanced_title(String title) throws Throwable {
        browseTo("/search");
        driver.findElement(By.id("title")).sendKeys(title);
        driver.findElement(By.id("buttonsearch")).click();
        waitForPageChange();
    }

    @When("^searching for tips with description \"([^\"]*)\"$")
    public void search_advanced_description(String description) throws Throwable {
        browseTo("/search");
        driver.findElement(By.id("description")).sendKeys(description);
        driver.findElement(By.id("buttonsearch")).click();
        waitForPageChange();
    }

    @When("^searching for tips with author \"([^\"]*)\"$")
    public void search_advanced_author(String author) throws Throwable {
        browseTo("/search");
        driver.findElement(By.id("author")).sendKeys(author);
        driver.findElement(By.id("buttonsearch")).click();
        waitForPageChange();
    }

    @When("^searching for article tips only$")
    public void search_advanced_article_only() throws Throwable {
        browseTo("/search");
        uncheck(driver.findElement(By.id("books")));
        check(driver.findElement(By.id("articles")));
        uncheck(driver.findElement(By.id("videos")));
        uncheck(driver.findElement(By.id("links")));
        driver.findElement(By.id("buttonsearch")).click();
        waitForPageChange();
    }

    @When("^searching for video tips only$")
    public void search_advanced_video_only() throws Throwable {
        browseTo("/search");
        uncheck(driver.findElement(By.id("books")));
        uncheck(driver.findElement(By.id("articles")));
        check(driver.findElement(By.id("videos")));
        uncheck(driver.findElement(By.id("links")));
        driver.findElement(By.id("buttonsearch")).click();
        waitForPageChange();
    }

    @When("^searching for link tips only$")
    public void search_advanced_link_only() throws Throwable {
        browseTo("/search");
        uncheck(driver.findElement(By.id("books")));
        uncheck(driver.findElement(By.id("articles")));
        uncheck(driver.findElement(By.id("videos")));
        check(driver.findElement(By.id("links")));
        driver.findElement(By.id("buttonsearch")).click();
        waitForPageChange();
    }

    @When("^searching for read tips only$")
    public void search_read_link_only() throws Throwable {
        browseTo("/search");
        uncheck(driver.findElement(By.id("unread")));
        check(driver.findElement(By.id("read")));
        driver.findElement(By.id("buttonsearch")).click();
        waitForPageChange();
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

    @When("^username \"([^\"]*)\" and password \"([^\"]*)\" and verify password \"([^\"]*)\" are registered$")
    public void username_password_registered_different_verify(String username, String password, String verify) throws Throwable {
        assertFalse(driver.findElements(By.id("buttonregister")).isEmpty());
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("verifyPassword")).sendKeys(verify);
        driver.findElement(By.name("name")).sendKeys(username);
        driver.findElement(By.id("buttonregister")).click();
        waitForPageChange();
    }

    @When("^new password \"([^\"]*)\" and for verifying new password \"([^\"]*)\" are given$")
    public void new_password_created(String password, String verifyPassword) throws Throwable {
        assertFalse(driver.findElements(By.id("buttonchangepassword")).isEmpty());
        driver.findElement(By.name("newPassword")).sendKeys(password);
        driver.findElement(By.name("verifyNewPassword")).sendKeys(verifyPassword);
        driver.findElement(By.id("buttonchangepassword")).click();
        waitForPageChange();
    }
    
    @When("^fetching information from our own login page$")
    public void autolink_fetch_from_self() throws Throwable {
        driver.findElement(By.name("url")).sendKeys(getBaseUrl() + "/login");
        Thread.sleep(SLEEPING_TIME);
        driver.findElement(By.id("buttonautofilllink")).click();
        Thread.sleep(FETCH_TIMEOUT);
    }
    
    @When("^fetching information from ISBN \"([^\"]*)\"$")
    public void autofill_book_isbn(String isbn) throws Throwable {
        driver.findElement(By.name("isbn")).sendKeys(isbn);
        Thread.sleep(SLEEPING_TIME);
        driver.findElement(By.id("buttonautofillbook")).click();
        Thread.sleep(FETCH_TIMEOUT);
    }

    @When("^adding test data$")
    public void adding_test_data() throws Throwable {
        browseTo("/testDataInsert");
        Thread.sleep(SLEEPING_TIME);
    }

    @Then("^new book tip with title \"([^\"]*)\" and description \"([^\"]*)\" and author \"([^\"]*)\" is created$")
    public void new_book_tip_is_created(String title, String description, String author) throws Throwable {
        browseTo("/readingTips/books");
        verifyTipInfo(title, description, author);
    }

    @Then("^new link tip with title \"([^\"]*)\" and description \"([^\"]*)\" and author \"([^\"]*)\" is created$")
    public void new_link_tip_is_created(String title, String description, String author) throws Throwable {
        browseTo("/readingTips/links");
        verifyTipInfo(title, description, author);
    }

    @Then("^new video tip with title \"([^\"]*)\" and description \"([^\"]*)\" and author \"([^\"]*)\" is created$")
    public void new_video_tip_is_created(String title, String description, String author) throws Throwable {
        browseTo("/readingTips/videos");
        verifyTipInfo(title, description, author);
    }

    @Then("^new article tip with title \"([^\"]*)\" and description \"([^\"]*)\" and author \"([^\"]*)\" is created$")
    public void new_article_tip_is_created(String title, String description, String author) throws Throwable {
        browseTo("/readingTips/articles");
        verifyTipInfo(title, description, author);
    }

    @Then("^new book tip with \"([^\"]*)\" and \"([^\"]*)\" is not created$")
    public void new_book_tip_is_not_created(String first, String second) throws Throwable {
        browseTo("/readingTips/books");
        assertFalse(driver.getPageSource().contains(first));
    }

    @Then("^new link tip with \"([^\"]*)\" and \"([^\"]*)\" is not created$")
    public void new_link_tip_is_not_created(String first, String second) throws Throwable {
        browseTo("/readingTips/links");
        assertFalse(driver.getPageSource().contains(first));
    }

    @Then("^new video tip with \"([^\"]*)\" and \"([^\"]*)\" is not created$")
    public void new_video_tip_is_not_created(String first, String second) throws Throwable {
        browseTo("/readingTips/links");
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

    @Then("^fetched title contains \"([^\"]*)\"$")
    public void autofill_title(String title) throws Throwable {
        assertTrue(driver.findElement(By.name("title")).getAttribute("value").contains(title));
    }

    @Then("^fetched title contains \"([^\"]*)\" and author contains \"([^\"]*)\" or there is an alert$")
    public void autofill_title_author(String title, String author) throws Throwable {
        try {
            assertTrue(driver.findElement(By.name("title")).getAttribute("value").contains(title));
            assertTrue(driver.findElement(By.name("author")).getAttribute("value").contains(author));
        } catch (UnhandledAlertException ex) {
        }
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

    @Then("^there are no search results$")
    public void zero_search_results() throws Throwable {
        assertEquals(0, driver.findElements(By.cssSelector(".searchresult")).size());
    }

    @Then("^there is 1 search result and it is \"([^\"]*)\"$")
    public void one_search_result(String candidate) throws Throwable {
        assertEquals(1, driver.findElements(By.cssSelector(".searchresult")).size());
        List<WebElement> elements = driver.findElements(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + candidate + "']"));
        assertTrue(elements.size() > 0);
    }

    @Then("^there are (\\d+) search results and one of them is \"([^\"]*)\"$")
    public void search_results(int total, String candidate) throws Throwable {
        assertEquals(total, driver.findElements(By.cssSelector(".searchresult")).size());
        List<WebElement> elements = driver.findElements(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + candidate + "']"));
        assertTrue(elements.size() > 0);
    }
    
    @Then("^URL goes to \"([^\"]*)\"$")
    public void url_clickable_to(String url) throws Throwable {
        assertEquals(url, driver.findElement(By.id("tipurl")).getAttribute("href"));
        assertEquals(url, driver.findElement(By.id("tipurl")).getText());
    }
    
    @Then("^ISBN can be clicked$")
    public void isbn_clickable() throws Throwable {
        assertTrue(driver.findElement(By.id("tipisbn")).getTagName().equalsIgnoreCase("a"));
    }
    
    @Then("^test data is added successfully$")
    public void test_data_added() throws Throwable {
        waitForPageChange();
        assertTrue(driver.getCurrentUrl().contains("login"));
    }

    /* helper methods */

    private String getProtocol() {
        try {
            String url = driver.getCurrentUrl();
            if (url != null) {
                String protocol = url.split(":")[0].toLowerCase();
                if (protocol.contains("http")) {
                    return protocol;
                }
            }
        } catch (Exception ex) {
            // fall down to return
        }
        return "http";
    }

    private String getBaseUrl() {
        return getProtocol() + "://localhost:" + port;
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
        browseTo("/readingTips/books/add");
        assertFalse(driver.findElements(By.id("buttonadd")).isEmpty());
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("description")).sendKeys(description);
        //driver.findElement(By.name("url")).sendKeys(url);
        driver.findElement(By.name("author")).sendKeys(author);
        driver.findElement(By.name("isbn")).sendKeys(isbn);
        driver.findElement(By.id("buttonadd")).click();
        waitForPageChange();
    }

    private void check(WebElement checkBox) throws InterruptedException {
        if (!checkBox.isSelected()) {
            checkBox.click();
            Thread.sleep(SLEEPING_TIME);
        }
    }

    private void uncheck(WebElement checkBox) throws InterruptedException {
        if (checkBox.isSelected()) {
            checkBox.click();
            Thread.sleep(SLEEPING_TIME);
        }
    }
    
    private void selectTip() throws InterruptedException{
        driver.findElement(By.id("buttonselect")).click();
        waitForPageChange();
        
    }

    private void createArticleTip(String title, String description, String author) throws InterruptedException {
        browseTo("/readingTips/articles/add");
        assertFalse(driver.findElements(By.id("buttonadd")).isEmpty());
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("description")).sendKeys(description);
        driver.findElement(By.name("author")).sendKeys(author);
        driver.findElement(By.id("buttonadd")).click();
        waitForPageChange();
    }

    private void createLinkTip(String title, String description, String url, String author) throws InterruptedException {
        browseTo("/readingTips/links/add");
        assertFalse(driver.findElements(By.id("buttonadd")).isEmpty());
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("description")).sendKeys(description);
        driver.findElement(By.name("url")).sendKeys(url);
        driver.findElement(By.name("author")).sendKeys(author);
        driver.findElement(By.id("buttonadd")).click();
        waitForPageChange();
    }

    private void createVideoTip(String title, String description, String url, String author) throws InterruptedException {
        browseTo("/readingTips/videos/add");
        assertFalse(driver.findElements(By.id("buttonadd")).isEmpty());
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("description")).sendKeys(description);
        driver.findElement(By.name("url")).sendKeys(url);
        driver.findElement(By.name("author")).sendKeys(author);
        driver.findElement(By.id("buttonadd")).click();
        waitForPageChange();
    }

    private void openTipWithTitle(String title) throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']"));
        
        assertTrue(elements.size() > 0);
        elements.get(0).click();
        waitForPageChange();
        waitForElementWithId("tiptitle");
    }

    private void verifyTipInfo(String title, String description, String author) throws InterruptedException {
        openTipWithTitle(title);
        
        assertEquals(title, driver.findElement(By.id("tiptitle")).getText());
        assertEquals(description, driver.findElement(By.id("tipdescription")).getText());
        assertEquals(author, driver.findElement(By.id("tipauthor")).getText());
    }
}
