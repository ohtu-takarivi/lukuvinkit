package ohtu.takarivi.lukuvinkit.repository;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The JPA Repository for CustomUser instances.
 */
@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

    /**
     * Searches an user by the username from the database.
     *
     * @param username The username to find an user with.
     * @return The CustomUser instance for the username, or null if not found.
     */
    CustomUser findByUsername(String username);

}
