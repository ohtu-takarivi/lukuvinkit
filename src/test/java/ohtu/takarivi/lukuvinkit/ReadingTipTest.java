package ohtu.takarivi.lukuvinkit;

import static org.junit.Assert.*;

import org.junit.Test;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;

import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import org.junit.Ignore;


public class ReadingTipTest {

    @Test
    public void constructorNoException() {
        CustomUser cu = new CustomUser("user", "password", "name");
        new ReadingTip("title", "description", "url", cu);
    }

    @Test
    public void canGetTitle() {
        CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip readingTip = new ReadingTip("title", "description", "url", cu);
        assertEquals("title", readingTip.getTitle());
    }

    @Test
    public void canSetTitle() {
        CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip readingTip = new ReadingTip("title", "description", "url", cu);
        readingTip.setTitle("title2");
        assertEquals("title2", readingTip.getTitle());
    }
    
    @Test
    public void canGetDescription() {
        CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip readingTip = new ReadingTip("title", "description", "url", cu);
        assertEquals("description", readingTip.getDescription());
    }

    @Test
    public void canSetDescription() {
        CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip readingTip = new ReadingTip("title", "description", "url", cu);
        readingTip.setDescription("description2");
        assertEquals("description2", readingTip.getDescription());
    }

    @Test
    public void canGetUrl() {
        CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip readingTip = new ReadingTip("title", "description", "url", cu);
        assertEquals("url", readingTip.getUrl());
    }

    @Test
    public void canSetUrl() {
        CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip readingTip = new ReadingTip("title", "description", "url", cu);
        readingTip.setUrl("url2");
        assertEquals("url2", readingTip.getUrl());
    }

    @Test
    public void canGetCustomUser() {
        CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip readingTip = new ReadingTip("title", "description", "url", cu);
        assertEquals("user", readingTip.getCustomUser().getUsername());
    }

    @Test
    public void canSetCustomUser() {
        CustomUser cu = new CustomUser("user", "password", "name");
        CustomUser cu2 = new CustomUser("user2", "password", "name");
        ReadingTip readingTip = new ReadingTip("title", "description", "url", cu);
        readingTip.setCustomUser(cu2);
        assertEquals("user2", readingTip.getCustomUser().getUsername());
    }
}