package ohtu.takarivi.lukuvinkit.forms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipTag;

public class ReadingTipAddFormTest {

    Set<ReadingTipTag> emptyTags;
    ReadingTipAddForm rtaf;
    CustomUser cu;
    BindingResult br;
    
    @Before
    public void setUp() {
        emptyTags = new HashSet<>();
        rtaf = new ReadingTipAddForm();
        cu = new CustomUser("user", "password", "name");
        br = new BeanPropertyBindingResult(rtaf, "");
    }
    
    @Test
    public void canCreateBooksWithCorrectInformationThroughForm() {
        setCategoryTitleDescriptionAuthorTags(rtaf, "BOOK", "title", "description", "author", "");
        rtaf.setIsbn("978-3-16-148410-0");
        
        rtaf.validateRest(br);
        assertFalse(br.hasErrors());
        
        ReadingTip rt = rtaf.createReadingTip(cu, null);
        assertEquals(new ReadingTip("title", ReadingTipCategory.BOOK, "description", "", "author", "978-3-16-148410-0", emptyTags, cu).toString(), rt.toString());
    }
    
    @Test
    public void canCreateLinksWithCorrectInformationThroughForm() {
        setCategoryTitleDescriptionAuthorTags(rtaf, "LINK", "title", "description", "author", "");
        rtaf.setUrl("https://example.com/");
        
        rtaf.validateRest(br);
        assertFalse(br.hasErrors());
        
        ReadingTip rt = rtaf.createReadingTip(cu, null);
        assertEquals(new ReadingTip("title", ReadingTipCategory.LINK, "description", "https://example.com/", "author", "", emptyTags, cu).toString(), rt.toString());
    }
    
    @Test
    public void canCreateVideosWithCorrectInformationThroughForm() {
        setCategoryTitleDescriptionAuthorTags(rtaf, "VIDEO", "title", "description", "author", "");
        rtaf.setUrl("https://example.com/");
        
        rtaf.validateRest(br);
        assertFalse(br.hasErrors());
        
        ReadingTip rt = rtaf.createReadingTip(cu, null);
        assertEquals(new ReadingTip("title", ReadingTipCategory.VIDEO, "description", "https://example.com/", "author", "", emptyTags, cu).toString(), rt.toString());
    }
    
    @Test
    public void canCreateArticlesWithCorrectInformationThroughForm() {
        setCategoryTitleDescriptionAuthorTags(rtaf, "ARTICLE", "title", "description", "author", "");
        
        rtaf.validateRest(br);
        assertFalse(br.hasErrors());
        
        ReadingTip rt = rtaf.createReadingTip(cu, null);
        assertEquals(new ReadingTip("title", ReadingTipCategory.ARTICLE, "description", "", "author", "", emptyTags, cu).toString(), rt.toString());
    }
    
    @Test
    public void cannotCreateBooksWithInvalidISBNThroughForm() {
        setCategoryTitleDescriptionAuthorTags(rtaf, "BOOK", "title", "description", "author", "");
        rtaf.setIsbn("978-3-16-148410-4");
        
        rtaf.validateRest(br);
        assertTrue(br.hasErrors());
    }
    
    @Test
    public void cannotCreateBooksWithInvalidISBNLengthThroughForm() {
        setCategoryTitleDescriptionAuthorTags(rtaf, "BOOK", "title", "description", "author", "");
        rtaf.setIsbn("978-3-16-1484165630-4");
        
        rtaf.validateRest(br);
        assertTrue(br.hasErrors());
    }
    
    @Test
    public void cannotCreateLinksWithInvalidURLThroughForm() {
        setCategoryTitleDescriptionAuthorTags(rtaf, "LINK", "title", "description", "author", "");
        rtaf.setUrl("URL");
        
        rtaf.validateRest(br);
        assertTrue(br.hasErrors());
    }
    
    @Test
    public void cannotCreateLinksWithInvalidURLLengthThroughForm() {
        final String LONG_STRING = "AAAAAAAAAAAAAAAAAAAAAAAA".replace("A","AAAAAAAAAAAAAAAAAAAAAAAA");

        setCategoryTitleDescriptionAuthorTags(rtaf, "LINK", "title", "description", "author", "");
        rtaf.setUrl(LONG_STRING);
        
        rtaf.validateRest(br);
        assertTrue(br.hasErrors());
    }

    private void setCategoryTitleDescriptionAuthorTags(ReadingTipAddForm rtaf, String category, String title,
            String description, String author, String tags) {
        rtaf.setCategory(category);
        rtaf.setTitle(title);
        rtaf.setDescription(description);
        rtaf.setAuthor(author);
        rtaf.setTags(tags);
    }
    
}
