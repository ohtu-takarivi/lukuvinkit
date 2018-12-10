package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipTag;
import ohtu.takarivi.lukuvinkit.forms.FormUtils;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * The Spring controller for miscellaneous pages.
 */
@Controller
public class MiscController {
    /**
     * When this is set to true, /testDataInsert is allowed to clear all data and replace it with test data.
     * This should always be set to false in production code!
     */
    public static boolean ENABLE_TESTDATA_ENTRY = true;

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
        InputStream is = getClass().getResourceAsStream("/testdata.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while (true) {
                ReadingTip rtip = getNextReadingTipFrom(br, user);
                if (rtip == null) {
                    break;
                }
                readingTipRepository.save(rtip);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        auth.setAuthenticated(false);
        return "redirect:/login#user_and_password_is_testuser";
    }

    /**
     * Reads the next reading tip from a Scanner providing test data.
     *
     * @param br         The Scanner to read from.
     * @param customUser The CustomUser to be passed to the ReadingTip constructor.
     * @return The next ReadingTip or null if there is no more (valid) test data.
     */
    private ReadingTip getNextReadingTipFrom(BufferedReader br, CustomUser customUser) throws IOException {
        // testdata format:
        /// category (BOOK, LINK, VIDEO, ARTICLE)
        /// title
        /// author
        /// description
        /// tags
        /// URL if VIDEO or ARTICLE, ISBN if BOOK, otherwise this line does not exist
        String cattext = br.readLine();
        // allow empty lines between test data entries
        while (cattext != null && cattext.trim().isEmpty()) {
            cattext = br.readLine();
        }
        if (cattext == null) {
            return null;
        }
        ReadingTipCategory category = ReadingTipCategory.getByName(cattext);
        // stop reading if the category is invalid
        if (category == null) {
            return null;
        }
        String title = br.readLine();
        String author = br.readLine();
        String description = br.readLine();
        String tagString = br.readLine();
        String url = "";
        String isbn = "";
        // read URL only if applicable
        if (category == ReadingTipCategory.LINK || category == ReadingTipCategory.VIDEO) {
            url = br.readLine();
        }
        // read ISBN only if applicable
        if (category == ReadingTipCategory.BOOK) {
            isbn = br.readLine();
        }
        Set<ReadingTipTag> tags = FormUtils.prepareTags(readingTipTagRepository, tagString.split(" "));
        return new ReadingTip(title, category, description, url, author, isbn, tags, customUser);
    }

}
