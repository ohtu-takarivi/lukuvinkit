package ohtu.takarivi.lukuvinkit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;

public class CustomUserTest {
    
    CustomUser cu;

    @Before
    public void before() {
        cu = new CustomUser("user", "password", "name");
    }

    @Test
    public void canGetUsername() {
        assertEquals("user", cu.getUsername());
    }

    @Test
    public void canGetPassword() {
        assertEquals("password", cu.getPassword());
    }

    @Test
    public void canGetName() {
        assertEquals("name", cu.getName());
    }

    @Test
    public void canSetUsername() {
        cu.setUsername("user2");
        assertEquals("user2", cu.getUsername());
    }

    @Test
    public void canSetPassword() {
        cu.setPassword("password2");
        assertEquals("password2", cu.getPassword());
    }

    @Test
    public void canSetName() {
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
    public void validUsernameIsValid() {
        assertEquals(true, CustomUser.isValidUsername("user"));
    }

    @Test
    public void tooShortUsernameIsInvalid() {
        assertEquals(false, CustomUser.isValidUsername("u"));
    }

    @Test
    public void tooLongUsernameIsInvalid() {
        assertEquals(false, CustomUser.isValidUsername("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    public void emptyUsernameIsInvalid() {
        assertEquals(false, CustomUser.isValidUsername(""));
    }

    @Test
    public void specialCharacterUsernameIsInvalid() {
        assertEquals(false, CustomUser.isValidUsername("%%%%%"));
    }
}
