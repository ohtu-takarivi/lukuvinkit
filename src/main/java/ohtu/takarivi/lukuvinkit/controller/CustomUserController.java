package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomUserController {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private ReadingTipRepository readingTipRepository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("*")
    public String defaultMapping() {
        return "redirect:/";
    }

    @GetMapping("/")
    public String loadIndex(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomUser customUser = customUserRepository.findByUsername(username);
        model.addAttribute("customUser", customUser);
        model.addAttribute("readingTips", readingTipRepository.findByCustomUserId(customUser.getId()));
        return "index";
    }

    @PostMapping(value = "/changePassword")
    public String submitNewName(Model model, @RequestParam String newPassword, @RequestParam String newPasswordAgain) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomUser customUser = customUserRepository.findByUsername(username);
        if (!newPassword.trim().isEmpty() && newPassword.equals(newPasswordAgain)) {
            customUser.setPassword(encoder.encode(newPassword));
            customUserRepository.save(customUser);
        }
        return "redirect:/";
    }

}
