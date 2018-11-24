package ohtu.takarivi.lukuvinkit.forms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class URLValidatorUnitTest {
    @Test
    public void acceptsValidURL() {
        assertTrue(LinkAddForm.isValidURL("https://example.com/"));
    }

    @Test
    public void rejectsURLWithoutProtocol() {
        assertFalse(LinkAddForm.isValidURL("://example.com/"));
    }

    @Test
    public void rejectsURLWithWhitespace() {
        assertFalse(LinkAddForm.isValidURL("https://example.com/           .txt"));
    }

    @Test
    public void rejectsURLWithInvalidDomain() {
        assertFalse(LinkAddForm.isValidURL("https://<b>example.com/"));
    }
}
