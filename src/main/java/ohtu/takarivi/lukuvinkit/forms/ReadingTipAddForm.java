package ohtu.takarivi.lukuvinkit.forms;

import lombok.Getter;
import lombok.Setter;
import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ReadingTipAddForm {

    private static final int TITLE_MIN_LENGTH = 1;
    private static final int TITLE_MAX_LENGTH = 255;
    private static final int DESCRIPTION_MIN_LENGTH = 1;
    private static final int DESCRIPTION_MAX_LENGTH = 255;
    private static final int AUTHOR_MIN_LENGTH = 1;
    private static final int AUTHOR_MAX_LENGTH = 255;
    private static final int ISBN_MIN_LENGTH = 13;
    private static final int ISBN_MAX_LENGTH = 17;
    private static final int URL_MIN_LENGTH = 1;
    private static final int URL_MAX_LENGTH = 255;

    private String category;
    @NotEmpty
    @Size(min = TITLE_MIN_LENGTH, max = TITLE_MAX_LENGTH, message = "Otsikon pituus 1-255 merkkiä")
    private String title;
    @NotEmpty
    @Size(min = DESCRIPTION_MIN_LENGTH, max = DESCRIPTION_MAX_LENGTH, message = "Kuvauksen pituus 1-255 merkkiä")
    private String description;
    @NotEmpty
    @Size(min = AUTHOR_MIN_LENGTH, max = AUTHOR_MAX_LENGTH, message = "Tekijän nimen pituus 1-255 merkkiä")
    private String author;
    private String isbn;
    private String url;

    /**
     * Used to run additional validation for this form.
     *
     * @param result The BindingResult that value rejections are submitted to.
     */
    public void validateRest(BindingResult result) {
        ReadingTipCategory readingTipCategory = ReadingTipCategory.getByName(category);
        if (readingTipCategory == ReadingTipCategory.BOOK) {
            if (isbn.length() < ISBN_MIN_LENGTH || isbn.length() > ISBN_MAX_LENGTH) {
                result.rejectValue("isbn", "", "ISBN-13 pituus välimerkkeinen 13-17 merkkiä");
            }
            if (!isValidISBN(this.isbn)) {
                result.rejectValue("isbn", "", "Huono ISBN");
            }
        } else if ((readingTipCategory == ReadingTipCategory.LINK || readingTipCategory == ReadingTipCategory.VIDEO)) {
            if (url.length() < URL_MIN_LENGTH || url.length() > URL_MAX_LENGTH) {
                result.rejectValue("url", "", "Urlin pituus 1-255 merkkiä");
            }
            if (!isValidURL(this.url)) {
                result.rejectValue("url", "", "Huono url");
            }
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

    /**
     * Checks if the given URL is valid.
     *
     * @param url   The value that is checked.
     * @return Return true if the given input is a valid URL and false if it is not.
     */
    public static boolean isValidURL(String url) {
        String regex = "^https?://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
        return url.matches(regex);
    }

    public ReadingTip createReadingTip(CustomUser customUser) {
        ReadingTipCategory readingTipCategory = ReadingTipCategory.getByName(category);
        if (readingTipCategory == ReadingTipCategory.ARTICLE) {
            return new ReadingTip(title, readingTipCategory, description, "", author, "", customUser);
        } else if (readingTipCategory == ReadingTipCategory.BOOK) {
            return new ReadingTip(title, readingTipCategory, description, "", author, isbn, customUser);
        } else if (readingTipCategory == ReadingTipCategory.LINK) {
            return new ReadingTip(title, readingTipCategory, description, url, author, "", customUser);
        } else if (readingTipCategory == ReadingTipCategory.VIDEO) {
            return new ReadingTip(title, readingTipCategory, description, url, author, "", customUser);
        } else {
            return null;
        }
    }

}
