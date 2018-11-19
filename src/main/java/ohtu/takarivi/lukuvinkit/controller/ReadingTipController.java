package ohtu.takarivi.lukuvinkit.controller;

import java.util.List;
import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The Spring controller for reading tip related activity.
 */
@Controller
public class ReadingTipController {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private ReadingTipRepository readingTipRepository;

    /**
     * The form submit page that allows an user to create a reading tip. It accepts the information related to the
     * reading tip and adds it to the database, if it is valid.
     *
     * @param auth        An Authentication object representing the currently authenticated user.
     * @param title       The title of the reading tip to add.
     * @param description The description of the reading tip to add.
     * @param url         The URL of the reading tip to add.
     * @return The action to be taken by this controller.
     */
    @PostMapping(value = "/readingTips/new")
    public String newReadingTip(Authentication auth, @RequestParam String title, @RequestParam String description,
                                @RequestParam String url) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        if (!title.trim().isEmpty() && !description.trim().isEmpty() && !url.trim().isEmpty()) {
            readingTipRepository.save(new ReadingTip(title, description, url, customUser));
        }
        return "redirect:/";
    }

    /**
     * The form submit page that allows an user to delete a reading tip.
     *
     * @param auth         An Authentication object representing the currently authenticated user. The user that
     *                     created the tip must also be the one removing it.
     * @param readingTipId The ID of the reading tip to delete.
     * @return The action to be taken by this controller.
     */
    @PostMapping(value = "/readingTips/delete/{readingTipId}")
    public String deleteReadingTip(Authentication auth, @PathVariable Long readingTipId) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        ReadingTip readingTip = readingTipRepository.getOne(readingTipId);
        if (readingTip.getCustomUser().getId() != customUser.getId()) {
            throw new AccessDeniedException("Access denied");
        }
        readingTipRepository.deleteById(readingTipId);
        return "redirect:/";
    }
    
    @PostMapping(value = "/searchTips")
    public String searchReadingTip(Authentication auth, @RequestParam String keyword, Model model) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        List<ReadingTip> list = readingTipRepository.findByCustomUserIdAndTitleContaining(customUser.getId(), keyword);
        model.addAttribute("customUser", customUser);
        model.addAttribute("readingTips", list);
        model.addAttribute("view", "index");
        return "layout";     
    }
    
    @PostMapping(value = "/resetSearch")
    public String resetSearch(Authentication auth) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        return "redirect:/";
    }
}
