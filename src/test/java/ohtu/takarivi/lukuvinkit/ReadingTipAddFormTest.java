
package ohtu.takarivi.lukuvinkit;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.forms.ReadingTipAddForm;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ReadingTipAddFormTest {
    
    @Test
    public void ReadingTipAddFormBook() {
        ReadingTipAddForm rtaf = new ReadingTipAddForm();
        rtaf.setCategory("BOOK");
        rtaf.setTitle("title");
        rtaf.setDescription("description");
        rtaf.setAuthor("author");
        rtaf.setIsbn("978-3-16-148410-0");
        CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip rt = rtaf.createReadingTip(cu);
        assertEquals(new ReadingTip("title", ReadingTipCategory.BOOK, "description", "", "author", "978-3-16-148410-0", cu).toString(), rt.toString());
    }
    @Test
    public void ReadingTipAddFormLink() {
        ReadingTipAddForm rtaf = new ReadingTipAddForm();
        rtaf.setCategory("LINK");
        rtaf.setTitle("title");
        rtaf.setDescription("description");
        rtaf.setAuthor("author");
        rtaf.setUrl("URL");
        CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip rt = rtaf.createReadingTip(cu);
        assertEquals(new ReadingTip("title", ReadingTipCategory.LINK, "description", "URL", "author", "", cu).toString(), rt.toString());
    }
    @Test
    public void ReadingTipAddFormVideo() {
        ReadingTipAddForm rtaf = new ReadingTipAddForm();
        rtaf.setCategory("VIDEO");
        rtaf.setTitle("title");
        rtaf.setDescription("description");
        rtaf.setAuthor("author");
        rtaf.setUrl("URL");
        CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip rt = rtaf.createReadingTip(cu);
        assertEquals(new ReadingTip("title", ReadingTipCategory.VIDEO, "description", "URL", "author", "", cu).toString(), rt.toString());
    }
    @Test
    public void ReadingTipAddFormArticle() {
        ReadingTipAddForm rtaf = new ReadingTipAddForm();
        rtaf.setCategory("ARTICLE");
        rtaf.setTitle("title");
        rtaf.setDescription("description");
        rtaf.setAuthor("author");
        CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip rt = rtaf.createReadingTip(cu);
        assertEquals(new ReadingTip("title", ReadingTipCategory.ARTICLE, "description", "", "author", "", cu).toString(), rt.toString());
    }
    
}
