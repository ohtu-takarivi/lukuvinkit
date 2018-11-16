package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
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
     * The form submit page that allows an user to create a reading tip. It accepts the information related to the reading tip and adds it to the database, if it is valid.
     *
     * @param auth An Authentication object representing the currently authenticated user.  
     * @param title The title of the reading tip to add.
     * @param description The description of the reading tip to add.
     * @param url The URL of the reading tip to add.
     * @return The action to be taken by this controller.
     */
    @PostMapping(value = "/newReadingTip")
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
     * @param auth An Authentication object representing the currently authenticated user. The user that created the tip must also be the one removing it.  
     * @param readingTip The ID of the reading tip to delete.
     * @return The action to be taken by this controller.
     */
    @PostMapping(value = "/deleteReadingTip")
    public String deleteReadingTip(Authentication auth, @RequestParam Long readingTip) {
        System.out.println(readingTip);
        ReadingTip tip;
        try {
            tip = readingTipRepository.getOne(readingTip);
            CustomUser customUser = customUserRepository.findByUsername(auth.getName());
            
            // if not logged in, refuse to remove
            if (customUser == null) {
                return "403"; /// TODO return HTTP error
            }

            // only the user that added it can remove it
            if (tip.getCustomUser().getId() == customUser.getId()) {
                readingTipRepository.deleteById(readingTip);
            } else {
                return "403"; /// TODO return HTTP error
            }
        } catch (EntityNotFoundException ex) {
            return "400"; /// TODO return HTTP error
        }
        return "redirect:/";
    }
}
