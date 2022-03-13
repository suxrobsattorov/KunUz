package ok.suxrob.controller;

import ok.suxrob.dto.ArticleDTO;
import ok.suxrob.dto.ProfileJwtDTO;
import ok.suxrob.dto.filter.ArticleFilterDTO;
import ok.suxrob.enums.UserRole;
import ok.suxrob.service.ArticleService;
import ok.suxrob.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/action")
    public ResponseEntity<?> create(@RequestBody ArticleDTO dto,
                                    HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, UserRole.MODERATOR);
        ArticleDTO response = articleService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam("page") int page,
                                    @RequestParam("size") int size,
                                    @RequestBody ArticleFilterDTO dto) {
        PageImpl<ArticleDTO> response = articleService.filterSpe(page, size, dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/publish/{id}")
    public ResponseEntity<?> publish(@PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, UserRole.PUBLISHER);
        articleService.publish(id, jwtDTO.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/action")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        JwtUtil.getProfile(request);
        List<ArticleDTO> dtoList = articleService.getAll();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/action/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id, HttpServletRequest request) {
        JwtUtil.getProfile(request);
        ArticleDTO response = articleService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/action/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, ArticleDTO dto, HttpServletRequest request) {
        JwtUtil.getProfile(request, UserRole.MODERATOR);
        boolean result = articleService.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/action/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        JwtUtil.getProfile(request, UserRole.PUBLISHER);
        boolean result = articleService.delete(id);
        return ResponseEntity.ok(result);
    }
}
