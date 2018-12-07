package ohtu.takarivi.lukuvinkit.domain;

import org.junit.Test;

public class CustomUserTest {

    @Test
    public void constructorNoException() {
        new CustomUser("user", "password", "name");
    }

}
