package ok.suxrob.controller;

<<<<<<< HEAD
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
import ok.suxrob.dto.ProfileJwtDTO;
import ok.suxrob.dto.RegionDTO;
import ok.suxrob.enums.UserRole;
import ok.suxrob.service.RegionService;
import ok.suxrob.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/region")
<<<<<<< HEAD
@Api(tags = "Region")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping
<<<<<<< HEAD
    @ApiOperation(value = "region create method", notes = "region")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> create(@RequestBody RegionDTO dto, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, UserRole.ADMIN);
        RegionDTO response = regionService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
<<<<<<< HEAD
    @ApiOperation(value = "region getAll method", notes = "region")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> getAll() {
        List<RegionDTO> dtoList = regionService.getAll();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
<<<<<<< HEAD
    @ApiOperation(value = "region getById method", notes = "region")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        RegionDTO dto = regionService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
<<<<<<< HEAD
    @ApiOperation(value = "region update method", notes = "region")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> update(@PathVariable("id") Integer id, RegionDTO dto, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, UserRole.ADMIN);
        boolean result = regionService.update(jwtDTO.getId(), id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
<<<<<<< HEAD
    @ApiOperation(value = "region delete method", notes = "region")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, UserRole.ADMIN);
        boolean result = regionService.delete(jwtDTO.getId(), id);
        return ResponseEntity.ok(result);
    }
}
