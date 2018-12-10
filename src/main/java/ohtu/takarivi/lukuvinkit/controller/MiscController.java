package ohtu.takarivi.lukuvinkit.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipTag;
import ohtu.takarivi.lukuvinkit.forms.FormUtils;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipTagRepository;

/**
 * The Spring controller for miscellaneous pages.
 */
@Controller
public class MiscController {
    /**
     * When this is set to true, /testDataInsert is allowed to clear all data and replace it with test data.
     * This should always be set to false in production code!
     */
    public static boolean ENABLE_TESTDATA_ENTRY = false;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private ReadingTipRepository readingTipRepository;

    @Autowired
    private ReadingTipTagRepository readingTipTagRepository;

    @Autowired
    private PasswordEncoder encoder;
    
    /**
     * Enables the test data entry form; this is only used by testing code.
     */
    public static void enableTestdataEntry() {
        ENABLE_TESTDATA_ENTRY = true;
    }

    /**
     * The page that fetches the title and description of a remote URL.
     *
     * @param auth An Authentication object representing the currently authenticated user.
     * @param url The URL to get the information from; this is a GET parameter.
     * @return The JSON text returned by this API endpoint.
     */
    @GetMapping("/testDataInsert")
    public String testDataInsert(Authentication auth) {
        if (!ENABLE_TESTDATA_ENTRY) {
            return "redirect:/";
        }
        
        readingTipRepository.deleteAll();
        customUserRepository.deleteAll();
        
        CustomUser user = new CustomUser("testuser", encoder.encode("testuser"), "testuser");
        customUserRepository.save(user);
        
        File file = new File(getClass().getClassLoader().getResource("testdata.txt").getFile());
        
        try (Scanner scanner = new Scanner(file, "utf-8")) {
            for (;;) {
                ReadingTip rtip = getNextReadingTipFrom(scanner, user);
                if (rtip == null) {
                    break;
                }
                readingTipRepository.save(rtip);
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        auth.setAuthenticated(false);

        return "redirect:/login#user_and_password_is_testuser";
    }

    /**
     * Reads the next reading tip from a Scanner providing test data.
     * 
     * @param scanner The Scanner to read from.
     * @param emptyTags The Set of ReadingTipTag objects to be provided as the tags.
     * @param customUser The CustomUser to be passed to the ReadingTip constructor.
     * @return The next ReadingTip or null if there is no more (valid) test data.
     */
    private ReadingTip getNextReadingTipFrom(Scanner scanner, CustomUser customUser) {
        // testdata format:
        /// category (BOOK, LINK, VIDEO, ARTICLE)
        /// title
        /// author
        /// description
        /// tags
        /// URL if VIDEO or ARTICLE, ISBN if BOOK, otherwise this line does not exist
        
        if (!scanner.hasNextLine()) {
            return null;
        }
        
        // allow empty lines between test data entries
        String cattext = scanner.nextLine();
        while (scanner.hasNextLine() && cattext.trim().isEmpty()) {
            cattext = scanner.nextLine();
        }
        
        ReadingTipCategory category = ReadingTipCategory.getByName(cattext);
        // stop reading if the category is invalid
        if (category == null) {
            return null;
        }
        
        String title = scanner.nextLine();
        String author = scanner.nextLine();
        String description = scanner.nextLine();
        String tagString = scanner.nextLine();
        String url = "";
        String isbn = "";
        
        // read URL only if applicable
        if (category == ReadingTipCategory.LINK || category == ReadingTipCategory.VIDEO) {
            url = scanner.nextLine();
        }
        // read ISBN only if applicable
        if (category == ReadingTipCategory.BOOK) {
            isbn = scanner.nextLine();
        }
        
        Set<ReadingTipTag> tags = FormUtils.prepareTags(readingTipTagRepository, tagString.split(" "));
        return new ReadingTip(title, category, description, url, author, isbn, tags, customUser);
    }
}
