package ok.suxrob.service;

import lombok.Data;
import ok.suxrob.dto.ArticleDTO;
import ok.suxrob.dto.filter.ArticleFilterDTO;
import ok.suxrob.entity.ArticleEntity;
import ok.suxrob.entity.ProfileEntity;
import ok.suxrob.enums.ArticleStatus;
import ok.suxrob.enums.UserRole;
import ok.suxrob.exceptions.BadRequestException;
import ok.suxrob.exceptions.ItemNotFoundException;
import ok.suxrob.repository.custom.ArticleCustomRepositoryImpl;
import ok.suxrob.repository.ArticleRepository;
import ok.suxrob.specification.ArticleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleCustomRepositoryImpl articleCustomRepository;

    public ArticleDTO create(ArticleDTO dto, Integer profileId) {
        ProfileEntity profileEntity = profileService.get(profileId);
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new BadRequestException("Title can not be null");
        }
        if (dto.getContent() == null || dto.getContent().isEmpty()) {
            throw new BadRequestException("Content can not be null");
        }
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setProfile(profileEntity);
        entity.setStatus(ArticleStatus.CREATED);

        articleRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public void publish(Integer articleId, Integer publishedId) {
        ProfileEntity profileEntity = profileService.get(publishedId);

        ArticleEntity entity = get(articleId);
        entity.setProfile(profileEntity);
        entity.setStatus(ArticleStatus.PUBLISHED);
        entity.setPublishedDate(LocalDateTime.now());
        articleRepository.save(entity);
    }

    public List<ArticleDTO> getAll() {
        List<ArticleEntity> entityList = articleRepository.findAll();
        if (entityList.isEmpty()) {
            throw new BadRequestException("List can not be empty");
        }
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ArticleDTO getById(Integer id) {
        ArticleEntity entity = articleRepository.getById(id);
        if (entity == null) {
            throw new BadRequestException("Article not found");
        }
        return toDTO(entity);
    }

    public boolean update(Integer id, ArticleDTO dto) {
        ArticleEntity entity = get(id);
        if (entity == null) {
            throw new BadRequestException("Not update");
        }
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        articleRepository.save(entity);
        return true;
    }

    public boolean delete(Integer id) {
        if (id == 0) {
            throw new BadRequestException("Not delete");
        }
        articleRepository.deleteById(id);
        return true;
    }

    public boolean isModerator(Integer id) {
        return profileService.getById(id).getUserRole().equals(UserRole.userRole(UserRole.MODERATOR.toString()));
    }

    public boolean isPublisher(Integer id) {
        return profileService.getById(id).getUserRole().equals(UserRole.userRole(UserRole.PUBLISHER.toString()));
    }

    public PageImpl<ArticleDTO> filterSpe(int page, int size, ArticleFilterDTO dto) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        Specification<ArticleEntity> spec = null;
        if (dto.getStatus() != null) {
            spec = Specification.where(ArticleSpecification.status(dto.getStatus()));
        } else {
            spec = Specification.where(ArticleSpecification.status(ArticleStatus.PUBLISHED));
        }

        if (dto.getTitle() != null) {
            spec.and(ArticleSpecification.title(dto.getTitle()));
        }
        if (dto.getArticleId() != null) {
            spec.and(ArticleSpecification.equal("id", dto.getArticleId()));
        }
        if (dto.getProfileId() != null) {
            spec.and(ArticleSpecification.equal("profile.id", dto.getProfileId()));
        }

        Page<ArticleEntity> articlePage = articleRepository.findAll(spec, pageable);

        List<ArticleDTO> dtoList = articlePage.getContent().stream().map(this::toDTO).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, articlePage.getTotalElements());
    }

    public ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setProfileId(entity.getProfile().getId());
        dto.setCreatedDate(entity.getCreatedAt());
        return dto;
    }

    public ArticleEntity get(Integer id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Article not found"));
    }
}
