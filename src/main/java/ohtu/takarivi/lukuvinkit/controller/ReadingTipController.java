package ohtu.takarivi.lukuvinkit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipSearch;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;

/**
 * The Spring controller for reading tip related activity. Handlers for adding reading tips are under
 * ReadingTipAddController.
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
     * @param readingTipId The ID of the reading tip to view.
     * @param model        The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/readingTips/view/{readingTipId}")
    public String viewReadingTip(@PathVariable Long readingTipId, Model model) {
        ReadingTip tip = readingTipRepository.getOne(readingTipId);
        if (tip == null) {
            return "redirect:/";
        }
        model.addAttribute("title", "Lukuvinkki");
        model.addAttribute("readingTip", tip);
        model.addAttribute("view", "viewtip");
        return "layout";
    }

    /**
     * The page that displays the search form to the user.
     *
     * @param model The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/search")
    public String viewSearch(Model model) {
        model.addAttribute("title", "Haku");
        model.addAttribute("view", "search");
        return "layout";
    }

    /**
     * The page that displays reading tips of that category.
     *
     * @param auth         An Authentication object representing the currently authenticated user.
     * @param categoryText The name of the reading tip to view.
     * @param model        The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/readingTips/{categoryText}")
    public String viewCategory(Authentication auth, @PathVariable String categoryText, Model model) {
        ReadingTipCategory category = ReadingTipCategory.getByName(categoryText.toUpperCase());
        if (category == null) {
            return "redirect:/";
        }
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        List<ReadingTip> tips = readingTipRepository.findByCustomUserIdAndCategoryOrderByIsReadAsc(customUser.getId(),
                category);
        model.addAttribute("title", "Kategoria");
        model.addAttribute("readingTips", tips);
        model.addAttribute("view", "category");
        return "layout";
    }

    /**
     * The page that displays the list of reading tips that the user has selected.
     *
     * @param auth  An Authentication object representing the currently authenticated user.
     * @param model The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/readingTips/selected")
    public String viewSelected(Authentication auth, Model model) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        model.addAttribute("title", "Valitut lukuvinkit");
        model.addAttribute("readingTips", readingTipRepository.findByCustomUserIdAndIsSelectedTrue(customUser.getId()));
        model.addAttribute("view", "selected");
        return "layout";
    }

    /**
     * The page that displays the text listing of selected reading tips.
     *
     * @param auth An Authentication object representing the currently authenticated user.
     * @return The text listing.
     */
    @GetMapping(value = "/readingTips/exportText", produces = "text/plain")
    @ResponseBody
    public String exportTextListingOfSelected(Authentication auth) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        List<ReadingTip> tips = readingTipRepository.findByCustomUserIdAndIsSelectedTrue(customUser.getId());
        StringBuilder result = new StringBuilder();
        
        result.append("\nYhteensä valittuja lukuvinkkejä: " + tips.size() + "\n");
        for (ReadingTip rtip : tips) {
            result.append("\n=====================\n");
            result.append(rtip.toString() + "\n");
        }
        return result.toString();
    }

    /**
     * The page that displays the HTML listing of selected reading tips.
     *
     * @param auth An Authentication object representing the currently authenticated user.
     * @return The text listing.
     */
    @GetMapping(value = "/readingTips/exportHTML")
    @ResponseBody
    public String exporthTMLListingOfSelected(Authentication auth) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        List<ReadingTip> tips = readingTipRepository.findByCustomUserIdAndIsSelectedTrue(customUser.getId());
        StringBuilder result = new StringBuilder();
        
        result.append(    "<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "  <head>\n"
                        + "    <title>Lukuvinkkilistaus</title>\n"
                        + "    <meta charset=\"utf-8\">\n"
                        + "  </head>\n"
                        + "  <body>\n");
        result.append(    "    <h1>Lukuvinkkilistaus</h1>\n");
        result.append(    "    <p>Yhteensä valittuja lukuvinkkejä: " + tips.size() + "</p>\n");
        result.append(    "    <table border=\"1\">\n");
        result.append(    "      <tr>\n");
        result.append(    "        <th>Otsikko</th>\n");
        result.append(    "        <th>Tekijä(t)</th>\n");
        result.append(    "        <th>Linkki tai ISBN</th>\n");
        result.append(    "        <th>Kuvaus</th>\n");
        result.append(    "      </tr>\n");
        for (ReadingTip rtip : tips) {
            String detail = "";
            if (rtip.getCategory() == ReadingTipCategory.BOOK) {
                detail = rtip.getIsbn();
            } else if (rtip.getCategory() == ReadingTipCategory.LINK || rtip.getCategory() == ReadingTipCategory.VIDEO) {
                detail = rtip.getUrl();
            }
            result.append("      <tr>\n");
            result.append("        <td>" + rtip.getTitle() + "</td>\n");
            result.append("        <td>" + rtip.getAuthor() + "</td>\n");
            result.append("        <td>" + detail + "</td>\n");
            result.append("        <td>" + rtip.getDescription() + "</td>\n");
            result.append("      </tr>\n");
        }
        result.append(    "    </table>\n");
        result.append(    "  </body>\n"
                        + "</html>");
        return result.toString();
    }

    /**
     * The page that displays reading tips with a given tag.
     *
     * @param auth         An Authentication object representing the currently authenticated user.
     * @param tagName      The name of the tag.
     * @param model        The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/tag/{tagName}")
    public String viewTag(Authentication auth, @PathVariable String tagName, Model model) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        List<ReadingTip> tips = readingTipRepository.findByCustomUserIdAndTags_Name(customUser.getId(), tagName);
        model.addAttribute("title", "Tagi");
        model.addAttribute("readingTips", tips);
        model.addAttribute("tagName", tagName);
        model.addAttribute("view", "tag");
        return "layout";
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
    public String markReadingTipAsRead(HttpServletRequest request, Authentication auth,
                                       @PathVariable Long readingTipId) {
        String referer = request.getHeader("Referer");
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        ReadingTip readingTip = readingTipRepository.getOne(readingTipId);
        if (readingTip.getCustomUser().getId() != customUser.getId()) {
            // the only user that can mark a reading tip as read is the one who added it
            throw new AccessDeniedException("Access denied");
        }
        readingTip.setIsRead(true);
        readingTipRepository.save(readingTip);
        return "redirect:" + (referer == null ? "/" : referer);
    }

    /**
     * The form submit page that allows an user to either select or de-eslect a reading tip.
     *
     * @param request      The HTTP request used to access this controller.
     * @param auth         An Authentication object representing the currently authenticated user. The user that
     *                     created the tip must also be the one selecting or de-selecting it.
     * @param readingTipId The ID of the reading tip to be selected or de-selected.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/readingTips/toggleSelect/{readingTipId}")
    public String toggleReadingTipSelect(HttpServletRequest request, Authentication auth,
                                         @PathVariable Long readingTipId) {
        String referer = request.getHeader("Referer");
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        ReadingTip readingTip = readingTipRepository.getOne(readingTipId);
        if (readingTip.getCustomUser().getId() != customUser.getId()) {
            // the only user that can select or de-select a reading tip is the one who added it
            throw new AccessDeniedException("Access denied");
        }
        readingTip.toggleIsSelected();
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
            // the only user that can delete a reading tip is the one who added it
            throw new AccessDeniedException("Access denied");
        }
        readingTipRepository.deleteById(readingTipId);
        return "redirect:/";
    }

    /**
     * The form search page that allows searching tips by keywords by using the search bar.
     *
     * @param auth    An Authentication object representing the currently authenticated user.
     * @param keyword The keyword to search with.
     * @param model   The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/searchTips")
    public String searchReadingTipWithKeyword(Authentication auth, @RequestParam String keyword, Model model) {
        if (keyword.isEmpty()) {
            return "redirect:/search";
        }
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        // do a simple keyword search
        List<ReadingTip> list1 = ReadingTipSearch.searchSimple(readingTipRepository, customUser.getUsername(),
                customUser.getId(), keyword);
        model.addAttribute("readingTips", list1);
        model.addAttribute("view", "search");
        return "layout";
    }

    /**
     * The form search page that allows searching tips by keywords by using the search form.
     *
     * @param auth          An Authentication object representing the currently authenticated user.
     * @param title         Keyword given to search the title or empty.
     * @param description   Keyword given to search the description or empty.
     * @param url           Keyword given to search the URL or empty.
     * @param author        Keyword given to search the author or empty.
     * @param tags          Keyword given to search the tags or empty.
     * @param category      Allowed categories for reading tips.
     * @param unreadstatus  Allowed unread/read statuses for reading tips.
     * @param model         The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/search")
    public String searchReadingTipWithFullForm(Authentication auth, @RequestParam String title,
                                               @RequestParam String description,
                                               @RequestParam String url, @RequestParam String author,
                                               @RequestParam String tags,
                                               @RequestParam("category") List<String> category,
                                               @RequestParam("unreadstatus") List<String> unreadstatus,
                                               Model model) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        // do an advanced search
        List<ReadingTip> list2 = ReadingTipSearch.searchAdvanced(readingTipRepository, customUser, customUser.getId(),
                title, description, url, author, tags, category, unreadstatus);
        model.addAttribute("readingTips", list2);
        model.addAttribute("view", "search");
        return "layout";
    }

    /**
     * The page that resets a search.
     *
     * @param auth An Authentication object representing the currently authenticated user.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/resetSearch")
    public String resetSearch(Authentication auth) {
        return "redirect:/";
    }

}
