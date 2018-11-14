package ohtu.takarivi.lukuvinkit.repository;

import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingTipRepository extends JpaRepository<ReadingTip, Long> {

    List<ReadingTip> findByCustomUserId(Long id);

}
