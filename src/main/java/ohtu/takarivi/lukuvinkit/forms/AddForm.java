
package ohtu.takarivi.lukuvinkit.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.validation.BindingResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AddForm {
    protected static final int TITLE_MIN_LENGTH = 1;
    protected static final int TITLE_MAX_LENGTH = 255;
    protected static final int DESCRIPTION_MIN_LENGTH = 1;
    protected static final int DESCRIPTION_MAX_LENGTH = 2000;
    protected static final int AUTHOR_MIN_LENGTH = 1;
    protected static final int AUTHOR_MAX_LENGTH = 255;

    @NotEmpty
    @Size(min = TITLE_MIN_LENGTH, max = TITLE_MAX_LENGTH, message = "Otsikon pituus 1-255 merkkiä")
    protected String title;
    @NotEmpty
    @Size(min = DESCRIPTION_MIN_LENGTH, max = DESCRIPTION_MAX_LENGTH, message = "Kuvauksen pituus 1-2000 merkkiä")
    protected String description;
    @NotEmpty
    @Size(min = AUTHOR_MIN_LENGTH, max = AUTHOR_MAX_LENGTH, message = "Tekijän nimen pituus 1-255 merkkiä")
    protected String author;
    
    /**
     * Used to run additional validation for this form.
     *
     * @param result The BindingResult that value rejections are submitted to.
     */
    public abstract void validateRest(BindingResult result);
}
