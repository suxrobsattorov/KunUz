package ok.suxrob.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ok.suxrob.dto.ProfileDTO;
import ok.suxrob.dto.auth.RegistrationDTO;
import ok.suxrob.dto.auth.AuthorizationDTO;
import ok.suxrob.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api(tags = "Auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "login method", notes = "qale", nickname = "ggg")
    public ResponseEntity<?> login(@RequestBody AuthorizationDTO dto) {
        ProfileDTO response = authService.authorization(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    @ApiOperation(value = "registration method", notes = "registration")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationDTO dto) {
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verification/{id}")
    @ApiOperation(value = "verification method", notes = "verification")
    public ResponseEntity<?> verification(@PathVariable("id") String  jwt) {
        authService.verification(jwt);
        return ResponseEntity.ok("Your status ACTIVE");
    }

}
