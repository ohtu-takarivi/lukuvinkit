package ohtu.takarivi.lukuvinkit.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * This class represents an user in the system.
 */
@Entity
public class CustomUser extends AbstractPersistable<Long> {

    private static final String ALLOWED_USERNAME_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789_";
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 32;

    @Column(unique = true)
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    @OneToMany
    private List<ReadingTip> readingTips;

    /**
     * Constructs a new empty CustomUser instance.
     */
    public CustomUser() {
        super();
    }

    /**
     * Constructs a new CustomUser with the given parameters.
     *
     * @param username The username for this user.
     * @param password The password for this user.
     * @param name     The display name of this user.
     */
    public CustomUser(String username, String password, String name) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the new username for this user.
     *
     * @param username The new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the new password for this user.
     *
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the display name of the user.
     *
     * @return The display name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the new display name for this user.
     *
     * @param name The new display name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the new list of reading tips for this user.
     *
     * @return The list of reading tips associated with this user.
     */
    public List<ReadingTip> getReadingTips() {
        return readingTips;
    }

    /**
     * Sets the new list of reading tips for this user.
     *
     * @param readingTips The new list of display tips.
     */
    public void setReadingTips(List<ReadingTip> readingTips) {
        this.readingTips = readingTips;
    }

    /**
     * Returns whether the given username is valid. Valid usernames only contain lowercase letters, numbers and
     * underscores and must be between 3-32 characters long.
     *
     * @param username The username to test.
     * @return Whether the username is valid.
     */
    public static boolean isValidUsername(String username) {
        String name = username.trim();
        if (name.isEmpty()) {
            return false;
        }

        if (name.length() < MIN_USERNAME_LENGTH || name.length() > MAX_USERNAME_LENGTH) {
            return false;
        }

        for (char c : username.toCharArray()) {
            if (!ALLOWED_USERNAME_CHARS.contains("" + c)) {
                return false;
            }
        }
        return true;
    }

}
