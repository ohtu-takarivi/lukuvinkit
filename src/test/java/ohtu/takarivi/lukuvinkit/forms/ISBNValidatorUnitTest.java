package ohtu.takarivi.lukuvinkit.forms;

import org.junit.Test;

import static ohtu.takarivi.lukuvinkit.forms.FormUtils.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ISBNValidatorUnitTest {

    @Test
    public void acceptsValidISBN10AsISBN() {
        assertTrue(isValidISBN("951-98548-9-4"));
    }

    @Test
    public void acceptsValidISBN13AsISBN() {
        assertTrue(isValidISBN("978-3-16-148410-0"));
    }

    @Test
    public void acceptsValidISBN10() {
        assertTrue(isValidISBN10("951-98548-9-4"));
    }

    @Test
    public void acceptsValidISBN10WithCheckX() {
        assertTrue(isValidISBN10("0-8044-2957-X"));
    }

    @Test
    public void acceptsValidISBN10WithoutDashes() {
        assertTrue(isValidISBN10("9519854894"));
    }

    @Test
    public void rejectsISBN10WithWrongCheckNumber() {
        assertFalse(isValidISBN10("951-98548-9-3"));
    }

    @Test
    public void rejectsISBN10ThatIsTooLong() {
        assertFalse(isValidISBN10("951-985748-9-4"));
    }

    @Test
    public void rejectsISBN10ThatIsTooShort() {
        assertFalse(isValidISBN10("951-9548-9-4"));
    }

    @Test
    public void rejectsISBN10WithInvalidChars() {
        assertFalse(isValidISBN10("951-9F548-9-4"));
    }

    @Test
    public void acceptsValidISBN13() {
        assertTrue(isValidISBN13("978-3-16-148410-0"));
    }

    @Test
    public void acceptsValidISBN13WithoutDashes() {
        assertTrue(isValidISBN13("9781938168208"));
    }

    @Test
    public void rejectsISBN13WithWrongCheckNumber() {
        assertFalse(isValidISBN13("978-3-16-148410-3"));
    }

    @Test
    public void rejectsISBN13ThatIsTooLong() {
        assertFalse(isValidISBN13("978-3-16-1448410-3"));
    }

    @Test
    public void rejectsISBN13ThatIsTooShort() {
        assertFalse(isValidISBN13("978-3-16-18410-3"));
    }

    @Test
    public void rejectsISBN13WithNonNumeric() {
        assertFalse(isValidISBN13("978-3-1b-148410-3"));
    }

}
