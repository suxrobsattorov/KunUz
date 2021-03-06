package ok.suxrob.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ok.suxrob.dto.LikeAndDislikeDTO;
import ok.suxrob.dto.LikeDTO;
import ok.suxrob.dto.ProfileJwtDTO;
import ok.suxrob.enums.LikeType;
import ok.suxrob.service.LikeService;
import ok.suxrob.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/like")
@Api(tags = "Like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    @ApiOperation(value = "like create method", notes = "like")
    public ResponseEntity<?> create(@RequestBody LikeDTO dto, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        LikeDTO response = likeService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/articleId/{id}")
    @ApiOperation(value = "like articleLikeAndDislikeCount method", notes = "like")
    public ResponseEntity<?> articleLikeAndDislikeCount(@PathVariable("id") Integer articleId,
                                                        @RequestParam("type") LikeType type) {
        LikeAndDislikeDTO count = likeService.articleLikeAndDislikeCount(articleId, type);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/commentId/{id}")
    @ApiOperation(value = "like commentLikeAndDislikeCount method", notes = "like")
    public ResponseEntity<?> commentLikeAndDislikeCount(@PathVariable("id") Integer commentId,
                                                        @RequestParam("type") LikeType type) {
        LikeAndDislikeDTO count = likeService.commentLikeAndDislikeCount(commentId, type);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/action/likedArticle/{id}")
    @ApiOperation(value = "like likedArticle method", notes = "like")
    public ResponseEntity<?> likedArticle(@PathVariable("id") Integer profileId, HttpServletRequest request) {
        JwtUtil.getProfile(request);
        List<String> articleLikedList = likeService.likedArticle(profileId);
        return ResponseEntity.ok(articleLikedList);
    }

    @PutMapping("/action/{id}")
    @ApiOperation(value = "like update method", notes = "like")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody LikeDTO dto, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        boolean result = likeService.update(jwtDTO.getId(), id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/action/{id}")
    @ApiOperation(value = "like delete method", notes = "like")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        boolean result = likeService.delete(jwtDTO.getId(), id);
        return ResponseEntity.ok(result);
    }
}
