package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The Spring controller for user-related activity.
 */
@Controller
public class CustomUserController {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private ReadingTipRepository readingTipRepository;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * The default mapping, used for pages that cannot be found.
     *
     * @return The action to be taken by this controller.
     */
    @GetMapping("*")
    public String defaultMapping() {
        return "redirect:/";
    }

    /**
     * The log in page, allowing users to log in.
     * 
     * @param model The Model that the task information will be fit into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Kirjaudu");
        model.addAttribute("view", "login");
        return "layout";
    }

    /**
     * The register page, allowing users to create an account.
     * 
     * @param model The Model that the task information will be fit into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Luo tili");
        model.addAttribute("view", "register");
        return "layout";
    }

    /**
     * The index page, listing all tasks.
     *
     * @param auth  An Authentication object representing the currently
     *              authenticated user. The user that created the tip must also be
     *              the one removing it.
     * @param model The Model that the task information will be fit into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/")
    public String index(Authentication auth, Model model) {
        model.addAttribute("title", "Etusivu");
        model.addAttribute("nav", "navbar");
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        model.addAttribute("customUser", customUser);
        model.addAttribute("readingTips", readingTipRepository.findByCustomUserId(customUser.getId()));
        model.addAttribute("view", "index");
        return "layout";
    }

    /**
     * The page used by users to register an account.
     *
     * @param auth           An Authentication object representing the currently
     *                       authenticated user.
     * @param username       The username set by the user.
     * @param password       The password set by the user.
     * @param verifyPassword The password set by the user; this must match password.
     * @param name           The name set by the user.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/register")
    public String register(Authentication auth, @RequestParam String username, @RequestParam String password,
                           @RequestParam String verifyPassword, @RequestParam String name) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/"; // cannot create account if you are already logged in one
        }
        if (CustomUser.isValidUsername(username) 
                && !password.trim().isEmpty() 
                && password.equals(verifyPassword)
                && !name.trim().isEmpty()) {
            if (customUserRepository.findByUsername(username) == null) {
                customUserRepository.save(new CustomUser(username, encoder.encode(password), name));
                return "redirect:/login?registerok";
            } else {
                return "redirect:/register?error=reserved";
            }
        } else {
            return "redirect:/register?error=invalid";
        }
    }

    /**
     * The page used by users to change their passwords.
     *
     * @param auth              An Authentication object representing the currently
     *                          authenticated user. The user that created the tip
     *                          must also be the one removing it.
     * @param newPassword       The new password set by the user.
     * @param verifyNewPassword The new password set by the user; this must match
     *                          newPassword.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/editPassword")
    public String editPassword(Authentication auth, @RequestParam String newPassword,
                               @RequestParam String verifyNewPassword) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        if (!newPassword.trim().isEmpty() && newPassword.equals(verifyNewPassword)) {
            customUser.setPassword(encoder.encode(newPassword));
            customUserRepository.save(customUser);
        }
        return "redirect:/";
    }

}
