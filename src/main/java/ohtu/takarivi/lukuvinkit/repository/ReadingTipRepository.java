package ohtu.takarivi.lukuvinkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;

/**
 * The JPA Repository for ReadingTip instances.
 */
@Repository
public interface ReadingTipRepository extends JpaRepository<ReadingTip, Long>, JpaSpecificationExecutor<ReadingTip> {

    /**
     * Searches a reading tip by its fields containing given text.
     *
     * @param customUserId The user ID doing the search.
     * @param title        The title to search for.
     * @param description  The description to search for.
     * @param url          The URL to search for.
     * @param author       The author to saerch for.
     * @return The list of matching ReadingTip instances.
     */
    List<ReadingTip> findByCustomUserIdAndTitleContainingOrDescriptionContainingOrUrlContainingOrAuthorContaining(Long customUserId, String title, String description, String url, String author);

    /**
     * Searches for all reading tips by the ID of the user that added them in a given category.
     * The results are sorted by their read status; unread reading tips are before the read ones.
     *
     * @param customUserId The ID of the custom user.
     * @param category     The category to search in.
     * @return The list of reading tips; unread tips are before the read tips.
     */
    List<ReadingTip> findByCustomUserIdAndCategoryOrderByIsReadAsc(Long customUserId, ReadingTipCategory category);

    /**
     * Finds tips based on the user ID and whether they have been selected.
     *
     * @param customUserId The ID of the custom user.
     * @return A list of selected reading tips.
     */
    List<ReadingTip> findByCustomUserIdAndIsSelectedTrue(Long customUserId);
    
    /**
     * Finds all reading tips based on the user ID and with a given tag.
     * 
     * @param customUserId The ID of the custom user.
     * @param name The name of the tag.
     * @return A list of selected reading tips.
     */
    List<ReadingTip> findByCustomUserIdAndTags_Name(Long customUserId, String name);

    /*
     * List<ReadingTip> findByCustomUserIdAndTitleContaining(Long customUserId, String title);
     *
     * List<ReadingTip> findByTitleContainingAndCustomUserIdOrDescriptionContainingAndCustomUserId(String title,
                                                                                                Long customUserId,
                                                                                                String description,
                                                                                                Long customUserId2);*/

}
