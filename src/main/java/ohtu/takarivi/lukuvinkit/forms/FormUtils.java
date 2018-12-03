package ohtu.takarivi.lukuvinkit.forms;

public class FormUtils {

    private static final String ISBN_10_CHECK = "0123456789X";

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

    /**
     * Checks if the given URL is valid.
     *
     * @param url   The value that is checked.
     * @param regex Regex for validating URLs that begin with http:// or https://.
     * @return Return true if the given input is a valid URL and false if it is not.
     */
    public static boolean isValidURL(String url) {
        // test input against regex
        String regex = "^(http|https|ftp):\\/\\/([a-zA-Z0-9_\\.\\-]+\\.([A-Za-z]{2,20})|localhost)(:[0-9]{1,5})" +
                "?[a-zA-Z0-9_\\/\\&\\?\\=\\.\\~\\%\\-]*";
        return url.matches(regex);
    }

}
