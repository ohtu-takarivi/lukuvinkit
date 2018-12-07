package ohtu.takarivi.lukuvinkit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.forms.ReadingTipAddForm;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;

/**
 * Class for defining global model attributes.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private ReadingTipRepository readingTipRepository;

    /**
     * A form for the sidebar.
     *
     * @return form
     */
    @ModelAttribute("readingTipAddForm")
    public ReadingTipAddForm populateReadingTipAddForm() {
        return new ReadingTipAddForm();
    }

    /**
     * A list of user's selected tips.
     *
     * @return list
     */
    @ModelAttribute("selectedReadingTips")
    public List<ReadingTip> populateSelectedReadingTips() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof String) {
            return null;
        }
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        if (customUser == null) {
            return new ArrayList<ReadingTip>();
        }
        return readingTipRepository.findByCustomUserIdAndIsSelectedTrue(customUser.getId());
    }

    /**
     * The current user.
     *
     * @return customUser
     */
    @ModelAttribute("customUser")
    public CustomUser populateCustomUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof String) {
            return null;
        }
        return customUserRepository.findByUsername(auth.getName());
    }

    /**
     * To check if a navbar is needed.
     *
     * @return string
     */
    @ModelAttribute("nav")
    public String populateNav() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof String) {
            return null;
        }
        return "navbar";
    }

}
