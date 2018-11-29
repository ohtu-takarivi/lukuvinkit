package ohtu.takarivi.lukuvinkit.forms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ISBNValidatorUnitTest {
    @Test
    public void acceptsValidISBN10AsISBN() {
        assertTrue(BookAddForm.isValidISBN("951-98548-9-4"));
    }
    
    @Test
    public void acceptsValidISBN13AsISBN() {
        assertTrue(BookAddForm.isValidISBN("978-3-16-148410-0"));
    }
    
    @Test
    public void acceptsValidISBN10() {
        assertTrue(BookAddForm.isValidISBN10("951-98548-9-4"));
    }
    
    @Test
    public void acceptsValidISBN10WithCheckX() {
        assertTrue(BookAddForm.isValidISBN10("0-8044-2957-X"));
    }
    
    @Test
    public void acceptsValidISBN10WithoutDashes() {
        assertTrue(BookAddForm.isValidISBN10("9519854894"));
    }

    @Test
    public void rejectsISBN10WithWrongCheckNumber() {
        assertFalse(BookAddForm.isValidISBN10("951-98548-9-3"));
    }

    @Test
    public void rejectsISBN10ThatIsTooLong() {
        assertFalse(BookAddForm.isValidISBN10("951-985748-9-4"));
    }

    @Test
    public void rejectsISBN10ThatIsTooShort() {
        assertFalse(BookAddForm.isValidISBN10("951-9548-9-4"));
    }

    @Test
    public void rejectsISBN10WithInvalidChars() {
        assertFalse(BookAddForm.isValidISBN10("951-9F548-9-4"));
    }
    
    @Test
    public void acceptsValidISBN13() {
        assertTrue(BookAddForm.isValidISBN13("978-3-16-148410-0"));
    }
    
    @Test
    public void acceptsValidISBN13WithoutDashes() {
        assertTrue(BookAddForm.isValidISBN13("9781938168208"));
    }

    @Test
    public void rejectsISBN13WithWrongCheckNumber() {
        assertFalse(BookAddForm.isValidISBN13("978-3-16-148410-3"));
    }

    @Test
    public void rejectsISBN13ThatIsTooLong() {
        assertFalse(BookAddForm.isValidISBN13("978-3-16-1448410-3"));
    }

    @Test
    public void rejectsISBN13ThatIsTooShort() {
        assertFalse(BookAddForm.isValidISBN13("978-3-16-18410-3"));
    }

    @Test
    public void rejectsISBN13WithNonNumeric() {
        assertFalse(BookAddForm.isValidISBN13("978-3-1b-148410-3"));
    }
}
