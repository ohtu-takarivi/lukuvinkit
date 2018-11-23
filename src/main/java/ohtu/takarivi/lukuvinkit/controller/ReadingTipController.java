package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipSearch;
import ohtu.takarivi.lukuvinkit.forms.ArticleAddForm;
import ohtu.takarivi.lukuvinkit.forms.BookAddForm;
import ohtu.takarivi.lukuvinkit.forms.LinkAddForm;
import ohtu.takarivi.lukuvinkit.forms.VideoAddForm;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
     * @param auth  An Authentication object representing the currently authenticated user.
     * @param model The model to feed the information into.
     * @return
     */
    @GetMapping("/search")
    public String viewSearch(Authentication auth, Model model) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());

        model.addAttribute("title", "Haku");
        model.addAttribute("nav", "navbar");
        model.addAttribute("customUser", customUser);
        model.addAttribute("view", "search");
        return "layout";
    }

    /**
     * The page that displays reading tips of that category.
     *
     * @param auth     An Authentication object representing the currently authenticated user.
     * @param category The name of the reading tip to view.
     * @param model    The model to feed the information into.
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

    @GetMapping("/readingTips/articles/new")
    public String newArticleForm(Model model, @ModelAttribute ArticleAddForm articleAddForm) {
        model.addAttribute("title", "Lisää artikkeli");
        model.addAttribute("nav", "navbar");
        model.addAttribute("view", "newArticle");
        return "layout";
    }

    @PostMapping("/readingTips/articles/new")
    public String newArticle(Authentication auth, @Valid @ModelAttribute ArticleAddForm articleAddForm,
                             BindingResult result, Model model) {
        articleAddForm.validateRest(result);
        if (result.hasErrors()) {
            model.addAttribute("title", "Lisää artikkeli");
            model.addAttribute("nav", "navbar");
            model.addAttribute("view", "newArticle");
            return "layout";
        }
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        readingTipRepository.save(new ReadingTip(articleAddForm.getTitle(),
                ReadingTipCategory.ARTICLE,
                articleAddForm.getDescription(),
                "",
                articleAddForm.getAuthor(),
                "",
                customUser));
        return "redirect:/";
    }

    @GetMapping("/readingTips/books/new")
    public String newBookForm(Model model, @ModelAttribute BookAddForm bookAddForm) {
        model.addAttribute("title", "Lisää kirja");
        model.addAttribute("nav", "navbar");
        model.addAttribute("view", "newBook");
        return "layout";
    }

    @PostMapping("/readingTips/books/new")
    public String newBook(Authentication auth, @Valid @ModelAttribute BookAddForm bookAddForm,
                          BindingResult result, Model model) {
        bookAddForm.validateRest(result);
        if (result.hasErrors()) {
            model.addAttribute("title", "Lisää kirja");
            model.addAttribute("nav", "navbar");
            model.addAttribute("view", "newBook");
            return "layout";
        }
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        readingTipRepository.save(new ReadingTip(bookAddForm.getTitle(),
                ReadingTipCategory.BOOK,
                bookAddForm.getDescription(),
                "",
                bookAddForm.getAuthor(),
                bookAddForm.getIsbn(),
                customUser));
        return "redirect:/";
    }

    @GetMapping("/readingTips/links/new")
    public String newLinkForm(Model model, @ModelAttribute LinkAddForm linkAddForm) {
        model.addAttribute("title", "Lisää linkki");
        model.addAttribute("nav", "navbar");
        model.addAttribute("view", "newLink");
        return "layout";
    }

    @PostMapping("/readingTips/links/new")
    public String newLink(Authentication auth, @Valid @ModelAttribute LinkAddForm linkAddForm,
                          BindingResult result, Model model) {
        linkAddForm.validateRest(result);
        if (result.hasErrors()) {
            model.addAttribute("title", "Lisää linkki");
            model.addAttribute("nav", "navbar");
            model.addAttribute("view", "newLink");
            return "layout";
        }
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        readingTipRepository.save(new ReadingTip(linkAddForm.getTitle(),
                ReadingTipCategory.LINK,
                linkAddForm.getDescription(),
                linkAddForm.getUrl(),
                linkAddForm.getAuthor(),
                "",
                customUser));
        return "redirect:/";
    }

    @GetMapping("/readingTips/videos/new")
    public String newVideoForm(Model model, @ModelAttribute VideoAddForm videoAddForm) {
        model.addAttribute("title", "Lisää video");
        model.addAttribute("nav", "navbar");
        model.addAttribute("view", "newVideo");
        return "layout";
    }

    @PostMapping("/readingTips/videos/new")
    public String newVideo(Authentication auth, @Valid @ModelAttribute VideoAddForm videoAddForm,
                           BindingResult result, Model model) {
        videoAddForm.validateRest(result);
        if (result.hasErrors()) {
            model.addAttribute("title", "Lisää video");
            model.addAttribute("nav", "navbar");
            model.addAttribute("view", "newVideo");
            return "layout";
        }
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        readingTipRepository.save(new ReadingTip(videoAddForm.getTitle(),
                ReadingTipCategory.VIDEO,
                videoAddForm.getDescription(),
                videoAddForm.getUrl(),
                videoAddForm.getAuthor(),
                "",
                customUser));
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
    public String markReadingTipAsRead(HttpServletRequest request, Authentication auth,
                                       @PathVariable Long readingTipId) {
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
     * @param auth    An Authentication object representing the currently authenticated user.
     * @param keyword The keyword to search with.
     * @param model   The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/searchTips")
    public String searchReadingTip(Authentication auth, @RequestParam String keyword, Model model) {
        if (keyword.isEmpty()) {
            return "redirect:/search";
        }
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        List<ReadingTip> list2 = ReadingTipSearch.searchSimple(readingTipRepository, customUser.getId(), keyword);
        model.addAttribute("nav", "navbar");
        model.addAttribute("customUser", customUser);
        model.addAttribute("readingTips", list2);
        model.addAttribute("view", "search");
        return "layout";
    }

    /**
     * @param auth        An Authentication object representing the currently authenticated user.
     * @param title       Keyword given to search the title or empty.
     * @param description Keyword given to search the description or empty.
     * @param url         Keyword given to search the URL or empty.
     * @param author      Keyword given to search the author or empty.
     * @param model       The model to feed the information into.
     * @return
     */
    @PostMapping("/search")
    public String searchTip(Authentication auth, @RequestParam String title, @RequestParam String description,
                            @RequestParam String url, @RequestParam String author,
                            @RequestParam("category") List<String> category,
                            @RequestParam("unreadstatus") List<String> unreadstatus,
                            Model model) {
        if (title.isEmpty() && description.isEmpty() && url.isEmpty() && author.isEmpty()) {
            return "redirect:/search";
        }
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        List<ReadingTip> list2 = ReadingTipSearch.searchAdvanced(readingTipRepository, customUser.getId(), title,
                description, url, author, category, unreadstatus);
        model.addAttribute("nav", "navbar");
        model.addAttribute("customUser", customUser);
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
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        return "redirect:/";
    }
}
