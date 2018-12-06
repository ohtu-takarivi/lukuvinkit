package ohtu.takarivi.lukuvinkit.forms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;

@Getter
@Setter
public class ArticleAddForm extends AddForm {

    @Override
    public void validateRest(BindingResult result) {
    }

}
