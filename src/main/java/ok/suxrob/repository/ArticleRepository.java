package ok.suxrob.repository;

import ok.suxrob.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer>, JpaSpecificationExecutor<ArticleEntity> {

    @Transactional
    @Modifying
    @Query("update ArticleEntity set title=:title, content=:content where id=:id")
    ArticleEntity update(@Param("id") Integer id, @Param("title") String title,
                         @Param("content") String content);

    @Transactional
    @Modifying
    @Query("delete from ArticleEntity where id=?1")
    ArticleEntity delete(Integer id);
}
