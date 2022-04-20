package ok.suxrob.repository;

import ok.suxrob.entity.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachRepository extends JpaRepository<AttachEntity, Integer> {

    Optional<AttachEntity> findByKey(String key);
}
