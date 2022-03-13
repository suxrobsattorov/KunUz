package ok.suxrob.repository;

import ok.suxrob.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update RegionEntity set name=?2 where id=?1")
    RegionEntity update(Integer id, String name);

    @Transactional
    @Modifying
    @Query("delete from RegionEntity  where id=?1")
    RegionEntity delete(Integer id);
}
