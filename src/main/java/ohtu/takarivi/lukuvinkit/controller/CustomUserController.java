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
     * The index page, listing all tasks.
     *
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
     * The page used by users to change their passwords.
     *
     * @return The action to be taken by this controller.
     */
    @PostMapping(value = "/changePassword")
    public String submitNewName(Authentication auth, @RequestParam String newPassword,
                                @RequestParam String newPasswordAgain) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        if (!newPassword.trim().isEmpty() && newPassword.equals(newPasswordAgain)) {
            customUser.setPassword(encoder.encode(newPassword));
            customUserRepository.save(customUser);
        }
        return "redirect:/";
    }

}
