package ohtu.takarivi.lukuvinkit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ohtu.takarivi.lukuvinkit.domain.ReadingTipTag;

/**
 * The JPA Repository for ReadingTip instances.
 */
@Repository
public interface ReadingTipTagRepository extends JpaRepository<ReadingTipTag, Long>, JpaSpecificationExecutor<ReadingTipTag> {

    /**
     * Gets a ReadingTipTag by the name of that tag.
     * 
     * @param name The name of the tag.
     * @return The existing ReadingTip or null if it doesn't exist.
     */
    ReadingTipTag findByName(String name);

}
