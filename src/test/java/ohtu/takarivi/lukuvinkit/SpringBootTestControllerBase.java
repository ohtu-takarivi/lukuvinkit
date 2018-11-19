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

    protected static boolean init = false;

    @Autowired
    protected UserDetailsService userDetailsService;
    @Autowired
    protected CustomUserRepository customUserRepository;
    @Autowired
    protected ReadingTipRepository readingTipRepository;
    @Autowired
    protected PasswordEncoder encoder;
    @Autowired
    protected WebApplicationContext context;
    protected MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        if (!init) {
            init = true;
            CustomUser cu1 = new CustomUser("user1", encoder.encode("password1"), "-");
            CustomUser cu2 = new CustomUser("user2", encoder.encode("password2"), "-");
            customUserRepository.save(cu1);
            customUserRepository.save(cu2);
            readingTipRepository.save(new ReadingTip("tip1", "-", "-", cu1));
            readingTipRepository.save(new ReadingTip("tip2", "-", "-", cu2));
        }
    }

}
