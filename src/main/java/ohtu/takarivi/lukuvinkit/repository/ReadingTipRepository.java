package ohtu.takarivi.lukuvinkit.repository;

import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The JPA Repository for ReadingTip instances.
 */
public interface ReadingTipRepository extends JpaRepository<ReadingTip, Long> {

    /**
     * Searches a reading tip by the ID from the database.
     *
     * @param id The ID to find a reading tip with.
     * @return The ReadingTip instance for the reading tip, or null if not found.
     */
    List<ReadingTip> findByCustomUserId(Long customUserId);
    
    /**
     * Searches a reading tips by the ID and title from the database.
     *
     * @param id The ID to find a reading tip with.
     * @param title The title of tip
     * @return The ReadingTip instance for the reading tip, or null if not found.
     */
    List<ReadingTip> findByCustomUserIdAndTitleContaining(Long customUserId, String title);
    
    List<ReadingTip> findByTitleContainingAndCustomUserIdOrDescriptionContainingAndCustomUserId(String title, Long customUserId, String description, Long customUserId2);

}
