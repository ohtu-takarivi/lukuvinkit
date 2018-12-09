package ohtu.takarivi.lukuvinkit.domain;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class ReadingTipTest {

    private static final Set<ReadingTipTag> emptyTags = new HashSet<>();
    CustomUser user;

    @Before
    public void beforeTest() {
        user = new CustomUser("username", "", "name");
    }

    @Test
    public void toStringContainsTitle() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.ARTICLE, "description", "url", "author", "isbn", emptyTags, user).toString().contains("title"));
    }

    @Test
    public void toStringContainsDescription() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.VIDEO, "description", "url", "author", "isbn", emptyTags, user).toString().contains("description"));
    }

    @Test
    public void toStringContainsAuthor() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.ARTICLE, "description", "url", "author", "isbn", emptyTags, user).toString().contains("author"));
    }

    @Test
    public void toStringContainsIsbnForBook() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.BOOK, "description", "url", "author", "isbn", emptyTags, user).toString().contains("isbn"));
    }

    @Test
    public void toStringContainsUrlForLink() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.LINK, "description", "url", "author", "isbn", emptyTags, user).toString().contains("url"));
    }

    @Test
    public void toStringContainsUrlForVideo() {
        assertTrue(new ReadingTip("title", ReadingTipCategory.VIDEO, "description", "url", "author", "isbn", emptyTags, user).toString().contains("url"));
    }

    @Test
    public void constructorNoException() {
        new ReadingTip("title", ReadingTipCategory.BOOK, "description", "url", "author", "978-3-16-148410-0", emptyTags, user);
    }

}
