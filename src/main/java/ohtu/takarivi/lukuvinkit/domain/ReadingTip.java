package ohtu.takarivi.lukuvinkit.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable
    private Set<ReadingTipTag> tags = new HashSet<>();

    /**
     * Constructs a new ReadingTip with the given parameters.
     *
     * @param title       The title of the work to be read.
     * @param category    The type e.g. a book, video or link.
     * @param description A description to the work, added by the user who adds the reading tip.
     * @param url         The URL of the reading tip; this is the video link for YouTube links, audio link for
     *                    podcasts and blog post link for blog posts. It is customizable for books.
     * @param author      The author of the work, such as of a book or an article.
     * @param isbn        The ISBN code of the work if it is a book.
     * @param tags        The tags as a Set of ReadingTipTags.
     * @param customUser  The CustomUser instance representing the user who added this reading tip.
     */
    public ReadingTip(String title,
                      ReadingTipCategory category,
                      String description,
                      String url,
                      String author,
                      String isbn,
                      Set<ReadingTipTag> tags,
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
        this.tags = tags;
    }

    @Override
    public String toString() {
        if (this.category == ReadingTipCategory.ARTICLE) {
            return articleToString();
        } else if (this.category == ReadingTipCategory.BOOK) {
            return bookToString();
        } else if (this.category == ReadingTipCategory.LINK) {
            return linkToString();
        } else if (this.category == ReadingTipCategory.VIDEO) {
            return videoToString();
        } else {
            return super.toString();
        }
    }
    
    private String articleToString() {
        return "Artikkeli: " + this.title + "\n"
                    + "Tekijä(t): " + this.author + "\n"
                    + this.description; 
    }
    
    private String bookToString() {
        return "Kirja: " + this.title + "\n"
                    + "Tekijä(t): " + this.author + "\n"
                    + "ISBN-tunnus: " + this.isbn + "\n"
                    + this.description;
    }
    
    private String linkToString() {
        return "Verkkosivu: " + this.title + "\n"
                    + "Tekijä(t): " + this.author + "\n"
                    + "Linkki: " + this.url + "\n"
                    + this.description;
    }
    
    private String videoToString() {
        return "Video: " + this.title + "\n"
                    + "Tekijä(t): " + this.author + "\n"
                    + "Linkki: " + this.url + "\n"
                    + this.description;
    }

    /**
     * Changes the tip's selected state.
     */
    public void toggleIsSelected() {
        isSelected = !isSelected;
    }

}
