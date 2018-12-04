package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.forms.ReadingTipAddForm;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private ReadingTipRepository readingTipRepository;

    @ModelAttribute("readingTipAddForm")
    public ReadingTipAddForm populateReadingTipAddForm() {
        return new ReadingTipAddForm();
    }

    @ModelAttribute("selectedReadingTips")
    public List<ReadingTip> populateSelectedReadingTips() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof String) {
            return null;
        }
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        return readingTipRepository.findByCustomUserIdAndIsSelectedTrue(customUser.getId());
    }

    @ModelAttribute("customUser")
    public CustomUser populateCustomUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof String) {
            return null;
        }
        return customUserRepository.findByUsername(auth.getName());
    }

    @ModelAttribute("nav")
    public String populateNav() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof String) {
            return null;
        }
        return "navbar";
    }

}
