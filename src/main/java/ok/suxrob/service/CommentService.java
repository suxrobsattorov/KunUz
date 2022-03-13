package ok.suxrob.service;

import ok.suxrob.dto.ArticleDTO;
import ok.suxrob.dto.CommentDTO;
import ok.suxrob.dto.filter.ArticleFilterDTO;
import ok.suxrob.dto.filter.CommentFilterDTO;
import ok.suxrob.entity.ArticleEntity;
import ok.suxrob.entity.CommentEntity;
import ok.suxrob.entity.ProfileEntity;
import ok.suxrob.enums.ArticleStatus;
import ok.suxrob.exceptions.BadRequestException;
import ok.suxrob.exceptions.ItemNotFoundException;
import ok.suxrob.repository.CommentRepository;
import ok.suxrob.repository.custom.CommentCustomRepository;
import ok.suxrob.specification.ArticleSpecification;
import ok.suxrob.specification.CommentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentCustomRepository customRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleService articleService;

    public CommentDTO create(Integer profileId, CommentDTO dto) {
        ProfileEntity profileEntity = profileService.get(profileId);
        ArticleEntity articleEntity = articleService.get(dto.getArticleId());
        if (dto.getContent() == null || dto.getContent().isEmpty()) {
            throw new BadRequestException("Content can not be null");
        }
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setProfile(profileEntity);
        entity.setArticle(articleEntity);

        commentRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public CommentDTO getById(Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("Comment not found");
        }
        return toDTO(optional.get());
    }

    public List<CommentDTO> getByProfileId(Integer profileId) {
        List<CommentEntity> commentEntityList = commentRepository.findbyProfileId(profileId);
        if (commentEntityList.isEmpty()) {
            throw new BadRequestException("Comments not found");
        }
        return commentEntityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<CommentDTO> getByArticleId(Integer articleId) {
        List<CommentEntity> commentEntityList = commentRepository.findbyArticleId(articleId);
        if (commentEntityList.isEmpty()) {
            throw new BadRequestException("Comments not fount");
        }
        return commentEntityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<CommentDTO> getAll(Integer adminId) {
        List<CommentEntity> commentEntityList = commentRepository.findbyProfileId(adminId);
        if (commentEntityList.isEmpty()) {
            throw new BadRequestException("AdminId not found");
        }
        return commentEntityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public boolean update(Integer id, CommentDTO dto) {
        CommentEntity entity = get(id);
        if (entity == null) {
            throw new BadRequestException("Not update");
        }
        entity.setContent(dto.getContent());
        commentRepository.save(entity);
        return true;
    }

    public boolean delete(Integer id) {
        if (id == 0) {
            throw new BadRequestException("Not update");
        }
        commentRepository.deleteById(id);
        return true;
    }

    public PageImpl<CommentDTO> filterSpe(int page, int size, CommentFilterDTO dto) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        Specification<CommentEntity> spec = null;
        if (dto.getId() != null) {
            spec = Specification.where(CommentSpecification.equal("id", dto.getId()));
        } else {
            spec = Specification.where(CommentSpecification.equal("id", 1));
        }

        if (dto.getProfileId() != null) {
            spec.and(CommentSpecification.equal("profile", dto.getProfileId()));
        }
        if (dto.getArticleId() != null) {
            spec.and(CommentSpecification.equal("article", dto.getArticleId()));
        }
        if (dto.getFromDate() != null) {
            spec.and(CommentSpecification.date(LocalDateTime.of(dto.getFromDate(), LocalTime.MIN)));
        }
        if (dto.getToDate() != null) {
            spec.and(CommentSpecification.date(LocalDateTime.of(dto.getToDate(), LocalTime.MAX)));
        }

        Page<CommentEntity> articlePage = commentRepository.findAll(spec, pageable);

        List<CommentDTO> dtoList = articlePage.getContent().stream().map(this::toDTO).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, articlePage.getTotalElements());
    }

    public CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setArticleId(entity.getArticle().getId());
        dto.setProfileId(entity.getProfile().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public CommentEntity get(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Article not found"));
    }
}
