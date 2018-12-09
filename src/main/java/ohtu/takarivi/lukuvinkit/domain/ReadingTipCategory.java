package ohtu.takarivi.lukuvinkit.domain;

public enum ReadingTipCategory {
    BOOK,
    ARTICLE,
    VIDEO,
    LINK;

    /**
     * Gets the proper ReadingTipCategory value by the name of that category.
     * 
     * @param category The name of the category to look for.
     * @return The correct ReadingTipCategory value, or null if no such category was found.
     */
    public static ReadingTipCategory getByName(String category) {
        switch (category.toLowerCase()) {
            case "articles":
                return ARTICLE;
            case "books":
                return BOOK;
            case "links":
                return LINK;
            case "videos":
                return VIDEO;
            default:
                try { 
                    return ReadingTipCategory.valueOf(category.toUpperCase()); 
                } catch (IllegalArgumentException ex) { 
                    return null; 
                }    
        }
    }
}
