package ohtu.takarivi.lukuvinkit.config;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Represents an user details service for Spring Data JPA. This handles users that are defined as CustomUser instances.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private ReadingTipRepository readingTipRepository;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * Initializes this details service.
     */
    @PostConstruct
    public void init() {
        CustomUser customUser = new CustomUser("abel", encoder.encode("123456"), "Abel");
        customUserRepository.save(customUser);
        readingTipRepository.save(new ReadingTip("Title 1", "-", "-", customUser));
        readingTipRepository.save(new ReadingTip("Title 2", "-", "-", customUser));
        readingTipRepository.save(new ReadingTip("Title 3", "-", "-", customUser));
        readingTipRepository.save(new ReadingTip("Title 4", "-", "-", customUser));

        customUser = new CustomUser("bob", encoder.encode("password"), "Bob");
        customUserRepository.save(customUser);
        readingTipRepository.save(new ReadingTip("Title 5", "-", "-", customUser));
        readingTipRepository.save(new ReadingTip("Title 6", "-", "-", customUser));

        customUser = new CustomUser("charlie", encoder.encode("qwerty"), "Charlie");
        customUserRepository.save(customUser);
        readingTipRepository.save(new ReadingTip("Title 7", "-", "-", customUser));
        readingTipRepository.save(new ReadingTip("Title 8", "-", "-", customUser));
        readingTipRepository.save(new ReadingTip("Title 9", "-", "-", customUser));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = customUserRepository.findByUsername(username);
        if (customUser == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        return new User(customUser.getUsername(),
                customUser.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }

}
