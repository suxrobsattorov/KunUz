package ok.suxrob.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ok.suxrob.dto.ArticleDTO;
import ok.suxrob.dto.ProfileDTO;
import ok.suxrob.dto.ProfileJwtDTO;
import ok.suxrob.dto.filter.ArticleFilterDTO;
import ok.suxrob.enums.UserRole;
import ok.suxrob.service.ArticleService;
import ok.suxrob.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/article")
@Api(tags = "Article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/action")
    @ApiOperation(value = "article create method", notes = "article")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error", response = ProfileDTO.class),
            @ApiResponse(code = 404, message = "Service not found", response = ProfileDTO.class),
            @ApiResponse(code = 200, message = "Successful retrieval", response = ArticleDTO.class, responseContainer = "List")}
    )
    public ResponseEntity<?> create(@RequestBody ArticleDTO dto,
                                    HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, UserRole.MODERATOR);
        ArticleDTO response = articleService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filter")
    @ApiOperation(value = "article filter method", notes = "article")
    public ResponseEntity<?> filter(@RequestParam("page") int page,
                                    @RequestParam("size") int size,
                                    @RequestBody ArticleFilterDTO dto) {
        PageImpl<ArticleDTO> response = articleService.filterSpe(page, size, dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/publish/{id}")
    @ApiOperation(value = "article publish method", notes = "article")
    public ResponseEntity<?> publish(@PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, UserRole.PUBLISHER);
        articleService.publish(id, jwtDTO.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/action")
    @ApiOperation(value = "article getAll method", notes = "article")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        JwtUtil.getProfile(request);
        List<ArticleDTO> dtoList = articleService.getAll();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/action/{id}")
    @ApiOperation(value = "article getById method", notes = "article")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id, HttpServletRequest request) {
        JwtUtil.getProfile(request);
        ArticleDTO response = articleService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/action/{id}")
    @ApiOperation(value = "article update method", notes = "article")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, ArticleDTO dto, HttpServletRequest request) {
        JwtUtil.getProfile(request, UserRole.MODERATOR);
        boolean result = articleService.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/action/{id}")
    @ApiOperation(value = "article delete method", notes = "article")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        JwtUtil.getProfile(request, UserRole.PUBLISHER);
        boolean result = articleService.delete(id);
        return ResponseEntity.ok(result);
    }
}
