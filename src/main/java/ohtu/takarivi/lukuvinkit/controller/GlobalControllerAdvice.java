package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.forms.ReadingTipAddForm;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("readingTipAddForm")
    public ReadingTipAddForm populateReadingTipAddForm() {
        return new ReadingTipAddForm();
    }

}
