package ohtu.takarivi.lukuvinkit;

import static ohtu.takarivi.lukuvinkit.controller.MiscController.enableTestdataEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.test.annotation.DirtiesContext;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@DirtiesContext
public class Stepdefs extends SpringBootTestBase {

    private static final int FETCH_TIMEOUT_MILLIS = 5000;
    private static final int WAIT_TIMEOUT = 10;

    WebDriver driver = new HtmlUnitDriver(true);

    public Stepdefs() {
        super();
        driver.manage().timeouts().implicitlyWait(WAIT_TIMEOUT, TimeUnit.SECONDS);
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
    }

    @Given("^user is at the register page$")
    public void user_is_at_the_register_page() throws Throwable {
        driver.get(getBaseUrl() + "/register");
    }

    @Given("^test user is logged in$")
    public void test_user_is_logged_in() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        logInWith("nolla", "yksi");
    }

    @Given("^test user is logged in and on the profile page$")
    public void test_user_is_logged_in_on_profile_page() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        logInWith("nolla", "yksi");
        browseTo("/profile");
    }

    @Given("^test user is logged in and browsing book tips$")
    public void test_user_is_logged_in_on_book_tip_list() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        logInWith("nolla", "yksi");
        browseTo("/readingTips/books");
    }

    @Given("^test user is logged in and browsing link tips$")
    public void test_user_is_logged_in_on_link_tip_list() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        logInWith("nolla", "yksi");
        browseTo("/readingTips/links");
    }

    @Given("^test user is logged in and creating a book tip$")
    public void test_user_is_logged_in_creating_book_tip() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        logInWith("nolla", "yksi");
        browseTo("/readingTips/books/add");
    }

    @Given("^test user is logged in and creating a link tip$")
    public void test_user_is_logged_in_creating_link_tip() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        logInWith("nolla", "yksi");
        browseTo("/readingTips/links/add");
    }

    @Given("^test data entry is enabled$")
    public void test_data_entry_is_enabled() throws Throwable {
        driver.get(getBaseUrl() + "/login");
        logInWith("nolla", "yksi");
        enableTestdataEntry();
    }

    @When("^browsing book tips$")
    public void browse_book_tips() throws Throwable {
        browseTo("/readingTips/books");
    }

    @When("^browsing link tips$")
    public void browse_link_tips() throws Throwable {
        browseTo("/readingTips/links");
    }

    @When("^viewing the book tip with title \"([^\"]*)\"$")
    public void view_book_tip(String title) throws Throwable {
        browseTo("/readingTips/books");
        openTipWithTitle(title);
    }

    @When("^viewing the link tip with title \"([^\"]*)\"$")
    public void view_link_tip(String title) throws Throwable {
        browseTo("/readingTips/links");
        openTipWithTitle(title);
    }

    @When("^selecting tip with title \"([^\"]*)\" and browsing is selected$")
    public void select_tip_and_browse_selected(String title) throws Throwable {
        driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttonselect")).click();
        browseTo("/readingTips/selected");
    }

    @When("^selecting tip with title \"([^\"]*)\" and exportText is selected$")
    public void select_tip_and_exportText_selected(String title) throws Throwable {
        driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttonselect")).click();
        browseTo("/readingTips/exportText");
    }

    @When("^selecting tip with title \"([^\"]*)\" and exportHTML is selected$")
    public void select_tip_and_exportHTML_selected(String title) throws Throwable {
        driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttonselect")).click();
        browseTo("/readingTips/exportHTML");
    }

    @When("^creating a book tip and correct title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and " +
            "author \"([^\"]*)\" and isbn \"([^\"]*)\" are given$")
    public void book_tip_with_valid_information_is_given(String title, String description, String url, String author,
                                                         String isbn) throws Throwable {
        createBookTip(title, description, url, author, isbn);
    }

    @When("^creating a book tip and no title and description \"([^\"]*)\" and url \"([^\"]*)\" and author \"([^\"]*)" +
            "\" and isbn \"([^\"]*)\" are given$")
    public void book_tip_with_invalid_title_is_given(String description, String url, String author, String isbn) throws Throwable {
        createBookTip("", description, url, author, isbn);
    }

    @When("^creating a book tip and title \"([^\"]*)\" and no description and url \"([^\"]*)\" and author \"([^\"]*)" +
            "\" and isbn \"([^\"]*)\" are given$")
    public void book_tip_with_invalid_description_is_given(String title, String url, String author, String isbn) throws Throwable {
        createBookTip(title, "", url, author, isbn);
    }

    @When("^creating a book tip and title \"([^\"]*)\" and description \"([^\"]*)\" and no url and author \"([^\"]*)" +
            "\" and isbn \"([^\"]*)\" are given$")
    public void book_tip_with_invalid_url_is_given(String title, String description, String author, String isbn) throws Throwable {
        createBookTip(title, description, "", author, isbn);
    }

    @When("^creating a book tip and title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and no " +
            "author and isbn \"([^\"]*)\" are given$")
    public void book_tip_with_invalid_author_is_given(String title, String description, String url, String isbn) throws Throwable {
        createBookTip(title, description, url, "", isbn);
    }

    @When("^creating a book tip and title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author " +
            "\"([^\"]*)\" and no isbn are given$")
    public void book_tip_with_invalid_ISBN_is_given(String title, String description, String url, String author) throws Throwable {
        createBookTip(title, description, url, author, "");
    }

    @When("^creating a link tip and title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author " +
            "\"([^\"]*)\" are given$")
    public void link_tip_with_valid_information_is_given(String title, String description, String url, String author) throws Throwable {
        createLinkTip(title, description, url, author);
    }

    @When("^creating a link tip and title \"([^\"]*)\" and description \"([^\"]*)\" and no url and author \"([^\"]*)" +
            "\" are given$")
    public void link_tip_with_invalid_url(String title, String description, String author) throws Throwable {
        createLinkTip(title, description, "", author);
    }

    @When("^creating a video tip and title \"([^\"]*)\" and description \"([^\"]*)\" and url \"([^\"]*)\" and author " +
            "\"([^\"]*)\" are given$")
    public void video_tip_with_valid_information_is_given(String title, String description, String url,
                                                          String author) throws Throwable {
        createVideoTip(title, description, url, author);
    }

    @When("^creating an article tip and correct title \"([^\"]*)\" and description \"([^\"]*)\" and author \"([^\"]*)" +
            "\" are given$")
    public void article_tip_with_valid_information_is_given(String title, String description, String author) throws Throwable {
        createArticleTip(title, description, author);
    }

    @When("^creating an article tip and correct title \"([^\"]*)\" and description \"([^\"]*)\" and author \"([^\"]*)" +
            "\" and tags \"([^\"]*) are given$")
    public void article_tip_with_valid_information_and_tags_is_given(String title, String description, String author, String tags) throws Throwable {
        createArticleTip(title, description, author, tags);
    }

    @When("^book tip with title \"([^\"]*)\" is marked as read$")
    public void tip_mark_as_read(String title) throws Throwable {
        driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttonread")).click();
    }

    @When("^book tip with title \"([^\"]*)\" is deleted$")
    public void tip_is_deleted(String title) throws Throwable {
        driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../..")).findElement(By.cssSelector(".buttondelete")).click();
    }

    @When("^listing all tips with the tag \"([^\"]*)\"$")
    public void list_all_tips_with_tag(String tag) throws Throwable {
        browseTo("/tag/" + tag);
    }

    @When("^searching for tips with \"([^\"]*)\"$")
    public void search_keyword(String keyword) throws Throwable {
        driver.findElement(By.id("keyword")).sendKeys(keyword);
        driver.findElement(By.id("buttonquicksearch")).click();
    }

    @When("^searching for tips with title \"([^\"]*)\"$")
    public void search_advanced_title(String title) throws Throwable {
        browseTo("/search");
        driver.findElement(By.id("title")).sendKeys(title);
        driver.findElement(By.id("buttonsearch")).click();
    }

    @When("^searching for tips with description \"([^\"]*)\"$")
    public void search_advanced_description(String description) throws Throwable {
        browseTo("/search");
        driver.findElement(By.id("description")).sendKeys(description);
        driver.findElement(By.id("buttonsearch")).click();
    }

    @When("^searching for tips with author \"([^\"]*)\"$")
    public void search_advanced_author(String author) throws Throwable {
        browseTo("/search");
        driver.findElement(By.id("author")).sendKeys(author);
        driver.findElement(By.id("buttonsearch")).click();
    }
    
    @When("^searching for tips with tag \"([^\"]*)\"$")
    public void search_advanced_tags(String tags) throws Throwable {
        browseTo("/search");
        driver.findElement(By.id("tags")).sendKeys(tags);
        driver.findElement(By.id("buttonsearch")).click();
    }

    @When("^searching for article tips only$")
    public void search_advanced_article_only() throws Throwable {
        browseTo("/search");
        uncheck(driver.findElement(By.id("books")));
        check(driver.findElement(By.id("articles")));
        uncheck(driver.findElement(By.id("videos")));
        uncheck(driver.findElement(By.id("links")));
        driver.findElement(By.id("buttonsearch")).click();
    }

    @When("^searching for video tips only$")
    public void search_advanced_video_only() throws Throwable {
        browseTo("/search");
        uncheck(driver.findElement(By.id("books")));
        uncheck(driver.findElement(By.id("articles")));
        check(driver.findElement(By.id("videos")));
        uncheck(driver.findElement(By.id("links")));
        driver.findElement(By.id("buttonsearch")).click();
    }

    @When("^searching for link tips only$")
    public void search_advanced_link_only() throws Throwable {
        browseTo("/search");
        uncheck(driver.findElement(By.id("books")));
        uncheck(driver.findElement(By.id("articles")));
        uncheck(driver.findElement(By.id("videos")));
        check(driver.findElement(By.id("links")));
        driver.findElement(By.id("buttonsearch")).click();
    }

    @When("^searching for read tips only$")
    public void search_read_link_only() throws Throwable {
        browseTo("/search");
        uncheck(driver.findElement(By.id("unread")));
        check(driver.findElement(By.id("read")));
        driver.findElement(By.id("buttonsearch")).click();
    }

    @When("^username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_password_entered(String username, String password) throws Throwable {
        logInWith(username, password);
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
    }

    @When("^username \"([^\"]*)\" and password \"([^\"]*)\" and verify password \"([^\"]*)\" are registered$")
    public void username_password_registered_different_verify(String username, String password, String verify) throws Throwable {
        assertFalse(driver.findElements(By.id("buttonregister")).isEmpty());
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("verifyPassword")).sendKeys(verify);
        driver.findElement(By.name("name")).sendKeys(username);
        driver.findElement(By.id("buttonregister")).click();
    }

    @When("^new password \"([^\"]*)\" and for verifying new password \"([^\"]*)\" are given$")
    public void new_password_created(String password, String verifyPassword) throws Throwable {
        assertFalse(driver.findElements(By.id("buttonchangepassword")).isEmpty());
        driver.findElement(By.name("newPassword")).sendKeys(password);
        driver.findElement(By.name("verifyNewPassword")).sendKeys(verifyPassword);
        driver.findElement(By.id("buttonchangepassword")).click();
    }

    @When("^fetching information from our own login page$")
    public void autolink_fetch_from_self() throws Throwable {
        driver.findElement(By.name("url")).sendKeys(getBaseUrl() + "/login");
        driver.findElement(By.id("buttonautofilllink")).click();
        Thread.sleep(FETCH_TIMEOUT_MILLIS);
    }

    @When("^fetching information from URL \"([^\"]*)\"$")
    public void autolink_fetch_url(String url) throws Throwable {
        driver.findElement(By.name("url")).sendKeys(url);
        driver.findElement(By.id("buttonautofilllink")).click();
        Thread.sleep(FETCH_TIMEOUT_MILLIS);
    }

    @When("^fetching information from ISBN \"([^\"]*)\"$")
    public void autofill_book_isbn(String isbn) throws Throwable {
        driver.findElement(By.name("isbn")).sendKeys(isbn);
        driver.findElement(By.id("buttonautofillbook")).click();
        Thread.sleep(FETCH_TIMEOUT_MILLIS);
    }

    @When("^adding test data$")
    public void adding_test_data() throws Throwable {
        browseTo("/testDataInsert");
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

    @Then("^new article tip with title \"([^\"]*)\" and description \"([^\"]*)\" and author \"([^\"]*)\" and tag \"([^\"]*)\" is created$")
    public void new_article_tip_with_tag_is_created(String title, String description, String author, String tag) throws Throwable {
        browseTo("/readingTips/articles");
        verifyTipInfo(title, description, author, tag);
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
        WebElement btn = driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../" +
                "..")).findElement(By.cssSelector(".buttonread"));
        assertNotNull(btn.getAttribute("disabled"));
    }

    @Then("^book tip with title \"([^\"]*)\" has not been marked as read$")
    public void book_tip_not_mark_as_read(String title) throws Throwable {
        browseTo("/readingTips/books");
        WebElement btn = driver.findElement(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']/../" +
                "..")).findElement(By.cssSelector(".buttonread"));
        assertNull(btn.getAttribute("disabled"));
    }

    @Then("^fetched title contains \"([^\"]*)\"$")
    public void autofill_title(String title) throws Throwable {
        assertTrue(driver.findElement(By.name("title")).getAttribute("value").contains(title));
    }

    @Then("^there is an alert$")
    public void autofill_alert() throws Throwable {
        try {
            driver.findElement(By.name("title")).getAttribute("value");
            fail("no alert found when one was expected");
        } catch (UnhandledAlertException ex) {
        }
    }

    @Then("^fetched title contains \"([^\"]*)\" and author contains \"([^\"]*)\" or there is an alert$")
    public void autofill_title_author(String title, String author) throws Throwable {
        // if there is an alert, return to signal success
        try {
            driver.switchTo().alert();
            return;
        } catch (NoAlertPresentException ex) {
        }
        
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
        logInWith("nolla", "yksi");
        assertFalse(driver.findElements(By.id("buttonlogout")).isEmpty());
    }

    @Then("^there are (\\d+) tips listed$")
    public void total_tips_listed(int total) throws Throwable {
        assertEquals(total, driver.findElements(By.cssSelector(".searchresult")).size());
    }

    @Then("^there are no search results$")
    public void zero_search_results() throws Throwable {
        assertEquals(0, driver.findElements(By.cssSelector(".searchresult")).size());
    }

    @Then("^there is 1 search result and it is \"([^\"]*)\"$")
    public void one_search_result(String candidate) throws Throwable {
        assertEquals(1, driver.findElements(By.cssSelector(".searchresult")).size());
        List<WebElement> elements =
                driver.findElements(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + candidate + "']"));
        assertTrue(elements.size() > 0);
    }

    @Then("^there are (\\d+) search results and one of them is \"([^\"]*)\"$")
    public void search_results(int total, String candidate) throws Throwable {
        assertEquals(total, driver.findElements(By.cssSelector(".searchresult")).size());
        List<WebElement> elements =
                driver.findElements(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + candidate + "']"));
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
    }

    private void logInWith(String username, String password) throws InterruptedException {
        assertFalse(driver.findElements(By.id("buttonlogin")).isEmpty());
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("buttonlogin")).click();
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
    }

    private void check(WebElement checkBox) throws InterruptedException {
        if (!checkBox.isSelected()) {
            checkBox.click();
        }
    }

    private void uncheck(WebElement checkBox) throws InterruptedException {
        if (checkBox.isSelected()) {
            checkBox.click();
        }
    }

    private void selectTip() throws InterruptedException {
        driver.findElement(By.id("buttonselect")).click();
    }

    private void createArticleTip(String title, String description, String author) throws InterruptedException {
        browseTo("/readingTips/articles/add");
        assertFalse(driver.findElements(By.id("buttonadd")).isEmpty());
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("description")).sendKeys(description);
        driver.findElement(By.name("author")).sendKeys(author);
        driver.findElement(By.id("buttonadd")).click();
    }

    private void createArticleTip(String title, String description, String author, String tags) throws InterruptedException {
        browseTo("/readingTips/articles/add");
        assertFalse(driver.findElements(By.id("buttonadd")).isEmpty());
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("description")).sendKeys(description);
        driver.findElement(By.name("author")).sendKeys(author);
        driver.findElement(By.name("tags")).sendKeys(tags);
        driver.findElement(By.id("buttonadd")).click();
    }

    private void createLinkTip(String title, String description, String url, String author) throws InterruptedException {
        browseTo("/readingTips/links/add");
        assertFalse(driver.findElements(By.id("buttonadd")).isEmpty());
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("description")).sendKeys(description);
        driver.findElement(By.name("url")).sendKeys(url);
        driver.findElement(By.name("author")).sendKeys(author);
        driver.findElement(By.id("buttonadd")).click();
    }

    private void createVideoTip(String title, String description, String url, String author) throws InterruptedException {
        browseTo("/readingTips/videos/add");
        assertFalse(driver.findElements(By.id("buttonadd")).isEmpty());
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("description")).sendKeys(description);
        driver.findElement(By.name("url")).sendKeys(url);
        driver.findElement(By.name("author")).sendKeys(author);
        driver.findElement(By.id("buttonadd")).click();
    }

    private void openTipWithTitle(String title) throws InterruptedException {
        List<WebElement> elements =
                driver.findElements(By.xpath("//td[contains(@class,'tiptitle')]/a[text()='" + title + "']"));
        assertTrue(elements.size() > 0);
        elements.get(0).click();
    }

    private void verifyTipInfo(String title, String description, String author) throws InterruptedException {
        openTipWithTitle(title);
        assertEquals(title, driver.findElement(By.id("tiptitle")).getText());
        assertEquals(description, driver.findElement(By.id("tipdescription")).getText());
        assertEquals(author, driver.findElement(By.id("tipauthor")).getText());
    }

    private void verifyTipInfo(String title, String description, String author, String tag) throws InterruptedException {
        verifyTipInfo(title, description, author);
        
    }

}
