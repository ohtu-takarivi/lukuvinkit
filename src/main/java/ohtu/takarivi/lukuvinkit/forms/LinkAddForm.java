package ohtu.takarivi.lukuvinkit.forms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LinkAddForm implements AddForm{

    private static final int TITLE_MIN_LENGTH = 1;
    private static final int TITLE_MAX_LENGTH = 255;
    private static final int DESCRIPTION_MIN_LENGTH = 1;
    private static final int DESCRIPTION_MAX_LENGTH = 255;
    private static final int AUTHOR_MIN_LENGTH = 1;
    private static final int AUTHOR_MAX_LENGTH = 255;
    private static final int URL_MIN_LENGTH = 1;
    private static final int URL_MAX_LENGTH = 255;

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
    @Size(min = URL_MIN_LENGTH, max = URL_MAX_LENGTH, message = "Urlin pituus 1-255 merkkiä")
    private String url;

    /**
     * Used to run additional validation for this form.
     * 
     * @param result The BindingResult that value rejections are submitted to.
     */
    @Override
    public void validateRest(BindingResult result) {
        if (!isValidURL(this.url)) {
            result.rejectValue("url", "", "Huono url");
        }
    }

    /**
     * Checks if the given URL is valid.
     *
     * @param url The value that is checked.
     * @param regex Regex for validating URLs that begin with http:// or https://.
     * @return Return true if the given input is a valid URL and false if it is not.
     */
    public static boolean isValidURL(String url) {
        // test input against regex
        String regex = "^(http|https|ftp):\\/\\/([a-zA-Z0-9_\\.\\-]+\\.([A-Za-z]{2,20})|localhost)(:[0-9]{1,5})?[a-zA-Z0-9_\\/\\&\\?\\=\\.\\~\\%\\-]*";
        return url.matches(regex);
    }

}
