package ohtu.takarivi.lukuvinkit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Test;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;

import ohtu.takarivi.lukuvinkit.domain.ReadingTip;

public class CustomUserTest {

    @Test
    public void constructorNoException() {
	new CustomUser("user", "password", "name");
    }

    @Test
    public void canGetUsername() {
        CustomUser cu = new CustomUser("user", "password", "name");
	assertEquals("user", cu.getUsername());
    }

    @Test
    public void canGetPassword() {
	CustomUser cu = new CustomUser("user", "password", "name");
	assertEquals("password", cu.getPassword());
    }

    @Test
    public void canGetName() {
	CustomUser cu = new CustomUser("user", "password", "name");
	assertEquals("name", cu.getName());
    }

    @Test
    public void canSetUsername() {
	CustomUser cu = new CustomUser("user", "password", "name");
	cu.setUsername("user2");
	assertEquals("user2", cu.getUsername());
    }

    @Test
    public void canSetPassword() {
	CustomUser cu = new CustomUser("user", "password", "name");
	cu.setPassword("password2");
	assertEquals("password2", cu.getPassword());
    }

    @Test
    public void canSetName() {
    	CustomUser cu = new CustomUser("user", "password", "name");
	cu.setName("name2");
	assertEquals("name2", cu.getName());
    }

    @Test
    public void canSetReadingTips() {
    	CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip readingTip = new ReadingTip("title", "description", "url", cu);
        List<ReadingTip> readingTips = new ArrayList<>();
        readingTips.add(readingTip);
        cu.setReadingTips(readingTips);
        assertEquals(readingTip, cu.getReadingTips().get(0));
    }

    @Test
    public void canGetReadingTips() {
    	CustomUser cu = new CustomUser("user", "password", "name");
        ReadingTip readingTip = new ReadingTip("title", "description", "url", cu);
        List<ReadingTip> readingTips = new ArrayList<>();
        readingTips.add(readingTip);
        cu.setReadingTips(readingTips);
        assertEquals(readingTip, cu.getReadingTips().get(0));
    }

    @Test
    public void validUsernameIsValid() {
    	CustomUser cu = new CustomUser("user", "password", "name");
        assertEquals(true, cu.isValidUsername(cu.getUsername()));
    }

    @Test
    public void invalidUsernameIsInvalid() {
    	CustomUser cu = new CustomUser("u", "password", "name");
        assertEquals(false, cu.isValidUsername(cu.getUsername()));
    }
}
