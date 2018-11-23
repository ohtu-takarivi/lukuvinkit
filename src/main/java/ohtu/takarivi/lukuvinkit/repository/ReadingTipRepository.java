package ohtu.takarivi.lukuvinkit.repository;

import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The JPA Repository for ReadingTip instances.
 */
@Repository
public interface ReadingTipRepository extends JpaRepository<ReadingTip, Long>, JpaSpecificationExecutor<ReadingTip> {

    /**
     * Searches a reading tip by the ID of the user that added them.
     *
     * @param id The ID of the custom user.
     * @return The list of ReadingTip instances representing the reading tips
     *         added by that user. 
     */
    List<ReadingTip> findByCustomUserId(Long customUserId);

    /**
     * Searches a reading tip by its fields containing given text.
     * 
     * @param customUserId The user ID doing the search.
     * @param title The title to search for.
     * @param description The description to search for.
     * @param url The URL to search for.
     * @param author The author to saerch for.
     * @return The list of matching ReadingTip instances.
     */
    List<ReadingTip> findByCustomUserIdAndTitleContainingOrDescriptionContainingOrUrlContainingOrAuthorContaining(Long customUserId, String title, String description, String url, String author);

    /**
     * Searches for all reading tips in a given category.
     * The results are sorted by their read status; unread reading tips are before the read ones.
     * 
     * @param category The category to search in.
     * @return The list of reading tips; unread tips are before the read tips.
     */
    List<ReadingTip> findByCategoryOrderByIsReadAsc(ReadingTipCategory category);
    
    /*
     * List<ReadingTip> findByCustomUserIdAndTitleContaining(Long customUserId, String title);
     * 
     * List<ReadingTip> findByTitleContainingAndCustomUserIdOrDescriptionContainingAndCustomUserId(String title,
                                                                                                Long customUserId,
                                                                                                String description,
                                                                                                Long customUserId2);*/
    
}
