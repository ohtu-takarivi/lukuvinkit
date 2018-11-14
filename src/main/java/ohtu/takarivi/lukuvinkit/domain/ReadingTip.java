package ohtu.takarivi.lukuvinkit.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * This class represents a reading tip, consisting of a title, description, URL and other associated data.
 * Each reading tip is also associated with an user as a CustomUser instance; this user represents the author of the reading tip.
 */
@Entity
public class ReadingTip extends AbstractPersistable<Long> {

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String url;
    @ManyToOne
    private CustomUser customUser;

    /**
     * Constructs a new empty ReadingTip instance.
     */
    public ReadingTip() {
        super();
    }

    /**
     * Constructs a new ReadingTip with the given parameters.
     * 
     * @param title The title of the work to be read.
     * @param description A description to the work, added by the user who adds the reading tip.
     * @param url The URL of the reading tip; this is the video link for YouTube links, audio link for podcasts and blog post link for blog posts. It is customizable for books.
     * @param customUser The CustomUser instance representing the user who added this reading tip.
     */
    public ReadingTip(String title, String description, String url, CustomUser customUser) {
        this();
        this.title = title;
        this.description = description;
        this.url = url;
        this.customUser = customUser;
    }

    /**
     * Gets the title of the work of this reading tip.
     * 
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the work of this reading tip. This should mostly be associated with operations to modify the reading tip.
     * 
     * @param title The new title for this reading tip.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of this reading tip.
     * 
     * @return The description.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the description of this reading tip. This is defined by the user.
     * 
     * @param description The new description for this reading tip.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the URL of this reading tip.
     * 
     * @return The URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the new URL of the work of this reading tip. This is the video link for YouTube links, audio link for podcasts and blog post link for blog posts. It is customizable for books.
     * 
     * @param url The new URL for this reading tip.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the user associated with this reading tip; this user is the author of the reading tip.
     * 
     * @return The user associated with this reading tip as a CustomUser instance.
     */
    public CustomUser getCustomUser() {
        return customUser;
    }

    /**
     * Sets the new user associated with this reading tip. This effectively allows moving the ownership of the reading tip. 
     * 
     * @param customUser The new user associated with reading tip as a CustomUser instance.
     */
    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

}
