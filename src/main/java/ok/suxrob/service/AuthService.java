package ok.suxrob.service;

import ok.suxrob.dto.ProfileDTO;
import ok.suxrob.dto.auth.RegistrationDTO;
import ok.suxrob.dto.auth.AuthorizationDTO;
import ok.suxrob.entity.ProfileEntity;
import ok.suxrob.enums.ProfileStatus;
import ok.suxrob.enums.UserRole;
import ok.suxrob.exceptions.BadRequestException;
import ok.suxrob.exceptions.ItemNotFoundException;
import ok.suxrob.repository.ProfileRepository;
import ok.suxrob.util.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailService emailService;

    public ProfileDTO authorization(AuthorizationDTO dto) {
        String pswd = DigestUtils.md5Hex(dto.getPassword());
        Optional<ProfileEntity> optional = profileRepository.
                findByLoginAndPswd(dto.getLogin(), dto.getPassword());

        if (!optional.isPresent()) {
            throw new RuntimeException("Login or Password incorrect");
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("You are not allowed");
        }
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(optional.get().getName());
        profileDTO.setSurname(optional.get().getSurname());
        profileDTO.setJwt(JwtUtil.createJwt(optional.get().getId(), optional.get().getRole()));
        return profileDTO;
    }

    public void registration(RegistrationDTO dto) {
        String emailCheck = profileRepository.emailCheck(dto.getEmail());
        String loginCheck = profileRepository.loginCheck(dto.getLogin());
        ProfileEntity entity = new ProfileEntity();
        Integer id = 0;
        if (emailCheck != null) {
            ProfileEntity profileEntity = profileRepository.gatByEmail(dto.getEmail());
            if (!profileEntity.getStatus().equals(ProfileStatus.CREATED)) {
                throw new BadRequestException("Email oldindan bor");
            }
            id = profileEntity.getId();
        }
        if (loginCheck != null && id == 0) {
            throw new BadRequestException("Login or email oldindan bor");
        }
        if (id != 0) {
            entity.setId(id);
        }
        String pswd = DigestUtils.md5Hex(dto.getPassword());

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setLogin(dto.getLogin());
        entity.setPswd(pswd);
        entity.setRole(UserRole.USER);
        entity.setStatus(ProfileStatus.CREATED);
        profileRepository.save(entity);

        String jwt = JwtUtil.createJwt(entity.getId());
        emailService.sendEmail(dto.getEmail(), jwt);
    }

    public void verification(String jwt) {
        Integer id = JwtUtil.decodeJwtAndGetId(jwt);
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ItemNotFoundException("User not found");
        }
        if (optional.get().getStatus().equals(ProfileStatus.CREATED)) {
            optional.get().setStatus(ProfileStatus.ACTIVE);
            emailService.updateEmailHistory();
            profileRepository.save(optional.get());
        } else {
            throw new ItemNotFoundException("User not created");
        }
    }
}
