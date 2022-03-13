package ok.suxrob.repository;

import ok.suxrob.entity.EmailHistoryEntity;
import ok.suxrob.enums.EmailStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailHistoryRepository extends JpaRepository<EmailHistoryEntity, Integer> {

    List<EmailHistoryEntity> findAllByCreatedAtBetween(LocalDateTime createdAt, LocalDateTime createdAt2);

    @Query(value = "select * from EmailHistoryEntity e ORDER BY\n" +
            "    e.createdAt desc FETCH FIRST 1 ROW ONLY ", nativeQuery = true)
    EmailHistoryEntity findByLast();

    EmailHistoryEntity findFirstByOrderByCreatedAtDesc();

    Page<EmailHistoryEntity> getAllByStatus(EmailStatus status, Pageable pageable);
}
