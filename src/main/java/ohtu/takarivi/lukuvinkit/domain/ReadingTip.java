package ohtu.takarivi.lukuvinkit.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * This class represents a reading tip, consisting of a title, 
 * description, URL and other associated data.
 * Each reading tip is also associated with an user as a CustomUser instance; 
 * this user represents the author of the reading tip.
 */
@Entity
@Getter
@Setter
public class ReadingTip extends AbstractPersistable<Long> {

    @NotEmpty
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReadingTipCategory category;
    
    @NotEmpty
    private String description;
    
    @NotEmpty
    private String url;
    
    @NotEmpty
    private String author;

    private Boolean isRead;

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
     * @param title       The title of the work to be read.
     * @param category    The type e.g. a book, video or link.
     * @param description A description to the work, added by the user who adds the reading tip.
     * @param url         The URL of the reading tip; this is the video link for YouTube links, audio link for
     *                    podcasts and blog post link for blog posts. It is customizable for books.
     * @param author      The author of the work, such as of a book or an article.
     * @param customUser  The CustomUser instance representing the user who added this reading tip.
     */
    public ReadingTip(String title, 
                      ReadingTipCategory category, 
                      String description, 
                      String url, 
                      String author, 
                      CustomUser customUser) {
        this();
        this.title = title;
        this.category = category;
        this.description = description;
        this.url = url;
        this.author = author;
        this.isRead = false;
        this.customUser = customUser;
    }

}
