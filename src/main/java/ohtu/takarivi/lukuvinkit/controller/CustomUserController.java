package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.forms.CustomUserRegisterForm;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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
        model.addAttribute("description", "Lukuvinkkien säilöntään soveltuva palvelu.");
        model.addAttribute("view", "login");
        return "layout";
    }

    /**
     * The register page, allowing users to create an account.
     *
     * @param model                  The Model that the task information will be fit into.
     * @param customUserRegisterForm The custom registration form.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/register")
    public String viewRegister(Model model, @ModelAttribute CustomUserRegisterForm customUserRegisterForm) {
        model.addAttribute("title", "Luo tili");
        model.addAttribute("view", "register");
        return "layout";
    }

    /**
     * The profile page for a given user.
     *
     * @param auth  An Authentication object representing the currently
     *              authenticated user.
     * @param model The Model that the profile information will be fit into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("title", "Profiili");
        model.addAttribute("view", "profile");
        return "layout";
    }

    /**
     * The index page or front page.
     *
     * @param auth  An Authentication object representing the currently
     *              authenticated user.
     * @param model The Model that the task information will be fit into.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Etusivu");
        model.addAttribute("description", "Lukuvinkkien säilöntään soveltuva palvelu.");
        model.addAttribute("view", "index");
        return "layout";
    }

    /**
     * The page used by users to register an account.
     *
     * @param auth         An Authentication object representing the currently
     *                     authenticated user.
     * @param registerForm The registration form.
     * @param result       The binding result of the given form.
     * @param model        The model to set the data into.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/register")
    public String register(Authentication auth, @Valid @ModelAttribute CustomUserRegisterForm registerForm,
                           BindingResult result, Model model) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/";
        }
        registerForm.validateRest(result, customUserRepository);
        if (result.hasErrors()) {
            model.addAttribute("title", "Luo tili");
            model.addAttribute("view", "register");
            return "layout";
        }
        customUserRepository.save(new CustomUser(registerForm.getUsername(),
                encoder.encode(registerForm.getPassword()), registerForm.getName()));
        return "redirect:/login#registerok";
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
        // can only change password if the two passwords match and the user matches
        if (!newPassword.trim().isEmpty() && newPassword.equals(verifyNewPassword)) {
            customUser.setPassword(encoder.encode(newPassword));
            customUserRepository.save(customUser);
        }
        return "redirect:/";
    }

}
