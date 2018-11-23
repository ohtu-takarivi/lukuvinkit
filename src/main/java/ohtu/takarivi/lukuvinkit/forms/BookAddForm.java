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
    @Size(min = ISBN_MIN_LENGTH, max = ISBN_MAX_LENGTH, message = "ISBN:n pituus väliviivoineen 13-17 merkkiä")
    private String isbn;

    public void validateRest(BindingResult result) {
        if (checkIfBadISBN()) {
            result.rejectValue("isbn", "", "Huono ISBN");
        }
    }

    /**
     * Checks if ISBN 13 is bad.
     *  
     * @param isbn The value that is checked.
     * @return Return true if ISBN 13 is bad.
     */
    private boolean checkIfBadISBN() {
        String isbnInteger = this.isbn;
        if (isbn.length() > 13) {
            if (!isbn.contains("-")){
                return true;
            } 
            isbnInteger = isbnInteger.replaceAll("-", "");
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
        int result = sum %10;
        if (result != 0) {
            result = 10-result;
        }
        if (result != Character.getNumericValue(isbnInteger.charAt(12))) {
            return false;
        }
        return false;
    }

}
