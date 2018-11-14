package ohtu.takarivi.lukuvinkit.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class CustomUser extends AbstractPersistable<Long> {

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @OneToMany
    private List<ReadingTip> readingTips;

    public CustomUser() {
        super();
    }

    public CustomUser(String username, String password, String name) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReadingTip> getReadingTips() {
        return readingTips;
    }

    public void setReadingTips(List<ReadingTip> readingTips) {
        this.readingTips = readingTips;
    }

}
