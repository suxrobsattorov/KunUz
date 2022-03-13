package ok.suxrob.specification;

import ok.suxrob.entity.ProfileEntity;
import ok.suxrob.enums.ProfileStatus;
import ok.suxrob.enums.UserRole;
import org.springframework.data.jpa.domain.Specification;

public class ProfileSpecification {
    public static Specification<ProfileEntity> role(UserRole role) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("role"), root);
        });
    }

    public static Specification<ProfileEntity> status(ProfileStatus status) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), status);
        });
    }

    public static Specification<ProfileEntity> nom(String name, String field) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(name), field);
        });
    }

    public static Specification<ProfileEntity> id(Integer id) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("id"), id);
        });
    }
}
