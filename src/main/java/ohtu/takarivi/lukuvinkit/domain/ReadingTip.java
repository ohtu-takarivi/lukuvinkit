package ohtu.takarivi.lukuvinkit.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * This class represents a reading tip, consisting of a title,
 * description, URL and other associated data.
 * Each reading tip is also associated with an user as a CustomUser instance;
 * this user represents the author of the reading tip.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class ReadingTip extends AbstractPersistable<Long> {

    private String title;
    @Enumerated(EnumType.STRING)
    private ReadingTipCategory category;
    @Lob
    private String description;
    private String url;
    private String author;
    private String isbn;
    private Boolean isRead;
    private Boolean isSelected;
    @ManyToOne
    private CustomUser customUser;

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
                      String isbn,
                      CustomUser customUser) {
        super();
        this.title = title;
        this.category = category;
        this.description = description;
        this.url = url;
        this.author = author;
        this.isbn = isbn;
        this.isRead = false;
        this.isSelected = false;
        this.customUser = customUser;
    }

    @Override
    public String toString() {
        if (this.category == ReadingTipCategory.ARTICLE) {
            return "Artikkeli: " + this.title + "\n"
                    + "Tekijä(t): " + this.author + "\n"
                    + this.description;
        } else if (this.category == ReadingTipCategory.BOOK) {
            return "Kirja: " + this.title + "\n"
                    + "Tekijä(t): " + this.author + "\n"
                    + "ISBN-tunnus: " + this.isbn + "\n"
                    + this.description;
        } else if (this.category == ReadingTipCategory.LINK) {
            return "Verkkosivu: " + this.title + "\n"
                    + "Tekijä(t): " + this.author + "\n"
                    + "Linkki: " + this.url + "\n"
                    + this.description;
        } else if (this.category == ReadingTipCategory.VIDEO) {
            return "Video: " + this.title + "\n"
                    + "Tekijä(t): " + this.author + "\n"
                    + "Linkki: " + this.url + "\n"
                    + this.description;
        } else {
            return super.toString();
        }
    }

    /**
     * Changes the tip's selected state.
     */
    public void toggleIsSelected() {
        isSelected = !isSelected;
    }

}
