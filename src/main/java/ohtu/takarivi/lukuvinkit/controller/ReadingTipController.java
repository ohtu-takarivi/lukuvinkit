package ohtu.takarivi.lukuvinkit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
     * The page that allows an user to view the details of a reading tip.
     *
     * @param auth         An Authentication object representing the currently authenticated user.
     * @param readingTipId The ID of the reading tip to view.
     * @param model        The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/readingTips/view/{readingTipId}")
    public String viewReadingTip(Authentication auth, @PathVariable Long readingTipId, Model model) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        ReadingTip tip = readingTipRepository.getOne(readingTipId);
        if (tip == null) {
            return "redirect:/";
        }
        model.addAttribute("title", "Lukuvinkki");
        model.addAttribute("nav", "navbar");
        model.addAttribute("customUser", customUser);
        model.addAttribute("readingTip", tip);
        model.addAttribute("view", "viewtip");
        return "layout";
    }
    
    /**
     * The page that displays reading tips of that category.
     *
     * @param auth      An Authentication object representing the currently authenticated user.
     * @param category  The name of the reading tip to view.
     * @param model     The model to feed the information into.
     * @return The action to be taken by this controller.
     */

    @GetMapping("/readingTips/{categoryText}")
    public String viewCategory(Authentication auth, @PathVariable String categoryText, Model model) {
        ReadingTipCategory category = ReadingTipCategory.getByName(categoryText.toUpperCase());
        if (category == null) {
            return "redirect:/";
        }
        
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        List<ReadingTip> tips = readingTipRepository.findByCategory(category);

        model.addAttribute("title", "Kategoria");
        model.addAttribute("nav", "navbar");
        model.addAttribute("customUser", customUser);
        model.addAttribute("readingTips", tips);
        model.addAttribute("view", "category");
        return "layout";
    }

    /**
     * The form submit page that allows an user to create a reading tip. 
     * It accepts the information related to the
     * reading tip and adds it to the database, if it is valid.
     *
     * @param auth        An Authentication object representing the currently authenticated user.
     * @param title       The title of the reading tip to add.
     * @param description The description of the reading tip to add.
     * @param url         The URL of the reading tip to add.
     * @param author      The author of the reading tip to add.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/readingTips/new")
    public String newReadingTip(Authentication auth,
                                @RequestParam String title,
                                @RequestParam String type,
                                @RequestParam String description,
                                @RequestParam String url,
                                @RequestParam String author) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        if (!title.trim().isEmpty() && !description.trim().isEmpty() && !url.trim().isEmpty() && !author.trim().isEmpty()) {
            ReadingTipCategory category = ReadingTipCategory.getByName(type.toUpperCase());
            if (category != null) {
                readingTipRepository.save(new ReadingTip(title, category, description, url, author, customUser));
            }
        }
        return "redirect:/";
    }

    /**
     * The form submit page that allows an user to mark a reading tip as having been read.
     *
     * @param request      The HTTP request used to access this controller.
     * @param auth         An Authentication object representing the currently authenticated user. The user that
     *                     created the tip must also be the one setting it as read.
     * @param readingTipId The ID of the reading tip to mark as read.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/readingTips/markAsRead/{readingTipId}")
    public String markReadingTipAsRead(HttpServletRequest request, Authentication auth, @PathVariable Long readingTipId) {
        String referer = request.getHeader("Referer");
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        ReadingTip readingTip = readingTipRepository.getOne(readingTipId);
        if (readingTip.getCustomUser().getId() != customUser.getId()) {
            throw new AccessDeniedException("Access denied");
        }
        readingTip.setIsRead(true);
        readingTipRepository.save(readingTip);
        return "redirect:" + (referer == null ? "/" : referer);
    }

    /**
     * The form submit page that allows an user to delete a reading tip.
     *
     * @param auth         An Authentication object representing the currently authenticated user. The user that
     *                     created the tip must also be the one removing it.
     * @param readingTipId The ID of the reading tip to delete.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/readingTips/delete/{readingTipId}")
    public String deleteReadingTip(Authentication auth, @PathVariable Long readingTipId) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        ReadingTip readingTip = readingTipRepository.getOne(readingTipId);
        if (readingTip.getCustomUser().getId() != customUser.getId()) {
            throw new AccessDeniedException("Access denied");
        }
        readingTipRepository.deleteById(readingTipId);
        return "redirect:/";
    }

    /**
     * The form search page that allows searching tips by keywords.
     *
     * @param auth         An Authentication object representing the currently authenticated user.
     * @param keyword      The keyword to search with.
     * @param model        The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/searchTips")
    public String searchReadingTip(Authentication auth, @RequestParam String keyword, Model model) {
        if (keyword.isEmpty()) {
            return "redirect:/";
        }
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        /// TODO also search from description and author
//        List<ReadingTip> list = readingTipRepository.findByTitleContainingAndCustomUserIdOrDescriptionContainingAndCustomUserId(keyword, customUser.getId(), keyword, customUser.getId());
        List<ReadingTip> list2 = readingTipRepository.findByCustomUserIdAndTitleContaining(customUser.getId(), keyword);
        model.addAttribute("nav", "navbar");
        model.addAttribute("customUser", customUser);
        model.addAttribute("readingTips", list2);
        model.addAttribute("view", "index");
        return "layout";     
    }

    /**
     * The page that resets a search.
     *
     * @param auth         An Authentication object representing the currently authenticated user.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/resetSearch")
    public String resetSearch(Authentication auth) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        return "redirect:/";
    }
}
