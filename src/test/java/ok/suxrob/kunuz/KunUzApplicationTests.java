package ok.suxrob.kunuz;

import ok.suxrob.dto.ArticleDTO;
import ok.suxrob.dto.filter.ArticleFilterDTO;
import ok.suxrob.entity.ArticleEntity;
import ok.suxrob.entity.ProfileEntity;
import ok.suxrob.enums.ProfileStatus;
import ok.suxrob.enums.UserRole;
import ok.suxrob.repository.ProfileRepository;
import ok.suxrob.repository.custom.ArticleCustomRepositoryImpl;
import ok.suxrob.service.ArticleService;
import ok.suxrob.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KunUzApplicationTests {

    @Autowired
    ProfileService profileService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    private ArticleCustomRepositoryImpl articleCustomRepository;

//    @Test
//    void createProfile() {
//        ProfileDTO dto = new ProfileDTO();
//        dto.setName("Sarvar");
//        dto.setSurname("Sarvarov");
//        dto.setLogin("sarvar");
//        dto.setEmail("sarvar55@mail.com");
//        dto.setPswd("sarvar123");
//        dto.setUserRole(UserRole.PUBLISHER);
//        profileService.createProfileAdmin(dto, 3);
//    }

//    @Test
//    void createProfile() {
//        ProfileEntity dto = new ProfileEntity();
//        dto.setName("Suxrob");
//        dto.setSurname("Sattorov");
//        dto.setLogin("suxrob");
//        dto.setEmail("sattorov6606@gmail.com");
//        dto.setPswd("suxrob1");
//        dto.setRole(UserRole.ADMIN);
//        dto.setStatus(ProfileStatus.ACTIVE);
//        profileRepository.save(dto);
//    }

    @Test
    void createProfiley() {
        ArticleFilterDTO dto = new ArticleFilterDTO();
        dto.setArticleId(1);
        dto.setTitle("Dollar");
        System.out.println(articleService.filterSpe(1, 2, dto));
    }

    @Test
    void createProfile() {
        ProfileEntity dto = new ProfileEntity();
        dto.setName("Yusuf");
        dto.setSurname("Yusufov");
        dto.setLogin("yusuf");
        dto.setEmail("yusuf@gmail.com");
        dto.setPswd("yusuf1");
        dto.setRole(UserRole.MODERATOR);
        dto.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(dto);
    }

    @Test
    void createArticle() {
        ArticleDTO dto = new ArticleDTO();
        dto.setTitle("Dollar kursi");
        dto.setContent("Dollar kursi pasaymoqda.");
        articleService.create(dto, 3);
    }

}
