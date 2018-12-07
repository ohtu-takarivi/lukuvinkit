package ohtu.takarivi.lukuvinkit.forms;

import static ohtu.takarivi.lukuvinkit.forms.FormUtils.isValidURL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.validation.BindingResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoAddForm extends AddForm {
    private static final int URL_MIN_LENGTH = 1;
    private static final int URL_MAX_LENGTH = 255;

    @NotEmpty
    @Size(min = URL_MIN_LENGTH, max = URL_MAX_LENGTH, message = "URL-osoitteen pituus 1-255 merkkiä")
    private String url;

    @Override
    public void validateRest(BindingResult result) {
        if (!isValidURL(this.url)) {
            result.rejectValue("url", "", "URL ei kelpaa");
        }
    }

}
