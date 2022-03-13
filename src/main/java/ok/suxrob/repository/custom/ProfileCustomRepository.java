package ok.suxrob.repository.custom;

import ok.suxrob.dto.filter.ProfileFilterDTO;
import ok.suxrob.entity.ProfileEntity;
import ok.suxrob.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PageImpl<ProfileEntity> filter(int page, int size, ProfileFilterDTO filterDTO) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder builder = new StringBuilder("select p from ProfileEntity p");
        StringBuilder builderCount = new StringBuilder("select count(p) from ProfileEntity p");

        if (filterDTO.getStatus() != null) {
            builder.append(" where p.status = '" + filterDTO.getStatus().name() + "'");
            builderCount.append(" where p.status = '" + filterDTO.getStatus().name() + "'");
        } else {
            builder.append(" where p.status = 'USER'");
            builderCount.append(" where p.status = 'USER'");
        }
        if (filterDTO.getId() != null) {
            builder.append(" and id=:id");
            builderCount.append(" and id=:id");
            params.put("id", filterDTO.getId());
        }
        if (filterDTO.getName() != null) {
            builder.append(" and name=:name");
            builderCount.append(" and name=:name");
            params.put("name", filterDTO.getName());
        }
        if (filterDTO.getSurname() != null) {
            builder.append(" and surname=:surname");
            builderCount.append(" and surname=:surname");
            params.put("surname", filterDTO.getSurname());
        }
        if (filterDTO.getEmail() != null) {
            builder.append(" and email=:email");
            builderCount.append(" and email=:email");
            params.put("email", filterDTO.getEmail());
        }
        if (filterDTO.getRole() != null) {
            builder.append(" and role=:role");
            builderCount.append(" and role=:role");
            params.put("role", filterDTO.getRole());
        }
        if (filterDTO.getStatus() != null) {
            builder.append(" and status=:status");
            builderCount.append(" and status=:status");
            params.put("status", filterDTO.getStatus());
        }
        Query query = entityManager.createQuery(builder.toString());
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        List<ProfileEntity> entityList = query.getResultList();

        query = entityManager.createQuery(builderCount.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        Long totalCount = (Long) query.getSingleResult();

        return new PageImpl<>(entityList, PageRequest.of(page, size), totalCount);
    }

    public List<ProfileEntity> filterCriateriaBuilder(ProfileFilterDTO dto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProfileEntity> criteriaQuery = criteriaBuilder.createQuery(ProfileEntity.class);
        Root<ProfileEntity> root = criteriaQuery.from(ProfileEntity.class);

        criteriaQuery.select(root);

        List<Predicate> predicateList = new ArrayList<>();

        if (dto.getRole() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("role"), dto.getRole()));
        } else {
            predicateList.add(criteriaBuilder.equal(root.get("role"), UserRole.USER));
        }

        if (dto.getId() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getName() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("name"), dto.getName()));
        }
        if (dto.getSurname() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("surname"), dto.getSurname()));
        }
        if (dto.getEmail() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("email"), dto.getEmail()));
        }
        if (dto.getStatus() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("status"), dto.getStatus()));
        }

        Predicate[] predicateArray = new Predicate[predicateList.size()];
        predicateList.toArray(predicateArray);

        criteriaQuery.where(predicateArray);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
