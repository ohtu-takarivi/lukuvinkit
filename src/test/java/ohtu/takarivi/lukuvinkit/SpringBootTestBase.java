package ohtu.takarivi.lukuvinkit;

import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;

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
    private PasswordEncoder encoder;
    
    public SpringBootTestBase() {
    }
    
    public void setUpTestData() {
        if (first) {
            first = false;
            
            customUserRepository.save(new CustomUser("nolla", encoder.encode("yksi"), "Testi"));
        }
    }
}
