package ohtu.takarivi.lukuvinkit.forms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static ohtu.takarivi.lukuvinkit.forms.FormUtils.isValidISBN;

@Getter
@Setter
public class BookAddForm implements AddForm{

    private static final int TITLE_MIN_LENGTH = 1;
    private static final int TITLE_MAX_LENGTH = 255;
    private static final int DESCRIPTION_MIN_LENGTH = 1;
    private static final int DESCRIPTION_MAX_LENGTH = 255;
    private static final int AUTHOR_MIN_LENGTH = 1;
    private static final int AUTHOR_MAX_LENGTH = 255;
    private static final int ISBN_MIN_LENGTH = 10;
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
    @Size(min = ISBN_MIN_LENGTH, max = ISBN_MAX_LENGTH, message = "ISBN tunnuksen pituus 10-17 merkkiä")
    private String isbn;

    /**
     * Used to run additional validation for this form.
     *
     * @param result The BindingResult that value rejections are submitted to.
     */
    @Override
    public void validateRest(BindingResult result) {
        if (!isValidISBN(this.isbn)) {
            result.rejectValue("isbn", "", "ISBN ei kelpaa");
        }
    }

}
