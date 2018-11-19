package ohtu.takarivi.lukuvinkit;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-test.properties")
public abstract class SpringBootTestBase {
    private static boolean first = true;
    protected static final int port = 8090;

    @Autowired
    private CustomUserRepository customUserRepository;
    @Autowired
    private ReadingTipRepository readingTipRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Before
    public void setUpTestData() {
        if (first) {
            first = false;
            CustomUser nolla = new CustomUser("nolla", encoder.encode("yksi"), "Testi");
            customUserRepository.save(nolla);
            CustomUser testi2 = new CustomUser("testi2", encoder.encode("testi2"), "testi2");
            customUserRepository.save(testi2);
            readingTipRepository.save(new ReadingTip("test reading tip 1", "description for tip 1", "https://example" +
                    ".com/", nolla));
            readingTipRepository.save(new ReadingTip("test reading tip 2", "description for tip 2", "https://example" +
                    ".com/", testi2));
        }
    }
}
