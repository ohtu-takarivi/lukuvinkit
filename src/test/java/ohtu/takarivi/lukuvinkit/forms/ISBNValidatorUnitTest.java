package ohtu.takarivi.lukuvinkit.forms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ISBNValidatorUnitTest {
    @Test
    public void acceptsValidISBN() {
        assertTrue(BookAddForm.isValidISBN("978-3-16-148410-0"));
    }

    @Test
    public void rejectsISBNWithWrongCheckNumber() {
        assertFalse(BookAddForm.isValidISBN("978-3-16-148410-3"));
    }

    @Test
    public void rejectsISBNThatIsTooLong() {
        assertFalse(BookAddForm.isValidISBN("978-3-16-1448410-3"));
    }

    @Test
    public void rejectsISBNThatIsTooShort() {
        assertFalse(BookAddForm.isValidISBN("978-3-16-18410-3"));
    }

    @Test
    public void rejectsISBNWithNonNumeric() {
        assertFalse(BookAddForm.isValidISBN("978-3-1b-148410-3"));
    }
}
