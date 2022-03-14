package ok.suxrob.controller;

<<<<<<< HEAD
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
import lombok.Data;
import ok.suxrob.dto.ArticleDTO;
import ok.suxrob.dto.ProfileDTO;
import ok.suxrob.dto.ProfileJwtDTO;
import ok.suxrob.dto.filter.ArticleFilterDTO;
import ok.suxrob.dto.filter.ProfileFilterDTO;
import ok.suxrob.enums.UserRole;
import ok.suxrob.service.ProfileService;
import ok.suxrob.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/profile")
<<<<<<< HEAD
@Api(tags = "Profile")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/action")
<<<<<<< HEAD
    @ApiOperation(value = "profile create method", notes = "profile")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> createProfile(@RequestBody ProfileDTO dto, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, UserRole.ADMIN);
        ProfileDTO response = profileService.createProfileAdmin(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filter")
<<<<<<< HEAD
    @ApiOperation(value = "profile filter method", notes = "profile")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> filter(@RequestParam("page") int page,
                                    @RequestParam("size") int size,
                                    @RequestBody ProfileFilterDTO dto) {
        PageImpl<ProfileDTO> response = profileService.filterSpe(page, size, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/action")
<<<<<<< HEAD
    @ApiOperation(value = "profile getAll method", notes = "profile")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        JwtUtil.getProfile(request);
        List<ProfileDTO> dtoList = profileService.getAll();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/action/{id}")
<<<<<<< HEAD
    @ApiOperation(value = "profile getById method", notes = "profile")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        ProfileDTO dto = profileService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.badRequest().body("Profile not found");
    }

    @PutMapping("/action/{id}")
<<<<<<< HEAD
    @ApiOperation(value = "profile update method", notes = "profile")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> update(@PathVariable("id") Integer userId, @RequestBody ProfileDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.getProfile(request, UserRole.ADMIN);
        profileService.update(userId, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/action/{id}")
<<<<<<< HEAD
    @ApiOperation(value = "profile delete method", notes = "profile")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> delete(@PathVariable("id") Integer userId,
                                    HttpServletRequest request) {
        JwtUtil.getProfile(request, UserRole.ADMIN);
        boolean result = profileService.delete(userId);
        return ResponseEntity.ok(result);
    }

    // registration
    // authorization
}
