package ok.suxrob.service;

import ok.suxrob.dto.ArticleDTO;
import ok.suxrob.dto.ProfileDTO;
import ok.suxrob.dto.filter.ArticleFilterDTO;
import ok.suxrob.dto.filter.ProfileFilterDTO;
import ok.suxrob.entity.ArticleEntity;
import ok.suxrob.entity.ProfileEntity;
import ok.suxrob.enums.ArticleStatus;
import ok.suxrob.enums.ProfileStatus;
import ok.suxrob.enums.UserRole;
import ok.suxrob.exceptions.BadRequestException;
import ok.suxrob.repository.ProfileRepository;
import ok.suxrob.repository.custom.ProfileCustomRepository;
import ok.suxrob.specification.ProfileSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCustomRepository customRepository;

    public ProfileDTO createProfileAdmin(ProfileDTO dto, Integer adminId) {
        if (!isAdmin(adminId)) {
            throw new BadRequestException("Admin id not found");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setLogin(dto.getLogin());
        entity.setPswd(dto.getPswd());
        entity.setRole(dto.getUserRole());
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<ProfileDTO> getAll() {
        List<ProfileEntity> entityList = profileRepository.findAll();
        if (entityList.isEmpty()) {
            throw new BadRequestException("Profiles not found");
        }
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ProfileDTO getById(Integer id) {
        Optional<ProfileEntity> entity = profileRepository.findById(id);
        return entity.map(this::toDTO).orElse(null);
    }

    public boolean update(Integer id, ProfileDTO dto) {
        ProfileEntity entity = get(id);
        if (entity == null) {
            throw new BadRequestException("Not update");
        }
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLogin(dto.getLogin());
        entity.setPswd(dto.getPswd());
        profileRepository.save(entity);
        return true;
    }

    public boolean delete(Integer id) {
        if (id == 0) {
            throw new BadRequestException("Not delete");
        }
        profileRepository.deleteById(id);
        return true;
    }

    public boolean isAdmin(Integer id) {
        return getById(id).getUserRole().equals(UserRole.userRole(UserRole.ADMIN.toString()));
    }


    public PageImpl<ProfileDTO> filterSpe(int page, int size, ProfileFilterDTO dto) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Specification<ProfileEntity> spec = null;
        if (dto.getStatus() != null) {
            spec = Specification.where(ProfileSpecification.role(dto.getRole()));
        } else {
            spec = Specification.where(ProfileSpecification.role(UserRole.USER));
        }

        if (dto.getName() != null) {
            spec.and(ProfileSpecification.nom("name",dto.getName()));
        }
        if (dto.getSurname() != null) {
            spec.and(ProfileSpecification.nom("surname",dto.getSurname()));
        }
        if (dto.getEmail() != null) {
            spec.and(ProfileSpecification.nom("email",dto.getEmail()));
        }
        if (dto.getId() != null) {
            spec.and(ProfileSpecification.id(dto.getId()));
        }
        if (dto.getStatus() != null) {
            spec.and(ProfileSpecification.status(dto.getStatus()));
        }

        Page<ProfileEntity> profilePage = profileRepository.findAll(spec, pageable);

        List<ProfileDTO> dtoList = profilePage.getContent().stream().map(this::toDTO).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, profilePage.getTotalElements());
    }

    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setLogin(entity.getLogin());
        dto.setPswd(entity.getPswd());
        dto.setUserRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
    }

}
