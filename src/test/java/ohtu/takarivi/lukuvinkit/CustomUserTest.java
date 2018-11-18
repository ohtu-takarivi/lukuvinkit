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
    public void canSetAndGetReadingTips() {
        List<ReadingTip> readingTips = new ArrayList<>();
        readingTips.add(new ReadingTip());
        cu.setReadingTips(readingTips);
        assertEquals(readingTips, cu.getReadingTips());
    }
    
    @Test
    public void validateUsername() {
        assertTrue(CustomUser.isValidUsername("username"));
        assertTrue(CustomUser.isValidUsername("user_name"));
        assertTrue(CustomUser.isValidUsername("user1337"));
        assertFalse(CustomUser.isValidUsername("x"));
        assertFalse(CustomUser.isValidUsername("an_user_name_that_is_way_too_long_and_reads_more_like_a_story"));
        assertFalse(CustomUser.isValidUsername("SpecialChars!"));
        assertFalse(CustomUser.isValidUsername("alpha beta"));
        assertFalse(CustomUser.isValidUsername(""));
    }
}
