package ohtu.takarivi.lukuvinkit.domain;

public enum ReadingTipCategory {
    BOOK,
    ARTICLE,
    VIDEO,
    LINK;

    public static ReadingTipCategory getByName(String category) {
        if (category.equalsIgnoreCase("books")) {
            return BOOK;
        } else if (category.equalsIgnoreCase("articles")) {
            return ARTICLE;
        } else if (category.equalsIgnoreCase("videos")) {
            return VIDEO;
        } else if (category.equalsIgnoreCase("links")) {
            return LINK;
        }
        try {
            return ReadingTipCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
