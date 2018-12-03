package ohtu.takarivi.lukuvinkit.forms;

import org.junit.Test;

import static ohtu.takarivi.lukuvinkit.forms.FormUtils.isValidURL;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class URLValidatorUnitTest {

    @Test
    public void acceptsValidURL() {
        String url = "https://example.com/";
        assertTrue(isValidURL(url));
    }

    @Test
    public void acceptsValidURLWithSubdomain() {
        String url = "https://www.example.com/";
        assertTrue(isValidURL(url));
    }

    @Test
    public void acceptsValidURLWithPort() {
        String url = "https://example.com:443/";
        assertTrue(isValidURL(url));
    }

    @Test
    public void rejectsURLWithoutProtocol() {
        String url = "://example.com/";
        assertFalse(isValidURL(url));
    }

    @Test
    public void rejectsURLWithWhitespace() {
        String url = "https://example.com/           .txt";
        assertFalse(isValidURL(url));
    }

    @Test
    public void rejectsURLWithInvalidDomain() {
        String url = "https://<b>example.com/";
        assertFalse(isValidURL(url));
    }

}
