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
     * Searches a reading tip by the ID from the database.
     *
     * @param id The ID to find a reading tip with.
     * @return The ReadingTip instance for the reading tip, or null if not found.
     */
    List<ReadingTip> findByCustomUserId(Long customUserId);


    List<ReadingTip> findByCustomUserIdAndTitleContaining(Long customUserId, String title);
    
    List<ReadingTip> findByCustomUserIdAndTitleContainingOrDescriptionContainingOrUrlContainingOrAuthorContaining(Long customUserId, String title, String description, String url, String author);
    
    List<ReadingTip> findByTitleContainingAndCustomUserIdOrDescriptionContainingAndCustomUserId(String title,
                                                                                                Long customUserId,
                                                                                                String description,
                                                                                                Long customUserId2);

    List<ReadingTip> findByCategory(ReadingTipCategory category);

}
