package ohtu.takarivi.lukuvinkit;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import org.junit.Test;


public class ReadingTipTest {

    @Test
    public void constructorNoException() {
        CustomUser cu = new CustomUser("user", "password", "name");
        new ReadingTip("title", "description", "url", cu);
    }

}
