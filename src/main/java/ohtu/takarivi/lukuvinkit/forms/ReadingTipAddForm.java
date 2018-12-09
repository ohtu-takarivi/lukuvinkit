package ohtu.takarivi.lukuvinkit.forms;

import lombok.Getter;
import lombok.Setter;
import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipTagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import static ohtu.takarivi.lukuvinkit.forms.FormUtils.isValidISBN;
import static ohtu.takarivi.lukuvinkit.forms.FormUtils.isValidURL;

@Getter
@Setter
public class ReadingTipAddForm extends AddForm {

    private static final int ISBN_MIN_LENGTH = 10;
    private static final int ISBN_MAX_LENGTH = 17;
    private static final int URL_MIN_LENGTH = 1;
    private static final int URL_MAX_LENGTH = 255;

    private String category;
    private String isbn;
    private String url;

    /**
     * Used to run additional validation for this form.
     *
     * @param result The BindingResult that value rejections are submitted to.
     */
    @Override
    public void validateRest(BindingResult result) {
        ReadingTipCategory readingTipCategory = ReadingTipCategory.getByName(category);
        if (readingTipCategory == ReadingTipCategory.BOOK) {
            if (isbn.length() < ISBN_MIN_LENGTH || isbn.length() > ISBN_MAX_LENGTH) {
                result.rejectValue("isbn", "", "ISBN:n pituus välimerkkeineen 10-17 merkkiä");
            }
            if (!isValidISBN(this.isbn)) {
                result.rejectValue("isbn", "", "ISBN ei kelpaa");
            }
        } else if ((readingTipCategory == ReadingTipCategory.LINK || readingTipCategory == ReadingTipCategory.VIDEO)) {
            if (url.length() < URL_MIN_LENGTH || url.length() > URL_MAX_LENGTH) {
                result.rejectValue("url", "", "URL:n pituus 1-255 merkkiä");
            }
            if (!isValidURL(this.url)) {
                result.rejectValue("url", "", "URL ei kelpaa");
            }
        }
    }

    /**
     * Creates a ReadinGTip object from the values of this form.
     *
     * @param customUser The CustomUser representing the currently authenticated user.
     * @param readingTipTagRepository The ReadingTipTagRepository used to prepare the tags for the created ReadingTip instance.
     * @return The ReadingTip instance.
     */
    public ReadingTip createReadingTip(CustomUser customUser, ReadingTipTagRepository readingTipTagRepository) {
        ReadingTipCategory readingTipCategory = ReadingTipCategory.getByName(category);
        switch (readingTipCategory) {
            case ARTICLE:
                return new ReadingTip(title, readingTipCategory, description, "", author, "", FormUtils.prepareTags(readingTipTagRepository, tags.split(" ")), customUser);
            case BOOK:
                return new ReadingTip(title, readingTipCategory, description, "", author, isbn, FormUtils.prepareTags(readingTipTagRepository, tags.split(" ")), customUser);
            case LINK:
                return new ReadingTip(title, readingTipCategory, description, url, author, "", FormUtils.prepareTags(readingTipTagRepository, tags.split(" ")), customUser);
            case VIDEO:
                return new ReadingTip(title, readingTipCategory, description, url, author, "", FormUtils.prepareTags(readingTipTagRepository, tags.split(" ")), customUser);
            default:
                return null;
        }
    }

}
