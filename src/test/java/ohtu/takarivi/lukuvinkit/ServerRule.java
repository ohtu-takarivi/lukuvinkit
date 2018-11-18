package ohtu.takarivi.lukuvinkit;

import java.util.Collections;

import org.junit.Ignore;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;

@Deprecated
@Ignore
public class ServerRule extends ExternalResource{

    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private PasswordEncoder encoder;
    
    private final int port;
    ConfigurableApplicationContext app;

    public ServerRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
    	SpringApplication sapp = new SpringApplication(Application.class);
        sapp.setDefaultProperties(Collections.singletonMap("server.port", Integer.toString(port)));
        
        setUpTestData();
        
        this.app = sapp.run();
    }

    private void setUpTestData() {
        customUserRepository.save(new CustomUser("nolla", encoder.encode("yksi"), "Testi"));
    }

    @Override
    protected void after() {
        app.close();
    }
}