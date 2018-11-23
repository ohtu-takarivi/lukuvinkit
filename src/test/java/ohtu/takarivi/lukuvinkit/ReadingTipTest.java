package ohtu.takarivi.lukuvinkit;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;

import org.junit.Test;


public class ReadingTipTest {

    @Test
    public void constructorNoException() {
        CustomUser cu = new CustomUser("user", "password", "name");
        new ReadingTip("title", ReadingTipCategory.BOOK, "description", "url", "author", cu);
    }

}
