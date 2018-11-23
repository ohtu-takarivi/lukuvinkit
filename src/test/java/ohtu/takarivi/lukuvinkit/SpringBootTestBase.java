package ohtu.takarivi.lukuvinkit;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-test.properties")
public abstract class SpringBootTestBase {
    private static boolean testDataHasBeenSetUp = false;
    protected static final int port = 8090;

    @Autowired
    protected CustomUserRepository customUserRepository;
    @Autowired
    protected ReadingTipRepository readingTipRepository;
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
        if (!testDataHasBeenSetUp) {
            setUpTestData();
            testDataHasBeenSetUp = true;
        }
    }

    public void setUpTestData() {
        CustomUser nolla = new CustomUser("nolla", encoder.encode("yksi"), "Testi");
        customUserRepository.save(nolla);
        CustomUser testi2 = new CustomUser("testi2", encoder.encode("testi2"), "testi2");
        customUserRepository.save(testi2);
        
        readingTipRepository.save(
            new ReadingTip("test reading tip 1",
                           ReadingTipCategory.BOOK,
                           "description for tip 1",
                           "https://example.com/",
                           "John Doe",
                           nolla));
        readingTipRepository.save(
            new ReadingTip("test reading tip 2",
                           ReadingTipCategory.VIDEO,
                           "description for tip 2",
                           "https://example.com/",
                           "Jane Doe",
                           testi2));
        readingTipRepository.save(
            new ReadingTip("test reading tip 3",
                           ReadingTipCategory.LINK,
                           "description for tip 3",
                           "https://example.com/",
                           "J. Doe",
                           nolla));
        readingTipRepository.save(
            new ReadingTip("test reading tip 4",
                           ReadingTipCategory.BOOK,
                           "description for tip 4",
                           "https://example.com/",
                           "Johnny Doe",
                           nolla));
        readingTipRepository.save(
            new ReadingTip("test reading tip 5",
                           ReadingTipCategory.BOOK,
                           "description for tip 5",
                           "https://example.com/",
                           "Doe, John",
                           testi2));
    }
}
