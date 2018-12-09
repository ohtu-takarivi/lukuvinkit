package ohtu.takarivi.lukuvinkit.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;

public class ReadingTipSearch {

    /**
     * Searches for reading tips with a single keyword.
     *
     * @param readingTipRepository The repository to search from.
     * @param username The user name of the user doing the search.
     * @param id The ID of the user doing the search.
     * @param keyword The keyword to search with.
     * @return The reading tips found with this search.
     */
    public static List<ReadingTip> searchSimple(ReadingTipRepository readingTipRepository, String username, Long id, String keyword) {
        Specification<ReadingTip> specRt = getSpecSearchSimple(id, username, keyword.toLowerCase());
        return readingTipRepository.findAll(specRt);
    }

    private static Specification<ReadingTip> getSpecSearchSimple(Long id, String username, String keyword) {
        return new Specification<ReadingTip>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<ReadingTip> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                // user can only search for their own tips
                predicates.add(builder.like(root.<String>get("customUser").get("username"), username));
                
                Predicate allowed = builder.disjunction();
                // allow the keyword to appear in the title, description, author, ISBN or URL
                allowed = builder.or(allowed, builder.like(builder.lower(root.get("title")), "%" + keyword + "%"));
                allowed = builder.or(allowed, builder.like(builder.lower(root.get("description")), "%" + keyword + "%"));
                allowed = builder.or(allowed, builder.like(builder.lower(root.get("author")), "%" + keyword + "%"));
                // ignore dashes/hyphens in ISBN search
                allowed = builder.or(allowed, builder.like(builder.function("REPLACE", 
                                                            String.class, 
                                                            root.get("isbn"), 
                                                            builder.literal("-"), 
                                                            builder.literal("")), 
                                                            "%" + keyword.replace("-", "") + "%"));
                allowed = builder.or(allowed, builder.like(root.get("url"), "%" + keyword + "%"));
                
                // tags
                Path<Set<String>> tagsPath = root.join("tags", JoinType.LEFT);
                Predicate tagsMatch = builder.disjunction();
                for (String word: keyword.split(" ")) {
                    if (!word.trim().isEmpty()) {
                        tagsMatch = builder.or(tagsMatch, builder.like(builder.lower(tagsPath.get("name")), "%" + word.trim() + "%"));
                    }
                }
                allowed = builder.or(allowed, tagsMatch);

                predicates.add(allowed);

                return builder.and(predicates.toArray(new Predicate[]{}));
            }
        };
    }

    /**
     * Searches for reading tips with an advanced search.
     *
     * @param readingTipRepository The repository to search from.
     * @param customUser The CustomUser instance of the user doing the search.
     * @param id The ID of the user doing the search.
     * @param title The title to search for, or empty if title isn't searched
     * for.
     * @param description The description to search for, or empty if description
     * isn't searched for.
     * @param url The URL to search for, or empty if URL isn't searched for.
     * @param isbn The ISBN to search for, or empty if ISBN isn't searched for.
     * @param author The author to search for, or empty if author isn't searched
     * for.
     * @param tags The tags to search for, separated with spaces.
     * @param category List of allowed categories for the search results.
     * @param unreadstatus List of allowed unread/read statuses for the search
     * results.
     * @return The reading tips found with this search.
     */
    public static List<ReadingTip> searchAdvanced(ReadingTipRepository readingTipRepository, CustomUser customUser, Long id, String title,
            String description, String url, String isbn, String author, String tags, List<String> category, List<String> unreadstatus) {
        return readingTipRepository.findAll(getSpec(customUser, id, title, description, url, isbn, author, tags, category, unreadstatus));
    }

    private static Specification<ReadingTip> getSpec(CustomUser customUser, Long id, String title, String description, String url, String isbn, String author, String tags, List<String> category, List<String> unreadstatus) {
        return new Specification<ReadingTip>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<ReadingTip> root, CriteriaQuery<?> query,
                    CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(builder.like(root.<String>get("customUser").get("username"), customUser.getUsername()));

                addAttributePredicates(predicates, root, builder);
                addTypePredicate(predicates, root, builder);
                addReadStatusPredicate(predicates, root, builder);
                addTagsPredicate(predicates, root, builder);

                return builder.and(predicates.toArray(new Predicate[]{}));
            }

            // attribute predicates: title, description, author, URL, ISBN
            private void addAttributePredicates(List<Predicate> predicates, Root<ReadingTip> root,
                    CriteriaBuilder builder) {
                if (!title.isEmpty()) { // title must match
                    predicates.add(builder.like(builder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
                }
                if (!description.isEmpty()) { // description must match
                    predicates.add(builder.like(builder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
                }
                if (!author.isEmpty()) { // author must match
                    predicates.add(builder.like(builder.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
                }
                if (!url.isEmpty()) { // URL must match
                    predicates.add(builder.like(builder.lower(root.get("url")), "%" + url.toLowerCase() + "%"));
                }
                if (!isbn.isEmpty()) { // ISBN must match
                    // ignore dashes/hyphens in ISBN search
                    predicates.add(builder.like(builder.function("REPLACE", 
                                                                String.class, 
                                                                root.get("isbn"), 
                                                                builder.literal("-"), 
                                                                builder.literal("")), 
                                                                "%" + isbn.replace("-", "") + "%"));
                }
            }

            // type predicate: books, articles, videos, links
            private void addTypePredicate(List<Predicate> predicates, Root<ReadingTip> root, CriteriaBuilder builder) {
                Predicate allowedTypes = builder.disjunction();
                // set allowed types based on checkbox values
                final String[] validCategories = new String[] {"books", "articles", "videos", "links"};
                for (String validCategory: validCategories) {
                    if (category.contains(validCategory)) {
                        allowedTypes = builder.or(allowedTypes, builder.equal(root.<ReadingTipCategory>get("category"), ReadingTipCategory.getByName(validCategory)));
                    }
                }
                predicates.add(allowedTypes);
            }

            // read status predicate: unread, read
            private void addReadStatusPredicate(List<Predicate> predicates, Root<ReadingTip> root,
                    CriteriaBuilder builder) {
                if (!unreadstatus.contains("unread")) { // must not be unread
                    predicates.add(builder.isTrue(root.get("isRead")));
                }
                if (!unreadstatus.contains("read")) { // must not be read
                    predicates.add(builder.isFalse(root.get("isRead")));
                }
            }

            // tags
            private void addTagsPredicate(List<Predicate> predicates, Root<ReadingTip> root, CriteriaBuilder builder) {
                if (!tags.trim().isEmpty()) {
                    Path<Set<String>> tagsPath = root.join("tags", JoinType.LEFT);
                    Predicate tagsMatch = builder.disjunction();
                    for (String word: tags.split(" ")) {
                        if (!word.trim().isEmpty()) {
                            tagsMatch = builder.or(tagsMatch, builder.like(builder.lower(tagsPath.get("name")), "%" + word.trim() + "%"));
                        }
                    }
                    predicates.add(tagsMatch);
                }
            }
        };
    }

    private ReadingTipSearch() {
    }
}
