package ok.suxrob.repository;

import ok.suxrob.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer>, JpaSpecificationExecutor<CommentEntity> {

    @Query("select c from CommentEntity c where c.profile.id=?1")
    List<CommentEntity> findbyProfileId(Integer id);

    @Query("select c from CommentEntity c where c.article.id=?1")
    List<CommentEntity> findbyArticleId(Integer id);

    @Transactional
    @Modifying
    @Query("update CommentEntity set content=?2 where id=?1")
    CommentEntity update(Integer id, String content);


}
