package ohtu.takarivi.lukuvinkit.forms;

import static ohtu.takarivi.lukuvinkit.forms.FormUtils.isValidISBN;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.validation.BindingResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookAddForm extends AddForm {
    private static final int ISBN_MIN_LENGTH = 10;
    private static final int ISBN_MAX_LENGTH = 17;

    @NotEmpty
    @Size(min = ISBN_MIN_LENGTH, max = ISBN_MAX_LENGTH, message = "ISBN-tunnuksen pituus 10-17 merkkiä")
    private String isbn;

    @Override
    public void validateRest(BindingResult result) {
        if (!isValidISBN(this.isbn)) {
            result.rejectValue("isbn", "", "ISBN ei kelpaa");
        }
    }

}
