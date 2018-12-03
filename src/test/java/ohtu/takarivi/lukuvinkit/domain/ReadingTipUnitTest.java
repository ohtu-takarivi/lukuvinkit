package ohtu.takarivi.lukuvinkit.domain;

import static ohtu.takarivi.lukuvinkit.forms.FormUtils.isValidISBN;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;

public class ReadingTipUnitTest {
    
    CustomUser user;
    ReadingTip tip;
    
    @Before
    public void beforeTest() {
        user = new CustomUser("username", "", "name");
    }

    @Test
    public void toStringContainsTitle() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.ARTICLE, "description", "url", "author", "isbn", user).toString().contains("title"));
    }

    @Test
    public void toStringContainsDescription() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.VIDEO, "description", "url", "author", "isbn", user).toString().contains("description"));
    }

    @Test
    public void toStringContainsAuthor() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.ARTICLE, "description", "url", "author", "isbn", user).toString().contains("author"));
    }

    @Test
    public void toStringContainsIsbnForBook() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.BOOK, "description", "url", "author", "isbn", user).toString().contains("isbn"));
    }

    @Test
    public void toStringContainsUrlForLink() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.LINK, "description", "url", "author", "isbn", user).toString().contains("url"));
    }

    @Test
    public void toStringContainsUrlForVideo() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.VIDEO, "description", "url", "author", "isbn", user).toString().contains("url"));
    }

}
