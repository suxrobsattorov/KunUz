package ok.suxrob.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ok.suxrob.dto.CommentDTO;
import ok.suxrob.dto.ProfileJwtDTO;
import ok.suxrob.enums.UserRole;
import ok.suxrob.service.CommentService;
import ok.suxrob.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Api(tags = "Comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/action")
    @ApiOperation(value = "comment create method", notes = "comment")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        CommentDTO response = commentService.create(jwtDTO.getId(), dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/action/{id}")
    @ApiOperation(value = "comment getById method", notes = "comment")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id, HttpServletRequest request) {
        JwtUtil.getProfile(request);
        CommentDTO dto = commentService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/profile/{id}")
    @ApiOperation(value = "comment getByProfileId method", notes = "comment")
    public ResponseEntity<?> getByProfileId(@PathVariable("id") Integer id) {
        List<CommentDTO> dtoList = commentService.getByProfileId(id);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/article/{id}")
    @ApiOperation(value = "comment getByArticleId method", notes = "comment")
    public ResponseEntity<?> getByArticleId(@PathVariable("id") Integer id) {
        List<CommentDTO> dtoList = commentService.getByArticleId(id);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/commentByAdmin")
    @ApiOperation(value = "comment getAll method", notes = "comment")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, UserRole.ADMIN);
        List<CommentDTO> dtoList = commentService.getAll(jwtDTO.getId());
        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/action/{id}")
    @ApiOperation(value = "comment update method", notes = "comment")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CommentDTO dto, HttpServletRequest request) {
        JwtUtil.getProfile(request);
        boolean result = commentService.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/action/{id}")
    @ApiOperation(value = "comment delete method", notes = "comment")
    public ResponseEntity<?> delete(@PathVariable Integer id, HttpServletRequest request) {
        JwtUtil.getProfile(request);
        boolean result = commentService.delete(id);
        return ResponseEntity.ok(result);
    }
}
