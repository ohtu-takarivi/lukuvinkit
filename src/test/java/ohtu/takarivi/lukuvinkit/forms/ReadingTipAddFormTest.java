package ohtu.takarivi.lukuvinkit.forms;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.forms.ReadingTipAddForm;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ReadingTipAddFormTest {
    
    ReadingTipAddForm rtaf;
    CustomUser cu;
    BindingResult br;
    
    @Before
    public void setUp() {
        rtaf = new ReadingTipAddForm();
        cu = new CustomUser("user", "password", "name");
        br = new BeanPropertyBindingResult(rtaf, "");
    }
    
    @Test
    public void canCreateBooksWithCorrectInformationThroughForm() {
        rtaf.setCategory("BOOK");
        rtaf.setTitle("title");
        rtaf.setDescription("description");
        rtaf.setAuthor("author");
        rtaf.setIsbn("978-3-16-148410-0");
        
        rtaf.validateRest(br);
        assertFalse(br.hasErrors());
        
        ReadingTip rt = rtaf.createReadingTip(cu);
        assertEquals(new ReadingTip("title", ReadingTipCategory.BOOK, "description", "", "author", "978-3-16-148410-0", cu).toString(), rt.toString());
    }
    
    @Test
    public void canCreateLinksWithCorrectInformationThroughForm() {
        rtaf.setCategory("LINK");
        rtaf.setTitle("title");
        rtaf.setDescription("description");
        rtaf.setAuthor("author");
        rtaf.setUrl("https://example.com/");
        
        rtaf.validateRest(br);
        assertFalse(br.hasErrors());
        
        ReadingTip rt = rtaf.createReadingTip(cu);
        assertEquals(new ReadingTip("title", ReadingTipCategory.LINK, "description", "https://example.com/", "author", "", cu).toString(), rt.toString());
    }
    
    @Test
    public void canCreateVideosWithCorrectInformationThroughForm() {
        rtaf.setCategory("VIDEO");
        rtaf.setTitle("title");
        rtaf.setDescription("description");
        rtaf.setAuthor("author");
        rtaf.setUrl("https://example.com/");
        
        rtaf.validateRest(br);
        assertFalse(br.hasErrors());
        
        ReadingTip rt = rtaf.createReadingTip(cu);
        assertEquals(new ReadingTip("title", ReadingTipCategory.VIDEO, "description", "https://example.com/", "author", "", cu).toString(), rt.toString());
    }
    
    @Test
    public void canCreateArticlesWithCorrectInformationThroughForm() {
        rtaf.setCategory("ARTICLE");
        rtaf.setTitle("title");
        rtaf.setDescription("description");
        rtaf.setAuthor("author");
        
        rtaf.validateRest(br);
        assertFalse(br.hasErrors());
        
        ReadingTip rt = rtaf.createReadingTip(cu);
        assertEquals(new ReadingTip("title", ReadingTipCategory.ARTICLE, "description", "", "author", "", cu).toString(), rt.toString());
    }
    
    @Test
    public void cannotCreateBooksWithInvalidISBNThroughForm() {
        rtaf.setCategory("BOOK");
        rtaf.setTitle("title");
        rtaf.setDescription("description");
        rtaf.setAuthor("author");
        rtaf.setIsbn("978-3-16-148410-4");
        
        rtaf.validateRest(br);
        assertTrue(br.hasErrors());
    }
    
    @Test
    public void cannotCreateLinksWithInvalidURLThroughForm() {
        rtaf.setCategory("LINK");
        rtaf.setTitle("title");
        rtaf.setDescription("description");
        rtaf.setAuthor("author");
        rtaf.setUrl("URL");
        
        rtaf.validateRest(br);
        assertTrue(br.hasErrors());
    }
    
}
