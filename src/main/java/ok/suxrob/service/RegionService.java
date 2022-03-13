package ok.suxrob.service;

import lombok.Data;
import ok.suxrob.dto.RegionDTO;
import ok.suxrob.entity.ProfileEntity;
import ok.suxrob.entity.RegionEntity;
import ok.suxrob.enums.UserRole;
import ok.suxrob.exceptions.BadRequestException;
import ok.suxrob.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ProfileService profileService;

    public RegionDTO create(RegionDTO dto, Integer adminId) {
        ProfileEntity profileEntity = profileService.get(adminId);
        if (dto.getName() == null || dto.getName().isEmpty() || profileEntity == null) {
            throw new BadRequestException("Region name can not be null");
        }
        RegionEntity entity = new RegionEntity();
        entity.setName(dto.getName());

        regionRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<RegionDTO> getAll() {
        List<RegionEntity> entityList = regionRepository.findAll();
        if (entityList.isEmpty()) {
            throw new BadRequestException("List can not be empity");
        }
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RegionDTO getById(Integer id) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("Region not found");
        }
        return toDTO(optional.get());
    }

    public boolean update(Integer adminId, Integer id, RegionDTO dto) {
        RegionEntity entity = regionRepository.update(id, dto.getName());
        if (entity == null || !isAdmin(adminId)) {
            throw new BadRequestException("Not update");
        }
        return true;
    }

    public boolean delete(Integer adminId, Integer id) {
        RegionEntity entity = regionRepository.delete(id);
        if (entity == null || !isAdmin(adminId)) {
            throw new BadRequestException("Not delete");
        }
        return true;
    }

    public boolean isAdmin(Integer id) {
        return profileService.getById(id).getUserRole().equals(UserRole.userRole(UserRole.ADMIN.toString()));
    }

    public RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
