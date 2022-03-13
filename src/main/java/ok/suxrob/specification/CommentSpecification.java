package ok.suxrob.specification;

import ok.suxrob.entity.CommentEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CommentSpecification {

    public static Specification<CommentEntity> equal(String field, Integer id) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(field), id);
        });
    }

    public static Specification<CommentEntity> date(LocalDateTime dateTime) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("createdAt"), dateTime);
        });
    }
}
