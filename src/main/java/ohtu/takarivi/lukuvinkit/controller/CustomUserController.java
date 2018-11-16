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
     * @return The action to be taken by this controller.
     */
    @GetMapping("/login")
    public String logIn() {
        return "login";
    }

    /**
     * The register page, allowing users to create an account.
     *
     * @return The action to be taken by this controller.
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * The index page, listing all tasks.
     *
     * @param auth An Authentication object representing the currently authenticated user. The user that created the tip must also be the one removing it.
     * @param model The Model that the task information will be fit into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/")
    public String loadIndex(Authentication auth, Model model) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        model.addAttribute("customUser", customUser);
        model.addAttribute("readingTips", readingTipRepository.findByCustomUserId(customUser.getId()));
        return "index";
    }

    /**
     * The page used by users to register an account.
     *
     * @param username The username set by the user.
     * @param password The password set by the user.
     * @param verifyPassword The password set by the user; this must match password.
     * @return The action to be taken by this controller.
     */
    @PostMapping(value = "/register/register")
    public String registerAccount(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String verifyPassword) {
        String name = username.trim();
        if (name.isEmpty() || password.trim().isEmpty()
                || !password.equals(verifyPassword)
                || !CustomUser.isValidUsername(name)) {
            return "400"; // TODO return HTTP error
        }
        
        CustomUser customUser = new CustomUser(name, encoder.encode(password), username);
        customUserRepository.save(customUser);
        return "redirect:/";
    }

    /**
     * The page used by users to change their passwords.
     *
     * @param auth An Authentication object representing the currently authenticated user. The user that created the tip must also be the one removing it.
     * @param newPassword The new password set by the user.
     * @param newPasswordAgain The new password set by the user; this must match newPassword.
     * @return The action to be taken by this controller.
     */
    @PostMapping(value = "/editPassword")
    public String editPassword(Authentication auth, @RequestParam String newPassword,
                               @RequestParam String newPasswordAgain) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        if (customUser == null) {
            return "403"; // TODO return HTTP error
        }
        if (!newPassword.trim().isEmpty() && newPassword.equals(newPasswordAgain)) {
            customUser.setPassword(encoder.encode(newPassword));
            customUserRepository.save(customUser);
            return "redirect:/";
        } else {
            return "400"; // TODO return HTTP error
        }
    }

}
