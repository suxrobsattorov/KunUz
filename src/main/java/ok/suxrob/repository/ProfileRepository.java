package ok.suxrob.repository;

import ok.suxrob.dto.ProfileDTO;
import ok.suxrob.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer>, JpaSpecificationExecutor<ProfileEntity> {

    Optional<ProfileEntity> findByLoginAndPswd(String login, String pswd);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set name=:name, surname=:surname, login=:login, " +
            "pswd=:password where id=:id")
    ProfileEntity update(@Param("id") Integer id, @Param("name") String name,
                         @Param("surname") String surname, @Param("login") String login,
                         @Param("password") String pswd);

    @Query("select p.email from ProfileEntity p where p.email=?1")
    String emailCheck(String email);

    @Query("select p from ProfileEntity p where p.email=?1")
    ProfileEntity gatByEmail(String email);

    @Query("select p.login from ProfileEntity p where p.login=?1")
    String loginCheck(String login);
}
