package ohtu.takarivi.lukuvinkit.forms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BookAddForm {

    private static final int TITLE_MIN_LENGTH = 1;
    private static final int TITLE_MAX_LENGTH = 255;
    private static final int DESCRIPTION_MIN_LENGTH = 1;
    private static final int DESCRIPTION_MAX_LENGTH = 255;
    private static final int AUTHOR_MIN_LENGTH = 1;
    private static final int AUTHOR_MAX_LENGTH = 255;
    private static final int ISBN_MIN_LENGTH = 13;
    private static final int ISBN_MAX_LENGTH = 17;
    private static final String ISBN_10_CHECK = "0123456789X";

    @NotEmpty
    @Size(min = TITLE_MIN_LENGTH, max = TITLE_MAX_LENGTH, message = "Otsikon pituus 1-255 merkkiä")
    private String title;
    @NotEmpty
    @Size(min = DESCRIPTION_MIN_LENGTH, max = DESCRIPTION_MAX_LENGTH, message = "Kuvauksen pituus 1-255 merkkiä")
    private String description;
    @NotEmpty
    @Size(min = AUTHOR_MIN_LENGTH, max = AUTHOR_MAX_LENGTH, message = "Tekijän nimen pituus 1-255 merkkiä")
    private String author;
    @NotEmpty
    @Size(min = ISBN_MIN_LENGTH, max = ISBN_MAX_LENGTH, message = "ISBN-13 tunnuksen pituus 13-17 merkkiä")
    private String isbn;

    /**
     * Used to run additional validation for this form.
     * 
     * @param result The BindingResult that value rejections are submitted to.
     */
    public void validateRest(BindingResult result) {
        if (!isValidISBN(this.isbn)) {
            result.rejectValue("isbn", "", "Huono ISBN");
        }
    }

    /**
     * Checks if the given code is a valid ISBN-10 or ISBN-13.
     *
     * @param isbn The value that is checked.
     * @return Return true if the given input is a valid ISBN-10 or ISBN-13 code and false if it is not.
     */
    public static boolean isValidISBN(String isbn) {
        return isValidISBN10(isbn) || isValidISBN13(isbn);
    }

    //CHECKSTYLE:OFF: MagicNumber
    /**
     * Checks if the given ISBN-10 is valid.
     *
     * @param isbn The value that is checked.
     * @return Return true if the given input is a valid ISBN-10 code and false if it is not.
     */
    public static boolean isValidISBN10(String isbn) {
        String isbnInteger = isbn.replace("-", "").replace(" ", "");
        if (isbnInteger.length() != 10 || !isbnInteger.matches("^[0-9]+[0-9xX]$")) {
            return false;
        }
        int sum = 0, result = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(isbnInteger.charAt(i));
            result += sum;
        }
        result = (11 - ((result + sum) % 11)) % 11;
        if (ISBN_10_CHECK.charAt(result) != isbnInteger.toUpperCase().charAt(9)) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks if the given ISBN-13 is valid.
     *
     * @param isbn The value that is checked.
     * @return Return true if the given input is a valid ISBN-13 code and false if it is not.
     */
    public static boolean isValidISBN13(String isbn) {
        String isbnInteger = isbn.replace("-", "").replace(" ", "");
        if (isbnInteger.length() != 13 || !isbnInteger.matches("^[0-9]+$")) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int charToNum = Character.getNumericValue(isbnInteger.charAt(i));
            if (i % 2 == 0) {
                sum += 1 * charToNum;
            } else {
                sum += 3 * charToNum;
            }
        }
        int result = sum % 10;
        if (result != 0) {
            result = 10 - result;
        }
        if (result != Character.getNumericValue(isbnInteger.charAt(12))) {
            return false;
        }
        return true;
    }
    //CHECKSTYLE:ON: MagicNumber

}
