package ohtu.takarivi.lukuvinkit.forms;

import java.util.HashSet;
import java.util.Set;

import ohtu.takarivi.lukuvinkit.domain.ReadingTipTag;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipTagRepository;

public class FormUtils {

    private static final String ISBN_10_CHECK = "0123456789X";
    private static final int MAXIMUM_TAG_LENGTH = 255;

    /**
     * Truncates the string if it is longer than length.
     * 
     * @param string The string to test.
     * @param length The maximum length allowed.
     * @return The original string if it is shorter than length, otherwise the first length characters of the string.
     */
    public static String truncateString(String string, int length) {
        return string.substring(0, Math.min(string.length(), length));
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
     * ISBN-10 format requires that the number is 10 digits. Characters are not accepted.
     * 
     * The first 9 numbers are each summed together. Each time the sum is calculated the sum is added to a variable called result.
     * The result and sum are used in the following calculation (11 - ((result + sum) % 11)) % 11 which is used to fetch a result form the variable ISBN_10_CHECK.
     * If the result isn't equal to the last digit of the isbn, then the isbn is rejected.
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
     * ISBN-13 format requires that the number is 13 digits. Characters are not accepted.
     * 
     * The first 12 numbers are multiplied, the first by 1 and every other after it by 3.
     * Then a modulo 10 division is done on the result.
     * If the result of this doesn't equal to zero then the result is 10 minus the result of the modulo division. 
     * If the result isn't equal to the last digit of the isbn, then the isbn is rejected.
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
     * Must begin with http, https or ftp. Must consist of numbers or characters. Domain must be 2-20 digits long.
     * or
     * Must begin with 'localhost:' and be followed by 1-5 numbers.
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

    /**
     * Prepares a ReadingTipTag Set for instantiating a ReadingTip instance.
     * 
     * @param readingTipTagRepository The ReadingTipTag repository.
     * @param tagNames The array containing tag names.
     * @return The Set containing the tags.
     */
    public static Set<ReadingTipTag> prepareTags(ReadingTipTagRepository readingTipTagRepository, String[] tagNames) {
        Set<ReadingTipTag> tags = new HashSet<>();
        if (readingTipTagRepository == null) {
            return tags;
        }
        
        for (String rawTagName: tagNames) {
            String tagName = truncateString(rawTagName.trim(), MAXIMUM_TAG_LENGTH);
            if (!tagName.isEmpty()) {
                ReadingTipTag tag = readingTipTagRepository.findByName(tagName);
                if (tag == null) {
                    tag = new ReadingTipTag(tagName);
                    readingTipTagRepository.save(tag);
                }
                tags.add(tag);
            }
        }
        return tags;
    }

}
