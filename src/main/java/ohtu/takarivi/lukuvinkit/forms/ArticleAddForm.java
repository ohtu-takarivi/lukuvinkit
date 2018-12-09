package ohtu.takarivi.lukuvinkit.forms;

import org.springframework.validation.BindingResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleAddForm extends AddForm {

    @Override
    public void validateRest(BindingResult result) {
    }

}
