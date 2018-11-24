package ohtu.takarivi.lukuvinkit.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;

public class ReadingTipSearch {
    
    /**
     * Searches for reading tips with a single keyword.
     * 
     * @param readingTipRepository The repository to search from.
     * @param id The ID of the user doing the search.
     * @param keyword The keyword to search with.
     * @return The reading tips found with this search.
     */
    public static List<ReadingTip> searchSimple(ReadingTipRepository readingTipRepository, Long id, String keyword) {
        return readingTipRepository.findByCustomUserIdAndTitleContainingOrDescriptionContainingOrUrlContainingOrAuthorContaining(id, keyword, keyword, keyword, keyword);
    }

    /**
     * Searches for reading tips with an advanced search.
     * 
     * @param readingTipRepository The repository to search from.
     * @param id The ID of the user doing the search.
     * @param title The title to search for, or empty if title isn't searched for.
     * @param description The description to search for, or empty if description isn't searched for.
     * @param url The URL to search for, or empty if URL isn't searched for.
     * @param author The author to search for, or empty if author isn't searched for.
     * @param category List of allowed categories for the search results.
     * @param unreadstatus List of allowed unread/read statuses for the search results.
     * @return The reading tips found with this search.
     */
    public static List<ReadingTip> searchAdvanced(ReadingTipRepository readingTipRepository, Long id, String title,
            String description, String url, String author, List<String> category, List<String> unreadstatus) {
        return readingTipRepository.findAll(getSpec(id, title, description, url, author, category, unreadstatus));
    }

    private static Specification<ReadingTip> getSpec(Long id, String title, String description, String url, String author, List<String> category, List<String> unreadstatus) {
        return new Specification<ReadingTip>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<ReadingTip> root, CriteriaQuery<?> query,
                    CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                
                if (!title.isEmpty()) { // title must match
                    predicates.add(builder.like(root.get("title"), "%" + title + "%"));
                }
                if (!description.isEmpty()) { // description must match
                    predicates.add(builder.like(root.get("description"), "%" + description + "%"));
                }
                if (!url.isEmpty()) { // URL must match
                    predicates.add(builder.like(root.get("url"), "%" + url + "%"));
                }
                if (!author.isEmpty()) { // author must match
                    predicates.add(builder.like(root.get("author"), "%" + author + "%"));
                }
                
                Predicate allowedTypes = builder.disjunction(); 
                if (category.contains("books")) {
                    allowedTypes = builder.or(allowedTypes, builder.equal(root.<ReadingTipCategory>get("category"), ReadingTipCategory.BOOK));
                }
                if (category.contains("articles")) {
                    allowedTypes = builder.or(allowedTypes, builder.equal(root.<ReadingTipCategory>get("category"), ReadingTipCategory.ARTICLE));
                }
                if (category.contains("videos")) {
                    allowedTypes = builder.or(allowedTypes, builder.equal(root.<ReadingTipCategory>get("category"), ReadingTipCategory.VIDEO));
                }
                if (category.contains("links")) {
                    allowedTypes = builder.or(allowedTypes, builder.equal(root.<ReadingTipCategory>get("category"), ReadingTipCategory.LINK));
                }
                predicates.add(allowedTypes);
                
                if (!unreadstatus.contains("unread")) { // must not be unread
                    predicates.add(builder.isTrue(root.get("isRead")));
                }
                if (!unreadstatus.contains("read")) { // must not be read
                    predicates.add(builder.isFalse(root.get("isRead")));
                }
                
                return builder.and(predicates.toArray(new Predicate[] {}));
            }
        };
    }

    private ReadingTipSearch() {}
}
