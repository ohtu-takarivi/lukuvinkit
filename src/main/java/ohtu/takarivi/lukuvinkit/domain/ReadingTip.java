package ohtu.takarivi.lukuvinkit.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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

    public ReadingTip() {
        super();
    }

    public ReadingTip(String title, String description, String url, CustomUser customUser) {
        this();
        this.title = title;
        this.description = description;
        this.url = url;
        this.customUser = customUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

}
