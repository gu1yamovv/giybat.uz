package api.giybat.uz.repository;

import api.giybat.uz.entity.SmsHistoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, String> {

    Long countByPhoneAndCreatedDateBetween(String phone, LocalDateTime from, LocalDateTime to);


    Optional<SmsHistoryEntity> findTop1ByPhoneOrderByCreatedDateDesc(String phone);

    @Modifying
    @Transactional
    @Query("update SmsHistoryEntity set attemptCount = coalesce(attemptCount,0)  + 1 where id = ?1")
    void updateAttemptCount(String id);
}
