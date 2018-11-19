package ohtu.takarivi.lukuvinkit;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-test-controller.properties")
public abstract class SpringBootTestControllerBase {

    private static boolean first = true;

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
    public void setUpTestData() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        if (first) {
            first = false;
            CustomUser nolla = new CustomUser("nolla", encoder.encode("yksi"), "Testi");
            CustomUser testi2 = new CustomUser("testi2", encoder.encode("testi2"), "testi2");
            customUserRepository.save(nolla);
            customUserRepository.save(testi2);
            readingTipRepository.save(new ReadingTip("test reading tip 1", "description for tip 1", "https://example" +
                    ".com/", nolla));
            readingTipRepository.save(new ReadingTip("test reading tip 2", "description for tip 2", "https://example" +
                    ".com/", testi2));
        }
    }

}
