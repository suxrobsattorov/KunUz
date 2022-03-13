package ok.suxrob.repository;

import ok.suxrob.entity.LikeEntity;
import ok.suxrob.entity.ProfileEntity;
import ok.suxrob.enums.LikeStatus;
import ok.suxrob.enums.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    @Query("select count(l) from LikeEntity l where l.actionId=?1 and l.type=?2 and l.status=?3")
    Integer likeOrDislikeCount(Integer actionId, LikeType type, LikeStatus status);

    @Query("select l from LikeEntity l where l.actionId=?1 and l.type=?2 and l.profile=?3")
    LikeEntity getLikeCheck(Integer actionId, LikeType type, ProfileEntity profileEntity);

    List<LikeEntity> findByProfile(ProfileEntity profileEntity);
}
