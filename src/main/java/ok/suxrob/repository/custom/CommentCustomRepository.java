package ok.suxrob.repository.custom;

import ok.suxrob.dto.filter.CommentFilterDTO;
import ok.suxrob.entity.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PageImpl<CommentEntity> filter(int page, int size, CommentFilterDTO filterDTO) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder builder = new StringBuilder("select c from CommentEntity c");
        StringBuilder builderCount = new StringBuilder("select count(c) from CommentEntity c");

        if (filterDTO.getId() != null) {
            builder.append(" where c.id='" + filterDTO.getId() + "'");
            builderCount.append(" where c.id='" + filterDTO.getId() + "'");
        } else {
            builder.append(" where c.id=1");
            builderCount.append(" where c.id=1");
        }
        if (filterDTO.getArticleId() != null) {
            builder.append(" and c.article.id=:articleId");
            builderCount.append(" and c.article.id=:articleId");
            params.put("articleId", filterDTO.getArticleId());
        }
        if (filterDTO.getProfileId() != null) {
            builder.append(" and c.profile.id=:profileId");
            builderCount.append(" and c.profile.id=:profileId");
            params.put("profileId", filterDTO.getProfileId());
        }
        if (filterDTO.getFromDate() != null) {
            builder.append(" and c.createAt=:fromDate");
            builderCount.append(" and c.createAt=:fromDate");
            params.put("fromDate", LocalDateTime.of(filterDTO.getFromDate(), LocalTime.MIN));
        }
        if (filterDTO.getToDate() != null) {
            builder.append(" and c.createdAt=:toDate");
            builderCount.append(" and c.createdAt=:toDate");
            params.put("toDate", LocalDateTime.of(filterDTO.getFromDate(), LocalTime.MAX));
        }
        Query query = entityManager.createQuery(builder.toString());
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        List<CommentEntity> commentList = query.getResultList();

        query = entityManager.createQuery(builderCount.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        Long totalCount = (Long) query.getSingleResult();

        return new PageImpl<>(commentList, PageRequest.of(page, size), totalCount);
    }

    public List<CommentEntity> filterCriateriaBuilder(CommentFilterDTO dto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CommentEntity> criteriaQuery = criteriaBuilder.createQuery(CommentEntity.class);
        Root<CommentEntity> root = criteriaQuery.from(CommentEntity.class);

        criteriaQuery.select(root);

        List<Predicate> predicateList = new ArrayList<>();

        if (dto.getId() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        } else {
            predicateList.add(criteriaBuilder.equal(root.get("id"), 1));
        }

        if (dto.getProfileId() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("profile.id"), dto.getProfileId()));
        }
        if (dto.getArticleId() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("article.id"), dto.getArticleId()));
        }
        if (dto.getFromDate() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("createdAt"),
                    LocalDateTime.of(dto.getFromDate(), LocalTime.MIN)));
        }
        if (dto.getToDate() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("createdAt"),
                    LocalDateTime.of(dto.getFromDate(), LocalTime.MAX)));
        }

        Predicate[] predicateArray = new Predicate[predicateList.size()];
        predicateList.toArray(predicateArray);

        criteriaQuery.where(predicateArray);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
