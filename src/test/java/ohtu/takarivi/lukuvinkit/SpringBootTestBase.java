package ohtu.takarivi.lukuvinkit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PropertySource("classpath:application-test.properties")
@EnableConfigurationProperties
public abstract class SpringBootTestBase {
    protected static final int port = 8090;
    private static boolean first = true;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private ReadingTipRepository readingTipRepository;

    @Autowired
    private PasswordEncoder encoder;
    
    public SpringBootTestBase() {
    }
    
    public void setUpTestData() {
        if (first) {
            first = false;
            
            CustomUser nolla = new CustomUser("nolla", encoder.encode("yksi"), "Testi");
            customUserRepository.save(nolla);
            CustomUser testi2 = new CustomUser("testi2", encoder.encode("testi2"), "testi2");
            customUserRepository.save(testi2);
            
            readingTipRepository.save(new ReadingTip("test reading tip 1", "description for tip 1", "https://example.com/", nolla));
            readingTipRepository.save(new ReadingTip("test reading tip 2", "description for tip 2", "https://example.com/", testi2));
        }
    }
}
