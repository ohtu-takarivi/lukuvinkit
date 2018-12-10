package ohtu.takarivi.lukuvinkit.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentForm {

    protected static final int COMMENT_MAX_LENGTH = 255;

    @Size(max = COMMENT_MAX_LENGTH, message = "Kommentin pituus 0-255 merkkiä")
    private String comment;

    /**
     * Constructs a new CommentForm with the given parameters.
     *
     * @param comment The comment of the reading tip as default.
     */
    public CommentForm(String comment) {
        this.comment = comment;
    }

}
