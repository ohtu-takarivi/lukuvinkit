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

    //CHECKSTYLE:OFF: MagicNumber
    /**
     * Checks if the given ISBN-13 is valid.
     *
     * @param isbn The value that is checked.
     * @return Return true if the given input is a valid ISBN-13 code and false if it is not.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public static boolean isValidISBN(String isbn) {
        String isbnInteger = isbn;
        if (isbnInteger.length() == 17) {
            isbnInteger = isbnInteger.replaceAll("-", "");
        }
        if (isbnInteger.length() != 13) {
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
