package ohtu.takarivi.lukuvinkit.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * This class represents an user in the system.
 */
@NoArgsConstructor
@Entity
@Getter
@Setter
public class CustomUser extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    @OneToMany
    private List<ReadingTip> readingTips;

    /**
     * Constructs a new CustomUser with the given parameters.
     *
     * @param username The username for this user.
     * @param password The password for this user.
     * @param name     The display name of this user.
     */
    public CustomUser(String username, String password, String name) {
        super();
        this.username = username;
        this.password = password;
        this.name = name;
    }

}
