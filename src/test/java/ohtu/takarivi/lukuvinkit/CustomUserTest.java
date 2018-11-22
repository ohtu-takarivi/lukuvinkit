package ohtu.takarivi.lukuvinkit;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomUserTest {

    CustomUser cu;

    @Before
    public void before() {
        cu = new CustomUser("user", "password", "name");
    }

//    @Test
//    public void validUsernameIsValid() {
//        assertTrue(CustomUser.isValidUsername("user"));
//    }
//
//    @Test
//    public void tooShortUsernameIsInvalid() {
//        assertFalse(CustomUser.isValidUsername("u"));
//    }
//
//    @Test
//    public void tooLongUsernameIsInvalid() {
//        assertFalse(CustomUser.isValidUsername("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
//    }
//
//    @Test
//    public void emptyUsernameIsInvalid() {
//        assertFalse(CustomUser.isValidUsername(""));
//    }
//
//    @Test
//    public void specialCharacterUsernameIsInvalid() {
//        assertFalse(CustomUser.isValidUsername("%%%%%"));
//    }

}
