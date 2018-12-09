package ohtu.takarivi.lukuvinkit;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipTag;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipTagRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-test.properties")
public abstract class SpringBootTestBase {
    protected static final int port = 8090;

    @Autowired
    protected CustomUserRepository customUserRepository;
    @Autowired
    protected ReadingTipRepository readingTipRepository;
    @Autowired
    protected ReadingTipTagRepository readingTipTagRepository;
    @Autowired
    protected PasswordEncoder encoder;
    @Autowired
    protected UserDetailsService userDetailsService;
    @Autowired
    protected WebApplicationContext context;
    protected MockMvc mvc;

    @Before
    public void setUpMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        setUpTestData();
    }

    public void setUpTestData() {
        readingTipRepository.deleteAll();
        customUserRepository.deleteAll();
        
        Set<ReadingTipTag> emptyTags = new HashSet<>();
        Set<ReadingTipTag> testTags = new HashSet<>();
        ReadingTipTag testTag = new ReadingTipTag("testing123");
        testTags.add(testTag);
        
        CustomUser nolla = new CustomUser("nolla", encoder.encode("yksi"), "Testi");
        customUserRepository.save(nolla);
        CustomUser testi2 = new CustomUser("testi2", encoder.encode("testi2"), "testi2");
        customUserRepository.save(testi2);
        
        readingTipTagRepository.save(testTag);
        
        readingTipRepository.save(
            new ReadingTip("test reading tip 1",
                           ReadingTipCategory.BOOK,
                           "description for tip 1",
                           "https://example.com/",
                           "John Doe",
                           "978-3-16-148410-0", 
                           emptyTags, 
                           nolla));
        readingTipRepository.save(
            new ReadingTip("test reading tip 2",
                           ReadingTipCategory.VIDEO,
                           "description for tip 2",
                           "https://example.com/",
                           "Jane Doe",
                           "",
                           emptyTags, 
                           testi2));
        ReadingTip testReading3 = new ReadingTip("test reading tip 3",
                ReadingTipCategory.LINK,
                "description for tip 3",
                "https://example.com/",
                "J. Doe",
                "",
                emptyTags, 
                nolla);
        testReading3.setComment("Example of a comment!");
        readingTipRepository.save(
            testReading3);
        readingTipRepository.save(
            new ReadingTip("test reading tip 4",
                           ReadingTipCategory.BOOK,
                           "description for tip 4",
                           "https://example.com/",
                           "Johnny Doe",
                           "978-3-16-148410-0",
                           testTags, 
                           nolla));
        readingTipRepository.save(
            new ReadingTip("test reading tip 5",
                           ReadingTipCategory.BOOK,
                           "description for tip 5",
                           "https://example.com/",
                           "Doe, John",
                           "978-3-16-148410-0",
                           emptyTags, 
                           testi2));

        readingTipRepository.save(new ReadingTip("Book Tip Alpha 1", ReadingTipCategory.BOOK, "DescSearch", "", "", "", emptyTags, nolla));
        readingTipRepository.save(new ReadingTip("Book Tip Alpha 2", ReadingTipCategory.BOOK, "", "", "", "", emptyTags, nolla));
        readingTipRepository.save(new ReadingTip("Book Tip Alpha 3", ReadingTipCategory.BOOK, "", "", "", "", testTags, nolla));
        readingTipRepository.save(new ReadingTip("Book Tip Beta 1", ReadingTipCategory.BOOK, "", "", "AuthorSearch", "", emptyTags, nolla));
        readingTipRepository.save(new ReadingTip("Book Tip Beta 2", ReadingTipCategory.BOOK, "", "", "", "", emptyTags, nolla));
        readingTipRepository.save(new ReadingTip("Video Tip 1", ReadingTipCategory.VIDEO, "", "", "", "", emptyTags, nolla));
        readingTipRepository.save(new ReadingTip("Video Tip 2", ReadingTipCategory.VIDEO, "", "", "", "", emptyTags, nolla));
    }
}
