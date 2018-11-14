package ohtu.takarivi.lukuvinkit.repository;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

    CustomUser findByUsername(String username);

}
